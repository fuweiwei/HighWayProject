package com.ty.highway.highwaysystem.support.db.basedata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ty.highway.highwaysystem.support.bean.basedata.CTDiseaseLevelBean;
import com.ty.highway.highwaysystem.support.db.basic.DBDao;
import com.ty.highway.highwaysystem.support.db.basic.TableColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuweiwei on 2015/9/15.
 */
public class DBCTDiseaseLevelDao extends DBDao implements TableColumns.CTDiseaseLevel{
    private final static String TAG = "DBCTDiseaseLevelDao";
    private static DBCTDiseaseLevelDao instance;

    private DBCTDiseaseLevelDao(Context context) {
        super(context);
    }

    public static DBCTDiseaseLevelDao getInstance(Context context) {
        if (instance == null) {
            synchronized (DBCTDictionaryDao.class) {
                instance = new DBCTDiseaseLevelDao(context.getApplicationContext());
            }
        }
        return instance;
    }
    public  void addData(CTDiseaseLevelBean bean){
        if(bean ==null){
            return ;
        }
        SQLiteDatabase database = getDataBase();
        ContentValues values = new ContentValues();
        values.put(NEWID,bean.getNewId());
        values.put(NAME, bean.getName());
        values.put(SORT, bean.getSort());
        values.put(TYPE, bean.getType());
        database.insert(TABLE_CTDISEASELEVEL, NEWID, values);
        closeDB();
    }
    /**
     * 获取判定级别by type
     * @param type
     * @return
     */
    public List<CTDiseaseLevelBean> getAllInfoByType(String type){
        List<CTDiseaseLevelBean> list = new ArrayList<CTDiseaseLevelBean>();
        SQLiteDatabase database = getDataBase();
        String sql = "select * from "+TABLE_CTDISEASELEVEL+" where "+TYPE+"=?";
        Cursor cursor = database.rawQuery(sql,new String[]{type});
        while (cursor.moveToNext()){
            CTDiseaseLevelBean bean = new CTDiseaseLevelBean();
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
     * 清空表数据
     */
    public  void clearData(){
        SQLiteDatabase database = getDataBase();
        database.execSQL("delete from " + TABLE_CTDISEASELEVEL);
        closeDB();
    }
}
