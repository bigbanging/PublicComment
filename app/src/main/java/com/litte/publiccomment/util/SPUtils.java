package com.litte.publiccomment.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by litte on 2018/1/21.
 *
 * 对偏好设置文件的操作
 *
 * 1) Context的getSharedPreferences(文件名,模式);
 * 2) Activity的getPreference(模式);获取以preference_activiy名的偏好设置文件
 * 3) PreferenceManager的getDefaultSharedPreferences(Context);
 *    获取preference_包名 偏好设置文件
 *    模式 Context_MODE_PRIVATE
 */

public class SPUtils {
    SharedPreferences sharedPreferences;

    //通过构造器重载，以不同的方式来获得偏好设置文件
    public SPUtils(Context context,String name) {
        sharedPreferences = context.getSharedPreferences(name,Context.MODE_PRIVATE);
    }

    public SPUtils(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }
    public boolean isFirst(){
        return sharedPreferences.getBoolean("FIRST",true);
    }
    public void setFirst(boolean flag){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("FIRST",flag);
        editor.commit();
    }
}
