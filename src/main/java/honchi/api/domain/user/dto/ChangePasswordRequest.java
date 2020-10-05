package honchi.api.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ChangePasswordRequest {

    @NotBlank
    private String password;
}
