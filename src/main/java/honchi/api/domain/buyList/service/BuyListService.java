package honchi.api.domain.buyList.service;

import honchi.api.domain.buyList.dto.BuyContentResponse;
import honchi.api.domain.buyList.dto.BuyListResponse;
import honchi.api.domain.buyList.dto.SetPriceRequest;

import java.util.List;

public interface BuyListService {

    void setPrice(SetPriceRequest setPriceRequest);
    List<BuyListResponse> getBuyList();
    List<BuyContentResponse> getContent(Integer buyId);
}
