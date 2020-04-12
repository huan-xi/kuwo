package com.huanxi.music;

import com.alibaba.fastjson.JSONObject;
import com.huanxi.music.http.downloader.IDownloader;
import com.huanxi.music.http.downloader.SingleThreadDownloader;
import com.huanxi.music.music.kuwo.KuwoService;
import com.huanxi.music.music.kuwo.MusicPiP;
import com.huanxi.music.music.kuwo.Searcher;
import com.huanxi.music.music.kuwo.vo.MusicInfo;
import com.huanxi.music.music.kuwo.vo.ReturnMessage;
import com.huanxi.music.nosql.CacheImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

@SpringBootTest
class MusicApplicationTests {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void test(){
        Map<String, String> link = new HashMap<>();
        link.put("https://www.baidu.com", "百度");
//        link.put("http://www.tuiguangpingtai.com", "推广平台");
        redisTemplate.delete("link");
        redisTemplate.opsForHash().putAll("link",link);
    }
}
