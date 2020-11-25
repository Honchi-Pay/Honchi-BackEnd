package honchi.api.domain.user.exception;

import honchi.api.global.error.exception.BusinessException;
import honchi.api.global.error.exception.ErrorCode;

public class PhoneNumberAlreadyExistsException extends BusinessException {

    public PhoneNumberAlreadyExistsException() {
        super(ErrorCode.USER_PHONE_NUMBER_DUPLICATION);
    }
}
