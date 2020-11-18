package honchi.api.domain.message.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class MessageRequest {

    @NotNull
    private Integer chatId;
}
