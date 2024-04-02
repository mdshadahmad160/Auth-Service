package com.expensePro.api.EtUserAuthService.service.auth.impl;

import com.expensePro.api.EtUserAuthService.AppConstant.ErrorMessage;
import com.expensePro.api.EtUserAuthService.AppConstant.LoggingConstants;
import com.expensePro.api.EtUserAuthService.entity.AppUser;
import com.expensePro.api.EtUserAuthService.exception.BadCredentialsException;
import com.expensePro.api.EtUserAuthService.exception.UserNotFoundException;
import com.expensePro.api.EtUserAuthService.repository.AppUserRepository;
import com.expensePro.api.EtUserAuthService.service.auth.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Shad Ahmad
 * @since 30-03-2024
 */


@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AppUser getUserInfo(String userId) {
        var methodName = "UserServiceImpl:getUserInfo";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId);
        var appUser = appUserRepository.findById(userId)
                .orElseThrow(() -> {
                    return new UserNotFoundException(
                            ErrorMessage.USER_NOT_FOUND_EXCEPTION.getErrorMessage(),
                            ErrorMessage.USER_NOT_FOUND_EXCEPTION.getErrorCode()
                    );
                });

        log.info(LoggingConstants.END_METHOD_LOG, methodName);
        return appUser;
    }

    @Override
    public void changePassword(String userId, String oldPassword, String newPassword) {
        var methodName = "UserServiceImpl:changePassword";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId);
        //Find User By UserId
        var appUser = getUserInfo(userId);


        //Check Old Password
        if (!passwordEncoder.matches(oldPassword, appUser.getPassword())) {
            log.info(LoggingConstants.ERROR_METHOD_LOG, methodName, "Incorrect Password !");
            throw new BadCredentialsException(
                    ErrorMessage.PASSWORD_NOT_MATCHED.getErrorMessage(),
                    ErrorMessage.PASSWORD_NOT_MATCHED.getErrorCode());
        }

        // Set new Password
        appUser.setPassword(passwordEncoder.encode(newPassword));

        //Save User
        appUserRepository.save(appUser);

        log.info(LoggingConstants.END_METHOD_LOG, methodName);

    }

    @Override
    public AppUser updateName(String userId, String name) {
        var methodName = "UserServiceImpl:updateName";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId);
        //
        //Find User by UserId
        var appUser = getUserInfo(userId);

        //Update user name
        appUser.setName(name);

        //save user
        var savedUser = appUserRepository.save(appUser);
        return savedUser;
    }

    @Override
    public AppUser updateEmail(String userId, String email) {
        var methodName = "UserServiceImpl:updateEmail";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId);

        //Find User by UserId
        var appUser = getUserInfo(userId);

        //Update user name
        appUser.setName(email);

        //save user
        var savedUser = appUserRepository.save(appUser);
        log.info(LoggingConstants.END_METHOD_LOG, methodName);
        return savedUser;
    }
}
