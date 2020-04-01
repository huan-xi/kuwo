package com.huanxi.music.music.kuwo;

import com.huanxi.music.http.request.OkHttp3Request;
import com.huanxi.music.nosql.ICache;
import lombok.extern.log4j.Log4j2;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 解析器
 * 1. 解析cookies 获取csrf
 * 2. 构建请求
 */
@Component
@Log4j2
public class Parser {


    @Resource
    OkHttp3Request okHttp3Request;

    @PostConstruct
    public void init() {
        Response res = okHttp3Request.get("http://www.kuwo.cn");
        res.close();
    }
}
