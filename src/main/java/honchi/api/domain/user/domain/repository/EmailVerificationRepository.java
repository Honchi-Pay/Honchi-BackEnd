package honchi.api.domain.user.domain.repository;

import honchi.api.domain.user.domain.EmailVerification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailVerificationRepository extends CrudRepository<EmailVerification, String> {
}
