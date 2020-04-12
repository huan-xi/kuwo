package com.huanxi.music.music.kuwo;

import com.alibaba.fastjson.JSON;
import com.huanxi.music.http.request.OkHttp3Request;
import com.huanxi.music.music.kuwo.vo.ReturnMessage;
import com.huanxi.music.music.kuwo.vo.SearchResultVo;
import com.huanxi.music.nosql.ICache;
import lombok.extern.log4j.Log4j2;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

/**
 * 搜索器
 */
@Service
@Log4j2
public class Searcher {
    @Resource
    private OkHttp3Request okHttp3Request;
    @Value("${app.gateway}")
    private String gateway;
    @Resource
    ICache cache;

    //http://www.kuwo.cn/api/www/search/searchMusicBykeyWord?key=%E5%91%A8%E6%9D%B0%E4%BC%A6&pn=1&rn=30&reqId=ebb74000-734e-11ea-ac7b-1b0664ec89bc
    public ReturnMessage search(String key) {
        return search(key, 1, 30);
    }

    public ReturnMessage search(String key, int pageNo, int pageSize) {
        String cacheKey = "music_list_" + key + "pageNo_" + pageNo + pageSize;
        ReturnMessage returnMessage = (ReturnMessage) cache.getObject(cacheKey, ReturnMessage.class);
        if (returnMessage != null && returnMessage.getReqId() != null) {
            return returnMessage;
        }
        String url = String.format(gateway + "/api/www/search/searchMusicBykeyWord?key=%s&pn=%d&rn=%d&reqId=%s", key, pageNo, pageSize, UUID.randomUUID());
        Response res = okHttp3Request.get(url);

        try {
            String str = res.body().string();

            returnMessage = JSON.parseObject(str, ReturnMessage.class);
            if (returnMessage.getReqId() != null) {
                cache.set(cacheKey, str, Duration.ofDays(1));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            res.close();
        }
        return returnMessage;
    }


}
