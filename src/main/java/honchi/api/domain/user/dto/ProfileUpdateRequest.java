package honchi.api.domain.user.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ProfileUpdateRequest {

    @NotBlank
    private String nickName;

    private MultipartFile profileImage;
}
