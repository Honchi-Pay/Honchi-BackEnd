package honchi.api.domain.post.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MakeChatResponse {

    private Integer userId;

    private String image;
}
