package com.litte.publiccomment.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.litte.publiccomment.R;
import com.litte.publiccomment.app.MyApp;
import com.litte.publiccomment.bean.TuanBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by litte on 2018/1/22.
 */

public class VolleyUtils {
    //1)声明一个私有的静态属性
    private static VolleyUtils INSTANCE;
    //2)声明一个共有的静态的获取属性的方法
    /*public synchronized static VolleyUtils getINSTANCE(){
        if (INSTANCE == null){
            INSTANCE = new VolleyUtils();
        }
        return INSTANCE;
    }*/
    //提升同步效率---------懒汉式单例
    public static VolleyUtils getInstance(){
        if (INSTANCE == null){
            synchronized (VolleyUtils.class){
                if (INSTANCE == null) {
                    INSTANCE = new VolleyUtils();
                }
            }
        }
        return INSTANCE;
    }
    RequestQueue queue = null;
    ImageLoader imageLoader;

    private VolleyUtils(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    private VolleyUtils() {
        queue = Volley.newRequestQueue(MyApp.CONTEXT);
        ImageLoader.ImageCache imageCache = new ImageLoader.ImageCache() {

            LruCache<String,Bitmap> cache = new LruCache<String, Bitmap>((int) (Runtime.getRuntime().maxMemory()/8)){
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getHeight()*value.getRowBytes();
                }
            };
            @Override
            public Bitmap getBitmap(String s) {
                return cache.get(s);
            }

            @Override
            public void putBitmap(String s, Bitmap bitmap) {
                cache.put(s,bitmap);
            }
        };
        imageLoader = new ImageLoader(queue,imageCache);
    }

    public void test() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("city","北京");
        paramMap.put("category","美食");
        String url = HttpUtils.getUrl("http://api.dianping.com/v1/business/find_businesses", paramMap);
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.i("TAG", "onResponse: "+s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("TAG", "onErrorResponse: "+volleyError.getMessage());
            }
        });
        queue.add(request);
    }
    /**
     * 利用Volley去获取城市今日新增的团购信息
     * @param city
     * @param listener
     */
    public void getDailyDeals(String city, final Response.Listener<String> listener){
        //1)获取新增团购的ID列表
        String url1 = "http://api.dianping.com/v1/deal/get_daily_new_id_list";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("city",city);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
        paramMap.put("date",date);
        String url = HttpUtils.getUrl(url1,paramMap);
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
               /* {
                    "status": "OK",
                        "count": 309,
                        "id_list": [
                            "1-33946",
                            "1-4531",
                            "1-4571",
                            "1-5336",
                            "1-5353",
                            "......"
                       ]
                }*/
                Log.i("TAG", "onResponse: 获取新增团购的ID:"+s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("id_list");
                    int size = jsonArray.length();
                    if (size > 40) {
                        size = 40;
                    }
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < size; i++) {
                        String id = jsonArray.getString(i);
                        sb.append(id + ",");
                    }
                    if (sb.length() > 0){
                        //包装获取到的ids的连接方式
                        String idList = sb.substring(0, sb.length() - 1);
                        Map<String, String> params = new HashMap<>();
                        params.put("deal_ids", idList);
                        //2)根据多个团购ID批量获取指定团购单的详细信息
                        String url2 = HttpUtils.getUrl("http://api.dianping.com/v1/deal/get_batch_deals_by_id", params);
                        StringRequest stringRequest = new StringRequest(url2, listener, null);
                        queue.add(stringRequest);
                }else {
                        //该城市今日无新增团购
                        listener.onResponse(null);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("TAG", "onErrorResponse: "+volleyError.getMessage());
            }
        });
        queue.add(request);
    }

    /**
     * 显示网络中的图片
     * @param url
     * @param imageView
     */
    public void loadImage(String url, ImageView imageView){
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView,
                R.drawable.bucket_no_picture,R.drawable.picture_fail);
        imageLoader.get(url, listener) ;
    }
    //Volley的自定义请求
    public class tuanBeanRequest extends Request<TuanBean>{

        Response.Listener<TuanBean> listener;
        public tuanBeanRequest( String url,Response.Listener<TuanBean> listener) {
            super(Method.GET, url, null);
            this.listener = listener;
        }

        @Override
        protected Response<TuanBean> parseNetworkResponse(NetworkResponse networkResponse) {
            String response = new String(networkResponse.data);
            Gson gson = new Gson();
            TuanBean tuanBean = gson.fromJson(response,TuanBean.class);
            //组装一个Volley和Response对象作为方法的返回值
            Response<TuanBean> result = Response.success(tuanBean,
                    HttpHeaderParser.parseCacheHeaders(networkResponse));
            return result;
        }

        @Override
        protected void deliverResponse(TuanBean tuanBean) {
            listener.onResponse(tuanBean);
        }
    }
    public void getDailyDeals2(String city, final Response.Listener<TuanBean> listener){
        //1)获取新增团购的ID列表
        String url1 = "http://api.dianping.com/v1/deal/get_daily_new_id_list";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("city",city);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
        paramMap.put("date",date);
        String url = HttpUtils.getUrl(url1,paramMap);
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
               /* {
                    "status": "OK",
                        "count": 309,
                        "id_list": ["1-33946","1-4531","1-4571","1-5336","1-5353","......" ]
                }*/
                Log.i("TAG", "onResponse: 获取新增团购的ID:"+s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("id_list");
                    int size = jsonArray.length();
                    if (size > 40) {
                        size = 40;
                    }
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < size; i++) {
                        String id = jsonArray.getString(i);
                        sb.append(id + ",");
                    }
                    if (sb.length() > 0){
                        //包装获取到的ids的连接方式
                        String idList = sb.substring(0, sb.length() - 1);
                        Map<String, String> params = new HashMap<>();
                        params.put("deal_ids", idList);
                        //2)根据多个团购ID批量获取指定团购单的详细信息
                        String url2 = HttpUtils.getUrl("http://api.dianping.com/v1/deal/get_batch_deals_by_id", params);
                        tuanBeanRequest stringRequest = new tuanBeanRequest(url2,listener);
                        queue.add(stringRequest);
                    }else {
                        //该城市今日无新增团购
                        listener.onResponse(null);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("TAG", "onErrorResponse: "+volleyError.getMessage());
            }
        });
        queue.add(request);
    }
}
