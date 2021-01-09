package com.huanxi.ngrok.client.handler;


import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson.JSON;
import com.huanxi.ngrok.client.NgrokContext;
import com.huanxi.ngrok.client.msg.MsgUtils;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

/**
 * @author huanxi
 * @version 1.0
 * @date 2021/1/7 5:31 下午
 */
@Log4j2
public class ReqProxyHandler implements NgrokHandler {
    NgrokContext ngrokContext;

    public ReqProxyHandler(NgrokContext ngrokContext) {
        this.ngrokContext = ngrokContext;
    }

    public void dataReceive() throws IOException {
        String msgBytes = MsgUtils.readMsg(ngrokContext.getSocket());

        //连接本地
        log.debug("收到客户端消息:" + msgBytes);
//        byte[] bytes = IoUtil.readBytes(ngrokContext.getSocket().getInputStream(), false);
//        log.info(bytes);
        String body = "<html><h1>test</h1></html>";
        String header = "HTTP/1.0 502 Bad Gateway" + "\r\n";
        header += "Content-Type: text/html" + "\r\n";
        header += "Content-Length: %d" + "\r\n";
        header += "\r\n" + "%s";
        try {
            ngrokContext.getSocket().getOutputStream().write(String.format(header,body.length(), body).getBytes());
            ngrokContext.getSocket().shutdownOutput();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
