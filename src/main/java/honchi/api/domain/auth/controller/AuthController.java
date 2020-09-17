package honchi.api.domain.auth.controller;

import honchi.api.domain.auth.dto.SignInRequest;
import honchi.api.domain.auth.dto.TokenResponse;
import honchi.api.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public TokenResponse login(@RequestBody @Valid SignInRequest signInRequest) {
        return authService.login(signInRequest);
    }

    @PutMapping
    public TokenResponse refreshToken(@RequestHeader("X-Refresh-Token") String token) {
        return authService.refreshToken(token);
    }
}
