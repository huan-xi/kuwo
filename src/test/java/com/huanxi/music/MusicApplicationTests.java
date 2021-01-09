package com.huanxi.music;

import com.huanxi.music.http.request.OkHttp3Request;
import com.huanxi.music.music.kuwo.KuwoService;
import com.huanxi.music.nosql.ICache;
import com.huanxi.music.nosql.Link;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class MusicApplicationTests {


    @Resource
    ICache cache;
    @Resource
    KuwoService kuwoService;
    @Resource
    OkHttp3Request request;

    @Test
    public void tests() {
        String key = "finish_1_红豆";
        cache.set(key, "ok");
    }

    @Test
    public void testRequest() throws Exception {
        //System.out.println("test:"+NetUtils.getRedirectUrl("https://round-mud-838c.huanxi.workers.dev/test.txt"));
        HttpURLConnection conn = (HttpURLConnection) new URL("http://round-mud-838c.huanxi.workers.dev/可泽&夏凌兮/起风了.mp3")
                .openConnection();
        conn.setInstanceFollowRedirects(false);
        conn.setConnectTimeout(5000);
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.163 Safari/537.36");
        conn.getHeaderFields().forEach((k, v) -> {
            System.out.println("k:" + k + "v:" + v);
        });
    }

    @Test
    public void test() {
        //[{link,name}]


//        redisTemplate.expire("link");
    }

    @Test
    public void testMv() {
        System.out.println(kuwoService.getMvLink(78712269L));
    }
}
