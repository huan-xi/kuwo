package com.huanxi.music.music.kuwo.vo;

public class PayInfo {
    private String play;

    private String local_encrypt;

    private int cannotDownload;

    private int cannotOnlinePlay;

    private FeeType feeType;

    private String down;

    public void setPlay(String play){
        this.play = play;
    }
    public String getPlay(){
        return this.play;
    }
    public void setLocal_encrypt(String local_encrypt){
        this.local_encrypt = local_encrypt;
    }
    public String getLocal_encrypt(){
        return this.local_encrypt;
    }
    public void setCannotDownload(int cannotDownload){
        this.cannotDownload = cannotDownload;
    }
    public int getCannotDownload(){
        return this.cannotDownload;
    }
    public void setCannotOnlinePlay(int cannotOnlinePlay){
        this.cannotOnlinePlay = cannotOnlinePlay;
    }
    public int getCannotOnlinePlay(){
        return this.cannotOnlinePlay;
    }
    public void setFeeType(FeeType feeType){
        this.feeType = feeType;
    }
    public FeeType getFeeType(){
        return this.feeType;
    }
    public void setDown(String down){
        this.down = down;
    }
    public String getDown(){
        return this.down;
    }
}
