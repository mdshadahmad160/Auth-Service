package com.expensePro.api.EtUserAuthService.exception;

import lombok.Getter;

/**
 * @author Shad Ahmad
 * @since 30-03-2024
 */

@Getter
public class UserNotFoundException extends RuntimeException{

    private String errorCode;

    public UserNotFoundException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
