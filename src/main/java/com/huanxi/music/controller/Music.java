package com.huanxi.music.controller;

import com.huanxi.music.common.message.AbstractMessage;
import com.huanxi.music.common.message.OutputUtils;
import com.huanxi.music.config.NetUtils;
import com.huanxi.music.music.kuwo.KuwoService;
import com.huanxi.music.music.kuwo.MusicPiP;
import com.huanxi.music.music.kuwo.Searcher;
import com.huanxi.music.music.kuwo.vo.MusicInfo;
import com.huanxi.music.music.kuwo.vo.ReturnMessage;
import com.huanxi.music.music.kuwo.vo.SearchKeyVo;
import com.huanxi.music.nosql.ICache;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@Log4j2
@RequestMapping("music")
public class Music {

    @Resource
    private ThreadPoolTaskExecutor executor;
    @Resource
    MusicPiP musicPiP;
    @Resource
    ICache cache;
    @Resource
    private Searcher searcher;
    @Resource
    private KuwoService kuwoService;


    @GetMapping("list")
    public ReturnMessage musicList(String key, int pageNo, int pageSize) {
        return searcher.search(key, pageNo, pageSize);
    }

    @GetMapping("link")
    public AbstractMessage getLink(Long rid) {
        return OutputUtils.success(kuwoService.getDownloadLinkCache(rid));
    }

    @GetMapping("mv")
    public AbstractMessage getMvLink(Long rid) {
        return OutputUtils.success(kuwoService.getMvLink(rid));
    }

    @GetMapping("searchKey")
    public SearchKeyVo getSearchKey(String key) {
        return kuwoService.getSearchKey(key);
    }

    @PostMapping("save")
    public AbstractMessage download(MusicInfo musicInfo) {
        //下载记录
        log.info("下载记录:"+musicInfo.getArtist()+"name:"+musicInfo.getName());
        String key2 = "music_finish" + "a" + musicInfo.getArtist() + "n" + musicInfo.getName();
        String key = "finish_1_" + musicInfo.getName();

        String filename = musicPiP.save(musicInfo);

        File file = new File(filename);
        if (file.isFile()) {
            return OutputUtils.success();
        } else {
            //获取onedrive链接
            String requestUrl = String.format("https://round-mud-838c.huanxi.workers.dev/%s/%s.mp3", musicInfo.getArtist(), musicInfo.getName());
            String url = null;
            try {
                url = NetUtils.getRedirectUrl(requestUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!StringUtils.isEmpty(url)) {
                log.info("使用onedrive连接:" + musicInfo.getName());
                cache.set(key2, "1");
                return OutputUtils.success(url);
            }
            //位置原因
            log.error("下载失败:" + musicInfo.getArtist() + "," + musicInfo.getName());
        }
        return OutputUtils.error();
    }

    @GetMapping("download")
    public ResponseEntity<byte[]> download(HttpServletRequest request, String artist, String name) {
        String filename = musicPiP.getFile(artist, name);
        InputStream in = null;
        byte[] body = null;
        ResponseEntity<byte[]> response = null;
        try {
            in = new FileInputStream(new File(filename));
            body = new byte[in.available()];
            //读入到输入流里面
            in.read(body);
            //设置响应头
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment;filename=" + filename);
            String sendName = new String((name + ".mp3").getBytes("utf-8"), "iso-8859-1");
            headers.setContentDispositionFormData("attachment", sendName);
            headers.setContentLength(body.length);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            response = new ResponseEntity<byte[]>(body, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return response;
    }

    public ReturnMessage downloadList(String key, int pageNo, int pageSize) {
        ReturnMessage res = searcher.search(key, pageNo, pageSize);
        if (res.getCode() == 200) {
            if (!CollectionUtils.isEmpty(res.getData().getList())) {
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

        }
        return res;
    }
}
