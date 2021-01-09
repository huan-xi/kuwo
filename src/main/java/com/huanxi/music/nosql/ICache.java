package com.huanxi.music.nosql;

import java.time.Duration;

public interface ICache {
    String get(String key);

     <T> T  getObject(String key, Class<T> clazz) ;

    void set(String key, String value);

    void set(String key, Object value);

    void set(String key, Object value, Duration duration);

    void set(String key, String value, Duration duration);
    void delete(String key);

    Boolean hasKey(String key);
}
