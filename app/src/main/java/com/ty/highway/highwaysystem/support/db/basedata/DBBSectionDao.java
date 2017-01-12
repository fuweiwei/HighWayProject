package com.ty.highway.highwaysystem.support.db.basedata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ty.highway.highwaysystem.support.bean.basic.BSectionBean;
import com.ty.highway.highwaysystem.support.db.basic.DBDao;
import com.ty.highway.highwaysystem.support.db.basic.TableColumns;
import com.ty.highway.highwaysystem.support.utils.exception.ExceptionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 路段表操作
 * Created by ${dzm} on 2015/9/11 0011.
 */
public class DBBSectionDao extends DBDao implements TableColumns.BSection{

    private final static String TAG = "DBBSectionDao";
    private static DBBSectionDao instance;
    private Context mContext;

    private DBBSectionDao(Context context) {
        super(context);
        mContext = context;
    }

    public static DBBSectionDao getInstance(Context context) {
        if (instance == null) {
            synchronized (DBBSectionDao.class) {
                instance = new DBBSectionDao(context.getApplicationContext());
            }
        }
        return instance;
    }

/*
    */
/**
     * 更新路段
     *
     * @param bSectionBeanS
     *//*

    public void updateBSection(BSectionResultBean.BSectionBeanS bSectionBeanS) {
        String date = getUpdateDate();
        if (!"ERROR".equals(date)) {
            if (date == null || new Date(getUpdateDate()).before(new Date(bSectionBeanS.getUpdateTime()))) {
                SQLiteDatabase db = dbHelper.getDatabase();
                db.beginTransaction();
                try {
                    for (BSectionBean bSectionBean : bSectionBeanS.getData()) {
                        ContentValues values = ObjectUtils.getContentValues(bSectionBean);
                        int result = db.update(TABLE_BSECTION, values, null, null);
                        if (result < 0) {
                            Log.e(TAG, "路段更新失败");
                        } else {
                            Log.e(TAG, "路段更新成功");
                        }
                    }
                    db.setTransactionSuccessful();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "路段更新失败");
                } finally {
                    db.endTransaction();
                    closeDB();
                }
            }
        }


    }
*/

    /**
     * 获取路段最后更新时间
     *
     * @return
     */
    public String getUpdateDate() {
        Cursor cursor = null;
        try {
            String sql = "select UpdateDate from "+TABLE_BSECTION;
            cursor = dbHelper.getDatabase().rawQuery(sql, null);
            cursor.moveToNext();
            String date = cursor.getString(0);
            return date;
        } catch (Exception e) {
            new ExceptionUtils().doExecInfo(e.toString(), mContext);
            Log.e(TAG, "获取路段最后更新时间失败");
            return "ERROR";
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            closeDB();
        }

    }
    /**
     * 获取路段名通过id
     * @param newId
     * @return
     */
    public String getSectionNameById(String newId){
        SQLiteDatabase database = getDataBase();
        String sql = "select "+SECTIONNAME+" from "+TABLE_BSECTION+" where "+NEWID+"=?";
        Cursor cursor = database.rawQuery(sql,new String[]{newId});
        String name=null;
        if(cursor != null && cursor.moveToFirst()){
            name = cursor.getString(0);
        }
        closeDB();
        return name;
    }
    /**
     * 添加数据
     */
    public  void addData(List<BSectionBean> list){
        if(list ==null){
            return ;
        }
        SQLiteDatabase database = getDataBase();
        ContentValues values = new ContentValues();
        for(BSectionBean bean:list){
            values.put(NEWID,bean.getNewId());
            values.put(SECTIONNAME,bean.getSectionName());
            values.put(ROADID,bean.getRoadId());
            values.put(ROADTYPEID,bean.getRoadTypeId());
            values.put(SORT,bean.getSort());
            values.put(SKILLLEVEL,bean.getSkillLevel());
            database.insert(TABLE_BSECTION, NEWID, values);
        }
        closeDB();
    }
    /**
     * 获取所有路段信息
     * @return
     */
    public  List<BSectionBean> getAllData(){
        List<BSectionBean> list = new ArrayList<>();
        SQLiteDatabase database = getDataBase();
        String sql = "select *from "+TABLE_BSECTION;
        Cursor cursor = database.rawQuery(sql,null);
        while (cursor != null && cursor.moveToNext()){
            BSectionBean bean = new BSectionBean();
            bean.setRoadId(cursor.getString(cursor.getColumnIndex(ROADID)));
            bean.setNewId(cursor.getString(cursor.getColumnIndex(NEWID)));
            bean.setSectionName(cursor.getString(cursor.getColumnIndex(SECTIONNAME)));
            list.add(bean);
        }
        if(cursor!=null){
            cursor.close();
        }
        closeDB();
        return list;
    }
    /**
     * 通过roadId获取所有路段ID
     * @return
     */
    public  List<String> getAllNewIdByRoadId(String roadId){
        List<String> list = new ArrayList<>();
        SQLiteDatabase database = getDataBase();
        String sql = "select *from "+TABLE_BSECTION +" where "+ROADID+"=?";
        Cursor cursor = database.rawQuery(sql,new String[]{roadId});
        while (cursor != null && cursor.moveToNext()){
            list.add(cursor.getString(cursor.getColumnIndex(NEWID)));
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
        database.execSQL("delete from " + TABLE_BSECTION);
        closeDB();
    }
}
