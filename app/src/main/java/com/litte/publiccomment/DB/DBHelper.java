package com.litte.publiccomment.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.litte.publiccomment.bean.CityPinYinBean;

import java.sql.SQLException;

/**
 * Created by litte on 2018/1/26.
 */

public class DBHelper extends OrmLiteSqliteOpenHelper {
    //单例
    private static DBHelper INSTANCE;
    public static DBHelper getINSTANCE(Context context){
        if (INSTANCE == null){
            synchronized (DBHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new DBHelper(context);
                }
            }
        }
        return INSTANCE;
    }
    public DBHelper(Context context) {
        super(context, "cityname.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource,CityPinYinBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {
            TableUtils.dropTable(connectionSource,CityPinYinBean.class,true);
            onCreate(sqLiteDatabase,connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
