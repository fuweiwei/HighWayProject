package com.ty.highway.highwaysystem.support.db.basedata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ty.highway.highwaysystem.support.bean.basedata.CTCheckPositionBean;
import com.ty.highway.highwaysystem.support.db.basic.DBDao;
import com.ty.highway.highwaysystem.support.db.basic.TableColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuweiwei on 2015/9/14.
 */
public class DBCTCheckPositionDao extends DBDao implements TableColumns.CTCheckPosition {
    protected DBCTCheckPositionDao(Context context) {
        super(context);
    }
    private static DBCTCheckPositionDao INSTANCE;
    public static DBCTCheckPositionDao getInstance(Context context){
        if(INSTANCE==null){
            synchronized (DBCTCheckPositionDao.class) {
                if(INSTANCE==null){
                    INSTANCE = new DBCTCheckPositionDao(context.getApplicationContext());
                }
            }
        }
        return INSTANCE;

    }
    /**
     * 获取检查位置by itemId
     * @param itemId
     * @return
     */
    public List<CTCheckPositionBean> getAllInfoById(String itemId){
        List<CTCheckPositionBean> list = new ArrayList<CTCheckPositionBean>();
        SQLiteDatabase database = getDataBase();
        String sql = "select * from "+TABLE_CTCHECKPOSITION+" where "+ITEMID+"=?";
        Cursor cursor = database.rawQuery(sql,new String[]{itemId});
        while (cursor.moveToNext()){
            CTCheckPositionBean bean = new CTCheckPositionBean();
            bean.setNewId(cursor.getString(cursor.getColumnIndex(NEWID)));
            bean.setCheckwayId(cursor.getString(cursor.getColumnIndex(CHECKWAYID)));
            bean.setItemId(cursor.getString(cursor.getColumnIndex(ITEMID)));
            bean.setRangeName(cursor.getString(cursor.getColumnIndex(RANGENAME)));
            list.add(bean);
        }
        if(cursor!=null){
            cursor.close();
        }
        closeDB();
        return list;
    }
    /**
     * 添加数据
     */
    public  void addData(List<CTCheckPositionBean> list){
        if(list ==null){
            return ;
        }
        SQLiteDatabase database = getDataBase();
        ContentValues values = new ContentValues();
        for (CTCheckPositionBean bean:list){
            values.put(NEWID,bean.getNewId());
            values.put(ITEMID,bean.getItemId());
            values.put(RANGENAME,bean.getRangeName());
            values.put(CHECKWAYID,bean.getCheckwayId());
            database.insert(TABLE_CTCHECKPOSITION, NEWID, values);
        }
        closeDB();
    }
    /**
     * 清空表数据
     */
    public  void clearData(){
        SQLiteDatabase database = getDataBase();
        database.execSQL("delete from " + TABLE_CTCHECKPOSITION);
        closeDB();
    }

    /**
     * 获取位置信息
     * @param newId
     * @return
     */
    public String getPosrionString(String newId){
        if(newId==null){
            return  "";
        }
        String s = null;
        SQLiteDatabase database = getDataBase();
        String sql = "select * from "+TABLE_CTCHECKPOSITION+" where "+NEWID+"=?";
        Cursor cursor = database.rawQuery(sql, new String[]{newId});
        while (cursor.moveToNext()){
            s = cursor.getString(cursor.getColumnIndex(RANGENAME));
        }
        if(cursor!=null){
            cursor.close();
        }
        closeDB();
        return  s;
    }
    /**
     * 获取位置信息
     * @param itemId
     * @return
     */
    public String getPosrionStringByItemId(String itemId){
        if(itemId==null){
            return  "";
        }
        String s = null;
        SQLiteDatabase database = getDataBase();
        String sql = "select * from "+TABLE_CTCHECKPOSITION+" where "+ITEMID+"=?";
        Cursor cursor = database.rawQuery(sql,new String[]{itemId});
        while (cursor.moveToNext()){
            s = cursor.getString(cursor.getColumnIndex(RANGENAME));
        }
        if(cursor!=null){
            cursor.close();
        }
        closeDB();
        return  s;
    }
    /**
     * 更新数据
     */
    public void updateData(){

    }
}
