package com.expensePro.api.EtUserAuthService.controller;

import com.expensePro.api.EtUserAuthService.AppConstant.LoggingConstants;
import com.expensePro.api.EtUserAuthService.dto.ChangePasswordRequest;
import com.expensePro.api.EtUserAuthService.dto.UserDetails;
import com.expensePro.api.EtUserAuthService.dto.UserInfo;
import com.expensePro.api.EtUserAuthService.mapper.UserInfoMapper;
import com.expensePro.api.EtUserAuthService.service.auth.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Shad Ahmad
 * @since 30-03-2024
 */

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    //Get user Information
    @GetMapping("/{userId}")
    public ResponseEntity<UserInfo> getUserInfo(@PathVariable String userId) {
        var methodName = "UserController:getUserInfo";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId);

        var appUser = userService.getUserInfo(userId);

        log.info(LoggingConstants.END_METHOD_LOG, methodName);
        return ResponseEntity.status(HttpStatus.OK)
                .body(UserInfoMapper.INSTANCE.mapToUserInfo(appUser));
    }

    // Change Password
    @PostMapping("/{userId}/change-password")
    public ResponseEntity<Void> changePassword(@PathVariable String userId,
                                               @RequestBody ChangePasswordRequest changePasswordRequest) {
        var methodName = "UserController:changePassword";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId);

        userService.changePassword(userId,
                changePasswordRequest.getOldPassword()
                , changePasswordRequest.getNewPassword());

        log.info(LoggingConstants.ERROR_METHOD_LOG, methodName);

        return ResponseEntity.ok().build();
    }

    // Update User Details Or Information
    @PutMapping("/{userId}/update-user")
    public ResponseEntity<UserInfo> updateUserDetails(@PathVariable String userId,
                                                      @RequestBody UserDetails userDetails) {
        var methodName = "UserController:updateUserDetails";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId);

        var appUser = switch (userDetails.getRequestType()) {
            case UPDATE_EMAIL -> userService.updateEmail(userId, userDetails.getEmail());
            case UPDATE_NAME -> userService.updateName(userId, userDetails.getName());

        };


        log.info(LoggingConstants.ERROR_METHOD_LOG, methodName);

        return ResponseEntity.status(HttpStatus.OK)
                .body(UserInfoMapper.INSTANCE.mapToUserInfo(appUser));

    }

}
