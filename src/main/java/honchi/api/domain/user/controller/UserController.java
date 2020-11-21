package honchi.api.domain.user.controller;

import honchi.api.domain.user.dto.*;
import honchi.api.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/alone")
    public void alone(@RequestParam("email") @Valid String email) {
        userService.alone(email);
    }

    @PostMapping
    public void join(@RequestBody @Valid SignUpRequest signUpRequest) {
        userService.join(signUpRequest);
    }

    @GetMapping("/profile")
    public ProfileResponse getProfile(@RequestParam("nickName") @Valid String nickName) {
        return userService.getProfile(nickName);
    }

    @PutMapping("/password/find")
    public void findPassword(@RequestBody @Valid FindPasswordRequest findPasswordRequest) {
        userService.findPassword(findPasswordRequest);
    }

    @PutMapping("/password/change")
    public void changePassword(@RequestBody @Valid ChangePasswordRequest changePasswordRequest) {
        userService.changePassword(changePasswordRequest);
    }

    @PutMapping("/profile")
    public void updateProfile(@ModelAttribute @Valid ProfileUpdateRequest profileUpdateRequest) {
        userService.updateProfile(profileUpdateRequest);
    }

    @PutMapping("/star")
    public void star(@RequestBody @Valid StarRequest starRequest) {
        userService.star(starRequest);
    }


    @DeleteMapping("/profile")
    public void deleteUser(@RequestParam("nickName") @Valid String nickName) {
        userService.deleteUser(nickName);
    }
}
