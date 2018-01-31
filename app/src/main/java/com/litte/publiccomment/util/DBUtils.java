package com.litte.publiccomment.util;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.litte.publiccomment.DB.DBHelper;
import com.litte.publiccomment.bean.CityPinYinBean;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by litte on 2018/1/26.
 */

public class DBUtils {
    DBHelper dbHelper;
    Dao<CityPinYinBean, ?> dao;
    public DBUtils(Context context){
        dbHelper = DBHelper.getINSTANCE(context);
        try {
            dao = dbHelper.getDao(CityPinYinBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void insert(CityPinYinBean cityPinYinBean){
        try {
            dao.createIfNotExists(cityPinYinBean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void insertAll(List<CityPinYinBean> cityPinYinBeanList){
        for (CityPinYinBean cityPinYinBean : cityPinYinBeanList) {
            insert(cityPinYinBean);
        }
    }
    public void insertBatch(final List<CityPinYinBean> list){
        //建立连接后，一次性将数据全部写入，再断开连接
        try {
            dao.callBatchTasks(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    for (CityPinYinBean bean :list){
                        insert(bean);
                    }
                    return null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<CityPinYinBean>  query(){
        try {
            List<CityPinYinBean> cityPinYinBeen = dao.queryForAll();
            return cityPinYinBeen;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询数据库异常");
        }
    }
}
