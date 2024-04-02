package com.expensePro.api.EtUserAuthService.controller;


import com.expensePro.api.EtUserAuthService.AppConstant.LoggingConstants;
import com.expensePro.api.EtUserAuthService.dto.AuthRequest;
import com.expensePro.api.EtUserAuthService.dto.AuthResponse;
import com.expensePro.api.EtUserAuthService.dto.VerifyTokenRequest;
import com.expensePro.api.EtUserAuthService.dto.VerifyTokenResponse;
import com.expensePro.api.EtUserAuthService.mapper.AuthRequestMapper;
import com.expensePro.api.EtUserAuthService.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Shad Ahmad
 * @since 30-03-2024
 */
@RestController
@RequestMapping("/api/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> signUp(@RequestBody AuthRequest authRequest) {

        var methodName = "AuthController:signUp";

        log.info(LoggingConstants.START_METHOD_LOG, methodName, authRequest);
        var accessToken = authService.signUp(AuthRequestMapper.INSTANCE.mapToSignUpRequest(authRequest));


        log.info(LoggingConstants.END_METHOD_LOG, methodName);

        return ResponseEntity.status(HttpStatus.CREATED).
                body(AuthResponse.builder()
                        .accessToken(accessToken)
                        .build());

    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {

        var methodName = "AuthController:login";

        log.info(LoggingConstants.START_METHOD_LOG, methodName, authRequest);
        var accessToken = authService.login(AuthRequestMapper.INSTANCE.mapToLoginRequest(authRequest));


        log.info(LoggingConstants.END_METHOD_LOG, methodName);

        return ResponseEntity.status(HttpStatus.OK).
                body(AuthResponse.builder()
                        .accessToken(accessToken)
                        .build());

    }

    @PostMapping("/verify-token")
    public ResponseEntity<VerifyTokenResponse> verifyToken(@RequestBody VerifyTokenRequest verifyTokenRequest) {

        var methodName = "AuthController:verifyToken";

        log.info(LoggingConstants.START_METHOD_LOG, methodName, verifyTokenRequest);
        var userId = authService.verifyToken(verifyTokenRequest.getAccessToken());


        log.info(LoggingConstants.END_METHOD_LOG, methodName);

        return ResponseEntity.status(HttpStatus.OK).
                body(VerifyTokenResponse.builder()
                        .userId(userId)
                        .build());

    }


}
