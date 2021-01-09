package com.huanxi.ngrok.client.msg;

import cn.hutool.core.io.IoUtil;
import com.huanxi.ngrok.client.HeartbeatThread;
import com.huanxi.ngrok.util.BytesUtils;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.Socket;

/**
 * @author huanxi
 * @version 1.0
 * @date 2021/1/7 2:31 下午
 */
@Log4j2
public class MsgUtils {
    public static void writeMsg(Socket socket, String str) throws IOException {
        HeartbeatThread.clearHeartbeat();
        log.debug("发送数据:" + str);
        byte[] len = BytesUtils.longToBytes(str.length());
        socket.getOutputStream().write(BytesUtils.concat(len, str.getBytes()));
        socket.getOutputStream().flush();
    }


    public static String readMsg(Socket socket) throws IOException {
        byte[] lenByte = IoUtil.readBytes(socket.getInputStream(), 8);
        long len = BytesUtils.bytesToLong(lenByte);
        return new String(IoUtil.readBytes(socket.getInputStream(), (int) len));
    }
}
