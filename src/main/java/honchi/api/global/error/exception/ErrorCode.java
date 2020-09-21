package honchi.api.global.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
@AllArgsConstructor
public enum ErrorCode {
    
    //Common
    BAD_REQUEST(400, "C400-0", "Bad Request(Invalid Parameter)"),
    USER_NOT_FOUND(404, "C404-0", "User Not Found."),
    INTERNAL_SERVER_ERROR(500, "C500-0", "Internal Server Error"),
    PASSWORD_SAME(409, "C409-0", "Password Same Before"),

    //Auth
    INVALID_AUTH_CODE(400, "A400-1", "Invalid Auth Code"),
    EXPIRED_AUTH_CODE(400, "A400-2", "Expired Auth Code"),
    INVALID_TOKEN(401, "A401-0", "Invalid Token"),
    EXPIRED_TOKEN(401, "A401-1", "Expired Token"),
    UNAUTHORIZED(401, "A401-2", "Authentication is required and has failed or has not yet been provided."),
    USER_DUPLICATION(409, "A409-0", "User is Already Exists");

    private final int status;
    private final String code;
    private final String message;

}