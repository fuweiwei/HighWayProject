package com.ty.highway.highwaysystem.support.db.basedata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ty.highway.highwaysystem.support.bean.basic.BTableUpdateBean;
import com.ty.highway.highwaysystem.support.db.basic.DBDao;
import com.ty.highway.highwaysystem.support.db.basic.TableColumns;

/**
 * Created by fuweiwei on 2015/9/16.
 */
public class DBBTableUpdateDao extends DBDao implements TableColumns.BTableUpdate{
    private final static String TAG = "DBBTableUpdateDao";
    private static DBBTableUpdateDao instance;

    private DBBTableUpdateDao(Context context) {
        super(context);
    }

    public static DBBTableUpdateDao getInstance(Context context) {
        if (instance == null) {
            synchronized (DBBTableUpdateDao.class) {
                instance = new DBBTableUpdateDao(context.getApplicationContext());
            }
        }
        return instance;
    }
    /**
     * 添加一条数据
     */
    public  void addData(BTableUpdateBean bean){
        if(bean ==null){
            return ;
        }
        deleteTaskInfo(bean.getUpdateType());
        SQLiteDatabase database = getDataBase();
        ContentValues values = new ContentValues();
        values.put(UPDATETYPE,bean.getUpdateType());
        values.put(UPDATETIME,bean.getUpdateTime());
        database.insert(TABLE_BTABLEUPDATE, UPDATETYPE, values);
        closeDB();
    }

    /**
     * 查询时间
     * @param type
     * @return
     */
    public String getTime(String type){
        SQLiteDatabase database = getDataBase();
        String sql = "select "+UPDATETIME+" from "+TABLE_BTABLEUPDATE+" where "+UPDATETYPE+"=?";
        Cursor cursor = database.rawQuery(sql, new String[]{type});
        String time =null;
        if(cursor != null && cursor.moveToFirst()) {
            time = cursor.getString(0);
        }
        if(cursor != null) {
            cursor.close();
            cursor = null;
        }
        closeDB();
        return time;
    }
    /**
     * 判断是否有该数据
     * @param type
     * @return
     */
    public Boolean isHasInfo(String type){
        SQLiteDatabase database = getDataBase();
        //判断表是否存在
        String sql = "select count(*)  from sqlite_master where type='table' and name = ?";
        Cursor cursor = database.rawQuery(sql, new String[]{TABLE_BTABLEUPDATE});
        if (cursor!=null && cursor.moveToFirst()){
            if(cursor.getInt(0)==0){
                return false;
            }
        }
        sql = "select count(*) from "+TABLE_BTABLEUPDATE+" where "+UPDATETYPE+"=?";
        cursor = database.rawQuery(sql, new String[]{type});
        int count =0;
        if(cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        if(cursor != null) {
            cursor.close();
            cursor = null;
        }
        closeDB();
        return count != 0;
    }
    /**
     * 删除一个任务
     * @param type
     */
    public  void deleteTaskInfo(String type){
        SQLiteDatabase database = getDataBase();
        database.delete(TABLE_BTABLEUPDATE, UPDATETYPE +"=?", new String[] { type });
        closeDB();
    }
    /**
     * 清空表数据
     */
    public  void clearData(){
        SQLiteDatabase database = getDataBase();
        database.execSQL("delete from " + TABLE_BTABLEUPDATE);
        closeDB();
    }
}
