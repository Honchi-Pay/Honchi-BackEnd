package honchi.api.domain.buyList.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class SetPriceRequest {

    @NotBlank
    private String chatId;

    @NotNull
    private Integer postId;

    @NotNull
    private Integer price;
}
