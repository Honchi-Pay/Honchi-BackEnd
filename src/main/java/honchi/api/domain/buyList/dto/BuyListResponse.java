package honchi.api.domain.buyList.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BuyListResponse {

    private Integer id;

    private String title;

    private Integer price;

    private LocalDateTime time;
}
