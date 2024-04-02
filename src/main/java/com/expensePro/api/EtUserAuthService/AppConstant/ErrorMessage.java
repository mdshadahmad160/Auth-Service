package com.expensePro.api.EtUserAuthService.AppConstant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Shad Ahmad
 * @since 30-03-2024
 */



@AllArgsConstructor
@Getter
public enum ErrorMessage {
    USER_ALREADY_EXISTS("E409", "User already exists !"),
    USER_NOT_FOUND_EXCEPTION("E404", "User not found !"),
    PASSWORD_NOT_MATCHED("E401", "Password not matched !"),
    INVALID_ACCESS_TOKEN("t401", "Invalid access token !");

    private final String errorCode;
    private final String errorMessage;
}

