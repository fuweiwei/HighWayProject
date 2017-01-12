package com.ty.highway.highwaysystem.support.db.basedata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ty.highway.highwaysystem.support.bean.basedata.CTDiseaseTypeByDescriptBean;
import com.ty.highway.highwaysystem.support.db.basic.DBDao;
import com.ty.highway.highwaysystem.support.db.basic.TableColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuweiwei on 2015/9/14.
 */
public class DBCTDiseaseTypeByDescriptDao extends DBDao implements TableColumns.CTDiseaseTypeByDescript {
    protected DBCTDiseaseTypeByDescriptDao(Context context) {
        super(context);
    }
    private static DBCTDiseaseTypeByDescriptDao INSTANCE;
    public static DBCTDiseaseTypeByDescriptDao getInstance(Context context){
        if(INSTANCE==null){
            synchronized (DBCTDiseaseTypeByDescriptDao.class) {
                if(INSTANCE==null){
                    INSTANCE = new DBCTDiseaseTypeByDescriptDao(context.getApplicationContext());
                }
            }
        }
        return INSTANCE;

    }

    public List<CTDiseaseTypeByDescriptBean> getAllById(String id){
        List<CTDiseaseTypeByDescriptBean>  list = new ArrayList<>();
        if(id ==null){
            return list ;
        }
        SQLiteDatabase database = getDataBase();
        String sql  = "select *from "+TABLE_CTDISEASETYPEBYDESCRIPT+" where "+DISEASEDESCRIBEID+"=?";
        Cursor cursor = database.rawQuery(sql ,new String[]{id});
        while (cursor.moveToNext()){
            CTDiseaseTypeByDescriptBean bean = new CTDiseaseTypeByDescriptBean();
            bean.setCreateDate(cursor.getString(cursor.getColumnIndex(CREATEDATE)));
            bean.setPropertyName(cursor.getString(cursor.getColumnIndex(PROPERTYNAME)));
            bean.setNewId(cursor.getString(cursor.getColumnIndex(NEWID)));
            bean.setDiseaseDescribeId(cursor.getString(cursor.getColumnIndex(DISEASEDESCRIBEID)));
            bean.setMeasuringUnit(cursor.getString(cursor.getColumnIndex(MEASURINGUNIT)));
            bean.setPropertyValue(cursor.getString(cursor.getColumnIndex(PROPERTYVALUE)));
            list.add(bean);
        }
        if(cursor!=null){
            cursor.close();;
        }
        closeDB();
        return list;
    }
    /**
     * 添加一条数据
     */
    public  void addData(List<CTDiseaseTypeByDescriptBean> list){
        if(list ==null){
            return ;
        }
        SQLiteDatabase database = getDataBase();
        ContentValues values = new ContentValues();
        for(CTDiseaseTypeByDescriptBean bean :list){
            values.put(NEWID,bean.getNewId());
            values.put(PROPERTYNAME,bean.getPropertyName());
            values.put(CREATEDATE,bean.getCreateDate());
            values.put(DISEASEDESCRIBEID,bean.getDiseaseDescribeId());
            values.put(MEASURINGUNIT,bean.getMeasuringUnit());
            values.put(PROPERTYVALUE,bean.getPropertyValue());
            database.insert(TABLE_CTDISEASETYPEBYDESCRIPT, NEWID, values);
        }
        closeDB();
    }
    /**
     * 清空表数据
     */
    public  void clearData(){
        SQLiteDatabase database = getDataBase();
        database.execSQL("delete from " + TABLE_CTDISEASETYPEBYDESCRIPT);
        closeDB();
    }

}
