package com.litte.publiccomment.view;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.litte.publiccomment.R;

/**
 * Created by litte on 2018/1/30.
 */

public class MyAdsBannerView extends FrameLayout {

    ViewPager viewPager_ads_banner;
    ImageView img_business_ads_delete;
    LinearLayout ll_director_ads_banner;
    int[] resIds;//ViewPager管理图片
    PagerAdapter adapter ;
    Handler handler = new Handler();//轮播时间管理
    boolean flag;
    CloseAddsPlay closeAddsPlay ;
    public MyAdsBannerView(@NonNull Context context,int[] ids) {
        super(context);
        if (ids!=null&&ids.length>0){
            resIds = new int[ids.length+2];
            resIds[0] = ids[ids.length-1];
            resIds[resIds.length-1] = ids[0];
            for (int i = 0; i < ids.length; i++) {
                resIds[i+1] = ids[i];
            }
        }else {
            //使用默认图片
            resIds = new int[]{
                    R.drawable.ad_1,
                    R.drawable.ad_2,
                    R.drawable.ad_3,
                    R.drawable.ad_4,
                    R.drawable.ad_5,
                    R.drawable.ad_6,
                    R.drawable.ad_7,
                    R.drawable.ad_8,
                    R.drawable.ad_9
            };
        }
        View view = LayoutInflater.from(context).inflate(R.layout.business_ads_bar, this, false);
        this.addView(view);
        initialView(view);
        startPlayAds();
    }

    public void setOnCloseAddsPlays(CloseAddsPlay closeAddsPlays){
        this.closeAddsPlay = closeAddsPlays;
    }
    private void startPlayAds() {
        flag = true;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (flag){
                    int idx = viewPager_ads_banner.getCurrentItem();
                    viewPager_ads_banner.setCurrentItem(idx+1);
                    handler.postDelayed(this,3000);//自己调用自己
                }
            }
        }, 3000);
    }
    //停止广告轮播
    private void stop(){
        flag = false;
        handler.removeCallbacksAndMessages(null);
    }

    private void initialView(View view) {
        viewPager_ads_banner = view.findViewById(R.id.viewPager_ads_banner);
        img_business_ads_delete = view.findViewById(R.id.img_business_ads_delete);
        ll_director_ads_banner = view.findViewById(R.id.ll_director_ads_banner);
        adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return resIds.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView = new ImageView(getContext());
                int resId = resIds[position];
                imageView.setImageResource(resId);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);//拉伸图片
                container.addView(imageView);
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        };
        viewPager_ads_banner.setAdapter(adapter);
        viewPager_ads_banner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0){
                    viewPager_ads_banner.setCurrentItem(resIds.length-2,false);
                    setIndicator(ll_director_ads_banner.getChildCount()-1);
                }
                if (position == resIds.length-1){
                    viewPager_ads_banner.setCurrentItem(1,false);
                    setIndicator(0);
                }else {
                    setIndicator(position-1);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager_ads_banner.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == event.ACTION_DOWN){
                    stop();
                }
                if (action == event.ACTION_UP){
                    startPlayAds();
                }
                return false;
            }
        });
        //广告轮播的滑动指示器
        for (int i = 0; i < resIds.length - 2; i++) {
            ImageView imageView = new ImageView(getContext());

            imageView.setImageResource(R.drawable.banner_dot);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,2,getResources().getDisplayMetrics());
            params.setMargins(margin,0,margin,0);
            imageView.setLayoutParams(params);
            ll_director_ads_banner.addView(imageView);
        }
        setIndicator(0);
        img_business_ads_delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (closeAddsPlay !=null){
                    closeAddsPlay.close();
                }
            }
        });
    }

    /**
     * 设置某“指示器”中图片的显示为橘红色
     * @param i
     */
    private void setIndicator(int i) {
        for (int j = 0; j < ll_director_ads_banner.getChildCount(); j++) {
            ImageView imageView = (ImageView) ll_director_ads_banner.getChildAt(j);
            if (j == i){
                imageView.setImageResource(R.drawable.banner_dot_pressed);
            }else {
                imageView.setImageResource(R.drawable.banner_dot);
            }
        }
    }
    public interface CloseAddsPlay{
        void close();
    }
}
