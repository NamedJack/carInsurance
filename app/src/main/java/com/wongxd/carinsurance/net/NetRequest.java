package com.wongxd.carinsurance.net;

import android.text.TextUtils;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by json on 2017/11/2.
 */

public class NetRequest {
    private static NetRequest netRequest;
    //    public  String baseUrl ;
    private Retrofit retrofit;
//    private OkHttpClient.Builder builder;

    private NetRequest(String baseUrl) {
        Gson gson = new Gson();
//        builder = HttpClient.getInstance().getBuilder();
        if (TextUtils.isEmpty(baseUrl)) throw new RuntimeException("baseUrl not initial");
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(HttpClient.getInstance().getBuilder().build())
                .build();
    }

    public static NetRequest getInstance(String baseUrl) {
        if (netRequest == null) {
            synchronized (NetRequest.class) {
                if (netRequest == null) {
                    netRequest = new NetRequest(baseUrl);
                }
            }
        }
        return netRequest;
    }

//    public static  class NetRequestHolder(String baseUrl){
//        private static final NetRequest request = new NetRequest(baseUrl);
//    }

    public <T> T create(Class<T> tClass) {
        return retrofit.create(tClass);
    }

}
