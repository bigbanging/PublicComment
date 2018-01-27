package com.litte.publiccomment.app;

import android.app.Application;

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
        CONTEXT = this;
    }
}
