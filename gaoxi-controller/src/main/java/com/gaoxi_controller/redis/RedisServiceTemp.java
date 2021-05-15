package com.gaoxi_controller.redis;

import com.gaoxi_common_service_facade.entity.user.AccessAuthEntity;
import com.gaoxi_common_service_facade.entity.user.UserEntity;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author 大闲人柴毛毛
 * @date 2017/11/14 下午2:57
 * @description
 */
public class RedisServiceTemp {

    public static Map<String, AccessAuthEntity> accessAuthMap = Maps.newHashMap();

    public static Map<String, UserEntity> userMap = Maps.newHashMap();
}
