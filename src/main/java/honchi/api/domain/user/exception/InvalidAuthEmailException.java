package honchi.api.domain.user.exception;

import honchi.api.global.error.exception.BusinessException;
import honchi.api.global.error.exception.ErrorCode;

public class InvalidAuthEmailException extends BusinessException {

    public InvalidAuthEmailException() {
        super(ErrorCode.INVALID_AUTH_EMAIL);
    }
}
