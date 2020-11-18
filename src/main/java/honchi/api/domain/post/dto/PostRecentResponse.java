package honchi.api.domain.post.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostRecentResponse {

    private Integer postId;

    private String title;

    private String writer;

    private String item;

    private String image;

    private Double lat;

    private Double lon;

    private LocalDateTime createdAt;
}
