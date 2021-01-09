package com.huanxi.music.nosql;

import lombok.Data;

/**
 * @author huanxi
 * @version 1.0
 * @date 2021/1/9 11:01 下午
 */
@Data
public class Link {
    String link;
    String name;

    public Link(String link, String name) {
        this.link = link;
        this.name = name;
    }
}
