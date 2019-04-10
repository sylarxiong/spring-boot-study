package com.example.shiro;

import com.example.entity.User;
import com.example.util.MD5Utils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

@Component
public class TokenUtils {
    @Autowired
    private static RedisTemplate redisTemplate;
    private static final String PREFIX = "TOKEN:";

    public static User getUser(String token) {
        User user = (User) redisTemplate.opsForValue().get(PREFIX + token);
        return user;
    }

    public static User getUser() {
        String token = SecurityUtils.getSubject().getPrincipal().toString();
        return getUser(token);
    }

    public static String initToken(User user) {
        String value = String.valueOf(Calendar.getInstance().getTimeInMillis()) + RandomUtils.nextInt();
        String md5 = MD5Utils.stringMD5(value);
        redisTemplate.opsForValue().set(PREFIX + md5, user);
        redisTemplate.expire(PREFIX + md5, 10, TimeUnit.MINUTES);
        return md5;
    }
}
