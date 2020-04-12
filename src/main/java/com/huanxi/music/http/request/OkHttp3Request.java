package com.huanxi.music.http.request;

import com.huanxi.music.nosql.ICache;
import lombok.extern.log4j.Log4j2;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;


@Component
@Log4j2
public class OkHttp3Request {
    @Resource
    OkHttpClient httpClient;
    @Resource
    ICache cache;

    public Response get(String url) {
        log.info("get:" + url);
        String kwToken = cache.get("kwToken");
        if (kwToken == null) {
            kwToken = "";
        }
        Request request = new Request.Builder()
                .url(url)
                .addHeader("csrf", kwToken)
                .addHeader("Referer", "http://www.kuwo.cn")
                .build();
        try {
            final Call call = httpClient.newCall(request);
            return call.execute();
//            log.info("res:" + response.body().string());
        } catch (Exception e) {
            log.error("调用失败，原因:" + e.getMessage());
        }
        return null;
    }
}
