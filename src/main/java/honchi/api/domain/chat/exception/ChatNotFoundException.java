package honchi.api.domain.chat.exception;

import honchi.api.global.error.exception.BusinessException;
import honchi.api.global.error.exception.ErrorCode;

public class ChatNotFoundException extends BusinessException {

    public ChatNotFoundException() {
        super(ErrorCode.CHAT_NOT_FOUND);
    }
}
