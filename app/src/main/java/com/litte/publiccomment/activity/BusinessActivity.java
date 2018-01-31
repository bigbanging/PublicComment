package com.litte.publiccomment.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.litte.publiccomment.R;
import com.litte.publiccomment.adapter.BusinessAdapter;
import com.litte.publiccomment.bean.BusinessBean;
import com.litte.publiccomment.bean.RegionBean;
import com.litte.publiccomment.util.HttpUtils;
import com.litte.publiccomment.util.SPUtils;
import com.litte.publiccomment.view.MyAdsBannerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

public class BusinessActivity extends Activity {

    @BindView(R.id.img_business_title_back)
    ImageView img_business_title_back;
    @BindView(R.id.listView_business)
    ListView listView_business;
    //数据源
    List<BusinessBean.BusinessesBean> datas;
    BusinessAdapter adapter;
    String city ;
    SPUtils spUtils = null;
    @BindView(R.id.img_business_refresh_loading)
    ImageView img_business_refresh_loading;
    @BindView(R.id.tv_one)
    TextView tv_one;
    @BindView(R.id.region_menu_layout)
    View menuNeighborLayout;

    @BindView(R.id.listView_region_business_area)
    ListView listView_region_left;
    @BindView(R.id.listView_region_business_detail_area)
    ListView listView_region_right;

    List<String> leftDatas;
    ArrayAdapter<String> adapterLeft;
    List<String> rightDatas;
    ArrayAdapter<String> adapterRight;
    private List<RegionBean.CitiesBean.DistrictsBean> districtsBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);
        ButterKnife.bind(this);
        spUtils = new SPUtils(this);
        city = getIntent().getStringExtra("city");
//        Log.i("TAG", "onCreate: City"+city);
        initialPullToRefreshListView();
        initialListViewSelectRegion();
    }

    private void initialListViewSelectRegion() {
        leftDatas = new ArrayList<>();
        adapterLeft = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,leftDatas);
        listView_region_left.setAdapter(adapterLeft);

        listView_region_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RegionBean.CitiesBean.DistrictsBean districtsBean = districtsBeanList.get(position);
                List<String> neighborhoods = new ArrayList<String>(districtsBean.getNeighborhoods());
                neighborhoods.add(0,"全部"+districtsBean.getDistrict_name());
                rightDatas.clear();
                rightDatas.addAll(neighborhoods);
                adapterRight.notifyDataSetChanged();
            }
        });
        listView_region_right.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                String region = adapterRight.getItem(position);
                tv_one.setText(region);
                menuNeighborLayout.setVisibility(View.INVISIBLE);
                adapter.removeAll();
                HttpUtils.getFoodByVolley(city, region, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Gson gson = new Gson();
                        BusinessBean businessBean = gson.fromJson(s, BusinessBean.class);
//                        List<String> neighborhoods = regionBean.getCities().get(position).getDistricts().get(position).getNeighborhoods();
//                        adapterRight.addAll(neighborhoods);
                        List<BusinessBean.BusinessesBean> businesses = businessBean.getBusinesses();
                        adapter.addAll(businesses,true);
                    }
                });
            }
        });

        rightDatas = new ArrayList<>();
        adapterRight = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,rightDatas);
        listView_region_right.setAdapter(adapterRight);
    }

    private void initialPullToRefreshListView() {
        datas = new ArrayList<>();
        adapter = new BusinessAdapter(this,datas);
        if (!spUtils.isClose()) {
            final MyAdsBannerView myAdsBannerView = new MyAdsBannerView(this,null);
            myAdsBannerView.setOnCloseAddsPlays(new MyAdsBannerView.CloseAddsPlay() {
                @Override
                public void close() {
                    spUtils.setCllose(true);
                    listView_business.removeView(myAdsBannerView);
                }
            });
        listView_business.addHeaderView(myAdsBannerView);
        }
        listView_business.setAdapter(adapter);
        AnimationDrawable drawable = (AnimationDrawable) img_business_refresh_loading.getDrawable();
        drawable.start();
        listView_business.setEmptyView(img_business_refresh_loading);
    }

    @OnClick(R.id.img_business_title_back)
    public void back(View view){
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        HttpUtils.getFoodByVolley(city, null, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Gson gson = new Gson();
                BusinessBean businessBean = gson.fromJson(s, BusinessBean.class);
                List<BusinessBean.BusinessesBean> businesses = businessBean.getBusinesses();
                adapter.addAll(businesses,true);
            }
        });
        adapter.notifyDataSetChanged();

        HttpUtils.getRegionBeanByRetrofit(city, new Callback<RegionBean>() {
            @Override
            public void onResponse(Call<RegionBean> call, retrofit2.Response<RegionBean> response) {
                RegionBean regionBean = response.body();
                districtsBeanList = regionBean.getCities().get(0).getDistricts();
                List<String> districtsName = new ArrayList<String>();
                for (int i = 0; i < districtsBeanList.size(); i++) {
                    RegionBean.CitiesBean.DistrictsBean districtsBean = districtsBeanList.get(i);
                    districtsName.add(districtsBean.getDistrict_name());
                }
                leftDatas.clear();
                leftDatas.addAll(districtsName);
                adapterLeft.notifyDataSetChanged();

                List<String> neighborhoods = new ArrayList<String>(districtsBeanList.get(0).getNeighborhoods());
                String district_name = districtsBeanList.get(0).getDistrict_name();
                neighborhoods.add(0,"全部"+district_name);
                rightDatas.clear();
                rightDatas.addAll(neighborhoods);
                adapterRight.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<RegionBean> call, Throwable throwable) {

            }
        });
    }
    @OnClick(R.id.tv_one)
    public void showRegionbusiness(View view){
        if (menuNeighborLayout.getVisibility() == View.VISIBLE){
            menuNeighborLayout.setVisibility(View.INVISIBLE);
        }else {
            menuNeighborLayout.setVisibility(View.VISIBLE);
        }
    }
}
