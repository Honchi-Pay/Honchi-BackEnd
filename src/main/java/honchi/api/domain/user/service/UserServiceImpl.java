package honchi.api.domain.user.service;

import honchi.api.domain.auth.exception.ExpiredTokenException;
import honchi.api.domain.user.domain.Star;
import honchi.api.domain.user.domain.User;
import honchi.api.domain.user.domain.repository.StarRepository;
import honchi.api.domain.user.domain.repository.UserRepository;
import honchi.api.domain.user.dto.ChargePasswordRequest;
import honchi.api.domain.user.dto.ProfileResponse;
import honchi.api.domain.user.dto.SignUpRequest;
import honchi.api.domain.user.dto.StarRequest;
import honchi.api.domain.user.exception.PasswordSameException;
import honchi.api.domain.user.exception.UserAlreadyExistException;
import honchi.api.domain.user.exception.UserSameException;
import honchi.api.global.config.security.AuthenticationFacade;
import honchi.api.global.error.exception.BadRequestException;
import honchi.api.global.error.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final StarRepository starRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationFacade authenticationFacade;

    @Override
    public void join(SignUpRequest signUpRequest) {
        if (userRepository.findByEmail(signUpRequest.getEmail()).isPresent()) throw new UserAlreadyExistException();

        String password = passwordEncoder.encode(signUpRequest.getPassword());

        userRepository.save(
                User.builder()
                        .email(signUpRequest.getEmail())
                        .password(password)
                        .nick_name(signUpRequest.getNick_name())
                        .phone_number(signUpRequest.getPhone_number())
                        .sex(signUpRequest.getSex())
                        .build()
        );
    }

    @Override
    public void chargePassword(ChargePasswordRequest chargePasswordRequest) {
        User user = userRepository.findByEmail(ExpiredToken(authenticationFacade.getUserEmail()))
                .orElseThrow(UserNotFoundException::new);

        if (passwordEncoder.matches(chargePasswordRequest.getPassword(), user.getPassword())) {
            throw new PasswordSameException();
        }

        user.setPassword(passwordEncoder.encode(chargePasswordRequest.getPassword()));

        userRepository.save(user);
    }

    @Override
    public ProfileResponse getProfile(Integer user_id) {
        User profile = userRepository.findById(user_id).orElseThrow(UserNotFoundException::new);

        User user = userRepository.findByEmail(ExpiredToken(authenticationFacade.getUserEmail()))
                .orElseThrow(UserNotFoundException::new);

        Star star = starRepository.findByStarredUserId(profile.getId())
                .orElseThrow(UserNotFoundException::new);

        return ProfileResponse.builder()
                .email(profile.getEmail())
                .nick_name(profile.getNick_name())
                .sex(profile.getSex())
                .star(star.getStar())
                .mine(user.getId().equals(profile.getId()))
                .build();
    }

    @Override
    public void star(StarRequest starRequest) {
        User user = userRepository.findByEmail(ExpiredToken(authenticationFacade.getUserEmail()))
                .orElseThrow(UserNotFoundException::new);

        User profile = userRepository.findById(starRequest.getUser_id())
                .orElseThrow(UserNotFoundException::new);

        Star stars = starRepository.findByStarredUserId(profile.getId())
                .orElseThrow(UserAlreadyExistException::new);

        if(user.getId().equals(profile.getId())) throw new UserSameException();

        if(starRequest.getUser_id() == null || starRequest.getStar() == null ||
                starRequest.getStar() > 5 || starRequest.getStar() < 1)
            throw new BadRequestException();

        if(stars.getStar() == null) {
            stars.setStar(starRequest.getStar());
        } else {
            if(starRepository.findByStarredUserId(starRequest.getUser_id()).isPresent())
                stars.setStar(starRequest.getStar());
            else
                stars.setStar(stars.getStar() + starRequest.getStar());
        }

        starRepository.save(
                Star.builder()
                .userId(user.getId())
                .starredUserId(profile.getId())
                .star(stars.getStar())
                .build());
    }

    private String ExpiredToken(String email) {
        if (email.equals("anonymousUser")) throw new ExpiredTokenException();
        return email;
    }
}
