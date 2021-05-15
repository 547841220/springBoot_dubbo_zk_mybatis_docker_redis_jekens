package com.gaoxi_controller.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.gaoxi_common_service_facade.UserService;
import com.gaoxi_common_service_facade.entity.user.UserEntity;
import com.gaoxi_common_service_facade.facade.redis.RedisService;
import com.gaoxi_common_service_facade.req.user.LoginReq;
import com.gaoxi_common_service_facade.rsp.Result;
import com.gaoxi_controller.redis.RedisServiceTemp;
import com.gaoxi_controller.util.KeyGenerator;
import com.gaoxi_controller.util.RedisPrefixUtil;
import com.gaoxi_controller.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * @Author 大闲人柴毛毛
 * @Date 2017/10/27 下午10:26
 */
@RestController
public class UserControllerImpl implements UserController {

    @Reference(version = "1.0.0")
    private UserService userService;

    //@Reference(version = "1.0.0")
    //private RedisService redisService;

    /** Session有效时间 */
    @Value("${session.expireTime}")
    private long sessionExpireTime;

    /** HTTP Response中Session ID 的名字 */
    @Value("${session.SessionIdName}")
    private String sessionIdName;

    @Autowired
    private UserUtil userUtil;

    @Override
    public Result login(LoginReq loginReq, HttpServletResponse httpRsp) {

        // 登录鉴权
        UserEntity userEntity = userService.login(loginReq);

        // 登录成功
        doLoginSuccess(userEntity, httpRsp);
        return Result.newSuccessResult(userEntity);
    }

    /**
     * 处理登录成功
     * @param userEntity 用户信息
     * @param httpRsp HTTP响应参数
     */
    private void doLoginSuccess(UserEntity userEntity, HttpServletResponse httpRsp) {
        // 生成SessionID
        String sessionID = RedisPrefixUtil.SessionID_Prefix + KeyGenerator.getKey();

        // 将 SessionID-UserEntity 存入Redis
        // TODO 暂时存储本地
//        redisService.set(sessionID, userEntity, sessionExpireTime);
        RedisServiceTemp.userMap.put(sessionID, userEntity);

        // 将SessionID存入HTTP响应头
        Cookie cookie = new Cookie(sessionIdName, sessionID);
        httpRsp.addCookie(cookie);
    }



}
