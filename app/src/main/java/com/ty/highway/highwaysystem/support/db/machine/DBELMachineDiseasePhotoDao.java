package com.ty.highway.highwaysystem.support.db.machine;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ty.highway.highwaysystem.support.bean.machine.ELMachineDiseasePhotoBean;
import com.ty.highway.highwaysystem.support.db.basic.DBDao;
import com.ty.highway.highwaysystem.support.db.basic.TableColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * j机电检修病害图片表操作
 * Created by fww on 2016/1/11 0011.
 */
public class DBELMachineDiseasePhotoDao extends DBDao implements TableColumns.ELMachineDiseasePhoto{
    private final static String TAG = "DBELMachineDiseasePhotoDao";
    private static DBELMachineDiseasePhotoDao instance;

    private DBELMachineDiseasePhotoDao(Context context) {
        super(context);
    }

    public static DBELMachineDiseasePhotoDao getInstance(Context context) {
        if (instance == null) {
            synchronized (DBELMachineDiseasePhotoDao.class) {
                instance = new DBELMachineDiseasePhotoDao(context.getApplicationContext());
            }
        }
        return instance;
    }



    /**
     * 添加一条数据
     */
    public  void addData(ELMachineDiseasePhotoBean bean){
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

        database.insert(TABLE_ELMACHINEDISEASEPHOTO, GUID, values);
        closeDB();
    }

    /**
     * 获取病害的图片信息
     * @param diseaseId
     * @return
     */
    public List<ELMachineDiseasePhotoBean> getAllByDisId(String diseaseId){
        List<ELMachineDiseasePhotoBean> list = new ArrayList<>();
        if(diseaseId ==null){
            return null ;
        }
        SQLiteDatabase database = getDataBase();
        String sql = "select * from "+TABLE_ELMACHINEDISEASEPHOTO+" where " +DISEASEGUID+"=?";
        Cursor cursor = database.rawQuery(sql,new String[]{diseaseId});
        while (cursor.moveToNext()){
            ELMachineDiseasePhotoBean bean = new ELMachineDiseasePhotoBean();
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
    public List<ELMachineDiseasePhotoBean> getAllByTaskId(String taskId){
        List<ELMachineDiseasePhotoBean> list = new ArrayList<>();
        if(taskId ==null){
            return null ;
        }
        SQLiteDatabase database = getDataBase();
        String sql = "select * from "+TABLE_ELMACHINEDISEASEPHOTO+" where " +TASKID+"=? and "+UPDATESTATE+"=0";
        Cursor cursor = database.rawQuery(sql,new String[]{taskId});
        while (cursor.moveToNext()){
            ELMachineDiseasePhotoBean bean = new ELMachineDiseasePhotoBean();
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
        String sql = "update "+TABLE_ELMACHINEDISEASEPHOTO+" set "+UPDATESTATE+"= '"+updateState+"' , "+WEBDOCUMENT
                +"='"+document+"' where "+POSITION+"= '"+position+"'";
        db.execSQL(sql);
        closeDB();
    }

    public  void relatedTaskState(String relatedId,String taskId){
        SQLiteDatabase db = getDataBase();
        String sql = "update "+TABLE_ELMACHINEDISEASEPHOTO +" set "+TASKID +"='"+taskId+"' where "+TASKID+"='"+relatedId+"'";
        db.execSQL(sql);
        closeDB();
    }
    /**
     * 清空表数据
     */
    public  void clearData(){
        SQLiteDatabase database = getDataBase();
        database.execSQL("delete from " + TABLE_ELMACHINEDISEASEPHOTO);
        closeDB();
    }
    /**
     * 清空指定数据
     */
    public  void clearDataById(String disId){
        SQLiteDatabase database = getDataBase();
        database.execSQL("delete from " + TABLE_ELMACHINEDISEASEPHOTO+" where "+DISEASEGUID+"='"+disId+"'");
        closeDB();
    }
    /**
     * 清空某任务数据
     */
    public  void clearDataByTaskId(String taskId){
        SQLiteDatabase database = getDataBase();
        database.execSQL("delete from " + TABLE_ELMACHINEDISEASEPHOTO+" where "+TASKID+"='"+taskId+"'");
        closeDB();
    }

}
