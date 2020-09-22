package honchi.api.domain.user.domain;

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
public class Star {

    @Id
    private Integer user_id;

    @Id
    private Integer starred_user_id;
}
