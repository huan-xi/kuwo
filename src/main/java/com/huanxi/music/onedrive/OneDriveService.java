package com.huanxi.music.onedrive;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huanxi.music.nosql.ICache;
import lombok.extern.log4j.Log4j2;
import okhttp3.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author huanxi
 * @date 2020/4/21 8:31 下午
 * @email 1355473748@qq.com
 */
@Service
@Log4j2
public class OneDriveService {
    String clientId = "b797b4ed-e0bc-4f16-8c29-1c3e68e7a87a";
    String clientSecret = "Kg1SiUozOKUGveqMTcUtd91zfAs]7?.-";
    String redirectUri = "https://heymind.github.io/tools/microsoft-graph-api-auth";
    String refreshToken = "OAQABAAAAAAAm-06blBE1TpVMil8KPQ41bcrJpw8PrdoxvtVwVtfdUbprYplbhMcqb-9AsUsSOR_NaMHPlhV2o5K35F4aN26XnGFIXOxBGLIU4jXQZFrw57xg832PSm-Mcvd4IWbnMNNzgaiiisQUsbAYSO5wG6K1WbLhO8vYfuecrGeg2PbKuXXGdOjwhvNfTQn6_kzKpT61e_Ci91CtoYvoEwfKBIT1jFds2DeQfND2y9E3UUSDtIbDZVxo8KpLoORvBEppq9q7dg0BxcscPpt1rPjSSlT2ZWc3TcCoSqA80Ka0B3IT2f7VvUOeJEof2AWxSitbLL3PyZmuy4U3J2P55V63MIed5EC4a9rUFtUxdmZcVDpYzk1kzjH2LJNuKNRwvdl-7Ng-MBWupELkTOjpS0200yWHlLMsa3DeArEZWYkf1pfE8Cgd-OCbjrPZitE5IyvwbNVOzAI9THqi9GGMkKpEWmilIMuOayhbXZBbgzthNO6gL16p2qd5xQ6jRHx6gIhdas3Cbnyq9QHx1A26_JYizbdj5wKOo6PRIrbY8wrWxE1SQ5r3eL6tp5P1Qp_2xvX1jwbf0gdYIbSCYBQre4VYEt4Gi-XAH3cAj4BB3ktHH2dT7PyjRxDkV4Hf_nnKgb1Vk7mHeQG--1a-vlR9dT-_OJTW0oFWVWGRpe2gt57aE9noL_NApgxvHnh2EHfr6M6gziUu2bxA47cNtEcXPfClzq-AT3JzXqp0gCgoClr3oH3cjX79PFDS_GzTj6bRqYkqz5vaFK2tTvCNAbmNjxDaJAQNCgmXpuiRmRCQJOIcG3bczM2x9Xh3TKJcaWzsHW1VVEPY1T_TxQBhcnsPBLUwmQ1WIAA";

    OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(60 * 5, TimeUnit.SECONDS)
            .readTimeout(60 * 5, TimeUnit.SECONDS).build();

    @Resource
    ICache cache;

    public String getAccessToken() {
        String accessToken = cache.get("access_token");
        if (!StringUtils.isEmpty(accessToken)) {
            return accessToken;
        }
        accessToken = getToken();
        return accessToken;
    }

    public String getToken() {
        String rs = cache.get("refresh_token");
        if (!StringUtils.isEmpty(rs)) {
            refreshToken = rs;
        }
        RequestBody body = new FormBody.Builder()
                .add("client_id", clientId)
                .add("redirect_uri", redirectUri)
                .add("client_secret", clientSecret)
                .add("refresh_token", refreshToken)
                .add("grant_type", "refresh_token")
                .build();


        Request request = new Request.Builder()
                .url("https://login.microsoftonline.com/common/oauth2/v2.0/token")
                .post(body)
                .build();
        // Execute the request and retrieve the response.
        String responseBody = null;
        Response response = null;
        try {
            response = client.newCall(request).execute();
            responseBody = response.body().string();
        } catch (IOException e) {

        } finally {
            if (response != null) {
                response.close();
            }
        }
        TokenBean tokenBean = null;
        String token = null;
        try {
            tokenBean = JSON.parseObject(responseBody, TokenBean.class);
            if (!StringUtils.isEmpty(tokenBean.getAccess_token())) {
                String access = tokenBean.getAccess_token();
                cache.set("access_token", access, Duration.ofSeconds(tokenBean.getExpires_in()));
            }
            if (!StringUtils.isEmpty(tokenBean.getRefresh_token())) {
                cache.set("refresh_token", tokenBean.getRefresh_token(), Duration.ofSeconds(tokenBean.getExt_expires_in()));
            }
            token = tokenBean.getAccess_token();
        } catch (Exception e) {
            return null;
        }
        if (StringUtils.isEmpty(token)) {
            log.error("获取token失败!");
        }
        return token;
    }

    public String getDownLoadLink(String fileName) {
        String url = "https://graph.microsoft.com/v1.0/me/drive/root:/" + fileName + "?orderby=size%20desc&select=%40microsoft.graph.downloadUrl&expand=children(select%3Dname,eTag,size,id,folder,file)";
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "bearer " + getAccessToken())
                .build();
        try {
            Response response = client.newCall(request).execute();
            String json = response.body().string();
            JSONObject jsonObject = JSON.parseObject(json);
            return jsonObject.get("@microsoft.graph.downloadUrl").toString();
        } catch (Exception e) {
        }
        return null;
    }

    @Scheduled(fixedRate = 3000 * 1000)
    public void fixed() throws InterruptedException {
        log.info("刷新onedrive token");
        getToken();
    }
}
