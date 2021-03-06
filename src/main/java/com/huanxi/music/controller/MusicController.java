package com.huanxi.music.controller;

import com.huanxi.music.common.IpUtils;
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
import com.huanxi.music.onedrive.OneDriveService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
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
import java.io.*;
import java.net.URLDecoder;
import java.time.Duration;

/**
 * @author huanxi
 */
@RestController
@Log4j2
@RequestMapping("music")
public class MusicController {

    @Resource
    private ThreadPoolTaskExecutor executor;
    @Resource
    MusicPiP musicPiP;
    @Resource
    ICache cache;
    @Resource
    private Searcher searcher;
    @Resource
    OneDriveService oneDriveService;
    @Resource
    private KuwoService kuwoService;

    /**
     * 获取歌曲列表
     *
     * @param key
     * @param pageNo
     * @param pageSize
     * @return
     */
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

    /**
     * 先保存后下载
     *
     * @param musicInfo
     * @return
     */
    @PostMapping("save")
    public AbstractMessage download(MusicInfo musicInfo) {
        String cacheKey = getCacheKey(musicInfo.getArtist(), musicInfo.getName());
        if (cache.hasKey(cacheKey)) {
            return OutputUtils.error(musicInfo.getArtist() + ":" + musicInfo.getName() + "下载过频繁");
        }
        //获取onedrive
        String filename = musicPiP.download(musicInfo);
        File file = new File(filename);
        if (file.isFile()) {
            return OutputUtils.success();
        } else {
            //未知原因
            log.error("下载失败:" + musicInfo.getArtist() + "," + musicInfo.getName());
        }
        return OutputUtils.error();
    }

    /**
     * 本地下载文件
     *
     * @param request
     * @param artist
     * @param name
     * @return
     * @throws UnsupportedEncodingException
     */
    @GetMapping("download")
    public ResponseEntity<byte[]> download(HttpServletRequest request, String artist, String name) throws UnsupportedEncodingException {
        //artist 处理
        String text = request.getQueryString();
        artist = getSubString(text, "artist=", "&name");
        artist = URLDecoder.decode(artist, "UTF-8");
        String key = getCacheKey(artist, name);
        String filename = musicPiP.getFile(artist, name);
        log.info("用户下载文件:" + filename);
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
            IOUtils.closeQuietly(in);
        }
        cache.set(key, "1", Duration.ofSeconds(60));
        return response;
    }

    private String getCacheKey(String artist, String name) {
        return "cache:ip:" + IpUtils.getIP() + "artist:" + artist + "name:" + name;
    }

    /**
     * 取两个文本之间的文本值
     *
     * @param text  源文本 比如：欲取全文本为 12345
     * @param left  文本前面
     * @param right 后面文本
     * @return 返回 String
     */
    private static String getSubString(String text, String left, String right) {
        String result = "";
        int zLen;
        if (left == null || left.isEmpty()) {
            zLen = 0;
        } else {
            zLen = text.indexOf(left);
            if (zLen > -1) {
                zLen += left.length();
            } else {
                zLen = 0;
            }
        }
        int yLen = text.indexOf(right, zLen);
        if (yLen < 0 || right == null || right.isEmpty()) {
            yLen = text.length();
        }
        result = text.substring(zLen, yLen);
        return result;
    }


    public ReturnMessage downloadList(String key, int pageNo, int pageSize) {
        ReturnMessage res = searcher.search(key, pageNo, pageSize);
        if (res.getCode() == 200) {
            if (!CollectionUtils.isEmpty(res.getData().getList())) {
                for (MusicInfo musicInfo : res.getData().getList()) {
                    //数据持久化 //
                    try {
                        executor.execute(() -> musicPiP.download(musicInfo));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

        }
        return res;
    }
}
