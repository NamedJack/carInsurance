package com.wongxd.carinsurance.net;



import com.wongxd.carinsurance.bean.PostInfoResultBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by json on 2017/11/2.
 */

public interface InfomationPost {

    @FormUrlEncoded
    @POST("user/saveCarAndPeople")
    Observable<PostInfoResultBean> postInfo(@QueryMap Map<String, Object> options,
                                            @Field("token")  String token);
}
