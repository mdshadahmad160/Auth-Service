package com.expensePro.api.EtUserAuthService.mapper;

import com.expensePro.api.EtUserAuthService.dto.UserInfo;
import com.expensePro.api.EtUserAuthService.entity.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Shad Ahmad
 * @since 30-03-2024
 */


@Mapper
public interface UserInfoMapper {

    UserInfoMapper INSTANCE= Mappers.getMapper(UserInfoMapper.class);

    UserInfo mapToUserInfo(AppUser user);
}
