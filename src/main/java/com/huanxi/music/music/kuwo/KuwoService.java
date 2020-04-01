package com.huanxi.music.music.kuwo;

import com.alibaba.fastjson.JSON;
import com.huanxi.music.http.request.OkHttp3Request;
import com.huanxi.music.music.kuwo.vo.GetLinkVo;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.UUID;

@Service
public class KuwoService {
    @Value("${app.gateway}")
    private String gateway;
    @Resource
    OkHttp3Request okHttp3Request;

    //获取下载链接
    public GetLinkVo getDownloadLink(Long rid) {
        String url = String.format("http://www.kuwo.cn/url?format=mp3&rid=%d&response=url&type=convert_url3&br=128kmp3&from=web&t=1585664806599&reqId=%s", rid, UUID.randomUUID());
        Response res = okHttp3Request.get(url);
        GetLinkVo a = null;
        try {
            a = JSON.parseObject(res.body().string(), GetLinkVo.class);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            res.close();
        }
        return a;
    }
}
