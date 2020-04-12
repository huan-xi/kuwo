package com.huanxi.music.music.kuwo;

import com.alibaba.fastjson.JSON;
import com.huanxi.music.http.request.OkHttp3Request;
import com.huanxi.music.music.kuwo.vo.GetLinkVo;
import com.huanxi.music.music.kuwo.vo.SearchKeyVo;
import com.huanxi.music.nosql.ICache;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.Duration;
import java.util.UUID;

@Service
public class KuwoService {
    @Value("${app.gateway}")
    private String gateway;
    @Resource
    OkHttp3Request okHttp3Request;
    @Resource
    ICache cache;

    public String getDownloadLinkCache(Long rid) {
        String link = null;
        GetLinkVo res = getDownloadLink(rid);
        if (res != null && res.getCode() == 200) {
            link = res.getUrl();
        }
        return link;
    }

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

    public SearchKeyVo getSearchKey(String key) {
        if (key == null) {
            key = "";
        }
        String url = String.format("http://www.kuwo.cn/api/www/search/searchKey?key=%s&reqId=%s", key, UUID.randomUUID());
        Response res = okHttp3Request.get(url);
        SearchKeyVo vo = null;
        String cacheKey = "search_key_" + key;
        vo = (SearchKeyVo) cache.getObject(cacheKey, SearchKeyVo.class);
        if (vo != null&&vo.getReqId()!=null) {
            return vo;
        }
        try {
            String str = res.body().string();
            if (!StringUtils.isEmpty(str)) {
                vo = JSON.parseObject(str, SearchKeyVo.class);
                if (vo.getReqId() != null) {
                    cache.set(cacheKey, vo, Duration.ofHours(1));
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vo;
    }
}
