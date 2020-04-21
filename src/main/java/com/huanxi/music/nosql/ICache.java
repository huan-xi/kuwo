package com.huanxi.music.nosql;

import java.time.Duration;

public interface ICache {
    String get(String key);

    Object getObject(String key, Class clazz);

    void set(String key, String value);

    void set(String key, Object value);

    void set(String key, Object value, Duration duration);

    void set(String key, String value, Duration duration);
    void delete(String key);
}
