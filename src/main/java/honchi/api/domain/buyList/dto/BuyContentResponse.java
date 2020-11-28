package honchi.api.domain.buyList.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class BuyContentResponse {

    private String title;

    private Integer price;

    private LocalDateTime time;

    private List images;

    private List attendList;
}
