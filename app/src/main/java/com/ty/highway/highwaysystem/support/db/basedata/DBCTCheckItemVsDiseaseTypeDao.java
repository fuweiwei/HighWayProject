package com.ty.highway.highwaysystem.support.db.basedata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.ty.highway.highwaysystem.support.bean.basedata.CTCheckItemVsDiseaseTypeBean;
import com.ty.highway.highwaysystem.support.db.basic.DBDao;
import com.ty.highway.highwaysystem.support.db.basic.TableColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuweiwei on 2015/9/14.
 */
public class DBCTCheckItemVsDiseaseTypeDao extends DBDao implements TableColumns.CTCheckItemVsDiseaseType{
    protected DBCTCheckItemVsDiseaseTypeDao(Context context) {
        super(context);
    }
    private static DBCTCheckItemVsDiseaseTypeDao INSTANCE;
    public static DBCTCheckItemVsDiseaseTypeDao getInstance(Context context){
        if(INSTANCE==null){
            synchronized (DBCTCheckItemVsDiseaseTypeDao.class) {
                if(INSTANCE==null){
                    INSTANCE = new DBCTCheckItemVsDiseaseTypeDao(context.getApplicationContext());
                }
            }
        }
        return INSTANCE;

    }
    /**
     * 获取检查类型by itemId
     * @param itemId
     * @return
     */
    public List<CTCheckItemVsDiseaseTypeBean> getAllInfoById(String itemId){
        List<CTCheckItemVsDiseaseTypeBean> list = new ArrayList<CTCheckItemVsDiseaseTypeBean>();
        SQLiteDatabase database = getDataBase();
        String sql = "select * from "+TABLE_CTCHECKITEMVSDISEASETYPE+" where "+ITEMID+"=?";
        Cursor cursor = database.rawQuery(sql,new String[]{itemId});
        while (cursor.moveToNext()){
            CTCheckItemVsDiseaseTypeBean bean = new CTCheckItemVsDiseaseTypeBean();
            bean.setNewId(cursor.getString(cursor.getColumnIndex(NEWID)));
            bean.setContentName(cursor.getString(cursor.getColumnIndex(CONTENTNAME)));
            bean.setItemId(cursor.getString(cursor.getColumnIndex(ITEMID)));
            bean.setContentId(cursor.getString(cursor.getColumnIndex(CONTENTID)));
            bean.setDiseaseId(cursor.getString(cursor.getColumnIndex(DISEASEID)));
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
    public  void addData(List<CTCheckItemVsDiseaseTypeBean> list){
        if(list ==null){
            return ;
        }
        SQLiteDatabase database = getDataBase();
        ContentValues values = new ContentValues();
        for (CTCheckItemVsDiseaseTypeBean bean :list){
            values.put(NEWID,bean.getNewId());
            values.put(CONTENTNAME,bean.getContentName());
            values.put(ITEMID,bean.getItemId().toLowerCase());
            values.put(CONTENTID,bean.getContentId().toLowerCase());
            values.put(DISEASEID,!TextUtils.isEmpty(bean.getDiseaseId())?bean.getDiseaseId().toLowerCase():null);
            database.insert(TABLE_CTCHECKITEMVSDISEASETYPE, NEWID, values);
        }
        closeDB();
    }
    /**
     * 清空表数据
     */
    public  void clearData(){
        SQLiteDatabase database = getDataBase();
        database.execSQL("delete from " + TABLE_CTCHECKITEMVSDISEASETYPE);
        closeDB();
    }
    /**
     * 更新数据
     */
    public void updateData(){

    }
}
