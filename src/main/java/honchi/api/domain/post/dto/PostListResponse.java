package honchi.api.domain.post.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostListResponse {

    private Integer postId;

    private String title;

    private String writer;

    private String address;

    private String image;

    private LocalDateTime createdAt;
}
