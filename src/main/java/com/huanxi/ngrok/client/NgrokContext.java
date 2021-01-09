package com.huanxi.ngrok.client;

import lombok.Data;

import java.net.Socket;

/**
 * @author huanxi
 * @version 1.0
 * @date 2021/1/7 5:28 下午
 */
@Data
public class NgrokContext {
    String host;
    int port;
    Socket socket;
    String clientId;

    public NgrokContext(String host, int port, Socket socket) {
        this.host = host;
        this.port = port;
        this.socket = socket;
    }

    public NgrokContext(NgrokContext ngrokContext) {
        this.host = ngrokContext.getHost();
        this.port = ngrokContext.getPort();
        this.clientId = ngrokContext.getClientId();

    }
}
