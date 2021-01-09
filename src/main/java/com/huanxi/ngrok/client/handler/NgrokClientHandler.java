package com.huanxi.ngrok.client.handler;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.huanxi.ngrok.client.HeartbeatThread;
import com.huanxi.ngrok.client.NgrokContext;
import com.huanxi.ngrok.client.NgrokJavaClient;
import com.huanxi.ngrok.client.msg.MsgCons;
import com.huanxi.ngrok.client.msg.MsgUtils;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.Socket;

/**
 * @author huanxi
 * @version 1.0
 * @date 2021/1/7 3:54 下午
 */
@Log4j2
public class NgrokClientHandler {
    Socket socket;
    NgrokContext ngrokContext;

    public NgrokClientHandler(NgrokContext context) {
        this.socket = context.getSocket();
        this.ngrokContext = context;
    }

    public void msgReceive() throws IOException {
        String msg = MsgUtils.readMsg(ngrokContext.getSocket());
        log.info("收到数据:" + msg);
        JSONObject rsp = JSON.parseObject(msg);
        String type = rsp.getString("Type");
        switch (type) {
            case "AuthResp":
                //保存clientId
                ngrokContext.setClientId(rsp.getJSONObject("Payload").getString("ClientId"));
                HeartbeatThread.startHeartbeat(socket);
                //注册隧道
                MsgUtils.writeMsg(socket, String.format(MsgCons.reqTunnel, RandomUtil.randomString(8), "test123"));
                break;
            case "ReqProxy":
                if (StrUtil.isNotEmpty(ngrokContext.getClientId())) {
                    //建立新链接
                    ThreadUtil.execute(() -> new NgrokJavaClient().run(new NgrokContext(ngrokContext)));
                }
                break;
            case "NewTunnel":
                //隧道建立成功
                String url = rsp.getJSONObject("Payload").getString("Url");
                log.info("隧道建立成功:" + url);
                break;
            case "Pong":
                break;
            default:
                log.error("未知消息类型");
        }
    }
}
