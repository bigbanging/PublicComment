package com.litte.publiccomment.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.litte.publiccomment.R;
import com.litte.publiccomment.bean.CommentBean;
import com.litte.publiccomment.util.HttpUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by litte on 2018/2/3.
 */

public class ReviewAdapter extends MyBaseAdapter<CommentBean> {

    public ReviewAdapter(Context context, List<CommentBean> commentBeen) {
        super(context, commentBeen);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ReviewViewHolder holder ;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.user_comments_layout,parent,false);
            holder = new ReviewViewHolder(convertView);
            convertView.setTag(holder);
            //改善用户评论晒图的显示效果
            holder.ll_comment_img_container.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    int width = holder.ll_comment_img_container.getWidth();
                    int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,3,context.getResources().getDisplayMetrics());
                    int size = (width - 2*margin)/3;
                    for (int i = 0; i < holder.ll_comment_img_container.getChildCount(); i++) {
                        ImageView iv = (ImageView) holder.ll_comment_img_container.getChildAt(i);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size,size);
                        if (i!=0) {
                            params.setMargins(margin, 0, 0, 0);
                        }
                        iv.setScaleType(ImageView.ScaleType.FIT_XY);
                        iv.setLayoutParams(params);
                    }
                    holder.ll_comment_img_container.requestLayout();
                }
            });
        }else {
            holder = (ReviewViewHolder) convertView.getTag();
        }
        CommentBean commentItem = getItem(position);
        //获取用户头像
        HttpUtils.loadImage(commentItem.getAvatar(),holder.iv_comment_header);
//        holder.iv_comment_header.setImageResource(Integer.parseInt(commentItem.getAvatar()));
        //获取用户昵称
        holder.tv_comment_username.setText(commentItem.getName());
        //获取评论时间
        holder.tv_comment_time.setText(commentItem.getDate());
        //获取评分
        int[] resIds = new int[]{
                R.drawable.movie_star10,
                R.drawable.movie_star20,
                R.drawable.movie_star30,
                R.drawable.movie_star35,
                R.drawable.movie_star40,
                R.drawable.movie_star45,
                R.drawable.movie_star50
        };
        int resId = 0;
        String rating  = commentItem.getRating();
        if (rating.contains("20")){
            resId = 1;
        }else if (rating.contains("30")){
            resId = 2;
        }else if (rating.contains("35")){
            resId =3;
        }else if (rating.contains("40")){
            resId = 4;
        }else if (rating.contains("45")){
            resId = 5;
        }else if (rating.contains("50")){
            resId = 6;
        }
        holder.iv_comment_dafen.setImageResource(resIds[resId]);
        //获取售价
        holder.tv_comment_prince.setText(commentItem.getPrice());
        //获得评语
        holder.tv_comment_comments.setText(commentItem.getContent());
        //获得评论晒图 图片

        /*holder.iv_comment_pic1.setImageResource(Integer.parseInt(commentItem.getImgs()[0]));
        holder.iv_comment_pic2.setImageResource(Integer.parseInt(commentItem.getImgs()[1]));
        holder.iv_comment_pic3.setImageResource(Integer.parseInt(commentItem.getImgs()[2]));*/
        String[] imgs = commentItem.getImgs();
        ImageView[] ivs = new ImageView[3];
        ivs[0] = holder.iv_comment_pic1;
        ivs[1] = holder.iv_comment_pic2;
        ivs[2] = holder.iv_comment_pic3;
        for (int i = 0; i < 3; i++) {
            ivs[i].setVisibility(View.GONE);
        }
        for (int i = 0; i < 3; i++) {
            ivs[i].setVisibility(View.VISIBLE);
            HttpUtils.loadImage(imgs[i],ivs[i]);
        }

        return convertView;
    }
    public class ReviewViewHolder{
        @BindView(R.id.iv_comment_header)
        ImageView iv_comment_header;
        @BindView(R.id.tv_comment_username)
        TextView tv_comment_username;
        @BindView(R.id.tv_comment_time)
        TextView tv_comment_time;
        @BindView(R.id.iv_comment_dafen)
        ImageView iv_comment_dafen;
        @BindView(R.id.tv_comment_prince)
        TextView tv_comment_prince;
        @BindView(R.id.tv_comment_comments)
        TextView tv_comment_comments;
        @BindView(R.id.ll_comment_img_container)
        LinearLayout ll_comment_img_container;
        @BindView(R.id.iv_comment_pic1)
        ImageView iv_comment_pic1;
        @BindView(R.id.iv_comment_pic2)
        ImageView iv_comment_pic2;
        @BindView(R.id.iv_comment_pic3)
        ImageView iv_comment_pic3;

        public ReviewViewHolder(View view) {
            ButterKnife.bind(this,view);
        }
    }
}
