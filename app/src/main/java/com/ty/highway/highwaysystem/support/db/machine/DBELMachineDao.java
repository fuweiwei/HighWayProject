package com.ty.highway.highwaysystem.support.db.machine;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ty.highway.highwaysystem.support.bean.machine.ELMachineBean;
import com.ty.highway.highwaysystem.support.db.basic.DBDao;
import com.ty.highway.highwaysystem.support.db.basic.TableColumns;

import java.util.List;

/**
 * Created by fuweiwei on 2016/1/5.
 */
public class DBELMachineDao extends DBDao implements TableColumns.ELMachine {
    private final static String TAG = "DBELMachineDao";
    private static DBELMachineDao instance;

    private DBELMachineDao(Context context) {
        super(context);
    }

    public static DBELMachineDao getInstance(Context context) {
        if (instance == null) {
            synchronized (DBELMachineDao.class) {
                instance = new DBELMachineDao(context.getApplicationContext());
            }
        }
        return instance;
    }

    /**
     * 添加数据
     * @param list
     */
    public  void addData(List<ELMachineBean> list,String type){
        if(list ==null){
            return ;
        }
        SQLiteDatabase database = getDataBase();
        for(ELMachineBean bean:list){
            ContentValues values = new ContentValues();
            values.put(NEWID,bean.getNewId());
            values.put(DEVICENAME,bean.getDeviceName());
            values.put(ORGID,bean.getOrgId());
            values.put(MMACHINETYPEID,bean.getMMachineTypeId());
            values.put(TYPE,type);
            database.insert(TABLE_ELMACHINE, NEWID, values);
        }
        closeDB();
    }

    /**
     * 获取设备的详细信息根据ID
     * @param newId
     * @return
     */
    public ELMachineBean getDataByNewId(String newId){
        ELMachineBean bean = new ELMachineBean();
        if(newId==null){
            return bean;
        }
        SQLiteDatabase database = getDataBase();
        String sql = "select *from "+TABLE_ELMACHINE+" where "+NEWID +"=?";
        Cursor c = database.rawQuery(sql, new String[]{newId});
        while (c!=null&&c.moveToNext()){
            bean.setNewId(c.getString(c.getColumnIndex(NEWID)));
            bean.setDeviceName(c.getString(c.getColumnIndex(DEVICENAME)));
            bean.setOrgId(c.getString(c.getColumnIndex(ORGID)));
            bean.setMMachineTypeId(c.getString(c.getColumnIndex(MMACHINETYPEID)));
            bean.setType(c.getString(c.getColumnIndex(TYPE)));
        }
        if(c != null) {
            c.close();
            c = null;
        }
        closeDB();
        return  bean;
    }
    /**
     * 清空表数据
     */
    public  void clearData(){
        SQLiteDatabase database = getDataBase();
        database.execSQL("delete from " + TABLE_ELMACHINE);
        closeDB();
    }
    /**
     * 清空表数据
     */
    public  void clearData(String type){
        SQLiteDatabase database = getDataBase();
        database.execSQL("delete from " + TABLE_ELMACHINE+" where "+TYPE+"='"+type+"'");
        closeDB();
    }
}
