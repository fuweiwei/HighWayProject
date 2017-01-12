package com.ty.highway.highwaysystem.support.db.basedata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ty.highway.highwaysystem.support.bean.basedata.CTTunnelVsItemBean;
import com.ty.highway.highwaysystem.support.db.basic.DBDao;
import com.ty.highway.highwaysystem.support.db.basic.TableColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuweiwei on 2015/9/14.
 */
public class DBCTTunnelVsItemDao extends DBDao implements TableColumns.CTTunnelVsItem{
    protected DBCTTunnelVsItemDao(Context context) {
        super(context);
    }
    private static DBCTTunnelVsItemDao INSTANCE;
    public static DBCTTunnelVsItemDao getInstance(Context context){
        if(INSTANCE==null){
            synchronized (DBCTTunnelVsItemDao.class) {
                if(INSTANCE==null){
                    INSTANCE = new DBCTTunnelVsItemDao(context.getApplicationContext());
                }
            }
        }
        return INSTANCE;

    }
    /**
     * 添加数据
     */
    public  void addData(List<CTTunnelVsItemBean> list){
        if(list ==null){
            return ;
        }
        SQLiteDatabase database = getDataBase();
        ContentValues values = new ContentValues();
        for (CTTunnelVsItemBean bean:list){
            values.put(NEWID,bean.getNewId());
            values.put(TUNNELID,bean.getTunnelId());
            values.put(CHECKWAYID,bean.getCheckWayId());
            values.put(CREATEDATE,bean.getCreateDate());
            values.put(ITEMNAME,bean.getItemName());
            values.put(ITEMID,bean.getItemId().toLowerCase());
            values.put(AUDITCOUNT,bean.getAuditCount());
            database.insert(TABLE_CTTUNNELVSITEM, NEWID, values);
        }
        closeDB();
    }
    /**
     * 清空表数据
     */
    public  void clearData(){
        SQLiteDatabase database = getDataBase();
        database.execSQL("delete from " + TABLE_CTTUNNELVSITEM);
        closeDB();
    }
    /**
     * 获取检查项通过tunnelid
     * @param tunnelid
     * @param checkWayId
     * @return
     */
    public List<CTTunnelVsItemBean> getAllInfoById(String tunnelid,String checkWayId){
        List<CTTunnelVsItemBean> list = new ArrayList<>();
        if(tunnelid==null||checkWayId==null){
            return list;
        }
        SQLiteDatabase database = getDataBase();
        String sql = "select * from "+TABLE_CTTUNNELVSITEM+" where "+TUNNELID+"=? and "+CHECKWAYID+"=?";
        Cursor cursor = database.rawQuery(sql,new String[]{tunnelid,checkWayId});
        while (cursor.moveToNext()){
            CTTunnelVsItemBean bean = new CTTunnelVsItemBean();
            bean.setNewId(cursor.getString(cursor.getColumnIndex(NEWID)));
            bean.setTunnelId(cursor.getString(cursor.getColumnIndex(TUNNELID)));
            bean.setCheckWayId(cursor.getString(cursor.getColumnIndex(CHECKWAYID)));
            bean.setCreateDate(cursor.getString(cursor.getColumnIndex(CREATEDATE)));
            bean.setItemName(cursor.getString(cursor.getColumnIndex(ITEMNAME)));
            bean.setItemId(cursor.getString(cursor.getColumnIndex(ITEMID)));
            bean.setAuditCount(cursor.getInt(cursor.getColumnIndex(AUDITCOUNT)));
            list.add(bean);
        }
        if (cursor!=null){
            cursor.close();
        }
        closeDB();
        return list;
    }
    /**
     * 获取检查项通过newId
     * @param newId
     * @return
     */
    public CTTunnelVsItemBean getInfoByNewId(String newId){
        SQLiteDatabase database = getDataBase();
        String sql = "select * from "+TABLE_CTTUNNELVSITEM+" where "+NEWID+"=?";
        Cursor cursor = database.rawQuery(sql,new String[]{newId});
        CTTunnelVsItemBean bean = new CTTunnelVsItemBean();
        while (cursor.moveToNext()){
            bean.setNewId(cursor.getString(cursor.getColumnIndex(NEWID)));
            bean.setTunnelId(cursor.getString(cursor.getColumnIndex(TUNNELID)));
            bean.setCheckWayId(cursor.getString(cursor.getColumnIndex(CHECKWAYID)));
            bean.setCreateDate(cursor.getString(cursor.getColumnIndex(CREATEDATE)));
            bean.setItemName(cursor.getString(cursor.getColumnIndex(ITEMNAME)));
            bean.setItemId(cursor.getString(cursor.getColumnIndex(ITEMID)));
            bean.setAuditCount(cursor.getInt(cursor.getColumnIndex(AUDITCOUNT)));
        }
        if (cursor!=null){
            cursor.close();
        }
        closeDB();
        return bean;
    }
    /**
     * 获取检查项通过检查方式
     * @param checkId
     * @return
     */
    public List<CTTunnelVsItemBean> getAllInfoByCheckId(String checkId){
        List<CTTunnelVsItemBean> list = new ArrayList<>();
        SQLiteDatabase database = getDataBase();
        String sql = "select * from "+TABLE_CTTUNNELVSITEM+" where "+CHECKWAYID+"=? and "+AUDITCOUNT+"='0'";
        Cursor cursor = database.rawQuery(sql,new String[]{checkId});
        while (cursor.moveToNext()){
            CTTunnelVsItemBean bean = new CTTunnelVsItemBean();
            bean.setNewId(cursor.getString(cursor.getColumnIndex(NEWID)));
            bean.setTunnelId(cursor.getString(cursor.getColumnIndex(TUNNELID)));
            bean.setCheckWayId(cursor.getString(cursor.getColumnIndex(CHECKWAYID)));
            bean.setCreateDate(cursor.getString(cursor.getColumnIndex(CREATEDATE)));
            bean.setItemName(cursor.getString(cursor.getColumnIndex(ITEMNAME)));
            bean.setItemId(cursor.getString(cursor.getColumnIndex(ITEMID)));
            bean.setAuditCount(cursor.getInt(cursor.getColumnIndex(AUDITCOUNT)));
            list.add(bean);
        }
        closeDB();
        return list;
    }
    /**
     * 更新数据
     */
    public void updateData(){

    }
}
