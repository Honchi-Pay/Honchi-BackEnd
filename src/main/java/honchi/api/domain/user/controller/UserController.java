package honchi.api.domain.user.controller;

import honchi.api.domain.user.dto.*;
import honchi.api.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public void join(@RequestBody @Valid SignUpRequest signUpRequest) {
        userService.join(signUpRequest);
    }

    @PostMapping("/email/verify")
    public void sendEmail(@RequestParam("email") @Email String email) {
        userService.sendEmail(email);
    }

    @PutMapping("/email/verify")
    public void verifyEmail(@RequestBody @Valid VerifyCodeRequest verifyCodeRequest) {
        userService.verifyEmail(verifyCodeRequest);
    }

    @PostMapping("/password")
    public void findPassword(@RequestBody @Valid FindPasswordRequest findPasswordRequest) {
        userService.findPassword(findPasswordRequest);
    }

    @PutMapping("/password")
    public void chargePassword(@RequestBody @Valid ChargePasswordRequest chargePasswordRequest) {
        userService.chargePassword(chargePasswordRequest);
    }

    @GetMapping("/{user_id}")
    public ProfileResponse getProfile(@PathVariable Integer user_id) {
        return userService.getProfile(user_id);
    }

    @PutMapping
    public void updateProfile(@ModelAttribute @Valid ProfileUpdateRequest profileUpdateRequest) {
        userService.updateProfile(profileUpdateRequest);
    }

    @PostMapping("/star")
    public void star(@RequestBody @Valid StarRequest starRequest) {
        userService.star(starRequest);
    }
}
