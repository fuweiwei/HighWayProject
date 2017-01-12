package com.ty.highway.highwaysystem.support.db.basedata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.ty.highway.highwaysystem.support.bean.basic.BRoadBean;
import com.ty.highway.highwaysystem.support.db.basic.DBDao;
import com.ty.highway.highwaysystem.support.db.basic.TableColumns;
import com.ty.highway.highwaysystem.support.utils.exception.ExceptionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 线路表操作
 * Created by ${dzm} on 2015/9/11 0011.
 */
public class DBBRoadDao extends DBDao implements TableColumns.BRoad{
    private final static String TAG = "DBBRoadeDao";
    private static DBBRoadDao instance;
    private Context mContext;

    private DBBRoadDao(Context context) {
        super(context);
        mContext = context;
    }

    public static DBBRoadDao getInstance(Context context) {
        if (instance == null) {
            synchronized (DBBRoadDao.class) {
                instance = new DBBRoadDao(context.getApplicationContext());
            }
        }
        return instance;
    }
    /**
     * 获取路线名通过id
     * @param newId
     * @return
     */
    public String getRoadNameById(String newId){
        if(TextUtils.isEmpty(newId)){
            return null;
        }
        SQLiteDatabase database = getDataBase();
        String sql = "select "+ROADNAME+" from "+TABLE_BROAD+" where "+NEWID+"=?";
        Cursor cursor = database.rawQuery(sql,new String[]{newId});
        String name=null;
        if(cursor != null && cursor.moveToFirst()){
            name = cursor.getString(0);
        }
        closeDB();
        return name;
    }

    /**
     * 获取所有路线信息
     * @return
     */
    public  List<BRoadBean> getAllData(){
        List<BRoadBean> list = new ArrayList<>();
        SQLiteDatabase database = getDataBase();
        String sql = "select *from "+TABLE_BROAD;
        Cursor cursor = database.rawQuery(sql,null);
        while (cursor != null && cursor.moveToNext()){
            BRoadBean bean = new BRoadBean();
            bean.setRoadName(cursor.getString(cursor.getColumnIndex(ROADNAME)));
            bean.setNewId(cursor.getString(cursor.getColumnIndex(NEWID)));
            bean.setRoadCode(cursor.getString(cursor.getColumnIndex(ROADCODE)));
            list.add(bean);
        }
        if(cursor!=null){
            cursor.close();
        }
        closeDB();
        return list;
    }
   /* *//**
     * 更新线路
     *
     * @param bRoadBeanS
     *//*
    public void updateBRoad(BRoadResutBean.BRoadBeanS bRoadBeanS) {
        String date = getUpdateDate();
        if (!"ERROR".equals(date)) {
            if (date == null || new Date(getUpdateDate()).before(new Date(bRoadBeanS.getUpdateTime()))) {
                SQLiteDatabase db = dbHelper.getDatabase();
                db.beginTransaction();
                try {
                    for (BRoadBean bRoadBean : bRoadBeanS.getData()) {
                        ContentValues values = ObjectUtils.getContentValues(bRoadBean);
                        int result = dbHelper.getDatabase().update(TABLE_BROAD, values, null, null);
                        if (result < 0) {
                            Log.e(TAG, "线路更新失败");
                        } else {
                            Log.e(TAG, "线路更新成功");
                        }
                    }
                    db.setTransactionSuccessful();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "线路更新失败");
                } finally {
                    db.endTransaction();
                    closeDB();
                }
            }
        }

    }
*/
    /**
     * 获取线路最后更新时间
     *
     * @return
     */
    public String getUpdateDate() {
        Cursor cursor = null;
        try {
            String sql = "select UpdateDate from "+TABLE_BROAD;
            cursor = dbHelper.getDatabase().rawQuery(sql, null);
            cursor.moveToNext();
            String date = cursor.getString(0);
            return date;
        } catch (Exception e) {
            new ExceptionUtils().doExecInfo(e.toString(),mContext);
            Log.e(TAG, "获取线路最后更新时间失败");
            return "ERROR";
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            closeDB();
        }

    }


    /**
     * 添加一条数据
     */
    public  void addData(List<BRoadBean> list){
        if(list ==null){
            return ;
        }
        SQLiteDatabase database = getDataBase();
        ContentValues values = new ContentValues();
        for(BRoadBean bean:list){
            values.put(NEWID,bean.getNewId());
            values.put(ROADNAME, bean.getRoadName());
            values.put(SORT, bean.getSort());
            values.put(ROADCODE, bean.getRoadCode());
            database.insert(TABLE_BROAD, NEWID, values);
        }
        closeDB();
    }
    /**
     * 清空表数据
     */
    public  void clearData(){
        SQLiteDatabase database = getDataBase();
        database.execSQL("delete from " + TABLE_BROAD);
        closeDB();
    }
}
