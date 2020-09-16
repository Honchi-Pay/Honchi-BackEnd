package honchi.api.domain.user.controller;

import honchi.api.domain.user.dto.SignUpRequest;
import honchi.api.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/")
    public void join(@RequestBody @Valid SignUpRequest signUpRequest) {
        userService.join(signUpRequest);
    }
}
