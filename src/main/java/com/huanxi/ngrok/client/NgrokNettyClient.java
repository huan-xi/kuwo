package com.huanxi.ngrok.client;

import cn.hutool.core.thread.ThreadUtil;
import com.huanxi.ngrok.client.handler.ClientHandler;
import com.huanxi.ngrok.util.SslUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslHandler;
import lombok.extern.log4j.Log4j2;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author huanxi
 * @version 1.0
 * @date 2021/1/6 8:53 下午
 */
@Log4j2
public class NgrokNettyClient {
    public void run() throws IOException {
        SSLContext sslCtx = SslUtils.getNoneServerContext();

        SSLEngine sslEngine = sslCtx.createSSLEngine();
        //设置加密套件
        sslEngine.setUseClientMode(true);
        sslEngine.setNeedClientAuth(false);

        //设置一个多线程循环器
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        //启动附注类
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                //通道是NioSocketChannel
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addFirst("ssl", new SslHandler(sslEngine,true));
                        ch.pipeline().addLast(new ByteArrayEncoder());
                        ch.pipeline().addLast(new ByteArrayDecoder());
                        ch.pipeline().addLast(new ClientHandler());


                    }
                });

        try {
            //连接服务
            Channel channel = bootstrap
                    .connect("vaiwan.com", 443)
                    .sync()
                    .channel();

            byte[] testBytes = {-110, 0, 0, 0, 0, 0, 0, 0, 123, 34, 84, 121, 112, 101, 34, 58, 32, 34, 65, 117, 116, 104, 34, 44, 32, 34, 80, 97, 121, 108, 111, 97, 100, 34, 58, 32, 123, 34, 67, 108, 105, 101, 110, 116, 73, 100, 34, 58, 32, 34, 34, 44, 32, 34, 79, 83, 34, 58, 32, 34, 100, 97, 114, 119, 105, 110, 34, 44, 32, 34, 65, 114, 99, 104, 34, 58, 32, 34, 97, 109, 100, 54, 52, 34, 44, 32, 34, 86, 101, 114, 115, 105, 111, 110, 34, 58, 32, 34, 50, 34, 44, 32, 34, 77, 109, 86, 101, 114, 115, 105, 111, 110, 34, 58, 32, 34, 49, 46, 55, 34, 44, 32, 34, 85, 115, 101, 114, 34, 58, 32, 34, 117, 115, 101, 114, 34, 44, 32, 34, 80, 97, 115, 115, 119, 111, 114, 100, 34, 58, 32, 34, 34, 125, 125};
            channel.writeAndFlush(testBytes);
            ThreadUtil.sleep(1000000);
        } catch (InterruptedException e) {
            log.error("服务器连接失败", e);
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
