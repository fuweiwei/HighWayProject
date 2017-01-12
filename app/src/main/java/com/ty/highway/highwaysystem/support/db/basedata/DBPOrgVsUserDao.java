package com.ty.highway.highwaysystem.support.db.basedata;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ty.highway.highwaysystem.support.bean.basedata.POrgVsUserBean;
import com.ty.highway.highwaysystem.support.db.basic.DBDao;
import com.ty.highway.highwaysystem.support.db.basic.TableColumns;

/**
 * Created by fuweiwei on 2015/9/14.
 */
public class DBPOrgVsUserDao extends DBDao implements TableColumns.POrgVsUser{
    protected DBPOrgVsUserDao(Context context) {
        super(context);
    }
    private static DBPOrgVsUserDao INSTANCE;
    public static DBPOrgVsUserDao getInstance(Context context){
        if(INSTANCE==null){
            synchronized (DBPOrgVsUserDao.class) {
                if(INSTANCE==null){
                    INSTANCE = new DBPOrgVsUserDao(context.getApplicationContext());
                }
            }
        }
        return INSTANCE;

    }
    /**
     * 添加一条数据
     */
    public  void addData(POrgVsUserBean bean){
        if(bean ==null){
            return ;
        }
        SQLiteDatabase database = getDataBase();
        ContentValues values = new ContentValues();
        values.put(NEWID,bean.getNewId());
        values.put(ORGANIZEID,bean.getOrganizeId());
        values.put(USERID,bean.getUserId());
        values.put(ROLEID,bean.getRoleId());
        values.put(STUTAS,bean.getStutas());
        values.put(ISDEFAULT,bean.getIsDefault());
        database.insert(TABLE_PORGVSUSER, NEWID, values);
        closeDB();
    }
    /**
     * 清空表数据
     */
    public  void clearData(){
        SQLiteDatabase database = getDataBase();
        database.execSQL("delete from " + TABLE_PORGVSUSER);
        closeDB();
    }
    /**
     * 更新数据
     */
    public void updateData(){

    }
}
