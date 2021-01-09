package com.huanxi.ngrok.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.log4j.Log4j2;

import java.util.Arrays;

/**
 * @author huanxi
 * @version 1.0
 * @date 2021/1/6 8:55 下午
 */
@Log4j2
public class ClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        log.debug("收到数据长度:" + bytes.length);
        log.debug("收到数据:" + Arrays.toString(bytes));
        log.debug("收到数据:" + new String(bytes));

    }
}
