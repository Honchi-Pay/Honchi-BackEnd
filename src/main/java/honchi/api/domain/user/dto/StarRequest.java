package honchi.api.domain.user.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class StarRequest {

    @NotNull
    private Integer targetId;

    @NotNull
    private Double star;
}
