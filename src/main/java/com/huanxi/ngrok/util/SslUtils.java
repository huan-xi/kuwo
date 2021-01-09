package com.huanxi.ngrok.util;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

class NoneTM implements TrustManager, X509TrustManager {
    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }
}

/**
 * @author huanxi
 * @version 1.0
 * @date 2021/1/6 8:48 下午
 */
public class SslUtils {
    private static SSLContext SERVER_CONTEXT;

    /**
     * 获取ssl
     *
     * @return
     */
    public static SSLContext getNoneServerContext() {
        if (SERVER_CONTEXT == null) {
            TrustManager[] trustAllCerts = new TrustManager[1];
            TrustManager tm = new NoneTM();
            trustAllCerts[0] = tm;
            try {
                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, null);
                SERVER_CONTEXT = sc;
            } catch (Exception e) {
                throw new Error("Failed to initialize the client-side SSLContext", e);
            }
        }
        return SERVER_CONTEXT;
    }
}
