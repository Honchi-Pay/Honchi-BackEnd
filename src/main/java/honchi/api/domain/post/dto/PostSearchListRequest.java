package honchi.api.domain.post.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PostSearchListRequest {

    @NotBlank
    private String title;

    @NotNull
    private int dist;
}
