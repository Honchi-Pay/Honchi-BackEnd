package honchi.api.domain.user.service;

import honchi.api.domain.user.dto.VerifyCodeRequest;

public interface EmailService {

    void sendEmail(String email);
    void verifyEmail(VerifyCodeRequest verifyCodeRequest);
}
