package com.ty.highway.highwaysystem.support.db.check;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ty.highway.highwaysystem.support.bean.check.CTNearTaskInfoBean;
import com.ty.highway.highwaysystem.support.db.basic.DBDao;
import com.ty.highway.highwaysystem.support.db.basic.TableColumns;

/**
 * 临时任务表操作
 * Created by ${dzm} on 2015/9/11 0011.
 */
public class DBCTNearTaskInfoDao extends DBDao implements TableColumns.CTNearTaskInfo{
    private final static String TAG = "DBBRoadeDao";
    private static DBCTNearTaskInfoDao instance;

    private DBCTNearTaskInfoDao(Context context) {
        super(context);
    }

    public static DBCTNearTaskInfoDao getInstance(Context context) {
        if (instance == null) {
            synchronized (DBCTNearTaskInfoDao.class) {
                instance = new DBCTNearTaskInfoDao(context.getApplicationContext());
            }
        }
        return instance;
    }

    /**
     * 添加一条数据
     */
    public  void addData(CTNearTaskInfoBean bean){
        if(bean ==null){
            return ;
        }
        SQLiteDatabase database = getDataBase();
        ContentValues values = new ContentValues();
        values.put(NEWID,bean.getNewId());
        values.put(CHECKTYPE, bean.getCheckType());
        values.put(CHECKWAYID, bean.getCheckWayId());
        values.put(CHECKRANGE, bean.getCheckRange());
        values.put(NAME, bean.getName());
        values.put(RELATIONTASKID, bean.getRelationTaskId());
        values.put(UPDATESTATE, bean.getUpdateState());
        database.insert(TABLE_CTNEARTASKINFO, NEWID, values);
        closeDB();
    }
    /**
     * 清空表数据
     */
    public  void clearData(){
        SQLiteDatabase database = getDataBase();
        database.execSQL("delete from " + TABLE_CTNEARTASKINFO);
        closeDB();
    }
}
