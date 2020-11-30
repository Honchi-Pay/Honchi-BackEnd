package honchi.api.domain.buyList.controller;

import honchi.api.domain.buyList.dto.BuyContentResponse;
import honchi.api.domain.buyList.dto.BuyListResponse;
import honchi.api.domain.buyList.dto.SetPriceRequest;
import honchi.api.domain.buyList.service.BuyListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/buyList")
@RequiredArgsConstructor
public class BuyListController {

    private final BuyListService buyListService;

    @PostMapping
    public void setPrice(@RequestBody @Valid SetPriceRequest setPriceRequest) {
        buyListService.setPrice(setPriceRequest);
    }

    @GetMapping
    public List<BuyListResponse> getBuyList() {
        return buyListService.getBuyList();
    }

    @GetMapping("/{buyId}")
    public List<BuyContentResponse> getContent(@PathVariable @Valid Integer buyId) {
        return buyListService.getContent(buyId);
    }
}
