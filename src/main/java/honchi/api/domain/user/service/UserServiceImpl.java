package honchi.api.domain.user.service;

import honchi.api.domain.auth.exception.ExpiredTokenException;
import honchi.api.domain.user.domain.EmailVerification;
import honchi.api.domain.user.domain.Star;
import honchi.api.domain.user.domain.User;
import honchi.api.domain.user.domain.UserImage;
import honchi.api.domain.user.domain.enums.EmailVerificationStatus;
import honchi.api.domain.user.domain.repository.EmailVerificationRepository;
import honchi.api.domain.user.domain.repository.ImageRepository;
import honchi.api.domain.user.domain.repository.StarRepository;
import honchi.api.domain.user.domain.repository.UserRepository;
import honchi.api.domain.user.dto.*;
import honchi.api.domain.user.exception.*;
import honchi.api.global.config.security.AuthenticationFacade;
import honchi.api.global.error.exception.BadRequestException;
import honchi.api.global.error.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final StarRepository starRepository;
    private final ImageRepository imageRepository;
    private final EmailVerificationRepository verificationRepository;

    private final EmailService emailService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationFacade authenticationFacade;

    @Value("${image.upload.dir}")
    private String imageDirPath;

    @Override
    public void join(SignUpRequest signUpRequest) {
        if (userRepository.findByEmail(signUpRequest.getEmail()).isPresent())
            throw new UserAlreadyExistException();

        String password = passwordEncoder.encode(signUpRequest.getPassword());

        userRepository.save(
                User.builder()
                        .email(signUpRequest.getEmail())
                        .password(password)
                        .nickName(signUpRequest.getNickName())
                        .phoneNumber(signUpRequest.getPhoneNumber())
                        .sex(signUpRequest.getSex())
                        .build()
        );
    }

    @Override
    public void sendEmail(String email) {
        EmailVerification emailVerification = verificationRepository.findByEmail(email).orElse(null);

        String code = randomCode();

        emailService.sendEmail(email, code);

        if(emailVerification != null) {
            emailVerification.setCode(code);

            verificationRepository.save(emailVerification);
        } else {
            verificationRepository.save(
                    EmailVerification.builder()
                            .email(email)
                            .code(code)
                            .status(EmailVerificationStatus.UNVERIFIED)
                            .build()
            );
        }
    }

    @Override
    public void verifyEmail(VerifyCodeRequest verifyCodeRequest) {
        String email = verifyCodeRequest.getEmail();
        String code = verifyCodeRequest.getCode();

        EmailVerification emailVerification = verificationRepository.findByEmail(email)
                .orElseThrow(InvalidAuthEmailException::new);

        if(!emailVerification.getCode().equals(code))
            throw new InvalidAuthCodeException();

        if(emailVerification.getStatus().equals(EmailVerificationStatus.UNVERIFIED)) {
            emailVerification.setStatus(EmailVerificationStatus.VERIFIED);

            verificationRepository.save(emailVerification);
        }
    }

    @Override
    public void findPassword(FindPasswordRequest findPasswordRequest) {
        User user = userRepository.findByEmail(findPasswordRequest.getEmail())
                .orElseThrow(UserNotFoundException::new);

        user.setPassword(findPasswordRequest.getEmail());

        userRepository.save(user);
    }

    @Override
    public void chargePassword(ChargePasswordRequest chargePasswordRequest) {
        User user = userRepository.findByEmail(ExpiredToken(authenticationFacade.getUserEmail()))
                .orElseThrow(UserNotFoundException::new);

        if (passwordEncoder.matches(chargePasswordRequest.getPassword(), user.getPassword()))
            throw new PasswordSameException();

        user.setPassword(passwordEncoder.encode(chargePasswordRequest.getPassword()));

        userRepository.save(user);
    }

    @Override
    public ProfileResponse getProfile(Integer user_id) {
        User profile = userRepository.findById(user_id).orElseThrow(UserNotFoundException::new);

        User user = userRepository.findByEmail(ExpiredToken(authenticationFacade.getUserEmail()))
                .orElseThrow(UserNotFoundException::new);

        UserImage image = imageRepository.findByUserId(profile.getId());

        double star = 0.0;

        if(starRepository.findByTargetId(profile.getId()).isPresent()) {
            star = (double) (Math.round((starRepository.sumStar(user_id)/
                    starRepository.countByTargetId(user_id)) *10)/10);
        }

        return ProfileResponse.builder()
                .email(profile.getEmail())
                .nickName(profile.getNickName())
                .sex(profile.getSex())
                .star(star)
                .image(image.getImageName())
                .mine(user.getId().equals(profile.getId()))
                .build();
    }

    @SneakyThrows
    @Override
    public void updateProfile(ProfileUpdateRequest profileUpdateRequest) {
        User user = userRepository.findByEmail(ExpiredToken(authenticationFacade.getUserEmail()))
                .orElseThrow(UserNotFoundException::new);

        if(profileUpdateRequest.getProfileImage() != null) {
            String imageName = UUID.randomUUID().toString();

            if(user.getImage() != null) {
                UserImage profile = imageRepository.findByUserId(user.getId());

                new File(imageDirPath, profile.getImageName()).delete();

                profile.setImageName(imageName);

                imageRepository.save(profile);
            } else {
                imageRepository.save(
                        UserImage.builder()
                                .userId(user.getId())
                                .imageName(imageName)
                                .build()
                );
            }
            profileUpdateRequest.getProfileImage().transferTo(new File(imageDirPath, imageName));

            UserImage image = imageRepository.findByImageName(imageName);

            user.setImage(image);
        }

        user.setNickName(profileUpdateRequest.getNickName());

        userRepository.save(user);

    }

    @Override
    public void star(StarRequest starRequest) {
        User user = userRepository.findByEmail(ExpiredToken(authenticationFacade.getUserEmail()))
                .orElseThrow(UserNotFoundException::new);

        User profile = userRepository.findById(starRequest.getTargetId())
                .orElseThrow(UserNotFoundException::new);

        if(user.getId().equals(profile.getId())) throw new UserSameException();

        if(starRequest.getTargetId() == null || starRequest.getStar() == null ||
                starRequest.getStar() > 5 || starRequest.getStar() < 1)
            throw new BadRequestException();

        Star star = new Star();

        if(starRepository.findByTargetId(profile.getId()).isPresent()) {
            star = starRepository.findByTargetId(profile.getId())
                    .orElseThrow(UserNotFoundException::new);
        }

        star.setStar(starRequest.getStar());

        starRepository.save(
                Star.builder()
                .userId(user.getId())
                .targetId(profile.getId())
                .star(star.getStar())
                .build());
    }

    private String ExpiredToken(String email) {
        if (email.equals("anonymousUser")) throw new ExpiredTokenException();
        return email;
    }

    private String randomCode() {
        String[] codes = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
                "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
                "Y", "Z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};

        Random random = new Random(System.currentTimeMillis());
        int tablelength = codes.length;
        StringBuffer buf = new StringBuffer();

        for (int i = 0; i < 6; i++) {
            buf.append(codes[random.nextInt(tablelength)]);
        }

        return buf.toString();
    }
}
