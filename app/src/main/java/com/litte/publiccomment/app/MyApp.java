package com.litte.publiccomment.app;

import android.app.Application;

/**
 * Created by litte on 2018/1/22.
 */

public class MyApp extends Application {
    public static MyApp CONTEXT;

    @Override
    public void onCreate() {
        super.onCreate();
        CONTEXT = this;
    }
}
