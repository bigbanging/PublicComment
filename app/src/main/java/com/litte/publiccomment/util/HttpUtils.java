package com.litte.publiccomment.util;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.litte.publiccomment.R;
import com.litte.publiccomment.app.MyApp;
import com.litte.publiccomment.bean.TuanBean;
import com.litte.publiccomment.content.IContant;
import com.litte.publiccomment.content.IRetrofit;
import com.squareup.picasso.Picasso;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by litte on 2018/1/22.
 */

public class HttpUtils {
    /*
    大众点评要求的URL格式
GET方式：要访问数据的地址?请求参数
比如：
官方的例子中，要访问的数据地址是：

http://api.dianping.com/v1/business/find_businesses

请求参数包括：appKey="49814079"&sign="MD5字符串"&city="上海UTF8"&category="美食UTF8"

签名对应的MD5字符串生成规则：

根据appKey，secret和所有其它的请求参数键值对拼成一个字符串，
49814079category美食city上海90e3438a41d646848033b6b9d461ed54

然后利用工具类把这个字符串转换为MD5字符串，
最后将MD5字符串中的所有字符都转为大写字母
     */
    public static String getUrl(String url,Map<String,String>paramMap){
        String result = "";
        String sign = getSign(IContant.APPKEY,IContant.APPSECRET,paramMap);
        String query = getQuery(IContant.APPKEY,sign,paramMap);
        result = url+"?"+query;
        return result;
    }
    //Step 1:App Key
    //Step 2:App Secret
    //Step 3: 生成请求签名调用点评API需要生成加密的请求签名，以防止API被盗用。
    // 开发者需要根据请求参数、App Key、App Secret生成签名

    /**
     * 获取请求地址的签名
     * @param appKey
     * @param secret
     * @param paramMap
     * @return
     */
    public static String getSign(String appKey, String secret, Map<String,String> paramMap){
        StringBuilder stringBuilder = new StringBuilder();

// 对参数名进行字典排序
        String[] keyArray = paramMap.keySet().toArray(new String[0]);
        Arrays.sort(keyArray);
// 拼接有序的参数名-值串
        stringBuilder.append(appKey);
        for (String key : keyArray) {
            stringBuilder.append(key).append(paramMap.get(key));
        }
        String codes = stringBuilder.append(secret).toString();
        //纯JAVA环境中，利用Codec对字符串进行SHA1转码采用如下方式
//        String sign = org.apache.commons.codec.digest.DigestUtils.shaHex(codes).toUpperCase();
        //安卓环境中，利用Codec对字符串进行SHA1转码采用如下方式
        String sign = new String(Hex.encodeHex(DigestUtils.sha(codes))).toUpperCase();
        return sign;
    }
    /*
    Step 4: 拼接请求URL
    将App Key、参数、生成的签名（即sign）以及API的访问路径（即apiUrl）拼接成一个URL
     */
    public static String getQuery(String appKey,String sign,Map<String,String> paramMap){
        try {
            // 添加签名
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("appkey=").append(appKey).append("&sign=").append(sign);
            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                stringBuilder.append('&').append(entry.getKey()).append('=').append(URLEncoder.encode(entry.getValue(),"utf8"));
            }
            String queryString = stringBuilder.toString();
            return queryString;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            //抛异常
            throw new RuntimeException("使用了不正确的字符名称");
        }
    }
    //使用HttpUrlConnection测试连接
    public static void testHttpConnection(){

            String url = "http://api.dianping.com/v1/business/find_businesses";
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("city","北京");
            paramMap.put("category","美食");
            final String path = getUrl(url,paramMap);
            Log.i("TAG", "testHttpConnection: 拼接的请求地址Url："+path);
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    try {
                        URL url1 = new URL(path);
                        HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setConnectTimeout(5000);
                        connection.setDoInput(true);
                        connection.connect();
                        int statusCode = connection.getResponseCode();
                        if (statusCode == 200){
                            Log.i("TAG", "testHttpConnection: 获取服务器响应："+statusCode);
                            StringBuilder sb = new StringBuilder();
                            String line = "";
                            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
                            while ((line=reader.readLine())!=null){
                                sb.append(line);
                            }
                            reader.close();
                            Log.i("TAG", "testHttpConnection: 读取的服务器数据："+sb.toString());
                        }else {
                            Log.i("TAG", "testHttpConnection: 服务器响应失败："+statusCode);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
    }
    public static void testVolley(){
        VolleyUtils.getInstance().test();
    }
    public static void getDailyDealsByVolley(String city, Response.Listener<String> listener){
        VolleyUtils.getInstance().getDailyDeals(city,listener);
    }
    public static void getDailyDealsByVolley2(String city, Response.Listener<TuanBean> listener){
        VolleyUtils.getInstance().getDailyDeals2(city,listener);
    }
    public static void loadImage(String url, ImageView imageView){
        VolleyUtils.getInstance().loadImage(url,imageView);
    }
    public static void testRetrofit(){
        /*//1、创建Retrofit对象
        String baseUrl = "http://api.dianping.com/v1/";//访问URL的共同部分
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).
                addConverterFactory(ScalarsConverterFactory.create()).build();
        //2、创建接口的实现类对象
        IRetrofit iRetrofit = retrofit.create(IRetrofit.class);
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("city","北京");
        paramMap.put("category","美食");
        String sign = getSign(IContant.APPKEY,IContant.APPSECRET,paramMap);
        //3）获得请求对象
        Call<String> call = iRetrofit.test(IContant.APPKEY,sign,paramMap);
        //4)将请求队列放到请求队列中
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                Log.i("TAG", "onResponse: response"+response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {

            }
        });*/
        RetrofitUtils.getInstance().test();
    }
    public static void getDealByRetrofit(String city,Callback<String> callback){
        RetrofitUtils.getInstance().getDailDeals(city,callback);
    }
    public static void getDealByRetrofit2(String city,Callback<TuanBean> callback){
        RetrofitUtils.getInstance().getDailyDeal(city,callback);
    }
    public static void getDealByRetrofit3(String city,Callback<TuanBean> callback){
        RetrofitUtils.getInstance().getDailyDeal3(city,callback);
    }
    public static void showImage(String url,ImageView imageView){
        Picasso.with(MyApp.CONTEXT).load(url).placeholder(R.drawable.bucket_no_picture).error(R.drawable.picture_fail).into(imageView);
    }
}
