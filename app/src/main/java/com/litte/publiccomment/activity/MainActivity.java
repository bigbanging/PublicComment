package com.litte.publiccomment.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.litte.publiccomment.R;
import com.litte.publiccomment.adapter.DealAdapter;
import com.litte.publiccomment.bean.TuanBean;
import com.litte.publiccomment.util.HttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends Activity {

    //控件初始化 顶部
    @BindView(R.id.ll_header_left_actionBar)
    LinearLayout ll_header_left_actionBar;//总布局
    @BindView(R.id.tv_city)
    TextView tv_city;//显示城市的文本
    @BindView(R.id.ll_header_center_actionBar)
    LinearLayout ll_header_center_actionBar;//显示搜索的文本
    @BindView(R.id.imgView_header_right_actionBar)
    ImageView imgView_header_right_actionBar;//呼出菜单栏选项的图片
    @BindView(R.id.menu_layout)
    View menuLayout;

    //中部 下拉刷新
    @BindView(R.id.pullToRefreshListView)
    PullToRefreshListView pullToRefreshListView;

    ListView listView;
    List<TuanBean.Deal> datas = null;
    DealAdapter adapter =null;
    //底部菜单选项
    RadioGroup radioGroup_buttom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initialListView();
    }

    @OnClick(R.id.tv_city)
    public void chooseCity(View view){
        startActivity(new Intent(this,ChooseCityActivity.class));
    }
    @OnClick(R.id.imgView_header_right_actionBar)
    public void showMenu(View view){
        if (menuLayout.getVisibility() == View.VISIBLE){
            menuLayout.setVisibility(View.INVISIBLE);
        }else {
            menuLayout.setVisibility(View.VISIBLE);
        }
    }
    private void initialListView() {
        listView = pullToRefreshListView.getRefreshableView();
        datas = new ArrayList<TuanBean.Deal>();
        adapter = new DealAdapter(this,datas);
        //为listView添加若干布局文件
        LayoutInflater inflater = LayoutInflater.from(this);//布局解析器
        //添加首页头部图片组viewPager
        View listHeaderIcon = inflater.inflate(R.layout.header_list_icon,listView,false);
        View listHeaderSquare = inflater.inflate(R.layout.header_list_square,listView,false);
        View listHeaderAds = inflater.inflate(R.layout.header_list_ads,listView,false);
        View listHeaderCategories = inflater.inflate(R.layout.header_list_categories,listView,false);
        View listHeaderRecommend = inflater.inflate(R.layout.header_list_recommend,listView,false);
        listView.addHeaderView(listHeaderIcon);
        listView.addHeaderView(listHeaderSquare);
        listView.addHeaderView(listHeaderAds);
        listView.addHeaderView(listHeaderCategories);
        listView.addHeaderView(listHeaderRecommend);
        listView.setAdapter(adapter);
        initialHeaderListIcon(listHeaderIcon);
        //添加下拉刷新（模拟）
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                /*new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        stringList.add(0,"新增内容");
                        adapter.notifyDataSetChanged();
                        pullToRefreshListView.onRefreshComplete();
                    }
                },1500);*/
                refresh();
            }
        });
        //下拉刷新时顶部菜单项状态的改变
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                /*Log.i("TAG", "firstVisibleItem: "+firstVisibleItem);
                Log.i("TAG", "visibleItemCount: "+visibleItemCount);
                Log.i("TAG", "totalItemCount: "+totalItemCount);*/
                if (firstVisibleItem == 0){
                    ll_header_left_actionBar.setVisibility(View.VISIBLE);
                    imgView_header_right_actionBar.setVisibility(View.VISIBLE);
                }else {
                    ll_header_left_actionBar.setVisibility(View.GONE);
                    imgView_header_right_actionBar.setVisibility(View.GONE);
                }
            }
        });
    }

    //头部图片菜单的ViewPager处理
    private void initialHeaderListIcon(View listHeaderIcon) {
        final ViewPager viewPager_header_icon_list = listHeaderIcon.findViewById(R.id.viewPager_header_list_icon);
        PagerAdapter adapter = new PagerAdapter() {
            int[] header_list_icon = new int[]{
                    R.layout.list_icon_1,
                    R.layout.list_icon_2,
                    R.layout.list_icon_3
            };
            @Override
            public int getCount() {
                return 30000;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
            //然后仍然需要重写的两个方法

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                int layoutId = header_list_icon[position%3];
                View view = LayoutInflater.from(MainActivity.this).inflate(layoutId,viewPager_header_icon_list,false);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        };
        viewPager_header_icon_list.setAdapter(adapter);
        viewPager_header_icon_list.setCurrentItem(15000);
        //三组header_icon的指示器
        final ImageView iv1 = listHeaderIcon.findViewById(R.id.imageView_header_list_icon_indicator_1);
        final ImageView iv2 = listHeaderIcon.findViewById(R.id.imageView_header_list_icon_indicator_2);
        final ImageView iv3 = listHeaderIcon.findViewById(R.id.imageView_header_list_icon_indicator_3);
        viewPager_header_icon_list.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                iv1.setImageResource(R.drawable.banner_dot);
                iv2.setImageResource(R.drawable.banner_dot);
                iv3.setImageResource(R.drawable.banner_dot);
                switch (position%3){
                    case 0:
                        iv1.setImageResource(R.drawable.banner_dot_pressed);
                        break;
                    case 1:
                        iv2.setImageResource(R.drawable.banner_dot_pressed);
                        break;
                    case 2:
                        iv3.setImageResource(R.drawable.banner_dot_pressed);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                HttpUtils.getDealByRetrofit3(tv_city.getText().toString(), new Callback<TuanBean>() {
                    @Override
                    public void onResponse(Call<TuanBean> call, retrofit2.Response<TuanBean> response) {
                        if (response != null){
                            TuanBean body = response.body();
                            List<TuanBean.Deal> deals = body.getDeals();
                            adapter.addAll(deals,true);
                        }else {
                            Toast.makeText(MainActivity.this, "今日无新增团购信息", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<TuanBean> call, Throwable throwable) {
                    }
                });
            }
        }, 2000);*/
        //1)发起一个请求，服务器响应
        //以GET的方式发起请求
        //请求格式：http://xxx.xxxx.com/xxx？key=14xxxxxxx&city=%e8%f8%c6%xx%xx%xx
        //利用HttpClient(apache)
        //HttpURLConnection
//        HttpUtils.testHttpConnection();
        //Volley
//        HttpUtils.testVolley();

        HttpUtils.getDailyDealsByVolley(tv_city.getText().toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.i("TAG", "onResponse: "+s);
                if (s!=null) {
                    Gson gson = new Gson();
                    TuanBean tuanBean = gson.fromJson(s, TuanBean.class);
                    List<TuanBean.Deal> deals = tuanBean.getDeals();
                    //将deals放到listView中呈现
                    adapter.addAll(deals, true);
                }else {
                    Toast.makeText(MainActivity.this, "今日无新增团购内容", Toast.LENGTH_SHORT).show();
                }
                pullToRefreshListView.onRefreshComplete();
            }
        });
        //测试自定义的Volley请求
       /* HttpUtils.getDailyDealsByVolley2(tv_city.getText().toString(), new Response.Listener<TuanBean>() {
            @Override
            public void onResponse(TuanBean s) {
                Log.i("TAG", "onResponse: "+s);
                if (s!=null) {
                    *//*Gson gson = new Gson();
                    TuanBean tuanBean = gson.fromJson(s, TuanBean.class);*//*
                    List<TuanBean.Deal> deals = s.getDeals();
                    //将deals放到listView中呈现
                    adapter.addAll(deals, true);
                }else {
                    Toast.makeText(MainActivity.this, "今日无新增团购内容", Toast.LENGTH_SHORT).show();
                }
                pullToRefreshListView.onRefreshComplete();
            }
        });*/
        //Retrofit请求的写法
        /*HttpUtils.getDealByRetrofit(tv_city.getText().toString(), new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                if (response != null){
                    Gson gson = new Gson();
                    TuanBean tuanBean = gson.fromJson(response.body(),TuanBean.class);
                    List<TuanBean.Deal> deals = tuanBean.getDeals();
                    adapter.addAll(deals,true);
                }else {
                    Toast.makeText(MainActivity.this, "今日无新增团购信息", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Log.i("TAG", "onFailure: "+throwable.getMessage());
                pullToRefreshListView.onRefreshComplete();
            }
        });*/
        //Retrofit直接请求实体类
        /*HttpUtils.getDealByRetrofit2(tv_city.getText().toString(), new Callback<TuanBean>() {
            @Override
            public void onResponse(Call<TuanBean> call, retrofit2.Response<TuanBean> response) {
                if (response != null) {
                    TuanBean body = response.body();
                    List<TuanBean.Deal> deals = body.getDeals();
                    adapter.addAll(deals,true);
                } else {
                    Toast.makeText(MainActivity.this, "今日无新增团购信息", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TuanBean> call, Throwable throwable) {

            }
        });*/
        //Retrofit+OKHttp请求时的写法
       /* HttpUtils.getDealByRetrofit3(tv_city.getText().toString(), new Callback<TuanBean>() {
            @Override
            public void onResponse(Call<TuanBean> call, retrofit2.Response<TuanBean> response) {
                if (response != null){
                    TuanBean body = response.body();
                    List<TuanBean.Deal> deals = body.getDeals();
                    adapter.addAll(deals,true);
                }else {
                    Toast.makeText(MainActivity.this, "今日无新增团购信息", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TuanBean> call, Throwable throwable) {

            }
        });*/
//        HttpUtils.testRetrofit();
        //2)根据服务器响应的内容进行解析
        // JSON字符串 / XML文档
        // 解析JSON字符串：
        // JSONLib(JsonObject)
        // GSON
        // fastJson
        // jackson
        // 解析XML文档
        // XMLPull
        // SAX

        //3)将解析结果放到View中显示
        //放到ListView中显示需要适配器、条目布局
    }
}
