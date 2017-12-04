package com.wongxd.carinsurance.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by json on 2017/11/2.
 */

public class HttpClient {
    private static final int DEFAULT_TIME = 10;
    private static final int READ_TIME = 20;
    private static final int WRITE_TIME = 20;
    private static HttpClient instance;
    private static OkHttpClient.Builder builder;

    public HttpClient() {
        builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME, TimeUnit.SECONDS);
        builder.readTimeout(READ_TIME, TimeUnit.SECONDS);
        builder.writeTimeout(WRITE_TIME, TimeUnit.SECONDS);
    }

    public static HttpClient getInstance() {
        if (instance == null) {
            synchronized (HttpClient.class) {
                if (instance == null) {
                    instance = new HttpClient();
                }
            }
        }
        return instance;
    }

    public OkHttpClient.Builder getBuilder() {
        return builder;
    }
}
