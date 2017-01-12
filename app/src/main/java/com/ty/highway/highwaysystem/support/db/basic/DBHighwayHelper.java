package com.ty.highway.highwaysystem.support.db.basic;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by fuweiwei on 2015/9/9.
 * 数据库操作类，与DBHelp不同，该数据库放在应用目录下，更安全
 */
public class DBHighwayHelper extends SQLiteOpenHelper {
    private final static String DB_NAME = "high_way.db";
    private final static int DB_VERSION = 1;
    public DBHighwayHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public DBHighwayHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//创建表
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//数据库更新操作
    }
}
