package com.litte.publiccomment.app;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.litte.publiccomment.bean.CityPinYinBean;

import java.util.List;

/**
 * Created by litte on 2018/1/22.
 */

public class MyApp extends Application {
    public static MyApp CONTEXT;
    //城市名称的缓存
    public static List<CityPinYinBean> cityPinYinBeanList;

    @Override
    public void onCreate() {
        super.onCreate();
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        CONTEXT = this;
    }
}
