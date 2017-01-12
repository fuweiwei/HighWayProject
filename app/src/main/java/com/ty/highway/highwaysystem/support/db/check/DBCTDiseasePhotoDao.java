package com.ty.highway.highwaysystem.support.db.check;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ty.highway.highwaysystem.support.bean.check.CTDiseasePhotoBean;
import com.ty.highway.highwaysystem.support.db.basic.DBDao;
import com.ty.highway.highwaysystem.support.db.basic.TableColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * 病害图片表操作
 * Created by ${dzm} on 2015/9/11 0011.
 */
public class DBCTDiseasePhotoDao extends DBDao implements TableColumns.CTDiseasePhoto{
    private final static String TAG = "DBCTDiseasePhotoDao";
    private static DBCTDiseasePhotoDao instance;

    private DBCTDiseasePhotoDao(Context context) {
        super(context);
    }

    public static DBCTDiseasePhotoDao getInstance(Context context) {
        if (instance == null) {
            synchronized (DBCTDiseasePhotoDao.class) {
                instance = new DBCTDiseasePhotoDao(context.getApplicationContext());
            }
        }
        return instance;
    }



    /**
     * 添加一条数据
     */
    public  void addData(CTDiseasePhotoBean bean){
        if(bean ==null){
            return ;
        }
        SQLiteDatabase database = getDataBase();
        ContentValues values = new ContentValues();
        values.put(GUID, bean.getGuid());
        values.put(DISEASEGUID, bean.getDiseaseGuid());
        values.put(POSITION, bean.getPosition());
        values.put(NAME, bean.getName());
        values.put(TASKID, bean.getTaskId());
        values.put(UPDATESTATE, bean.getUpdateState());
        values.put(LATTERNAME, bean.getLatterName());
        values.put(WEBDOCUMENT, bean.getWebDocument());

        database.insert(TABLE_CTDISEASEPHOTO, GUID, values);
        closeDB();
    }

    /**
     * 获取病害的图片信息
     * @param diseaseId
     * @return
     */
    public List<CTDiseasePhotoBean> getAllByDisId(String diseaseId){
        List<CTDiseasePhotoBean> list = new ArrayList<>();
        if(diseaseId ==null){
            return null ;
        }
        SQLiteDatabase database = getDataBase();
        String sql = "select * from "+TABLE_CTDISEASEPHOTO+" where " +DISEASEGUID+"=?";
        Cursor cursor = database.rawQuery(sql,new String[]{diseaseId});
        while (cursor.moveToNext()){
            CTDiseasePhotoBean bean = new CTDiseasePhotoBean();
            bean.setGuid(cursor.getString(cursor.getColumnIndex(GUID)));
            bean.setDiseaseGuid(cursor.getString(cursor.getColumnIndex(DISEASEGUID)));
            bean.setPosition(cursor.getString(cursor.getColumnIndex(POSITION)));
            bean.setName(cursor.getString(cursor.getColumnIndex(NAME)));
            bean.setTaskId(cursor.getString(cursor.getColumnIndex(TASKID)));
            bean.setUpdateState(cursor.getInt(cursor.getColumnIndex(UPDATESTATE)));
            bean.setLatterName(cursor.getString(cursor.getColumnIndex(LATTERNAME)));
            bean.setWebDocument(cursor.getString(cursor.getColumnIndex(WEBDOCUMENT)));
            list.add(bean);
        }
        if (cursor!=null){
            cursor.close();
        }
        closeDB();
        return list;
    }

    /**
     * 获取任务中所有病害的未上传图片
     * @param taskId
     * @return
     */
    public List<CTDiseasePhotoBean> getAllByTaskId(String taskId){
        List<CTDiseasePhotoBean> list = new ArrayList<>();
        if(taskId ==null){
            return null ;
        }
        SQLiteDatabase database = getDataBase();
        String sql = "select * from "+TABLE_CTDISEASEPHOTO+" where " +TASKID+"=? and "+UPDATESTATE+"=0";
        Cursor cursor = database.rawQuery(sql,new String[]{taskId});
        while (cursor.moveToNext()){
            CTDiseasePhotoBean bean = new CTDiseasePhotoBean();
            bean.setGuid(cursor.getString(cursor.getColumnIndex(GUID)));
            bean.setDiseaseGuid(cursor.getString(cursor.getColumnIndex(DISEASEGUID)));
            bean.setPosition(cursor.getString(cursor.getColumnIndex(POSITION)));
            bean.setName(cursor.getString(cursor.getColumnIndex(NAME)));
            bean.setTaskId(cursor.getString(cursor.getColumnIndex(TASKID)));
            bean.setUpdateState(cursor.getInt(cursor.getColumnIndex(UPDATESTATE)));
            bean.setLatterName(cursor.getString(cursor.getColumnIndex(LATTERNAME)));
            bean.setWebDocument(cursor.getString(cursor.getColumnIndex(WEBDOCUMENT)));
            list.add(bean);
        }
        if (cursor!=null){
            cursor.close();
        }
        closeDB();
        return list;
    }

    /**
     * 更新文件上传状态
     * @param position
     * @param updateState
     * @param document
     */
    public void updatePhotoInfo(String position, int updateState,String document){
        SQLiteDatabase db = getDataBase();
        String sql = "update "+TABLE_CTDISEASEPHOTO+" set "+UPDATESTATE+"= '"+updateState+"' , "+WEBDOCUMENT
                +"='"+document+"' where "+POSITION+"= '"+position+"'";
        db.execSQL(sql);
        closeDB();
    }

    public  void relatedTaskState(String relatedId,String taskId){
        SQLiteDatabase db = getDataBase();
        String sql = "update "+TABLE_CTDISEASEPHOTO +" set "+TASKID +"='"+taskId+"' where "+TASKID+"='"+relatedId+"'";
        db.execSQL(sql);
        closeDB();
    }
    /**
     * 清空表数据
     */
    public  void clearData(){
        SQLiteDatabase database = getDataBase();
        database.execSQL("delete from " + TABLE_CTDISEASEPHOTO);
        closeDB();
    }
    /**
     * 清空指定数据
     */
    public  void clearDataById(String disId){
        SQLiteDatabase database = getDataBase();
        database.execSQL("delete from " + TABLE_CTDISEASEPHOTO+" where "+DISEASEGUID+"='"+disId+"'");
        closeDB();
    }
    /**
     * 清空某任务数据
     */
    public  void clearDataByTaskId(String taskId){
        SQLiteDatabase database = getDataBase();
        database.execSQL("delete from " + TABLE_CTDISEASEPHOTO+" where "+TASKID+"='"+taskId+"'");
        closeDB();
    }

}
