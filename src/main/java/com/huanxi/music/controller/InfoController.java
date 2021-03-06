package com.huanxi.music.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huanxi.music.common.message.AbstractMessage;
import com.huanxi.music.common.message.OutputUtils;
import com.huanxi.music.http.request.OkHttp3Request;
import com.huanxi.music.nosql.ICache;
import okhttp3.Response;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author huanxi
 * @date 2020/4/12 10:29 上午
 * @email 1355473748@qq.com
 */

@RestController
@RequestMapping("info")
public class InfoController {

    @Resource
    OkHttp3Request okHttp3Request;
    private static final List<String> ENTRANCE_LIST = new ArrayList<>();
    @Resource
    ICache iCache;
    static {
        ENTRANCE_LIST.add("http://music.vaiwan.com/");
    }

    public String getUrl() throws IOException {
        for (String s : ENTRANCE_LIST) {
            Response response = okHttp3Request.get(s);
            String string = response.body().string();
            response.close();
            if (!StringUtils.isEmpty(string)) {
                return s;
            }
        }
        return "";
    }

    @GetMapping
    public AbstractMessage getHomeInfo() {
        Map<String, Object> info = new HashMap<>();
        String linkString = iCache.get("link");
        JSONArray links = JSON.parseArray(linkString);
        info.put("link", links);
        return OutputUtils.success(info);
    }

}
