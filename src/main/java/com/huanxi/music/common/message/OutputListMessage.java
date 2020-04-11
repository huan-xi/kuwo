package com.huanxi.music.common.message;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author huangjiawei
 */
@Getter
@Setter
public class OutputListMessage extends AbstractMessage {
    private Collection data;
    private int code = 0;
    private Long total;

    public OutputListMessage(Collection data, Long total) {
        if (data == null) {
            data = new ArrayList();
        }
        this.data = data;
        this.total = total;
    }
}
