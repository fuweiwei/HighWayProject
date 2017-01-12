package com.ty.highway.highwaysystem.support.db.machine;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ty.highway.highwaysystem.support.bean.machine.ELMachineByTypeBean;
import com.ty.highway.highwaysystem.support.db.basic.DBDao;
import com.ty.highway.highwaysystem.support.db.basic.TableColumns;

import java.util.List;

/**
 * Created by fuweiwei on 2016/1/5.
 */
public class DBELMachineByTypeDao extends DBDao implements TableColumns.ELMachineByType {
    private final static String TAG = "DBELMachineByTypeDao";
    private static DBELMachineByTypeDao instance;

    private DBELMachineByTypeDao(Context context) {
        super(context);
    }

    public static DBELMachineByTypeDao getInstance(Context context) {
        if (instance == null) {
            synchronized (DBELMachineByTypeDao.class) {
                instance = new DBELMachineByTypeDao(context.getApplicationContext());
            }
        }
        return instance;
    }
    /**
     * 添加数据
     * @param list
     */
    public  void addData(List<ELMachineByTypeBean> list){
        if(list ==null){
            return ;
        }
        SQLiteDatabase database = getDataBase();
        for(ELMachineByTypeBean bean:list){
            ContentValues values = new ContentValues();
            values.put(NEWID,bean.getNewId());
            values.put(WAYID,bean.getWayId());
            values.put(MMACHINETYPEID,bean.getMMachineTypeId());
            values.put(DEPLOYTYPE,bean.getDeployType());
            values.put(TUNNELID,bean.getTunnelId());
            values.put(CREATEDATE,bean.getCreateDate());
            values.put(UPDATEDATE,bean.getUpdateDate());
            database.insert(TABLE_ELMACHINEBYTYPE, NEWID, values);
        }
        closeDB();
    }

    /**
     * 获取机电类型关系根据类型id
     * @param typeId
     * @return
     */
    public ELMachineByTypeBean getDataByTypeId(String typeId,String checkWay,String tunnelId){
        ELMachineByTypeBean bean = new ELMachineByTypeBean();
        if(typeId==null||checkWay==null||tunnelId==null){
            return bean;
        }
        SQLiteDatabase database = getDataBase();
        String sql2 = "select *from "+TABLE_ELMACHINEBYTYPE+" where "+MMACHINETYPEID +"=? and "+WAYID+"=? and "+TUNNELID+"=?";
        Cursor c2 = database.rawQuery(sql2, new String[]{typeId,checkWay,tunnelId});
        if (c2!=null&&c2.moveToNext()){
            bean.setNewId(c2.getString(c2.getColumnIndex(NEWID)));
            bean.setWayId(c2.getString(c2.getColumnIndex(WAYID)));
            bean.setMMachineTypeId(c2.getString(c2.getColumnIndex(MMACHINETYPEID)));
            bean.setDeployType(c2.getInt(c2.getColumnIndex(DEPLOYTYPE)));
            bean.setTunnelId(c2.getString(c2.getColumnIndex(TUNNELID)));
            bean.setCreateDate(c2.getString(c2.getColumnIndex(CREATEDATE)));
            bean.setUpdateDate(c2.getString(c2.getColumnIndex(UPDATEDATE)));
        }else{
            String sql = "select *from "+TABLE_ELMACHINEBYTYPE+" where "+MMACHINETYPEID +"=? and "+WAYID+"=? and "+DEPLOYTYPE+"=?";
            Cursor c = database.rawQuery(sql, new String[]{typeId,checkWay,"0"});
            if (c!=null&&c.moveToNext()){
                bean.setNewId(c.getString(c.getColumnIndex(NEWID)));
                bean.setWayId(c.getString(c.getColumnIndex(WAYID)));
                bean.setMMachineTypeId(c.getString(c.getColumnIndex(MMACHINETYPEID)));
                bean.setDeployType(c.getInt(c.getColumnIndex(DEPLOYTYPE)));
                bean.setTunnelId(c.getString(c.getColumnIndex(TUNNELID)));
                bean.setCreateDate(c.getString(c.getColumnIndex(CREATEDATE)));
                bean.setUpdateDate(c.getString(c.getColumnIndex(UPDATEDATE)));
            }
            if(c != null) {
                c.close();
            }
        }
        if(c2 != null) {
            c2.close();
        }
        closeDB();
        return bean;
    }
    /**
     * 清空表数据
     */
    public  void clearData(){
        SQLiteDatabase database = getDataBase();
        database.execSQL("delete from " + TABLE_ELMACHINEBYTYPE);
        closeDB();
    }
}
