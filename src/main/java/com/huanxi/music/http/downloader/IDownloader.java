package com.huanxi.music.http.downloader;

public interface IDownloader {
     void downLoad(String url, String fileName, int retry);
     void downLoad(String url,String path, String name, int retry);

      byte[] downLoadBytes(String fileUrl);
}
