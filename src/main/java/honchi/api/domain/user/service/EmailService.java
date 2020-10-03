package honchi.api.domain.user.service;

public interface EmailService {

    void sendEmail(String email, String code);
}
