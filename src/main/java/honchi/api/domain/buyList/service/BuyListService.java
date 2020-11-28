package honchi.api.domain.buyList.service;

import honchi.api.domain.buyList.dto.BuyListResponse;

import java.util.List;

public interface BuyListService {

    List<BuyListResponse> getBuyList();
}
