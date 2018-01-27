package com.litte.publiccomment.util;

import android.util.Log;

import com.litte.publiccomment.bean.CityBean;
import com.litte.publiccomment.bean.TuanBean;
import com.litte.publiccomment.content.IContant;
import com.litte.publiccomment.content.IRetrofit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
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
    private OkHttpClient okHttpClient;
    public RetrofitUtils() {
        //创建OkHttpClient的拦截器
        okHttpClient = new OkHttpClient.Builder().addInterceptor(new MyOkHttpInterceptor()).build();
        retrofit = new Retrofit.Builder().client(okHttpClient).baseUrl(IContant.BASEURL).
                addConverterFactory(ScalarsConverterFactory.create()).
                addConverterFactory(GsonConverterFactory.create()).build();
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

    public void getDailDeals(String city,final Callback<String> callBack2){
        //1、创建Retrofit对象
        final String baseUrl1 = "http://api.dianping.com/v1/";
        Retrofit retrofit1 = new Retrofit.Builder().baseUrl(baseUrl1).
                addConverterFactory(ScalarsConverterFactory.create()).build();
        //2、创建接口的实现类型
        IRetrofit iRetrofit1 = retrofit1.create(IRetrofit.class);
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("city",city);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
        paramMap.put("date",date);
        String sign = HttpUtils.getSign(IContant.APPKEY,IContant.APPSECRET,paramMap);
        Call<String> call = iRetrofit1.deal_id(IContant.APPKEY, sign, paramMap);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                /* {
                    "status": "OK",
                        "count": 309,
                        "id_list": ["1-33946","1-4531","1-4571",  "1-5336","1-5353", "......" ]}*/
                try {
                    JSONObject jsonObject = new JSONObject(response.body());
                    JSONArray jsonArray = jsonObject.getJSONArray("id_list");
                    int size = jsonArray.length();
                    if (size>40){
                        size = 40;
                    }
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i <size ; i++) {
                        String id = jsonArray.getString(i);
                        sb.append(id+",");
                    }
                    if (sb.length()>0) {
                        String idList = sb.substring(0, sb.length() - 1);
                        Retrofit retrofit2 = new Retrofit.Builder().baseUrl(baseUrl1).
                                addConverterFactory(ScalarsConverterFactory.create()).build();
                        IRetrofit iRetrofit2 = retrofit2.create(IRetrofit.class);
                        Map<String, String> paramMap2 = new HashMap<String, String>();
                        paramMap2.put("deal_ids",idList);
                        String sign2 = HttpUtils.getSign(IContant.APPKEY,IContant.APPSECRET,paramMap2);
                        Call<String> call2 = iRetrofit2.deal(IContant.APPKEY, sign2, paramMap2);
                        call2.enqueue(callBack2);
                    }else {
                        callBack2.onResponse(call, response);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {

            }
        });
    }
    public void getDailyDeal(String city, final Callback<TuanBean> callBack){
        final String baseUrl = "http://api.dianping.com/v1/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).
                addConverterFactory(ScalarsConverterFactory.create()).build();
        IRetrofit iRetrofit = retrofit.create(IRetrofit.class);
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("city",city);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
        paramMap.put("date",date);
        final String sign = HttpUtils.getSign(IContant.APPKEY,IContant.APPSECRET,paramMap);
        Call<String> call = iRetrofit.deal_id(IContant.APPKEY, sign, paramMap);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body());
                    JSONArray id_list = jsonObject.getJSONArray("id_list");
                    StringBuilder sb = new StringBuilder();
                    int size = id_list.length();
                    if (size>40){
                        size = 40;
                    }
                    for (int i = 0; i < size; i++) {
                        String ids = id_list.getString(i);
                        sb.append(ids+",");
                    }
                    if (sb.length()>0){
                        String idList = sb.substring(0,sb.length()-1);
                        Retrofit retrofit1 = new Retrofit.Builder().baseUrl(baseUrl).
                                addConverterFactory(ScalarsConverterFactory.create()).
                                addConverterFactory(GsonConverterFactory.create()).build();
                        IRetrofit iRetrofit1 = retrofit1.create(IRetrofit.class);
                        Map<String, String> paramMap1 = new HashMap<String, String>();
                        paramMap1.put("deal_ids",idList);
                        String sign1 = HttpUtils.getSign(IContant.APPKEY,IContant.APPSECRET,paramMap1);
                        Call<TuanBean> tuanBeanCall = iRetrofit1.deal2(IContant.APPKEY, sign1, paramMap1);
                        tuanBeanCall.enqueue(callBack);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            /**
             * OKHttp的拦截器
             * @param call
             * @param throwable
             */
            @Override
            public void onFailure(Call<String> call, Throwable throwable) {

            }
        });
    }
    public class MyOkHttpInterceptor implements Interceptor{

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            HttpUrl url = request.url();
            //请求路径
            //比如：http://baseurl/deal/get_daily_new_id_list?city=xxx&date=xxx
            HashMap<String,String> params = new HashMap<>();

            Set<String> set = url.queryParameterNames();

            for (String key: set) {
                params.put(key,url.queryParameter(key));
            }
            String sign = HttpUtils.getSign(IContant.APPKEY,IContant.APPSECRET,params);
            String urlString = url.toString();

            Log.i("TAG", "intercept:OKHTTP原始请求路径 "+urlString);
            StringBuilder sb = new StringBuilder(urlString);
            if (set.size() == 0){
                sb.append("?");
            }else {
                sb.append("&");
            }
            sb.append("appkey=").append(IContant.APPKEY);
            sb.append("&").append("sign=").append(sign);
            //http://baseurl/deal/get_daily_new_id_list?city=xxx&date=xxx&appkey=xxx&sign=xxx
            Request newRequest = new Request.Builder().url(sb.toString()).build();
            Log.i("TAG", "intercept: 经过拼接后的请求路径"+sb.toString());
            return chain.proceed(newRequest);
        }
    }
    public void getDailyDeal3(String city, final Callback<TuanBean> callback){
        Map<String, String> params = new HashMap<>();
        params.put("city",city);
        params.put("date",new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis()));
        Call<String> call = iRetrofit.deal_id3(params);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    String body = response.body();
                    Log.i("TAG", "onResponse: "+body);
                    JSONObject jsonObject = new JSONObject(body);
                    JSONArray id_list = jsonObject.getJSONArray("id_list");

                    int size = id_list.length();
                    if (size>40){
                        size = 40;
                    }
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < size; i++) {
                        String string = id_list.getString(i);
                        sb.append(string).append(",");
                    }
                    if (sb.length()>0){
                        String substring = sb.substring(0, sb.length() - 1);
                        Map<String, String> paramsMap = new HashMap<String, String>();
                        paramsMap.put("deal_ids",substring);
                        Call<TuanBean> tuanBeanCall = iRetrofit.deal3(paramsMap);
                        tuanBeanCall.enqueue(callback);
                    }else {
                        callback.onResponse(null, null);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {

            }
        });
    }
    public void getCities(Callback<CityBean> callBack){
        Map<String, String> paramMap = new HashMap<>();
        Call<CityBean> cityBeanCall = iRetrofit.deal_city(paramMap);
        cityBeanCall.enqueue(callBack);
    }
}
