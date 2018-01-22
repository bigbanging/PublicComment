package com.litte.publiccomment.util;

import android.util.Log;

import com.litte.publiccomment.content.IContant;
import com.litte.publiccomment.content.IRetrofit;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by litte on 2018/1/22.
 */

public class RetrofitUtils {
    //1、
    private static RetrofitUtils INSTANCE;
    public synchronized static RetrofitUtils getInstance(){
        if (INSTANCE == null){
            INSTANCE = new RetrofitUtils();
        }
        return INSTANCE;
    }
    private Retrofit retrofit;
    private IRetrofit iRetrofit;
    public RetrofitUtils() {
        retrofit = new Retrofit.Builder().baseUrl(IContant.BASEURL).
                addConverterFactory(ScalarsConverterFactory.create()).build();
        iRetrofit = retrofit.create(IRetrofit.class);
    }
    public void test(){
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("city","北京");
        paramMap.put("category","美食");
        String sign = HttpUtils.getSign(IContant.APPKEY,IContant.APPSECRET, paramMap);
        Call<String> call = iRetrofit.test(IContant.APPKEY,sign,paramMap);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("TAG", "onResponse:Retrofit获得的网络相应 response:"+response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {

            }
        });
    }

}
