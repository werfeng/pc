package com.wf.pc.utils;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private static OkHttpClient client;

    private static final String DEFAULT_MEDIA_TYPE = "application/json; charset=utf-8";

    private static final int CONNECT_TIMEOUT = 5;

    private static final int READ_TIMEOUT = 7;

    /**
     * 单例模式  获取类实例
     *
     * @return client
     */
    private static OkHttpClient getInstance() {
        if (client == null) {
            synchronized (OkHttpClient.class) {
                if (client == null) {
                    client = new OkHttpClient.Builder()
                            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
        return client;
    }

    public static String doGet(String url) {

        try {
            Request request = new Request.Builder().url(url).build();
            // 创建一个请求
            Response response = getInstance().newCall(request).execute();
            String result;
            if (response.body() != null) {
                result = response.body().string();
            } else {
                throw new RuntimeException("exception in OkHttpUtil,response body is null");
            }
            return result;
        } catch (Exception ex) {
            return null;
        }
    }


    public static String doPost(String url, String postBody, String mediaType, String callMethod) {
        try {
            MediaType createMediaType = MediaType.parse(mediaType == null ? DEFAULT_MEDIA_TYPE : mediaType);
            Request request = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(createMediaType, postBody))
                    .build();

            Response response = getInstance().newCall(request).execute();
            String result;
            if (response.body() != null) {
                result = response.body().string();
            } else {
                throw new IOException("exception in OkHttpUtil,response body is null");
            }
            return result;
        } catch (Exception ex) {
            return null;
        }
    }

    public static String doPost(String url, Map<String, String> parameterMap, String callMethod) {
        try {
            List<String> parameterList = new ArrayList<>();
            FormBody.Builder builder = new FormBody.Builder();
            if (parameterMap.size() > 0) {
                parameterMap.keySet().forEach(parameterName -> {
                    String value = parameterMap.get(parameterName);
                    builder.add(parameterName, value);
                    parameterList.add(parameterName + ":" + value);
                });
            }

            FormBody formBody = builder.build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            Response response = getInstance().newCall(request).execute();
            String result;
            if (response.body() != null) {
                result = response.body().string();
            } else {
                throw new IOException("exception in OkHttpUtil,response body is null");
            }
            return result;

        } catch (Exception ex) {
            return null;
        }
    }

    public static void main(String[] args) {
        String doGet = doGet("https://baidurank.aizhan.com/baidu/www.zhihu.com/-1/0/1/position/1/");
        System.out.println(doGet);
    }
}
