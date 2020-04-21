package com.huanxi.music;

import com.alibaba.fastjson.JSONObject;
import com.huanxi.music.config.NetUtils;
import com.huanxi.music.http.downloader.IDownloader;
import com.huanxi.music.http.downloader.SingleThreadDownloader;
import com.huanxi.music.http.request.OkHttp3Request;
import com.huanxi.music.music.kuwo.KuwoService;
import com.huanxi.music.music.kuwo.MusicPiP;
import com.huanxi.music.music.kuwo.Searcher;
import com.huanxi.music.music.kuwo.vo.MusicInfo;
import com.huanxi.music.music.kuwo.vo.ReturnMessage;
import com.huanxi.music.nosql.CacheImpl;
import com.huanxi.music.nosql.ICache;
import okhttp3.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

@SpringBootTest
class MusicApplicationTests {
    @Autowired
    private StringRedisTemplate redisTemplate;

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
        Map<String, String> link = new HashMap<>();
        link.put("https://www.my404.cn", "木尤小站");
        link.put("http://ebook.chengfeng8.top/", "免费小说");
        link.put("https://www.baidu.com", "百度一下");
        link.put("https://music.shengxiansen.xyz", "生先森音乐网");
        link.put("https://mamu.xls0514.top/", "Mc麻木音乐网");
        link.put("http://wlmqlww.xj917.com", "乌鲁木齐论文网");
        link.put("http://www.ifreetube.com", "免费商用音乐");
        link.put("https://www.iwangl.com", "爱网络导航");
        link.put("http://www.0558.la/", "自动秒收录");
        link.put("https://www.zdslc.com/", "站点收录池");
        link.put("http://vip.dytt666.net", "免费优酷vip视频");
        link.put("https://huan-xi.gitee.io", "huanxi'blog");
        redisTemplate.delete("link");
        redisTemplate.opsForHash().putAll("link", link);
//        redisTemplate.expire("link");
    }

    @Test
    public void testMv() {
        System.out.println(kuwoService.getMvLink(78712269L));
        ;

    }
}
