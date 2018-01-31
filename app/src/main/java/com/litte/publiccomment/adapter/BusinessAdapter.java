package com.litte.publiccomment.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.litte.publiccomment.R;
import com.litte.publiccomment.bean.BusinessBean;
import com.litte.publiccomment.util.HttpUtils;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by litte on 2018/1/30.
 */

public class BusinessAdapter extends MyBaseAdapter<BusinessBean.BusinessesBean> {

    public BusinessAdapter(Context context, List<BusinessBean.BusinessesBean> businessesBeen) {
        super(context, businessesBeen);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BusinessViewHolder holder = null;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.business_item,parent,false);
            holder = new BusinessViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (BusinessViewHolder) convertView.getTag();
        }
        BusinessBean.BusinessesBean businessesBean = getItem(position);
        HttpUtils.loadImage(businessesBean.getPhoto_url(),holder.img_business_item_picture);
//        String  name = holder.tv_business_item_title.setText(businessesBean.getName().substring(0,businessesBean.getName().indexOf("(")));
        String name = businessesBean.getName().substring(0,businessesBean.getName().indexOf("("));
        if (!TextUtils.isEmpty(businessesBean.getBranch_name())){
            name = name+"("+businessesBean.getBranch_name()+")";
        }
        holder.tv_business_item_title.setText(name);
        int[] commentStar = new int[]{
            R.drawable.movie_star10,
            R.drawable.movie_star20,
            R.drawable.movie_star30,
            R.drawable.movie_star35,
            R.drawable.movie_star40,
            R.drawable.movie_star45,
            R.drawable.movie_star50
        };
        Random random = new Random();
        int idx = random.nextInt(7);
        holder.img_business_item_comment.setImageResource(commentStar[idx]);
        holder.tv_business_item_prince.setText("￥"+random.nextInt(100)+50+"/人");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < businessesBean.getRegions().size(); i++) {
            if (i==0) {
                sb.append(businessesBean.getBranch_name());
            }else {
                sb.append("/").append(businessesBean.getRegions());
            }
        }
        for (int i = 0; i < businessesBean.getCategories().size(); i++) {
            if (i==0){
                sb.append(businessesBean.getCategories().get(i));
            }else {
                sb.append("/").append(businessesBean.getCategories().get(i));
            }
        }
        holder.tv_business_item_category.setText(sb.toString());
        holder.tv_business_item_discount.setText(random.nextInt(50)+100+"m");
        holder.tv_business_item_discount.setText(businessesBean.getCoupon_description());
        return convertView;
    }
    public class BusinessViewHolder{
        @BindView(R.id.img_business_item_picture)
        ImageView img_business_item_picture;
        @BindView(R.id.tv_business_item_title)
        TextView tv_business_item_title;
        @BindView(R.id.img_business_item_comment)
        ImageView img_business_item_comment;
        @BindView(R.id.tv_business_item_prince)
        TextView tv_business_item_prince;
        @BindView(R.id.tv_business_item_category)
        TextView tv_business_item_category;
        @BindView(R.id.tv_business_item_distance)
        TextView tv_business_item_distance;
        @BindView(R.id.tv_business_item_discount)
        TextView tv_business_item_discount;
        public BusinessViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
