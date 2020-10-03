package honchi.api.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String adminEmail;

    @Override
    @Async
    public void sendEmail(String email, String code) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(adminEmail);
        mailMessage.setTo(email);
        mailMessage.setSubject("혼치페이 이메일 인증입니다.");
        mailMessage.setText("계정 인증을 위한 코드는 " + code + "입니다.");
        javaMailSender.send(mailMessage);
    }
}
