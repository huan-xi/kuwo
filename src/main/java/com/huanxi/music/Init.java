package com.huanxi.music;

import com.huanxi.music.nosql.ICache;
import com.huanxi.music.nosql.Link;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huanxi
 * @date 2020/7/30 9:08 下午
 * @email 1355473748@qq.com
 */
@Component
public class Init {
    @Autowired
    private ICache iCache;
    @PostConstruct
    public void init(){
        //[{link,name}]
        List<Link> links = new ArrayList<>();
        links.add(new Link("https://www.my404.cn", "木尤小站"));
        links.add(new Link("http://ebook.chengfeng8.top/", "免费小说"));
        links.add(new Link("https://www.baidu.com", "百度一下"));
        links.add(new Link("https://music.shengxiansen.xyz", "生先森音乐网"));
        links.add(new Link("https://mamu.xls0514.top/", "Mc麻木音乐网"));
        links.add(new Link("http://wlmqlww.xj917.com", "乌鲁木齐论文网"));
        links.add(new Link("http://www.ifreetube.com", "免费商用音乐"));
        links.add(new Link("http://vip.dytt666.net", "免费优酷vip视频"));
        links.add(new Link("https://www.zdslc.com/", "站点收录池"));
        links.add(new Link("http://www.0558.la/", "自动秒收录"));
        links.add(new Link("https://www.iwangl.com", "爱网络导航"));
        links.add(new Link("https://huan-xi.gitee.io", "huanxi'blog"));
        iCache.delete("link");
        iCache.set("link", links);
    }
}
