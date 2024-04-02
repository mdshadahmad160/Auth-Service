package com.expensePro.api.EtUserAuthService.mapper;

import com.expensePro.api.EtUserAuthService.dto.AuthRequest;
import com.expensePro.api.EtUserAuthService.models.LoginRequest;
import com.expensePro.api.EtUserAuthService.models.SignUpRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * @author Shad Ahmad
 * @since 30-03-2024
 */

@Mapper
public interface AuthRequestMapper {


    AuthRequestMapper INSTANCE = Mappers.getMapper(AuthRequestMapper.class);

    SignUpRequest mapToSignUpRequest(AuthRequest authRequest);

    LoginRequest mapToLoginRequest(AuthRequest authRequest);
}
