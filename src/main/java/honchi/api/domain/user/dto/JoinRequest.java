package honchi.api.domain.user.dto;

import honchi.api.domain.user.domain.enums.Sex;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class JoinRequest {

    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String nick_name;

    @NotBlank
    private String phone_number;

    private Sex gender;
}
