package com.litte.publiccomment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.litte.publiccomment.R;
import com.litte.publiccomment.bean.TuanBean;
import com.litte.publiccomment.util.HttpUtils;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by litte on 2018/1/22.
 */

public class DealAdapter extends MyBaseAdapter<TuanBean.Deal> {
    public DealAdapter(Context context,  List<TuanBean.Deal> list) {
        super(context,list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.inflater_header_list_deal,parent,false);
            holder = new ViewHolder(convertView);
//            holder.img_deal_picture = convertView.findViewById(R.id.img_deal_picture);
//            holder.tv_deal_title = convertView.findViewById(R.id.tv_deal_title);
//            holder.tv_deal_describe = convertView.findViewById(R.id.tv_deal_describe);
//            holder.tv_deal_price = convertView.findViewById(R.id.tv_deal_price);
//            holder.tv_deal_distance = convertView.findViewById(R.id.tv_deal_distance);
//            holder.tv_deal_count = convertView.findViewById(R.id.tv_deal_count);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        TuanBean.Deal dealsBean = getItem(position);
        //TODO 呈现图片
//        HttpUtils.loadImage(dealsBean.getImage_url(),holder.img_deal_picture);
        //Picasso框架呈现图片
        HttpUtils.showImage(dealsBean.getImage_url(),holder.img_deal_picture);
        holder.tv_deal_title.setText(dealsBean.getTitle());
        holder.tv_deal_describe.setText(dealsBean.getDescription());
        holder.tv_deal_price.setText(dealsBean.getCurrent_price()+"￥");
        //TODO 距离的计算 学过百度地图之后在做处理
        holder.tv_deal_distance.setText("XXXX");
        Random random = new Random();
        int count = random.nextInt(2000)+500;
        holder.tv_deal_count.setText("已售"+count);//账号权限问题 先用随机数替代

        return convertView;
    }
    public class ViewHolder{
        @BindView(R.id.img_deal_picture)
        ImageView img_deal_picture;
        @BindView(R.id.tv_deal_title)
        TextView tv_deal_title;
        @BindView(R.id.tv_deal_describe)
        TextView tv_deal_describe;
        @BindView(R.id.tv_deal_price)
        TextView tv_deal_price;
        @BindView(R.id.tv_deal_distance)
        TextView tv_deal_distance;
        @BindView(R.id.tv_deal_count)
        TextView tv_deal_count;

        public ViewHolder(View view) {
            ButterKnife.bind(this,view);
        }
    }
}
