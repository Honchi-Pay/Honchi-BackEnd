package honchi.api.domain.post.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PostListRequest {

    private double lon;

    private double lat;

    @NotNull
    private int dist;
}
