package com.huanxi.music.music.kuwo.vo;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class MusicInfo {
    private String musicrid;

    private String artist;

    private Mvpayinfo mvpayinfo;

    private String pic;

    private int isstar;

    private Long rid;

    private int duration;

    private String content_type;

    private int track;

    private boolean hasLossless;

    private int hasmv;

    private String releaseDate;

    private String album;

    private int albumid;

    private String pay;

    private int artistid;

    private String albumpic;

    private String songTimeMinutes;

    private boolean isListenFee;

    private String pic120;

    private String name;

    private int online;

    private PayInfo payInfo;


}
