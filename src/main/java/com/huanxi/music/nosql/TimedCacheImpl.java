package com.huanxi.music.nosql;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * @author huanxi
 * @version 1.0
 * @date 2021/1/9 10:48 下午
 */
@Service
public class TimedCacheImpl implements ICache{
    TimedCache<String, String> timedCache = CacheUtil.newTimedCache(Duration.ofHours(1).toMillis());

    public TimedCacheImpl() {

    }

    @Override
    public String get(String key) {
        return timedCache.get(key);
    }

    @Override
    public <T> T getObject(String key, Class<T> clazz) {
        T t = null;
        String value = get(key);
        if (StrUtil.isNotEmpty(value)) {
            t = JSON.parseObject(value, clazz);
        }
        return t;
    }

    @Override
    public void set(String key, String value) {
        timedCache.put(key, value);
    }

    @Override
    public void set(String key, Object value) {
        this.set(key, JSON.toJSONString(value));
    }

    @Override
    public void set(String key, Object value, Duration duration) {
        this.set(key, JSON.toJSONString(value),duration);
    }

    @Override
    public void set(String key, String value, Duration duration) {
        timedCache.put(key, value,duration.toMillis());
    }

    @Override
    public void delete(String key) {
        timedCache.remove(key);
    }

    @Override
    public Boolean hasKey(String key) {
        return timedCache.containsKey(key);
    }
}
