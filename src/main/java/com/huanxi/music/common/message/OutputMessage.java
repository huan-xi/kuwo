package com.huanxi.music.common.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OutputMessage extends AbstractMessage {
    private String msg;
    private int code;

    public OutputMessage(String msg) {
        this.msg = msg;
        this.code = -1;
    }
    public OutputMessage(){
        this.msg = "";
        this.code = 1;
    }

    public OutputMessage(String msg, int code) {
        this.code = code;
        this.msg = msg;
    }
}
