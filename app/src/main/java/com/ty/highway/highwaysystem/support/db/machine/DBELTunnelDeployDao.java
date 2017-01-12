package com.ty.highway.highwaysystem.support.db.machine;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ty.highway.highwaysystem.support.bean.machine.ELTunnelDeployBean;
import com.ty.highway.highwaysystem.support.db.basic.DBDao;
import com.ty.highway.highwaysystem.support.db.basic.TableColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuweiwei on 2016/1/5.
 */
public class DBELTunnelDeployDao extends DBDao implements TableColumns.ELTunnelDeploy {
    private final static String TAG = "DBELTunnelDeployDao";
    private static DBELTunnelDeployDao instance;

    private DBELTunnelDeployDao(Context context) {
        super(context);
    }

    public static DBELTunnelDeployDao getInstance(Context context) {
        if (instance == null) {
            synchronized (DBELTunnelDeployDao.class) {
                instance = new DBELTunnelDeployDao(context.getApplicationContext());
            }
        }
        return instance;
    }
    /**
     * 添加数据
     * @param list
     */
    public  void addData(List<ELTunnelDeployBean> list){
        if(list ==null){
            return ;
        }
        SQLiteDatabase database = getDataBase();
        for(ELTunnelDeployBean bean:list){
            ContentValues values = new ContentValues();
            values.put(NEWID,bean.getNewId());
            values.put(TUNNELD,bean.getTunneld());
            values.put(MAXDEVICEID,bean.getMaxDeviceId());
            values.put(MINDEVICEID,bean.getMinDeviceId());
            values.put(DEVICETYPE,bean.getDeviceType());
            database.insert(TABLE_ELTUNNELDEPLOY, NEWID, values);
        }
        closeDB();
    }

    /**
     * 获取隧道的设备
     * @param tunnelId
     * @return
     */
    public  List<ELTunnelDeployBean> getDataByTunnelId(String tunnelId){
        List<ELTunnelDeployBean> list = new ArrayList<>();
        if(tunnelId==null){
            return  list;

        }
        SQLiteDatabase database = getDataBase();
        String sql = "select *from "+TABLE_ELTUNNELDEPLOY+" where "+TUNNELD +"=?";
        Cursor c = database.rawQuery(sql, new String[]{tunnelId});
        while (c!=null&&c.moveToNext()){
            ELTunnelDeployBean bean = new ELTunnelDeployBean();
            bean.setNewId(c.getString(c.getColumnIndex(NEWID)));
            bean.setTunneld(c.getString(c.getColumnIndex(TUNNELD)));
            bean.setMaxDeviceId(c.getString(c.getColumnIndex(MAXDEVICEID)));
            bean.setMinDeviceId(c.getString(c.getColumnIndex(MINDEVICEID)));
            bean.setDeviceType(c.getInt(c.getColumnIndex(DEVICETYPE)));
            list.add(bean);
        }
        if(c != null) {
            c.close();
            c = null;
        }
        closeDB();
        return list;
    }
    /**
     * 清空表数据
     */
    public  void clearData(){
        SQLiteDatabase database = getDataBase();
        database.execSQL("delete from " + TABLE_ELTUNNELDEPLOY);
        closeDB();
    }
}
