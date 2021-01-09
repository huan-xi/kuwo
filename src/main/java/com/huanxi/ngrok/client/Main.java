package com.huanxi.ngrok.client;

import java.io.IOException;

/**
 * @author huanxi
 * @version 1.0
 * @date 2021/1/6 8:57 下午
 */
public class Main {
    public static void main(String[] args) throws IOException {
        new NgrokJavaClient().run(new NgrokContext("vaiwan.com", 443, null));
    }
}
