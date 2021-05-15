package com.gaoxi_common_service_facade;

import com.gaoxi_common_service_facade.entity.user.UserEntity;
import com.gaoxi_common_service_facade.req.user.LoginReq;

public interface UserService {
    public UserEntity login(LoginReq loginReq);
}
