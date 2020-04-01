package com.huanxi.music.http.downloader;

import org.springframework.stereotype.Service;

public class Downloader {
   /* public boolean downloadBlock(int blockId, String fileUrl, String name, int startIndex, int endIndex, int retry) {
        String fileName = Config.getTempFileName(name);
        boolean res = this.downloadBlock(blockId, fileUrl, fileName, startIndex, endIndex);
        if (!res && retry > 0) {
            System.err.println("文件:" + fileUrl + "第" + blockId + "进行第" + (Config.retryTimes - retry + 1) + "次尝试");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
            downloadBlock(blockId, fileUrl, name, startIndex, endIndex, retry - 1);
        }
        if (res) {
            System.out.println("文件:" + name + "第" + blockId + "下载完成");
            //判断是否下载完成
            int times = DBUtil.incr(name);
            if (times >= Config.blockCount) {
                System.out.println(name + ":下载完成");
                DBUtil.put("finish:" + fileUrl, "ok");
                //移动
                File source = new File(fileName);
                source.renameTo(new File(Config.getSuccessFileName(name)));
            }
        }
        return res;
    }

    public boolean downloadBlock(int blockId, String fileUrl, String filePath, int startIndex, int endIndex) {
        try {
            if (!StringUtil.isBlank(DBUtil.get(fileUrl + blockId))) {
                //缓存
                System.out.println("文件:" + fileUrl + "第" + blockId + "已经下载");
                return true;
            }
            URL url = new URL(fileUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(3000);
            // 设置子线程请求数据的范围
            conn.setRequestProperty("Range", "bytes=" + startIndex + "-" + endIndex);
            int code = conn.getResponseCode();
            if (code == 206) {// 请求部分数据
                InputStream is = conn.getInputStream();
                RandomAccessFile file = new RandomAccessFile(filePath, "rw");
                // 指定从哪个位置开始写数据
                file.seek(startIndex);
                byte[] buffer = new byte[1024];
                int len = -1;
                while ((len = is.read(buffer)) != -1) {
                    file.write(buffer, 0, len);
                }
                file.close();
                //cache finish
                DBUtil.put(fileUrl + blockId, "1");
                return true;
            }
        } catch (Exception e) {
            System.err.println("文件:" + fileUrl + "第" + blockId + "下载错误:" + e.getMessage());
        }
        return false;
    }


    public void downLoad(String fileUrl, String name, int threadCount) {
        try {
            String fileName = Config.getTempFileName(name);

            URL url = new URL(fileUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10000);
            int code = conn.getResponseCode();
            if (code == 200) {
                int length = conn.getContentLength();
                int fileSize = length / 1024 / 1024;
                System.out.println("开始下载文件:url" + fileUrl + "文件大小:" + fileSize + "M");
                // 1、在客户端创建一个与服务端文件一样大小的文件
                RandomAccessFile file = new RandomAccessFile(fileName, "rw");
                file.setLength(length);
                // 3、每个子线程下载数据块 ,下载的起始位置和结束位置
                int blockSize = length / Config.blockCount;

                for (int i = 0; i < Config.blockCount; i++) {

                    // 下载的起始位置和结束位置
                    int startIndex = i * blockSize;
                    int endIndex = i != (threadCount - 1) ? (i + 1) * blockSize - 1 : length - 1;
                    System.out.println("第" + i + "块:" + startIndex + "~" + endIndex);
                    int finalI = i;
                    // 开启子线程下载数据
                    new Thread(() -> {
                        try {
                            AvPipeline.semaphore.acquire();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        this.downloadBlock(finalI, fileUrl, name, startIndex, endIndex, Config.retryTimes);
                        AvPipeline.semaphore.release();
                    }).start();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
