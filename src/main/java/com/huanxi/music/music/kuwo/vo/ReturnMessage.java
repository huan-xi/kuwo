package com.huanxi.music.music.kuwo.vo;

public class ReturnMessage {
    private int code;

    private Long curTime;

    private ReturnData data;

    private String msg;

    private String profileId;

    private String reqId;

    public void setCode(int code){
        this.code = code;
    }
    public int getCode(){
        return this.code;
    }
    public void setCurTime(Long curTime){
        this.curTime = curTime;
    }
    public Long getCurTime(){
        return this.curTime;
    }
    public void setData(ReturnData data){
        this.data = data;
    }
    public ReturnData getData(){
        return this.data;
    }
    public void setMsg(String msg){
        this.msg = msg;
    }
    public String getMsg(){
        return this.msg;
    }
    public void setProfileId(String profileId){
        this.profileId = profileId;
    }
    public String getProfileId(){
        return this.profileId;
    }
    public void setReqId(String reqId){
        this.reqId = reqId;
    }
    public String getReqId(){
        return this.reqId;
    }
}
