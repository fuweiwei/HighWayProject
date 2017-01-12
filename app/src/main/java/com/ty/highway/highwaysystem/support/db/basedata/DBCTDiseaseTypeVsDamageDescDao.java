package com.ty.highway.highwaysystem.support.db.basedata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.ty.highway.highwaysystem.support.bean.basedata.CTDiseaseTypeVsDamageDescBean;
import com.ty.highway.highwaysystem.support.db.basic.DBDao;
import com.ty.highway.highwaysystem.support.db.basic.TableColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuweiwei on 2015/9/14.
 */
public class DBCTDiseaseTypeVsDamageDescDao extends DBDao implements TableColumns.CTDiseaseTypeVsDamageDesc {
    protected DBCTDiseaseTypeVsDamageDescDao(Context context) {
        super(context);
    }
    private static DBCTDiseaseTypeVsDamageDescDao INSTANCE;
    public static DBCTDiseaseTypeVsDamageDescDao getInstance(Context context){
        if(INSTANCE==null){
            synchronized (DBCTDiseaseTypeVsDamageDescDao.class) {
                if(INSTANCE==null){
                    INSTANCE = new DBCTDiseaseTypeVsDamageDescDao(context.getApplicationContext());
                }
            }
        }
        return INSTANCE;

    }
    /**
     * 获取病害by contentId
     * @param contentId
     * @return
     */
    public List<CTDiseaseTypeVsDamageDescBean> getAllInfoById(String contentId){
        List<CTDiseaseTypeVsDamageDescBean> list = new ArrayList<CTDiseaseTypeVsDamageDescBean>();
        SQLiteDatabase database = getDataBase();
        String sql = "select * from "+TABLE_CTDISEASETYPEVSDAMAGEDESC+" where "+CONTENTID+"=?";
        Cursor cursor = database.rawQuery(sql,new String[]{contentId});
        while (cursor.moveToNext()){
            CTDiseaseTypeVsDamageDescBean bean = new CTDiseaseTypeVsDamageDescBean();
            bean.setNewId(cursor.getString(cursor.getColumnIndex(NEWID)));
            bean.setDiseaseName(cursor.getString(cursor.getColumnIndex(DISEASENAME)));
            bean.setContentName(cursor.getString(cursor.getColumnIndex(CONTENTNAME)));
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
     * 添加一条数据
     */
    public  void addData(List<CTDiseaseTypeVsDamageDescBean> list){
        if(list ==null){
            return ;
        }
        SQLiteDatabase database = getDataBase();
        ContentValues values = new ContentValues();
        for(CTDiseaseTypeVsDamageDescBean bean :list){
            values.put(NEWID,bean.getNewId());
            String name = bean.getContentName();
            if(TextUtils.isEmpty(name)){
                values.put(CONTENTNAME,name);
            }else{
                values.put(CONTENTNAME,name.toLowerCase());
            }
            values.put(DISEASENAME,bean.getDiseaseName());
            values.put(CONTENTID,bean.getContentId().toLowerCase());
            values.put(DISEASEID,bean.getDiseaseId().toLowerCase());
            database.insert(TABLE_CTDISEASETYPEVSDAMAGEDESC, NEWID, values);
        }
        closeDB();
    }
    /**
     * 清空表数据
     */
    public  void clearData(){
        SQLiteDatabase database = getDataBase();
        database.execSQL("delete from " + TABLE_CTDISEASETYPEVSDAMAGEDESC);
        closeDB();
    }

    /**
     * 更新数据
     */
    public void updateData(){

    }
}
