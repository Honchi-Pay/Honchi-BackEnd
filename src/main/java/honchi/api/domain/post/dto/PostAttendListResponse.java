package honchi.api.domain.post.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostAttendListResponse {

    private String userName;

    private String image;
}
