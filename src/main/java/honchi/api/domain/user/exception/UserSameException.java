package honchi.api.domain.user.exception;

import honchi.api.global.error.exception.BusinessException;
import honchi.api.global.error.exception.ErrorCode;

public class UserSameException extends BusinessException {

    public UserSameException() {
        super(ErrorCode.USER_SAME);
    }
}
