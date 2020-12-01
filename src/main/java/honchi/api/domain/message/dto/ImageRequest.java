package honchi.api.domain.message.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ImageRequest {

    @NotBlank
    private String chatId;

    @NotNull
    private MultipartFile image;
}
