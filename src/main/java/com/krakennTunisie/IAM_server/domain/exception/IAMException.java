package com.krakennTunisie.IAM_server.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class IAMException extends RuntimeException {

    private final HttpStatus status;
    private final String errorCode;

    public IAMException(HttpStatus status, String errorCode, String message) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
    }
    public IAMException(HttpStatus status, String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
        this.errorCode = errorCode;
    }


    public static IAMException notFound(String resource, String field, String id) {
        return new IAMException(
                HttpStatus.NOT_FOUND,
                "NOT_FOUND",
                resource + " avec "+field+" : " + id+ " est introuvable !"
        );
    }

    public static IAMException alreadyExists(String resource, String field, String value) {
        return new IAMException(
                HttpStatus.CONFLICT,
                "ALREADY_EXISTS",
                resource + " Dèjà existant avec " + field + ": " + value
        );
    }

    public static IAMException badRequest(String message) {
        return new IAMException(
                HttpStatus.BAD_REQUEST,
                "BAD_REQUEST",
                message
        );
    }

    public static IAMException fileTooLarge(String message) {
        return new IAMException(
                HttpStatus.BAD_REQUEST,
                "FILE_TOO_LARGE",
                message
        );
    }

    public static IAMException forbidden(String message) {
        return new IAMException(
                HttpStatus.FORBIDDEN,
                "FORBIDDEN",
                message
        );
    }

    public static IAMException internalError(String message) {
        return new IAMException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "INTERNAL_ERROR",
                message
        );
    }
    public static IAMException BusinessException(String message,String field) {
        return new IAMException(
                HttpStatus.CONFLICT,
                field,
                message
        );
    }
}
