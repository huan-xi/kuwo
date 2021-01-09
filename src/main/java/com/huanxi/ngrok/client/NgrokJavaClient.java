package com.huanxi.ngrok.client;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import com.huanxi.ngrok.client.handler.NgrokClientHandler;
import com.huanxi.ngrok.client.handler.ReqProxyHandler;
import com.huanxi.ngrok.client.msg.MsgCons;
import com.huanxi.ngrok.client.msg.MsgUtils;
import com.huanxi.ngrok.util.BytesUtils;
import com.huanxi.ngrok.util.SslUtils;
import lombok.extern.log4j.Log4j2;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;

/**
 * @author huanxi
 * @version 1.0
 * @date 2021/1/7 2:07 下午
 */
@Log4j2
public class NgrokJavaClient {


    public void run(NgrokContext ngrokContext) {
        Socket socket = null;
        try {
            socket = SslUtils.getNoneServerContext().getSocketFactory().createSocket();
            socket.connect(new InetSocketAddress(ngrokContext.getHost(), ngrokContext.getPort()));
            log.info(String.format("连接建立%s:%s", ngrokContext.getHost(), ngrokContext.getPort()));
            //启动心跳包线程
            ngrokContext.setSocket(socket);
            boolean isProxy = StrUtil.isNotEmpty(ngrokContext.getClientId());
            if (isProxy) {
                //注册代理
                MsgUtils.writeMsg(socket, String.format(MsgCons.regProxy, ngrokContext.getClientId()));
                try {
                    new ReqProxyHandler(ngrokContext).dataReceive();
                } catch (IOException e) {
                    log.error("处理消息错误:", e);
                }
            } else {
                //启动认证
                MsgUtils.writeMsg(socket, MsgCons.authMsg);
                NgrokClientHandler ngrokClientHandler = new NgrokClientHandler(ngrokContext);
                while (!socket.isClosed() && socket.isConnected()) {
                    ngrokClientHandler.msgReceive();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
