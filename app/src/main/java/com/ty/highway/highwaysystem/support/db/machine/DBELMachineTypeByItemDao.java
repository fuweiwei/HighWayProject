package com.ty.highway.highwaysystem.support.db.machine;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ty.highway.highwaysystem.support.bean.machine.ELMachineTypeByItemBean;
import com.ty.highway.highwaysystem.support.db.basic.DBDao;
import com.ty.highway.highwaysystem.support.db.basic.TableColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuweiwei on 2016/1/5.
 */
public class DBELMachineTypeByItemDao extends DBDao implements TableColumns.ELMachineTypeByItem {
    private final static String TAG = "DBELMachineTypeByItemDao";
    private static DBELMachineTypeByItemDao instance;

    private DBELMachineTypeByItemDao(Context context) {
        super(context);
    }

    public static DBELMachineTypeByItemDao getInstance(Context context) {
        if (instance == null) {
            synchronized (DBELMachineTypeByItemDao.class) {
                instance = new DBELMachineTypeByItemDao(context.getApplicationContext());
            }
        }
        return instance;
    }

    /**
     * 添加数据
     * @param list
     */
    public  void addData(List<ELMachineTypeByItemBean> list){
        if(list ==null){
            return ;
        }
        SQLiteDatabase database = getDataBase();
        for(ELMachineTypeByItemBean bean :list){
            ContentValues values = new ContentValues();
            values.put(NEWID,bean.getNewId());
            values.put(MMACHINERWAYID,bean.getMMachineRWayId());
            values.put(MMACHINEITEMID,bean.getMMachineItemId());
            values.put(MMACHINEITEMNAME,bean.getMMachineItemName());
            database.insert(TABLE_ELMACHINETYPEBYITEM, NEWID, values);
        }
        closeDB();
    }

    /**
     * 获取检查项目
     * @param rWayId
     * @return
     */
    public List<ELMachineTypeByItemBean> getDataByRWayId(String rWayId){
        List<ELMachineTypeByItemBean> list = new ArrayList<>();
        if(rWayId==null){
            return list;
        }
        SQLiteDatabase database = getDataBase();
        String sql = "select *from "+TABLE_ELMACHINETYPEBYITEM+" where "+MMACHINERWAYID +"=?";
        Cursor c = database.rawQuery(sql, new String[]{rWayId});
        while (c!=null&&c.moveToNext()){
            ELMachineTypeByItemBean bean = new ELMachineTypeByItemBean();
            bean.setNewId(c.getString(c.getColumnIndex(NEWID)));
            bean.setMMachineRWayId(c.getString(c.getColumnIndex(MMACHINERWAYID)));
            bean.setMMachineItemId(c.getString(c.getColumnIndex(MMACHINEITEMID)));
            bean.setMMachineItemName(c.getString(c.getColumnIndex(MMACHINEITEMNAME)));
            list.add(bean);
        }
        if(c != null) {
            c.close();
            c = null;
        }
        closeDB();
        return  list;
    }
    /**
     * 获取检查项目
     * @param itemId
     * @return
     */
    public ELMachineTypeByItemBean getDataByItemId(String itemId){
        ELMachineTypeByItemBean bean = new ELMachineTypeByItemBean();
        if(itemId==null){
            return bean;
        }
        SQLiteDatabase database = getDataBase();
        String sql = "select *from "+TABLE_ELMACHINETYPEBYITEM+" where "+MMACHINEITEMID +"=?";
        Cursor c = database.rawQuery(sql, new String[]{itemId});
        while (c!=null&&c.moveToNext()){
            bean.setNewId(c.getString(c.getColumnIndex(NEWID)));
            bean.setMMachineRWayId(c.getString(c.getColumnIndex(MMACHINERWAYID)));
            bean.setMMachineItemId(c.getString(c.getColumnIndex(MMACHINEITEMID)));
            bean.setMMachineItemName(c.getString(c.getColumnIndex(MMACHINEITEMNAME)));
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
        database.execSQL("delete from " + TABLE_ELMACHINETYPEBYITEM);
        closeDB();
    }
}
