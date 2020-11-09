package honchi.api.domain.message.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class MessageRequest {

    @NotBlank
    private String roomId;
}
