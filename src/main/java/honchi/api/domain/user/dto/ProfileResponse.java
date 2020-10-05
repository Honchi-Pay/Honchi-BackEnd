package honchi.api.domain.user.dto;

import honchi.api.domain.user.domain.enums.Sex;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProfileResponse {

    private String email;

    private String nickName;

    private Sex sex;

    private Double star;

    private Boolean mine;

    private String image;
}
