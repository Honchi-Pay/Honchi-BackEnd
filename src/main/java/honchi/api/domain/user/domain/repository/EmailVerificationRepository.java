package honchi.api.domain.user.domain.repository;

import honchi.api.domain.user.domain.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailVerificationRepository extends JpaRepository<EmailVerification, String> {
    Optional<EmailVerification> findByEmail (String email);
}
