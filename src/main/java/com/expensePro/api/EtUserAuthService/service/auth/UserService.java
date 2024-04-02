package com.expensePro.api.EtUserAuthService.service.auth;

import com.expensePro.api.EtUserAuthService.entity.AppUser;

/**
 * @author Shad Ahmad
 * @since 30-03-2024
 */


public interface UserService {



    AppUser getUserInfo(String userId);

    void changePassword(String userId, String oldPassword, String newPassword);


    AppUser updateName(String userId, String name);
    AppUser updateEmail(String userId, String email);
}
