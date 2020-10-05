package honchi.api.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class StarRequest {

    @NotBlank
    private Integer targetId;

    @NotBlank
    private Double star;
}
