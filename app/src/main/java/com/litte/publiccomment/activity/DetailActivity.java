package com.litte.publiccomment.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.litte.publiccomment.R;
import com.litte.publiccomment.adapter.ReviewAdapter;
import com.litte.publiccomment.bean.BusinessBean;
import com.litte.publiccomment.bean.CommentBean;
import com.litte.publiccomment.util.HttpUtils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity extends Activity {

    BusinessBean.BusinessesBean business;
    @BindView(R.id.listView_detail)
    ListView listView_detail;
    @BindView(R.id.img_business_detail_back)
    ImageView img_business_detail_back;


    List<CommentBean> datas;
//    ArrayAdapter<String> adapter;
    ReviewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        business = (BusinessBean.BusinessesBean) getIntent().getSerializableExtra("business");
        Log.i("TAG", "onCreate: 点击商户传递的信息:--->"+business.getName());

        initialListView();
    }

    private void initialListView() {
        datas = new ArrayList<>();
//        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,datas);
        adapter = new ReviewAdapter(this,datas);

        View headerBusiness = LayoutInflater.from(this).inflate(R.layout.business_detail_layout_store,listView_detail,false);
        initialHeaderBusiness(headerBusiness);
        listView_detail.addHeaderView(headerBusiness);
        View businessInformation = LayoutInflater.from(this).inflate(R.layout.business_detail_information,listView_detail,false);
        initialBusinessInformation(businessInformation);
        listView_detail.addHeaderView(businessInformation);

        listView_detail.setAdapter(adapter);
    }
    private void initialHeaderBusiness(View view) {
        ImageView iv_picture = view.findViewById(R.id.img_business_detail_store_picture);
        HttpUtils.showImage(business.getPhoto_url(),iv_picture);
//        iv_picture.setImageResource(R.drawable.bucket_no_picture);
        TextView tv_name = view.findViewById(R.id.tv_business_detail_store_name);
        String name = business.getName().substring(0,business.getName().indexOf("("));
        if (!TextUtils.isEmpty(business.getBranch_name())){
            name=name+"("+business.getBranch_name()+")";
        }
        tv_name.setText(name);
        ImageView iv_comment = view.findViewById(R.id.img_business_detail_store_comment);
        Random random = new Random();
        int[] resIds = new int[]{
                R.drawable.movie_star10,
                R.drawable.movie_star20,
                R.drawable.movie_star30,
                R.drawable.movie_star35,
                R.drawable.movie_star40,
                R.drawable.movie_star45,
                R.drawable.movie_star50
        };
        int anInt = random.nextInt(7);
        iv_comment.setImageResource(resIds[anInt]);
        /*TextView tv_count = view.findViewById(R.id.tv_business_detail_store_comment_count);
        tv_count.setText(business.getDeal_count());*/
        TextView tv_prince = view.findViewById(R.id.tv_business_detail_store_prince);
        int prince = random.nextInt(50)+100;
        tv_prince.setText("￥"+prince+"/人");
        TextView tv_position = view.findViewById(R.id.tv_business_detail_store_position);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < business.getRegions().size(); i++) {
            if (i==0){
                sb.append(business.getRegions().get(i));
            }else {
                sb.append("/").append(business.getRegions().get(i));
            }
        }
        sb.append(" ");
        for (int i = 0; i < business.getCategories().size(); i++) {
            if (i==0){
                sb.append(business.getCategories().get(i));
            }else {
                sb.append("/").append(business.getCategories().get(i));
            }
        }
        tv_position.setText(sb.toString());

    }
    private void initialBusinessInformation(View view) {
        TextView tv_position = view.findViewById(R.id.tv_business_detail_information_position);
        tv_position.setText(business.getAddress());
        tv_position.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailActivity.this,MapActivity.class));
            }
        });
        TextView tv_phone = view.findViewById(R.id.tv_business_detail_information_phone);
        tv_phone.setText(business.getTelephone());
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        /*datas.add("aaa");
        datas.add("aa2");
        datas.add("aa3");
        datas.add("aa4");
        datas.add("aa5");
        datas.add("aa6");
        datas.add("aa7");
        adapter.notifyDataSetChanged();*/

        HttpUtils.getComment(business.getReview_list_url(), new HttpUtils.OnResponseListener<Document>() {
            @Override
            public void onResponse(Document document) {
                Log.i("TAG", "点击商户传递的信息:--->连接"+business.getReview_list_url());
                //1)解析
                List<CommentBean> commentBeenList = new ArrayList<CommentBean>();

                Elements elements = document.select("div[class=reviews-items]");
                Log.i("TAG", "======> elements"+elements.toString());
                for (Element element:elements){
                    CommentBean commentBean = new CommentBean();
                    Element imgElement = element.select("li img").get(0);
                    Log.i("TAG", " ======>"+imgElement);
                    //获取的是评论用户的头像
                    commentBean.setAvatar(imgElement.attr("data-lazyload"));
                    Element nameElement = element.select("div[class=main-review] div[class=dper-info] a").get(0);
                    //获取用户昵称
//                    commentBean.setName(nameElement.attr("href"));
                    commentBean.setName(nameElement.text());

                    Elements priceElements = element.select("div[class=review-rank]  span[class=score]");
                    if (priceElements.size()>0){
                        //人均￥85 人均消费价格
//                        commentBean.setPrice(priceElements.get(0).text().split("")[1]+"/人");
                        commentBean.setPrice(priceElements.get(3).text()+"/人");
                    }else {
                        commentBean.setPrice("");
                    }
                    //star40 用户评价
                    Element rateElement = element.select("div[class=review-rank]").get(0);// span[class=sml-rank-stars sml-str30 star]
                    String rate = rateElement.attr("class");
//                    commentBean.setRating(rate.split("-")[rate.length()]);
                    commentBean.setRating(rate);
                    //用户评语
                    Element divElement = element.select("div[class=review-truncated-words]").get(0);
                    commentBean.setContent(divElement.text());
                    //y用户晒图
                    Elements imgElements = element.select("div[class=review-pictures] li[class=item] img");
                    int size = imgElements.size();
                    //晒出评论图片3张
                    if (size>3){
                        size = 3;
                    }
                    String[] imgs = new String[size];
                    for (int i = 0; i < size; i++) {
                        imgs[i] = imgElements.get(i).attr("data-lazyload");
                    }
                    commentBean.setImgs(imgs);
                    //用户评论的时间戳
                    Element spanEle = element.select("div[class=misc-info clearfix] span[class=time]").get(0);
                    commentBean.setDate(spanEle.text());
                    commentBeenList.add(commentBean);
                }
                Log.i("TAG", "=-=>评论内容： "+commentBeenList);
                adapter.addAll(commentBeenList,true);
            }
        });
    }
    @OnClick(R.id.img_business_detail_back)
    public void backToBusiness(View view){
        startActivity(new Intent(DetailActivity.this,BusinessActivity.class));
        finish();
    }
}
