package honchi.api.domain.user.service;

import honchi.api.domain.user.domain.User;
import honchi.api.domain.user.domain.enums.Gender;
import honchi.api.domain.user.domain.repository.UserRepository;
import honchi.api.domain.user.dto.JoinRequest;
import honchi.api.domain.user.exception.UserAlreadyExistException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void join(JoinRequest joinRequest) {
        if(userRepository.findByEmail(joinRequest.getEmail()).isPresent()) throw new UserAlreadyExistException();

        String password = passwordEncoder.encode(joinRequest.getPassword());

        userRepository.save(User.builder()
                .email(joinRequest.getEmail())
                .password(password)
                .nick_name(joinRequest.getNick_name())
                .phone_number(joinRequest.getPhone_number())
                .gender(joinRequest.getGender().equals(Gender.Male) ? Gender.Male : Gender.Female)
                .build());
    }
}
