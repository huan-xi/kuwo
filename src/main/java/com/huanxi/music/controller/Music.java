package com.huanxi.music.controller;

import com.huanxi.music.music.kuwo.MusicPiP;
import com.huanxi.music.music.kuwo.Searcher;
import com.huanxi.music.music.kuwo.vo.MusicInfo;
import com.huanxi.music.music.kuwo.vo.ReturnMessage;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
@RestController
@RequestMapping("music")
public class Music {
    @Resource
    private ThreadPoolTaskExecutor executor;
    @Resource
    MusicPiP musicPiP;
    @Resource
    private Searcher searcher;
    @GetMapping("download")
    public ReturnMessage download(String key, int pageNo, int pageSize){
        ReturnMessage res = searcher.search(key, pageNo, pageSize);
        if (res.getCode() == 200) {
            for (MusicInfo musicInfo : res.getData().getList()) {
                //数据持久化 //
                try {
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            musicPiP.save(musicInfo);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        return res;
    }
}
