package com.huanxi.music.nosql;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Duration;

@Service
public class CacheImpl implements ICache {

    @Autowired
    private StringRedisTemplate redisTemplate;


    @Override
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public Object getObject(String key, Class clazz) {
        Object resObj = null;
        String res = get(key);
        if (!StringUtils.isEmpty(res)) {
            try {
                resObj = JSON.parseObject(res, clazz);
            } catch (Exception e) {
                return null;
            }

        }
        return resObj;
    }

    @Override
    public void set(String key, Object value) {
        this.set(key, JSON.toJSONString(value));
    }

    @Override
    public void set(String key, Object value, Duration duration) {
        redisTemplate.opsForValue().set(key, JSON.toJSONString(value), duration);
    }

    @Override
    public void set(String key, String value, Duration duration) {
        redisTemplate.opsForValue().set(key, value, duration);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }
}
