package com.litte.publiccomment.content;

import com.litte.publiccomment.bean.CityBean;
import com.litte.publiccomment.bean.TuanBean;

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
    @GET("deal/get_daily_new_id_list")
    Call<String> deal_id(@Query("appkey")String appKey,@Query("sign")String sign,@QueryMap Map<String,String>paramMap);
    @GET("deal/get_batch_deals_by_id")
    Call<String> deal(@Query("appkey") String appKey,@Query("sign") String sign,@QueryMap Map<String,String> paramMap);
    @GET("deal/get_batch_deals_by_id")
    Call<TuanBean> deal2(@Query("appkey") String appKey, @Query("sign") String sign, @QueryMap Map<String,String> paramMap);


    @GET("deal/get_daily_new_id_list")
    Call<String> deal_id3(@QueryMap Map<String,String>paramMap);
    @GET("deal/get_batch_deals_by_id")
    Call<TuanBean> deal3(@QueryMap Map<String,String> paramMap);
    @GET("metadata/get_cities_with_businesses")
    Call<CityBean> deal_city(@QueryMap Map<String,String> paramMap);
}
