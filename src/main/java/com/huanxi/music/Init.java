package com.huanxi.music;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author huanxi
 * @date 2020/7/30 9:08 下午
 * @email 1355473748@qq.com
 */
@Component
public class Init {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @PostConstruct
    public void init(){
        Map<String, String> link = new HashMap<>();
        link.put("https://www.my404.cn", "木尤小站");
        link.put("http://ebook.chengfeng8.top/", "免费小说");
        link.put("https://www.baidu.com", "百度一下");
        link.put("https://music.shengxiansen.xyz", "生先森音乐网");
        link.put("https://mamu.xls0514.top/", "Mc麻木音乐网");
        link.put("http://wlmqlww.xj917.com", "乌鲁木齐论文网");
        link.put("http://www.ifreetube.com", "免费商用音乐");
        link.put("https://www.iwangl.com", "爱网络导航");
        link.put("http://www.0558.la/", "自动秒收录");
        link.put("https://www.zdslc.com/", "站点收录池");
        link.put("http://vip.dytt666.net", "免费优酷vip视频");
        link.put("https://huan-xi.gitee.io", "huanxi'blog");
        redisTemplate.delete("link");
        redisTemplate.opsForHash().putAll("link", link);
    }
}
