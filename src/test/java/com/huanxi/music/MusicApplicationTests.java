package com.huanxi.music;

import com.huanxi.music.http.downloader.IDownloader;
import com.huanxi.music.http.downloader.SingleThreadDownloader;
import com.huanxi.music.music.kuwo.KuwoService;
import com.huanxi.music.music.kuwo.MusicPiP;
import com.huanxi.music.music.kuwo.Searcher;
import com.huanxi.music.music.kuwo.vo.MusicInfo;
import com.huanxi.music.music.kuwo.vo.ReturnMessage;
import com.huanxi.music.nosql.CacheImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.io.File;
import java.util.concurrent.Semaphore;

@SpringBootTest
class MusicApplicationTests {

    @Resource
    Searcher searcher;


    @Resource
    private ThreadPoolTaskExecutor executor;

    @Resource
    MusicPiP musicPiP;

    @Resource
    CacheImpl cache;


    @Test
    void testMo() {
       /* DBIterator it = levelDbCache.getDb().iterator();
        String prefix = "finish";
        try {
            for (it.seek(prefix.getBytes()); it.hasNext(); it.next()) {
                if (!equalPrefix(it.peekNext().getKey(), prefix.getBytes()))
                    break;
                String key = new String(it.peekNext().getKey());
                String value = new String(it.peekNext().getValue());
                cache.set(key,value);
                System.out.println(key+"="+value);
            }
        } finally {

        }*/
    }

    public static boolean equalPrefix(byte[] src, byte[] target) {
        if (src.length < target.length)
            return false;

        for (int i = 0; i < target.length; i++) {
            if (src[i] != target[i])
                return false;
        }

        return true;
    }

    @Test
    void contextLoads() {
        Semaphore semaphore = new Semaphore(20);
        for (int i = 1; i < 20; i++) {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }

        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void testSearch() {

    }

}
