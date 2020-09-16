package honchi.api.domain.user.exception;

import honchi.api.global.error.exception.BusinessException;
import honchi.api.global.error.exception.ErrorCode;

public class UserIsAlreadyExistException extends BusinessException {

    public UserIsAlreadyExistException() {
        super(ErrorCode.USER_DUPLICATION);
    }
}
