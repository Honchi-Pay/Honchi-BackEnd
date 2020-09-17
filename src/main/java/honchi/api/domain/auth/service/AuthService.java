package honchi.api.domain.auth.service;

import honchi.api.domain.auth.dto.SignInRequest;
import honchi.api.domain.auth.dto.TokenResponse;

public interface AuthService {

    TokenResponse login(SignInRequest signInRequest);
    TokenResponse refreshToken(String token);
}
