package com.expensePro.api.EtUserAuthService.service.auth.impl;

import com.expensePro.api.EtUserAuthService.AppConstant.ErrorMessage;
import com.expensePro.api.EtUserAuthService.AppConstant.LoggingConstants;
import com.expensePro.api.EtUserAuthService.entity.AppUser;
import com.expensePro.api.EtUserAuthService.exception.BadCredentialsException;
import com.expensePro.api.EtUserAuthService.exception.InvalidTokenException;
import com.expensePro.api.EtUserAuthService.exception.UserAlreadyExistsException;
import com.expensePro.api.EtUserAuthService.exception.UserNotFoundException;
import com.expensePro.api.EtUserAuthService.models.LoginRequest;
import com.expensePro.api.EtUserAuthService.repository.AppUserRepository;
import com.expensePro.api.EtUserAuthService.service.auth.AuthService;
import com.expensePro.api.EtUserAuthService.models.SignUpRequest;
import com.expensePro.api.EtUserAuthService.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


/**
 * @author Shad Ahmad
 * @since 30-03-2024
 */

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String signUp(SignUpRequest signUpRequest) {
        var methodName = "AuthServiceImpl:signUp";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, signUpRequest);

        if (appUserRepository.existsByEmail(signUpRequest.getEmail())) {
            log.error(LoggingConstants.ERROR_METHOD_LOG, methodName, signUpRequest.getEmail()
                    + " already exists");
            throw new UserAlreadyExistsException(
                    ErrorMessage.USER_ALREADY_EXISTS.getErrorMessage(),
                    ErrorMessage.USER_ALREADY_EXISTS.getErrorCode());
        }

        var savedUser = AppUser.builder()
                .name(signUpRequest.getName())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .build();

        appUserRepository.save(savedUser);

        var accessToken = JwtUtils.generateAccessToken(signUpRequest.getEmail());
        log.info(LoggingConstants.END_METHOD_LOG, methodName, signUpRequest);
        return accessToken;
    }

    @Override
    public String login(LoginRequest loginRequest) {
        var methodName = "AuthServiceImpl:login";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, loginRequest);

        var appUser = appUserRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> {
                    log.error(LoggingConstants.END_METHOD_LOG, methodName, loginRequest.getEmail()
                            + " User not found !");
                    return new UserNotFoundException(
                            ErrorMessage.USER_NOT_FOUND_EXCEPTION.getErrorMessage(),
                            ErrorMessage.USER_NOT_FOUND_EXCEPTION.getErrorCode());
                });

        if (!passwordEncoder.matches(loginRequest.getPassword(), appUser.getPassword())) {
            log.error(LoggingConstants.END_METHOD_LOG, methodName, loginRequest.getEmail()
                    + " User not found !");
            throw new BadCredentialsException(
                    ErrorMessage.PASSWORD_NOT_MATCHED.getErrorMessage(),
                    ErrorMessage.PASSWORD_NOT_MATCHED.getErrorCode());
        }

        var accessToken = JwtUtils.generateAccessToken(loginRequest.getEmail());
        log.info(LoggingConstants.END_METHOD_LOG, methodName);
        return accessToken;
    }

    @Override
    public String verifyToken(String accessToken) {
        var methodName = "AuthServiceImpl:verifyToken";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, accessToken);

        var username = JwtUtils.getUsernameFromToken(accessToken)
                .orElseThrow(() -> {
                    log.error(LoggingConstants.END_METHOD_LOG, methodName, "Invalid Token !");
                    return new InvalidTokenException(
                            ErrorMessage.INVALID_ACCESS_TOKEN.getErrorMessage(),
                            ErrorMessage.INVALID_ACCESS_TOKEN.getErrorCode()
                    );

                });
        // Find User by Email
        var appUser = appUserRepository.findByEmail(username)
                .orElseThrow(() -> {
                    log.error(LoggingConstants.END_METHOD_LOG, methodName, username
                            + " User not found !");
                    return new UserNotFoundException(
                            ErrorMessage.USER_NOT_FOUND_EXCEPTION.getErrorMessage(),
                            ErrorMessage.USER_NOT_FOUND_EXCEPTION.getErrorCode());
                });
        //Return userId
        var userId = appUser.getUserId();
        log.info(LoggingConstants.START_METHOD_LOG, methodName, accessToken);

        return userId;

    }

}