package honchi.api.domain.message.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MessageResponse {

    private Integer userId;

    private String nickName;

    private String message;

    private LocalDateTime time;

    private boolean isMine;
}
