package com.huanxi.music.nosql;

import org.iq80.leveldb.DB;
import org.iq80.leveldb.DBFactory;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.impl.Iq80DBFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

//@Service
public class LevelDbCache implements ICache {

    @Value("${cache.leveldb.path}")
    private String path;
    private DB db;

    @PostConstruct
    public void init() {
        DBFactory factory = new Iq80DBFactory();

        Options options = new Options();
        options.createIfMissing(true);
        //folder 是db存储目录
        try {
            db = factory.open(new File(path), options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void put(String key, String value) {
        db.put(key.getBytes(), value.getBytes());
    }

    public  DB getDb() {
        return this.db;
    }

    @Override
    public String get(String key) {
        byte[] res = db.get(key.getBytes());

        return res != null ? new String(res) : null;
    }

    @Override
    public Object getObject(String key, Class clazz) {
        return null;
    }

    @Override
    public void set(String key, String value) {
        db.put(key.getBytes(),value.getBytes());
    }

    @Override
    public void set(String key, Object value) {

    }

    @Override
    public void set(String key, Object value, Duration duration) {

    }

    @Override
    public void set(String key, String value, Duration duration) {

    }

    @Override
    public void delete(String key) {

    }

    public int incr(String key) {
        byte[] res = db.get(key.getBytes());
        int result = res == null ? 0 : bytes2Int(res);
        result++;
        db.put(key.getBytes(), IntToByte(result));
        return result;
    }

    /**
     * byte数组转int类型的对象
     *
     * @param bytes
     * @return
     */
    public static int bytes2Int(byte[] bytes) {
        return (bytes[0] & 0xff) << 24
                | (bytes[1] & 0xff) << 16
                | (bytes[2] & 0xff) << 8
                | (bytes[3] & 0xff);
    }

    /**
     * int转byte数组
     *
     * @return
     */
    public static byte[] IntToByte(int num) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) ((num >> 24) & 0xff);
        bytes[1] = (byte) ((num >> 16) & 0xff);
        bytes[2] = (byte) ((num >> 8) & 0xff);
        bytes[3] = (byte) (num & 0xff);
        return bytes;
    }
}
