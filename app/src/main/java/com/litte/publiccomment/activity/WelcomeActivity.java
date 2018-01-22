package com.litte.publiccomment.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.litte.publiccomment.R;
import com.litte.publiccomment.util.SPUtils;

/**
 * * 对偏好设置文件的操作
 *
 * 1) Context的getSharedPreferences(文件名,模式);
 * 2) Activity的getPreference(模式);获取以preference_activiy名的偏好设置文件
 * 3) PreferenceManager的getDefaultSharedPreferences(Context);
 *    获取preference_包名 偏好设置文件
 *    模式 Context_MODE_PRIVATE
 */
public class WelcomeActivity extends Activity {
    SPUtils spUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        spUtils = new SPUtils(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (spUtils.isFirst()){
                    //第一次安装则显示新手指导页 之后就不在显示
                    intent = new Intent(WelcomeActivity.this, GuideActivity.class);
                    spUtils.setFirst(false);
                }else{
                    intent = new Intent(WelcomeActivity.this, MainActivity.class);
                }
                startActivity(intent);
                finish();
            }
        },1500);
    }
}
