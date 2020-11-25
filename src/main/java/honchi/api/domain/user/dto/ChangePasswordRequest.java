package honchi.api.domain.user.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class ChangePasswordRequest {

    @NotBlank
    private String password;
}
