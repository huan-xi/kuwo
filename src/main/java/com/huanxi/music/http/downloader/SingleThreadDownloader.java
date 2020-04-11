package com.huanxi.music.http.downloader;

import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@Log4j2
//单线程下载
public class SingleThreadDownloader implements IDownloader {



    @Override
    public void downLoad(String url, String fileName, int retry) {
        downLoad(url, fileName);
    }

    @Override
    public void downLoad(String url, String path, String name, int retry) {
        File pathFile = new File(path);
        if (!pathFile.isDirectory()) {
            pathFile.mkdirs();
        }
        this.downLoad(url, pathFile.getPath() + File.separator + name, retry);
    }

    public void downLoad(String fileUrl, String filename) {
        try {
            URL url = new URL(fileUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10000);
            int code = conn.getResponseCode();
            if (code == 200) {
                int length = conn.getContentLength();
                int fileSize = length / 1024 / 1024;
                log.info("开始下载文件:url" + fileUrl + "文件大小:" + fileSize + "M");
                // 1、在客户端创建一个与服务端文件一样大小的文件
                RandomAccessFile file = new RandomAccessFile(filename, "rw");
                file.setLength(length);
                // 3、下载
                InputStream is = conn.getInputStream();
                byte[] buffer = new byte[1024];
                int len = -1;
                while ((len = is.read(buffer)) != -1) {
                    file.write(buffer, 0, len);
                }
                file.close();
                //cache finish
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] downLoadBytes(String fileUrl) {
        byte[] result = null;
        try {
            URL url = new URL(fileUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10000);
            int code = conn.getResponseCode();

            if (code == 200) {
                result = toByteArray(conn.getInputStream());
            }
        } catch (Exception e) {

        }
        return result;
    }

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }
}
