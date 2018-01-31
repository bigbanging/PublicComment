package com.litte.publiccomment.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.litte.publiccomment.R;
import com.litte.publiccomment.app.MyApp;
import com.litte.publiccomment.bean.CityPinYinBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchCityActivity extends Activity {

    @BindView(R.id.img_back)
    ImageView img_back;
    @BindView(R.id.et_search_city)
    EditText et_search_city;
    @BindView(R.id.list_search_item)
    ListView list_search_item;
    //数据源
    List<String> cities;
    //适配器
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);
        ButterKnife.bind(this);
        initialListView();
    }

    private void initialListView() {
        cities = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,cities);
        list_search_item.setAdapter(adapter);
        list_search_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent data = new Intent();
                String city = adapter.getItem(position);
                data.putExtra("city",city);
                setResult(RESULT_OK,data);
                finish();
            }
        });
    }

    @OnClick(R.id.img_back)
    public void BackCity(View view){
        startActivity(new Intent(this,ChooseCityActivity.class));
        finish();
    }
    @OnClick(R.id.et_search_city)
    public void EditTextSearch(Editable editable){
        if (editable.length() == 0){
            cities.clear();
            adapter.notifyDataSetChanged();
        }else {
            searchCitiesTips(editable.toString().toUpperCase());
        }
    }

    /**
     * 根据输入内容筛选
     * @param s
     */
    private void searchCitiesTips(String s) {
        List<String> search = new ArrayList<>();
        //中文  char 16bit 0-65535
        if (s.matches("[\u4e00-\u9fff]+")){
            for (CityPinYinBean cityBean: MyApp.cityPinYinBeanList) {
                if (cityBean.getCityName().contains(s)){
                    search.add(cityBean.getCityName());
                }
            }
        }else {//拼音
            for (CityPinYinBean c:MyApp.cityPinYinBeanList){
                if (c.getPyName().contains(s)){
                    search.add(c.getCityName());
                }
            }
        }
        if (search.size() > 0){
            cities.clear();
            cities.addAll(search);
            adapter.notifyDataSetChanged();
        }
    }
}
