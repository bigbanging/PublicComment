package com.litte.publiccomment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.litte.publiccomment.R;
import com.litte.publiccomment.bean.CityPinYinBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by litte on 2018/1/25.
 */

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> implements SectionIndexer{

    //上下文
    Context context;
    //数据源
    List<CityPinYinBean> datas;
    //布局解析器
    LayoutInflater inflater;
    OnItemClickListener onItemClickListener;

    public CityAdapter(Context context, List<CityPinYinBean> datas) {
        this.context = context;
        this.datas = datas;
        this.inflater = LayoutInflater.from(context);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = inflater.inflate(R.layout.item_city_list, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        CityPinYinBean cityName = datas.get(position);
        holder.tv_city_name_listItem.setText(cityName.getCityName());
        holder.tv_city_group_letter.setText(cityName.getLetter()+"");
        //字母分组
        //position这个位置的数据是不是该数据所属分组的起始位置
        if (position == getPositionForSection(getSectionForPosition(position))){
            holder.tv_city_group_letter.setVisibility(View.VISIBLE);
        }else {
            holder.tv_city_group_letter.setVisibility(View.GONE);
        }
        final View itemView = holder.itemView;
        if (this.onItemClickListener != null){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.OnItemClick(itemView,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void addAll(List<CityPinYinBean> list,boolean isClear){
        if (isClear){
            datas.clear();
        }
        datas.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public Object[] getSections() {
        return null;//new Object[0];
    }

    /**
     * 某一分组的位置是什么
     * @param sectionIndex
     * @return
     */
    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < datas.size(); i++) {
            if (datas.get(i).getLetter() == sectionIndex){
                return i;
            }
        }
        return datas.size();
    }

    /**
     * 第position位置上的数据的分组是什么
     * @param position
     * @return
     */
    @Override
    public int getSectionForPosition(int position) {
        return datas.get(position).getLetter();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_city_group_letter)
        TextView tv_city_group_letter;
        @BindView(R.id.tv_city_name_listItem)
        TextView tv_city_name_listItem;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public interface OnItemClickListener{
        void OnItemClick(View itemView,int position);
    }
}
