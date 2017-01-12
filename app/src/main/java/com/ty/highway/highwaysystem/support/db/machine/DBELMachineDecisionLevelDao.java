package com.ty.highway.highwaysystem.support.db.machine;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ty.highway.highwaysystem.support.bean.machine.ELMachineDecisionLevelBean;
import com.ty.highway.highwaysystem.support.db.basic.DBDao;
import com.ty.highway.highwaysystem.support.db.basic.TableColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuweiwei on 2016/1/8.
 */
public class DBELMachineDecisionLevelDao extends DBDao implements TableColumns.ELMachineDecisionLevel{
    private final static String TAG = "DBELMachineDecisionLevelDao";
    private static DBELMachineDecisionLevelDao instance;

    private DBELMachineDecisionLevelDao(Context context) {
        super(context);
    }

    public static DBELMachineDecisionLevelDao getInstance(Context context) {
        if (instance == null) {
            synchronized (DBELMachineDecisionLevelDao.class) {
                instance = new DBELMachineDecisionLevelDao(context.getApplicationContext());
            }
        }
        return instance;
    }
    public List<ELMachineDecisionLevelBean> getAllInfo(){
        List<ELMachineDecisionLevelBean> list = new ArrayList<ELMachineDecisionLevelBean>();
        SQLiteDatabase database = getDataBase();
        String sql = "select * from "+TABLE_ELMACHINEDECISIONLEVEL;
        Cursor cursor = database.rawQuery(sql,null);
        while (cursor.moveToNext()){
            ELMachineDecisionLevelBean bean = new ELMachineDecisionLevelBean();
            bean.setNewId(cursor.getString(cursor.getColumnIndex(NEWID)));
            bean.setName(cursor.getString(cursor.getColumnIndex(NAME)));
            bean.setSort(cursor.getString(cursor.getColumnIndex(SORT)));
            bean.setType(cursor.getString(cursor.getColumnIndex(TYPE)));
            list.add(bean);
        }
        if(cursor!=null){
            cursor.close();
        }
        closeDB();
        return list;
    }

    /**
     * 获取判定等级根据类型
     * @param type
     * @return
     */
    public List<ELMachineDecisionLevelBean> getInfoByType(String type){
        List<ELMachineDecisionLevelBean> list = new ArrayList<ELMachineDecisionLevelBean>();
        SQLiteDatabase database = getDataBase();
        String sql = "select * from "+TABLE_ELMACHINEDECISIONLEVEL+" where "+TYPE+"=?";
        Cursor cursor = database.rawQuery(sql,new String[]{type});
        while (cursor.moveToNext()){
            ELMachineDecisionLevelBean bean = new ELMachineDecisionLevelBean();
            bean.setNewId(cursor.getString(cursor.getColumnIndex(NEWID)));
            bean.setName(cursor.getString(cursor.getColumnIndex(NAME)));
            bean.setSort(cursor.getString(cursor.getColumnIndex(SORT)));
            bean.setType(cursor.getString(cursor.getColumnIndex(TYPE)));
            list.add(bean);
        }
        if(cursor!=null){
            cursor.close();
        }
        closeDB();
        return list;
    }

    /**
     *根据id获取名称
     * @param newId
     * @return
     */
    public String getNameById(String newId){
        if(newId==null){
            return null;
        }
        SQLiteDatabase database = getDataBase();
        String sql = "select "+NAME+" from "+TABLE_ELMACHINEDECISIONLEVEL+" where "+NEWID+"=?";
        Cursor cursor = database.rawQuery(sql,new String[]{newId});
        String name=null;
        if(cursor != null && cursor.moveToFirst()){
            name = cursor.getString(0);
        }
        if(cursor!=null){
            cursor.close();
        }
        closeDB();
        return name;
    }
    public  void addData(ELMachineDecisionLevelBean bean){
        if(bean ==null){
            return ;
        }
        SQLiteDatabase database = getDataBase();
        ContentValues values = new ContentValues();
        values.put(NEWID,bean.getNewId());
        values.put(NAME, bean.getName());
        values.put(SORT, bean.getSort());
        values.put(TYPE, bean.getType());
        database.insert(TABLE_ELMACHINEDECISIONLEVEL, NEWID, values);
        closeDB();
    }
    /**
     * 清空表数据
     */
    public  void clearData(){
        SQLiteDatabase database = getDataBase();
        database.execSQL("delete from " + TABLE_ELMACHINEDECISIONLEVEL);
        closeDB();
    }
}
