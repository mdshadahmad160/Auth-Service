package com.expensePro.api.EtUserAuthService.exception;

import lombok.Getter;

/**
 * @author Shad Ahmad
 * @since 30-03-2024
 */


@Getter
public class BadCredentialsException extends RuntimeException{



    private String errorCode;

    public BadCredentialsException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}
