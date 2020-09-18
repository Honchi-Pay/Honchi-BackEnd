package honchi.api.domain.user.controller;

import honchi.api.domain.user.dto.ProfileResponse;
import honchi.api.domain.user.dto.SignUpRequest;
import honchi.api.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public void join(@RequestBody @Valid SignUpRequest signUpRequest) {
        userService.join(signUpRequest);
    }

    @GetMapping("/{user_id}")
    public ProfileResponse getProfile(@PathVariable Integer user_id) {
        return userService.getProfile(user_id);
    }
}
