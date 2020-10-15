package honchi.api.domain.post.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class PostContentResponse {

    private String title;

    private String content;

    private String writer;

    private List<String> images;

    private LocalDateTime createdAt;

    private boolean isMine;

    private boolean isAttend;
}
