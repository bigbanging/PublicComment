package com.litte.publiccomment.content;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by litte on 2018/1/22.
 */

public interface IRetrofit {
    @GET("business/find_businesses")
    Call<String> test(@Query("appkey") String appKey,@Query("sign") String sign,@QueryMap Map<String,String>paramMap);
}
