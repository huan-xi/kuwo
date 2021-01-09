package com.huanxi.ngrok.client.msg;

/**
 * @author huanxi
 * @version 1.0
 * @date 2021/1/7 2:16 下午
 */
public class MsgCons {
    public static final String PING = "{\"Type\": \"Ping\", \"Payload\": {}}";
    public static String authMsg = "{\"Type\": \"Auth\", \"Payload\": {\"ClientId\": \"\", \"OS\": \"darwin\", \"Arch\": \"amd64\", \"Version\": \"2\", \"MmVersion\": \"1.7\", \"User\": \"user\", \"Password\": \"\"}}";
    /**
     * 请求隧道
     */
    public static String reqTunnel = "{\"Type\": \"ReqTunnel\", \"Payload\": {\"ReqId\": \"%s\", \"Protocol\": \"http\", \"Hostname\": \"\", \"Subdomain\": \"%s\", \"HttpAuth\": \"\", \"RemotePort\": 0}}";
    public static String regProxy = "{\"Type\": \"RegProxy\", \"Payload\": {\"ClientId\": \"%s\"}}";

}
