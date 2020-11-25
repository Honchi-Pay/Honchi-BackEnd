package honchi.api.domain.auth.service;

import honchi.api.domain.auth.dto.SignInRequest;
import honchi.api.domain.auth.dto.TokenResponse;
import honchi.api.domain.auth.exception.InvalidTokenException;
import honchi.api.domain.user.domain.User;
import honchi.api.domain.user.domain.repository.UserRepository;
import honchi.api.global.config.security.JwtTokenProvider;
import honchi.api.global.error.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    @Value("${auth.jwt.prefix}")
    private String prefix;

    @Override
    public TokenResponse login(SignInRequest signInRequest) {
        User user = userRepository.findByEmail(signInRequest.getEmail())
                .filter(u -> passwordEncoder.matches(signInRequest.getPassword(), u.getPassword()))
                .orElseThrow(UserNotFoundException::new);

        return responseToken(user.getEmail());
    }

    @Override
    public TokenResponse refreshToken(String token) {
        if(!jwtTokenProvider.isRefreshToken(token)) throw new InvalidTokenException();

        return responseToken(jwtTokenProvider.getUserEmail(token));
    }

    private TokenResponse responseToken(String email) {
        return TokenResponse.builder()
                .accessToken(jwtTokenProvider.generateAccessToken(email))
                .refreshToken(jwtTokenProvider.generateRefreshToken(email))
                .tokenType(prefix)
                .build();
    }
}
