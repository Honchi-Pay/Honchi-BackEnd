package honchi.api.domain.buyList.exception;

import honchi.api.global.error.exception.BusinessException;
import honchi.api.global.error.exception.ErrorCode;

public class BuyListNotFoundException extends BusinessException {

    public BuyListNotFoundException() {
        super(ErrorCode.BUY_LIST_NOT_FOUND);
    }
}
