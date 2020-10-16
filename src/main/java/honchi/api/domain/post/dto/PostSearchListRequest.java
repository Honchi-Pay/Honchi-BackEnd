package honchi.api.domain.post.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class PostSearchListRequest {

    private String title;

    private double lon;

    private double lat;

    @NotNull
    private int dist;
}
