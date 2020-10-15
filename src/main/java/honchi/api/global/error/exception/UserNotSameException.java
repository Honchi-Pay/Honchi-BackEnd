package honchi.api.global.error.exception;

public class UserNotSameException extends BusinessException {

    public UserNotSameException(){
        super(ErrorCode.USER_NOT_SAME);
    }
}
