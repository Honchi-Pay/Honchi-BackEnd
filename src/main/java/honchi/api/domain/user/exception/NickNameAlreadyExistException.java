package honchi.api.domain.user.exception;

import honchi.api.global.error.exception.BusinessException;
import honchi.api.global.error.exception.ErrorCode;

public class NickNameAlreadyExistException extends BusinessException {

    public NickNameAlreadyExistException() {
        super(ErrorCode.USER_NICKNAME_DUPLICATION);
    }
}
