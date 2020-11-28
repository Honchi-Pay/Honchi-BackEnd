package honchi.api.domain.message.exception;

import honchi.api.global.error.exception.BusinessException;
import honchi.api.global.error.exception.ErrorCode;

public class MessageNotFoundException extends BusinessException {

    public MessageNotFoundException() {
        super(ErrorCode.MESSAGE_NOT_FOUND);
    }
}
