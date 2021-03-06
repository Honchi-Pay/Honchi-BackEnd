package honchi.api.domain.user.dto;

import honchi.api.domain.user.domain.enums.Sex;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class SignUpRequest {

    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String nickName;

    @NotBlank
    private String phoneNumber;

    @NotNull
    private Sex sex;

    @NotNull
    private Double lat;

    @NotNull
    private Double lon;
}
