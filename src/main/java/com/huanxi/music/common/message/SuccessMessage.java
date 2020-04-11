package com.huanxi.music.common.message;

import lombok.Getter;
import lombok.Setter;

/**
 * @author huangjiawei
 */
@Getter
@Setter
public class SuccessMessage extends AbstractMessage{
    private Object data;
    private int code = 0;

    public SuccessMessage(){
        data = "";
    }

    public SuccessMessage(Object data){
        this.data = data;
    }
}
