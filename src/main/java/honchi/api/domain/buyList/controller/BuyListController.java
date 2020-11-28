package honchi.api.domain.buyList.controller;

import honchi.api.domain.buyList.dto.BuyListResponse;
import honchi.api.domain.buyList.service.BuyListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/buyList")
@RequiredArgsConstructor
public class BuyListController {

    private final BuyListService buyListService;

    @GetMapping
    public List<BuyListResponse> getBuyList() {
        return buyListService.getBuyList();
    }
}
