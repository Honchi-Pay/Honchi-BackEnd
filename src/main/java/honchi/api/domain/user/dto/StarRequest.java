package honchi.api.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StarRequest {

    private Integer user_id;

    private Double star;
}
