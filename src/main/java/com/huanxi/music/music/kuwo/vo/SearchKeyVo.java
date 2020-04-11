package com.huanxi.music.music.kuwo.vo;

import lombok.Data;

import java.util.List;

@Data
public class SearchKeyVo {
    private int code;

    private Long curTime;

    private List<String> data;

    private String msg;

    private String profileId;

    private String reqId;


}
