package com.huanxi.ngrok.client.msg;

import lombok.Data;

/**
 * @author huanxi
 * @version 1.0
 * @date 2021/1/7 3:52 下午
 */
@Data
public class AuthRep {
    private String Version;

    private String MmVersion;

    private String ClientId;

    private String Error;
}
