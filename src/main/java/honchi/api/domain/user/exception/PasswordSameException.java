package honchi.api.domain.user.exception;

import honchi.api.global.error.exception.BusinessException;
import honchi.api.global.error.exception.ErrorCode;

public class PasswordSameException extends BusinessException {

    public PasswordSameException() {
        super(ErrorCode.PASSWORD_SAME);
    }
}
