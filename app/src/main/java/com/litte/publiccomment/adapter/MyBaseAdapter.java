package com.litte.publiccomment.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.litte.publiccomment.bean.DealBean;

import java.util.List;

/**
 * Created by litte on 2018/1/22.
 */

public abstract class MyBaseAdapter extends BaseAdapter {
    List<DealBean> dealBeen = null;
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
