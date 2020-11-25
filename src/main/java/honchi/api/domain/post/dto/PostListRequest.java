package honchi.api.domain.post.dto;

import honchi.api.domain.post.domain.enums.Category;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PostListRequest {

    @NotNull
    private Category category;

    @NotBlank
    private String item;

    @NotNull
    private Integer dist;
}
