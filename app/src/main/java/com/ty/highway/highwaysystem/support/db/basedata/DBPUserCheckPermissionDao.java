package com.ty.highway.highwaysystem.support.db.basedata;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ty.highway.highwaysystem.support.bean.basedata.PUserCheckPermissionBean;
import com.ty.highway.highwaysystem.support.db.basic.DBDao;
import com.ty.highway.highwaysystem.support.db.basic.TableColumns;

/**
 * Created by fuweiwei on 2015/9/14.
 */
public class DBPUserCheckPermissionDao extends DBDao implements TableColumns.PUserCheckPermission {
    protected DBPUserCheckPermissionDao(Context context) {
        super(context);
    }
    private static DBPUserCheckPermissionDao INSTANCE;
    public static DBPUserCheckPermissionDao getInstance(Context context){
        if(INSTANCE==null){
            synchronized (DBPUserCheckPermissionDao.class) {
                if(INSTANCE==null){
                    INSTANCE = new DBPUserCheckPermissionDao(context.getApplicationContext());
                }
            }
        }
        return INSTANCE;

    }
    /**
     * 添加一条数据
     */
    public  void addData(PUserCheckPermissionBean bean){
        if(bean ==null){
            return ;
        }
        SQLiteDatabase database = getDataBase();
        ContentValues values = new ContentValues();
        values.put(NEWID,bean.getNewId());
        values.put(SECTIONID,bean.getSectionId());
        values.put(TUNNELNAME,bean.getTunnelName());
        values.put(TUNNELCODE,bean.getTunnelCode());
        database.insert(TABLE_PUSERCHECKPERMISSION, NEWID, values);
        closeDB();
    }

    /**
     * 清空表数据
     */
    public  void clearData(){
        SQLiteDatabase database = getDataBase();
        database.execSQL("delete from "+TABLE_PUSERCHECKPERMISSION);
        closeDB();
    }
    /**
     * 更新数据
     */
    public void updateData(){

    }
}
