package com.huanxi.music.music.kuwo.vo;

import java.util.List;

public class ReturnData {
    private String total;

    private List<MusicInfo> list;

    public void setTotal(String total){
        this.total = total;
    }
    public String getTotal(){
        return this.total;
    }
    public void setList(List<MusicInfo> list){
        this.list = list;
    }
    public List<MusicInfo> getList(){
        return this.list;
    }
}
