package com.ty.highway.highwaysystem.support.db.basedata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ty.highway.highwaysystem.support.bean.basic.BTunnelBean;
import com.ty.highway.highwaysystem.support.db.basic.DBDao;
import com.ty.highway.highwaysystem.support.db.basic.TableColumns;
import com.ty.highway.highwaysystem.support.utils.exception.ExceptionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 隧道表操作
 * Created by ${dzm} on 2015/9/11 0011.
 */
public class DBBTunnelDao extends DBDao implements TableColumns.BTunnel{

    private final static String TAG = "DBBTunnelDao";
    private static DBBTunnelDao instance;
    private Context mContext;

    private DBBTunnelDao(Context context) {
        super(context);
        mContext = context;
    }

    public static DBBTunnelDao getInstance(Context context){
        if(instance == null){
            synchronized (DBBTunnelDao.class){
                instance = new DBBTunnelDao(context.getApplicationContext());
            }
        }
        return instance;
    }


    /**
     * 获取隧道最后更新时间
     *
     * @return
     */
    public String getUpdateDate() {
        Cursor cursor = null;
        try {
            String sql = "select UpdateDate from "+TABLE_BTUNNEL;
            cursor = dbHelper.getDatabase().rawQuery(sql, null);
            cursor.moveToNext();
            String date = cursor.getString(0);
            return date;
        } catch (Exception e) {
            new ExceptionUtils().doExecInfo(e.toString(), mContext);
            Log.e(TAG, "获取隧道最后更新时间失败");
            return "ERROR";
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            closeDB();
        }

    }

    /**
     * 获取隧道名通过id
     * @param id
     * @return
     */
    public String getTunnelById(String id){
        if(id==null){
            return "未知隧道";
        }
        SQLiteDatabase database = getDataBase();
        String sql = "select "+TUNNELNAME+" from "+TABLE_BTUNNEL+" where "+NEWID+"=?";
        Cursor cursor = database.rawQuery(sql,new String[]{id});
        String name="未知隧道";
        if(cursor != null && cursor.moveToFirst()){
            name = cursor.getString(0);
        }
        if(cursor!=null){
            cursor.close();
        }
        closeDB();
        return name;
    }
    /**
     * 获取bean通过newid
     * @param newID
     * @return
     */
    public BTunnelBean getInfoById(String newID,String belongUserId){
        BTunnelBean info = new BTunnelBean();
        if(newID==null||belongUserId==null){
            return info;
        }
        SQLiteDatabase database = getDataBase();
        String sql = "select * from "+TABLE_BTUNNEL+" where "+NEWID+"=? and "+BELONGUSERID+"=?";
        Cursor cursor = database.rawQuery(sql,new String[]{newID,belongUserId});
        if(cursor != null && cursor.moveToNext()){
            info.setNewId(cursor.getString(cursor.getColumnIndex(NEWID)));
            info.setSectionId(cursor.getString(cursor.getColumnIndex(SECTIONID)));
            info.setTunnelName(cursor.getString(cursor.getColumnIndex(TUNNELNAME)));
            info.setTunnelCode(cursor.getString(cursor.getColumnIndex(TUNNELCODE)));
            info.setRoadId(cursor.getString(cursor.getColumnIndex(ROADID)));
            info.setStartMileageNum(cursor.getDouble(cursor.getColumnIndex(STARTMILEAGENUM)));
            info.setEndMileageNum(cursor.getDouble(cursor.getColumnIndex(ENDMILEAGENUM)));
            info.setStartMileage(cursor.getString(cursor.getColumnIndex(STARTMILEAGE)));
            info.setEndMileage(cursor.getString(cursor.getColumnIndex(ENDMILEAGE)));
            info.setTunnelWidth(cursor.getDouble(cursor.getColumnIndex(TUNNELWIDTH)));
            info.setTunnelHeight(cursor.getDouble(cursor.getColumnIndex(TUNNELHEIGHT)));
            info.setTunnelLength(cursor.getDouble(cursor.getColumnIndex(TUNNELLENGTH)));
            info.setRemark(cursor.getString(cursor.getColumnIndex(REMARK)));
            info.setCompletionDate(cursor.getString(cursor.getColumnIndex(COMPLETIONDATE)));
        }
        if(cursor!=null){
            cursor.close();
        }
        closeDB();
        return info;
    }
    /**
     * 添加数据
     */
    public  void addData(List<BTunnelBean> list,String belongUserId){
        if(list ==null){
            return ;
        }
        SQLiteDatabase database = getDataBase();
        ContentValues values = new ContentValues();
        for(BTunnelBean bean:list){
            values.put(NEWID,bean.getNewId());
            values.put(SECTIONID,bean.getSectionId());
            values.put(TUNNELNAME,bean.getTunnelName());
            values.put(TUNNELCODE,bean.getTunnelCode());
            values.put(ROADID,bean.getRoadId());
            values.put(STARTMILEAGENUM,bean.getStartMileageNum());
            values.put(ENDMILEAGENUM,bean.getEndMileageNum());
            values.put(BELONGUSERID,belongUserId);
            values.put(STARTMILEAGE,bean.getStartMileage());
            values.put(ENDMILEAGE,bean.getEndMileage());
            values.put(TUNNELHEIGHT,bean.getTunnelHeight());
            values.put(TUNNELLENGTH,bean.getTunnelLength());
            values.put(TUNNELWIDTH,bean.getTunnelWidth());
            values.put(REMARK,bean.getRemark());
            values.put(COMPLETIONDATE,bean.getCompletionDate());

            database.insert(TABLE_BTUNNEL, NEWID, values);
        }
        closeDB();
    }

