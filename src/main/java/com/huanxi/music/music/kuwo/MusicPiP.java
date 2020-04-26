package com.huanxi.music.music.kuwo;

import com.huanxi.music.http.downloader.IDownloader;
import com.huanxi.music.music.kuwo.vo.GetLinkVo;
import com.huanxi.music.music.kuwo.vo.MusicInfo;
import com.huanxi.music.nosql.ICache;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.ID3v24Tag;
import com.mpatric.mp3agic.Mp3File;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.File;

@Service
@Log4j2
public class MusicPiP {
    @Resource
    KuwoService kuwoService;
    @Resource
    IDownloader downloader;

    @Value("${app.path}")
    private String downloadPath;

    @Resource
    ICache cache;

    public String getFile(String artist, String name) {
        File path = new File(downloadPath + File.separator + artist);
        String fileName = path.getPath() + File.separator + name + ".mp3";
        return fileName;
    }

    public String download(MusicInfo musicInfo) {
        File path = new File(downloadPath + File.separator + musicInfo.getArtist());
        String fileName = path.getPath() + File.separator + musicInfo.getName() + ".mp3";
        if (!path.isDirectory()) {
            path.mkdirs();
        }
        if (new File(fileName).isFile()) {
            return fileName;
        }
        //获取input
        try {
            GetLinkVo getLinkVo = kuwoService.getDownloadLink(musicInfo.getRid());
            if (getLinkVo == null) {
                return fileName;
            }
            String folder = System.getProperty("java.io.tmpdir");
            String tmpMp3 = folder + musicInfo.getName();
            //下载到临时目录
            downloader.downLoad(getLinkVo.getUrl(), tmpMp3, 10);
            Mp3File mp3File = new Mp3File(tmpMp3);
            ID3v2 v2 = new ID3v24Tag();
            v2.setArtist(musicInfo.getArtist());
            v2.setAlbum(musicInfo.getAlbum());
            v2.setTitle(musicInfo.getName());
            v2.setTrack(String.valueOf(musicInfo.getTrack()));
            v2.setYear(musicInfo.getReleaseDate());
            v2.setAlbumImage(downloader.downLoadBytes(musicInfo.getAlbumpic()), "image/jpg");
            //下载图片
            mp3File.setId3v2Tag(v2);
            mp3File.save(fileName);
            new File(tmpMp3).delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

    public String save(MusicInfo musicInfo) {
        String key = "finish_1_" + musicInfo.getName();
        String key2 = "music_finish" + "a" + musicInfo.getArtist() + "n" + musicInfo.getName();
        File path = new File(downloadPath + File.separator + musicInfo.getArtist());
        String fileName = path.getPath() + File.separator + musicInfo.getName() + ".mp3";
        if (!path.isDirectory()) {
            path.mkdirs();
        }
        if (!StringUtils.isEmpty(cache.get(key))) {
            log.info("已经下载:" + musicInfo.getName());
            cache.set(key2, "1");
            return fileName;
        } else if (!StringUtils.isEmpty(cache.get(key2))) {
            log.info("已经下载:" + musicInfo.getName());
            return fileName;
        }

        //获取input
        try {
            GetLinkVo getLinkVo = kuwoService.getDownloadLink(musicInfo.getRid());
            if (getLinkVo == null) {
                return fileName;
            }
            String folder = System.getProperty("java.io.tmpdir");
            String tmpMp3 = folder + musicInfo.getName();
            //下载到临时目录
            downloader.downLoad(getLinkVo.getUrl(), tmpMp3, 10);
            Mp3File mp3File = new Mp3File(tmpMp3);
            ID3v2 v2 = new ID3v24Tag();
            v2.setArtist(musicInfo.getArtist());
            v2.setAlbum(musicInfo.getAlbum());
            v2.setTitle(musicInfo.getName());
            v2.setTrack(String.valueOf(musicInfo.getTrack()));
            v2.setYear(musicInfo.getReleaseDate());
            v2.setAlbumImage(downloader.downLoadBytes(musicInfo.getAlbumpic()), "image/jpg");
            //下载图片
            mp3File.setId3v2Tag(v2);
            mp3File.save(fileName);
            cache.set(key, "ok");
            cache.set(key2, "1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }
}
