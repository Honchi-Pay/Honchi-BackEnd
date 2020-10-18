package honchi.api.domain.user.controller;

import honchi.api.domain.user.dto.VerifyCodeRequest;
import honchi.api.domain.user.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;

@RestController
@RequestMapping("/email/verify")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping
    public void sendEmail(@RequestParam("email") @Email String email) {
        emailService.sendEmail(email);
    }

    @PutMapping
    public void verifyEmail(@RequestBody @Valid VerifyCodeRequest verifyCodeRequest) {
        emailService.verifyEmail(verifyCodeRequest);
    }
}
