package honchi.api.domain.auth.exception;

import honchi.api.global.error.exception.BusinessException;
import honchi.api.global.error.exception.ErrorCode;

public class InvalidTokenException extends BusinessException {

    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}
