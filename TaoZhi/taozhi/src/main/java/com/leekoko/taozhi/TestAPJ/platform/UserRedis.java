package com.leekoko.taozhi.TestAPJ.platform;

import com.google.gson.Gson;
import com.leekoko.taozhi.TestAPJ.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class UserRedis {
/*
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    public void add(String key,Long time,User user){
        Gson gson = new Gson();
        redisTemplate.opsForValue().set(key,gson.toJson(user),time, TimeUnit.MINUTES);
    }*/



}