    /**
     * 获取用户所有的隧道
     * @return
     */
    public List<BTunnelBean> getAllTunnel(String belongUserId){
        List<BTunnelBean> list = new ArrayList<>();
        String sql = "select *from "+TABLE_BTUNNEL+" where "+BELONGUSERID+"=?";
        SQLiteDatabase database = getDataBase();
        Cursor cursor = database.rawQuery(sql,new String[]{belongUserId});
        while (cursor != null && cursor.moveToNext()){
            BTunnelBean info  = new BTunnelBean();
            info.setNewId(cursor.getString(cursor.getColumnIndex(NEWID)));
            info.setSectionId(cursor.getString(cursor.getColumnIndex(SECTIONID)));
            info.setTunnelName(cursor.getString(cursor.getColumnIndex(TUNNELNAME)));
            info.setTunnelCode(cursor.getString(cursor.getColumnIndex(TUNNELCODE)));
            info.setRoadId(cursor.getString(cursor.getColumnIndex(ROADID)));
            info.setStartMileageNum(cursor.getDouble(cursor.getColumnIndex(STARTMILEAGENUM)));
            info.setEndMileageNum(cursor.getDouble(cursor.getColumnIndex(ENDMILEAGENUM)));
            info.setStartMileage(cursor.getString(cursor.getColumnIndex(STARTMILEAGE)));
            info.setEndMileage(cursor.getString(cursor.getColumnIndex(ENDMILEAGE)));
            info.setTunnelWidth(cursor.getDouble(cursor.getColumnIndex(TUNNELWIDTH)));
            info.setTunnelHeight(cursor.getDouble(cursor.getColumnIndex(TUNNELHEIGHT)));
            info.setTunnelLength(cursor.getDouble(cursor.getColumnIndex(TUNNELLENGTH)));
            info.setRemark(cursor.getString(cursor.getColumnIndex(REMARK)));
            info.setCompletionDate(cursor.getString(cursor.getColumnIndex(COMPLETIONDATE)));
            list.add(info);
        }
        if(cursor!=null){
            cursor.close();
        }
        closeDB();
        return  list;
    }
    /**
     * 获取用户所有的newId通过section
     * @return
     */
    public List<String> getAllTunnelIdBySectionId(String belongUserId,String sectionId){
        List<String> list = new ArrayList<>();
        String sql = "select *from "+TABLE_BTUNNEL+" where "+BELONGUSERID+"=? and "+SECTIONID+"=?";
        SQLiteDatabase database = getDataBase();
        Cursor cursor = database.rawQuery(sql,new String[]{belongUserId,sectionId});
        while (cursor != null && cursor.moveToNext()){
            list.add(cursor.getString(cursor.getColumnIndex(NEWID)));
        }
        if(cursor!=null){
            cursor.close();
        }
        closeDB();
        return  list;
    }
    /**
     * 清空表数据
     * @param userId 用户id
     */
    public  void clearData(String userId){
        SQLiteDatabase database = getDataBase();
        database.execSQL("delete from " + TABLE_BTUNNEL+" where "+BELONGUSERID+"='"+userId+"'");
        closeDB();
    }
}
