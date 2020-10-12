package honchi.api.domain.post.exception;

import honchi.api.global.error.exception.BusinessException;
import honchi.api.global.error.exception.ErrorCode;

public class PostNotFoundException extends BusinessException {

    public PostNotFoundException() {
        super(ErrorCode.POST_NOT_FOUND);
    }
}
