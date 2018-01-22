package com.litte.publiccomment.util;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.litte.publiccomment.app.MyApp;

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

    private VolleyUtils(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    private VolleyUtils() {
        queue = Volley.newRequestQueue(MyApp.CONTEXT);
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
}
