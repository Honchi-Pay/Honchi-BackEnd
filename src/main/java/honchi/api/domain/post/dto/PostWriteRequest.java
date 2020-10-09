package honchi.api.domain.post.dto;

import honchi.api.domain.post.domain.enums.Category;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PostWriteRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private Category category;

    @NotBlank
    private String item;

    private MultipartFile[] images;
}
