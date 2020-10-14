package honchi.api.domain.post.exception;

import honchi.api.global.error.exception.BusinessException;
import honchi.api.global.error.exception.ErrorCode;

public class UserNotSameException extends BusinessException {

    public UserNotSameException(){
        super(ErrorCode.USER_NOT_SAME);
    }
}
