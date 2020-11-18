package honchi.api.domain.message.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ImageRequest {

    private Integer chatId;

    private MultipartFile image;
}
