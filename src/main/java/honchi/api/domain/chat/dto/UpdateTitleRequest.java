package honchi.api.domain.chat.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UpdateTitleRequest {

    @NotBlank
    private String roomId;

    @NotBlank
    private String title;
}
