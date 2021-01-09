package com.huanxi.ngrok.client;

import cn.hutool.core.thread.ThreadUtil;
import com.huanxi.ngrok.client.msg.MsgCons;
import com.huanxi.ngrok.client.msg.MsgUtils;

import java.io.IOException;
import java.net.Socket;

/**
 * @author huanxi
 * @version 1.0
 * @date 2021/1/7 4:23 下午
 */
public class HeartbeatThread extends Thread {
    private static HeartbeatThread INSTANCE;
    Socket socket;
    int pingTime = 0;

    public HeartbeatThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while (true) {
            if (pingTime > 20) {
                try {
                    //判断是否下线
                    MsgUtils.writeMsg(socket, MsgCons.PING);
                    pingTime = 0;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            pingTime++;
            ThreadUtil.sleep(1000);
        }
    }

    public static void clearHeartbeat() {
        if (INSTANCE != null) {
            INSTANCE.pingTime = 0;
        }
    }

    public static void startHeartbeat(Socket socket) {
        if (INSTANCE == null) {
            INSTANCE = new HeartbeatThread(socket);
        }
        INSTANCE.start();
    }
}
