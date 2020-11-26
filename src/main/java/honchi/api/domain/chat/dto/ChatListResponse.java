package honchi.api.domain.chat.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatListResponse {

    private String chatId;

    private String title;

    private Integer people;

    private String message;

    private String[] images;
}
