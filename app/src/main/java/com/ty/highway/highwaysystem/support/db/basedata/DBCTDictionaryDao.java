package com.ty.highway.highwaysystem.support.db.basedata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ty.highway.highwaysystem.support.bean.basedata.CTDictionaryBean;
import com.ty.highway.highwaysystem.support.db.basic.DBDao;
import com.ty.highway.highwaysystem.support.db.basic.TableColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuweiwei on 2015/9/15.
 */
public class DBCTDictionaryDao extends DBDao implements TableColumns.CTDictionary{
    private final static String TAG = "DBCTDictionaryDao";
    private static DBCTDictionaryDao instance;

    private DBCTDictionaryDao(Context context) {
        super(context);
    }

    public static DBCTDictionaryDao getInstance(Context context) {
        if (instance == null) {
            synchronized (DBCTDictionaryDao.class) {
                instance = new DBCTDictionaryDao(context.getApplicationContext());
            }
        }
        return instance;
    }
    public List<CTDictionaryBean> getAllInfo(){
        List<CTDictionaryBean> list = new ArrayList<CTDictionaryBean>();
        SQLiteDatabase database = getDataBase();
        String sql = "select * from "+TABLE_CTDICTIONARY;
        Cursor cursor = database.rawQuery(sql,null);
        while (cursor.moveToNext()){
            CTDictionaryBean bean = new CTDictionaryBean();
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

    public String getIDBySort(int sort){
        SQLiteDatabase database = getDataBase();
        String sql = "select "+NEWID+" from "+TABLE_CTDICTIONARY+" where "+SORT+"=?";
        Cursor cursor = database.rawQuery(sql,new String[]{sort+""});
        String id=null;
        if(cursor != null && cursor.moveToFirst()){
            id = cursor.getString(0);
        }
        if(cursor!=null){
            cursor.close();
        }
        closeDB();
        return id;
    }
    public  void addData(CTDictionaryBean bean){
        if(bean ==null){
            return ;
        }
        SQLiteDatabase database = getDataBase();
        ContentValues values = new ContentValues();
        values.put(NEWID,bean.getNewId());
        values.put(NAME, bean.getName());
        values.put(SORT, bean.getSort());
        values.put(TYPE, bean.getType());
        database.insert(TABLE_CTDICTIONARY, NEWID, values);
        closeDB();
    }
    /**
     * 清空表数据
     */
    public  void clearData(){
        SQLiteDatabase database = getDataBase();
        database.execSQL("delete from " + TABLE_CTDICTIONARY);
        closeDB();
    }
}
