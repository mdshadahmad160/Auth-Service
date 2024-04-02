package com.expensePro.api.EtUserAuthService.service.auth;

import com.expensePro.api.EtUserAuthService.models.LoginRequest;
import com.expensePro.api.EtUserAuthService.models.SignUpRequest;


/**
 * @author Shad Ahmad
 * @since 30-03-2024
 */

public interface AuthService {

    String signUp(SignUpRequest signUpRequest);

    String login(LoginRequest loginRequest);


    String verifyToken(String accessToken);
}
