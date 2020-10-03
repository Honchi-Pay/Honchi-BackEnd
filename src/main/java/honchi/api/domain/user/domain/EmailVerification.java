package honchi.api.domain.user.domain;

import honchi.api.domain.user.domain.enums.EmailVerificationStatus;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Builder
@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class EmailVerification {

    @Id
    private String email;

    private String code;

    private EmailVerificationStatus status;
}
