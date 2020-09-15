package honchi.api.domain.user.exception;

import honchi.api.global.error.exception.BusinessException;
import honchi.api.global.error.exception.ErrorCode;

public class UserAlreadyExistException extends BusinessException {

    public UserAlreadyExistException() {
        super(ErrorCode.USER_DUPLICATION);
    }
}
