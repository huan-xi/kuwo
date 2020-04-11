package com.huanxi.music.controller;

import com.huanxi.music.common.message.AbstractMessage;
import com.huanxi.music.common.message.OutputUtils;
import com.huanxi.music.music.kuwo.KuwoService;
import com.huanxi.music.music.kuwo.MusicPiP;
import com.huanxi.music.music.kuwo.Searcher;
import com.huanxi.music.music.kuwo.vo.MusicInfo;
import com.huanxi.music.music.kuwo.vo.ReturnMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("music")
public class Music {

    @Resource
    private ThreadPoolTaskExecutor executor;
    @Resource
    MusicPiP musicPiP;
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

    @PostMapping("save")
    public AbstractMessage download(MusicInfo musicInfo) {
        String filename = musicPiP.save(musicInfo);
        File file = new File(filename);
        if (file.isFile()) {
            return OutputUtils.success();
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
            String sendName = new String((name+".mp3").getBytes("utf-8"), "iso-8859-1");
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
