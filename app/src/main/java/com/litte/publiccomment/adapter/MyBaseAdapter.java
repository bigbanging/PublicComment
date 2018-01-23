package com.litte.publiccomment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.litte.publiccomment.bean.TuanBean;

import java.util.List;

/**
 * Created by litte on 2018/1/22.
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter {
    Context context;
    LayoutInflater inflater;

    List<T> datas = null;

    public MyBaseAdapter(Context context, List<T> tList) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.datas = tList;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public T getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 追加数据
     * @param t
     */
    public void add(T t){
        datas.add(t);
        notifyDataSetChanged();
    }

    /**
     * 追加或替换
     * @param list
     * @param isClear -----------加载数据的方法
     */
    public void addAll(List<T> list,boolean isClear){
        if (isClear){
            datas.clear();
        }
        datas.addAll(list);

        notifyDataSetChanged();
    }

    /**
     * 清除所有
     */
    public void removeAll(){
        datas.clear();
        notifyDataSetChanged();
    }

}
