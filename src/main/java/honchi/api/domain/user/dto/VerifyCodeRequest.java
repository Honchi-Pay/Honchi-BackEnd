package honchi.api.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyCodeRequest {

    private String email;

    private String code;
}
