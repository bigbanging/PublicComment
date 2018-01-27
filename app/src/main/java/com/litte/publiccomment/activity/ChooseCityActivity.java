package com.litte.publiccomment.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.litte.publiccomment.R;
import com.litte.publiccomment.adapter.CityAdapter;
import com.litte.publiccomment.app.MyApp;
import com.litte.publiccomment.bean.CityBean;
import com.litte.publiccomment.bean.CityPinYinBean;
import com.litte.publiccomment.util.DBUtils;
import com.litte.publiccomment.util.HttpUtils;
import com.litte.publiccomment.util.PinYinSortUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

public class ChooseCityActivity extends Activity {

    //顶部回退图片
    @BindView(R.id.img_back_search_city)
    ImageView img_back_search_city;
    //搜索框
    @BindView(R.id.tv_search_city)
    TextView tv_search_city;
    //显示城市的RecyclerView
    @BindView(R.id.recyclerView_show_city)
    RecyclerView recyclerView_show_city;

    CityAdapter adapter = null;
    List<CityPinYinBean> datas = null;
    DBUtils dbUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_city);
        dbUtils = new DBUtils(this);
        ButterKnife.bind(this);
        initialRecyclerView();
    }

    private void initialRecyclerView() {
        //初始化数据，适配器
        recyclerView_show_city.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        //添加Android自带的分割线
        recyclerView_show_city.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        datas = new ArrayList<>();
        adapter = new CityAdapter(this,datas);
        adapter.setOnItemClickListener(new CityAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View itemView, int position) {
                CityPinYinBean cityPinYinBean = datas.get(position);
                Toast.makeText(ChooseCityActivity.this,cityPinYinBean.getCityName(), Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView_show_city.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        //从内存缓存中读取城市数据
        if (MyApp.cityPinYinBeanList != null&&MyApp.cityPinYinBeanList.size()>0){
            adapter.addAll(MyApp.cityPinYinBeanList,true);
            Log.i("TAG", "城市名称从内存中加载。。。");
            return;
        }
        //从数据库中读取城市名称
        List<CityPinYinBean> list = dbUtils.query();
        if (list!=null&&list.size()>0){
            adapter.addAll(list,true);
            Log.i("TAG", "城市名称从数据库加载。。。");
            return;
        }
        //Volley请求方式
        /*HttpUtils.getCitiesByVolley(new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.i("TAG", "onResponse: "+s);
            }
        });*/
        //Retrofit请求方式
        HttpUtils.getCitiesByRetrofit(new Callback<CityBean>() {
            @Override
            public void onResponse(Call<CityBean> call, retrofit2.Response<CityBean> response) {
                Log.i("TAG", "onResponse: "+response.body());
                CityBean cityBean = response.body();
                List<String> cities = cityBean.getCities();

                final List<CityPinYinBean> cityPinYinBeanList = new ArrayList<>();
                for (String name : cities) {
                    if (!name.equals("全国")&&!name.equals("点评实验室")&&!name.equals("其它城市")&&!name.equals("点评海外实验室")){
                        CityPinYinBean cityPinYinBean = new CityPinYinBean();
                        cityPinYinBean.setCityName(name);
                        cityPinYinBean.setPyName(PinYinSortUtils.getPinYin(name));
                        //取首字母
//                cityPinYinBean.setLetter(cityPinYinBean.getPyName().charAt(0));
                        cityPinYinBean.setLetter(PinYinSortUtils.getLetter(name));
                        Log.i("TAG", "initialRecyclerView:创建的citynameBean： "+ cityPinYinBean);
                        cityPinYinBeanList.add(cityPinYinBean);
                    }
                }
                //排序
                Collections.sort(cityPinYinBeanList, new Comparator<CityPinYinBean>() {
                    @Override
                    public int compare(CityPinYinBean o1, CityPinYinBean o2) {
                        return o1.getPyName().compareTo(o2.getPyName());
                    }
                });
                adapter.addAll(cityPinYinBeanList,true);
                Log.i("TAG", "城市名称数据从网络中加载 ");
                //将数据缓存起来
                MyApp.cityPinYinBeanList = cityPinYinBeanList;
                //向数据库中写入城市数据
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        long start = System.currentTimeMillis();
                        dbUtils.insertBatch(cityPinYinBeanList);
                        long end = System.currentTimeMillis();
                        long time = end-start;
                        Log.i("TAG", "完成写入数据库，耗时："+time);
                    }
                }.start();
            }

            @Override
            public void onFailure(Call<CityBean> call, Throwable throwable) {

            }
        });
    }
    @OnClick(R.id.img_back_search_city)
    public void backSearchCity(View view){
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
