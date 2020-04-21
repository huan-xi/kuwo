package com.huanxi.music.onedrive;

import lombok.Getter;
import lombok.Setter;

/**
 * @author huanxi
 * @date 2020/4/21 8:29 下午
 * @email 1355473748@qq.com
 */
@Getter
@Setter
public class TokenBean {
    private String token_type;

    private String scope;

    private int expires_in;

    private int ext_expires_in;

    private String access_token;

    private String refresh_token;
}
