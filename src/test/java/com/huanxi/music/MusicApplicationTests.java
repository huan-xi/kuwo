package com.huanxi.music;

import com.huanxi.music.http.downloader.IDownloader;
import com.huanxi.music.http.downloader.SingleThreadDownloader;
import com.huanxi.music.music.kuwo.KuwoService;
import com.huanxi.music.music.kuwo.MusicPiP;
import com.huanxi.music.music.kuwo.Searcher;
import com.huanxi.music.music.kuwo.vo.MusicInfo;
import com.huanxi.music.music.kuwo.vo.ReturnMessage;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
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
    @Test
    void contextLoads() {
        Semaphore semaphore = new Semaphore(20);
        for (int i = 1; i < 20; i++) {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ReturnMessage res = searcher.search("周杰伦", i, 20);
            if (res.getCode() == 200) {
                for (MusicInfo musicInfo : res.getData().getList()) {
                    //数据持久化 //
                    try {
                       executor.execute(new Runnable() {
                           @Override
                           public void run() {
                               musicPiP.save(musicInfo);
                               semaphore.release();
                           }
                       });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
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
