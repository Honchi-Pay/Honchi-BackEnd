package honchi.api.domain.user.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Builder
@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
@IdClass(StarPK.class)
public class Star implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Integer userId;

    @Id
    private Integer targetId;

    private Double star;

    public void setStar(double star) {
        this.star = star;
    }
}
