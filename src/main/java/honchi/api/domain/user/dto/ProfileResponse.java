package honchi.api.domain.user.dto;

import honchi.api.domain.user.domain.enums.Sex;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class ProfileResponse {

    private String email;

    private String nickName;

    private Sex sex;

    private Double star;

    private Boolean mine;

    private MultipartFile images;
}
