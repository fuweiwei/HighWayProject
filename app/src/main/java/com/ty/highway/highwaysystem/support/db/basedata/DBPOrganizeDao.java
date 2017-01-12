package com.ty.highway.highwaysystem.support.db.basedata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ty.highway.highwaysystem.support.bean.basic.POrganizeBean;
import com.ty.highway.highwaysystem.support.db.basic.DBDao;
import com.ty.highway.highwaysystem.support.db.basic.TableColumns;
import com.ty.highway.highwaysystem.support.utils.exception.ExceptionUtils;

/**
 * 组织表操作
 * Created by ${dzm} on 2015/9/11 0011.
 */
public class DBPOrganizeDao extends DBDao implements TableColumns.POrganize{

    private final static String TAG = "DBPOrganizeDao";
    private static DBPOrganizeDao instance;
    private Context mContext;

    private DBPOrganizeDao(Context context) {
        super(context);
        mContext = context;
    }

    public static DBPOrganizeDao getInstance(Context context) {
        if (instance == null) {
            synchronized (DBPOrganizeDao.class) {
                instance = new DBPOrganizeDao(context.getApplicationContext());
            }
        }
        return instance;
    }

   /* *//**
     * 组织隧道
     *
     * @param pOrganizeBeanS
     *//*
    public void updatePOrganize(POrganizeResultBean.POrganizeBeanS pOrganizeBeanS) {
        String date = getUpdateDate();
        if (!"ERROR".equals(date)) {
            if (date == null || new Date(getUpdateDate()).before(new Date(pOrganizeBeanS.getUpdateTime()))) {
                SQLiteDatabase db = dbHelper.getDatabase();
                db.beginTransaction();
                try {
                    for (POrganizeBean pOrganizeBean : pOrganizeBeanS.getData()) {
                        ContentValues values = ObjectUtils.getContentValues(pOrganizeBean);
                        int result = db.update(TABLE_PORGANIZE, values, null, null);
                        if (result < 0) {
                            Log.e(TAG, "组织更新失败");
                        } else {
                            Log.e(TAG, "组织更新成功");
                        }
                    }
                    db.setTransactionSuccessful();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "组织更新失败");
                } finally {
                    db.endTransaction();
                    closeDB();
                }
            }
        }

    }*/

    /**
     * 获取组织最后更新时间
     *
     * @return
     */
    public String getUpdateDate() {
        Cursor cursor = null;
        try {
            String sql = "select UpdateDate from "+TABLE_PORGANIZE;
            cursor = dbHelper.getDatabase().rawQuery(sql, null);
            cursor.moveToNext();
            String date = cursor.getString(0);
            return date;
        } catch (Exception e) {
            new ExceptionUtils().doExecInfo(e.toString(), mContext);
            Log.e(TAG, "获取组织最后更新时间失败");
            return "ERROR";
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            closeDB();
        }

    }
    /**
     * 添加一条数据
     */
    public  void addData(POrganizeBean bean){
        if(bean ==null){
            return ;
        }
        SQLiteDatabase database = getDataBase();
        ContentValues values = new ContentValues();
        values.put(NEWID,bean.getNewId());
        values.put(ORGANIZENAME,bean.getOrganizeName());
        values.put(ORGANIZEADDRESS,bean.getOrganizeAddress());
        values.put(ORGANIZENATURE,bean.getOrganizeNature());
        values.put(ORGANIZEPHONE,bean.getOrganizePhone());
        values.put(PID,bean.getPid());
        values.put(CREATEDATE,bean.getCreateDate());
        values.put(UPDATEDATE,bean.getUpdateDate());
        values.put(ORGANIZENUMBER,bean.getOrganizeNumber());
        values.put(SIMPLENAME,bean.getSimpleName());
        values.put(ORGANIZETYPE,bean.getOrganizeType());
        database.insert(TABLE_PORGANIZE, NEWID, values);
        closeDB();
    }
    /**
     * 清空表数据
     */
    public  void clearData(){
        SQLiteDatabase database = getDataBase();
        database.execSQL("delete from " + TABLE_PORGANIZE);
        closeDB();
    }
}
