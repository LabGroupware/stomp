package org.cresplanex.nova.stomp.exception;

import lombok.Getter;
import org.cresplanex.nova.stomp.constants.ServerErrorCode;

@Getter
public class AuthServerError {

    private final String code;
    private final String message;

    public AuthServerError() {
        this(ServerErrorCode.STOMP_INTERNAL_ERROR, "Internal Server Error");
    }

    public AuthServerError(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
