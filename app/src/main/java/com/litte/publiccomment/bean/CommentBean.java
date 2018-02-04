package com.litte.publiccomment.bean;

import java.util.Arrays;

/**
 * Created by litte on 2018/2/1.
 */

public class CommentBean {
    String avatar;//头像
    String name;//名字
    String date;//评论时间
    String price;//评论显示价格
    String rating;//打分
    String content;//评论正文
    String[] imgs;//配图

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getImgs() {
        return imgs;
    }

    public void setImgs(String[] imgs) {
        this.imgs = imgs;
    }

    @Override
    public String toString() {
        return "CommentBean{" +
                "avatar='" + avatar + '\'' +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", price='" + price + '\'' +
                ", rating='" + rating + '\'' +
                ", content='" + content + '\'' +
                ", imgs=" + Arrays.toString(imgs) +
                '}';
    }
}
