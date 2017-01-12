package com.ty.highway.highwaysystem.support.db.machine;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ty.highway.highwaysystem.support.bean.machine.ELMachineContentByDescriptBean;
import com.ty.highway.highwaysystem.support.db.basic.DBDao;
import com.ty.highway.highwaysystem.support.db.basic.TableColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuweiwei on 2016/1/5.
 */
public class DBELMachineContentByDescriptDao extends DBDao implements TableColumns.ELMachineContentByDescript {
    private final static String TAG = "DBELMachineContentByDescriptDao";
    private static DBELMachineContentByDescriptDao instance;

    private DBELMachineContentByDescriptDao(Context context) {
        super(context);
    }

    public static DBELMachineContentByDescriptDao getInstance(Context context) {
        if (instance == null) {
            synchronized (DBELMachineContentByDescriptDao.class) {
                instance = new DBELMachineContentByDescriptDao(context.getApplicationContext());
            }
        }
        return instance;
    }

    /**
     * 添加数据
     * @param list
     */
    public  void addData(List<ELMachineContentByDescriptBean> list){
        if(list ==null){
            return ;
        }
        SQLiteDatabase database = getDataBase();
        for(ELMachineContentByDescriptBean bean:list){
            ContentValues values = new ContentValues();
            values.put(NEWID,bean.getNewId());
            values.put(MMACHINEITEMRCONTENTID,bean.getMMachineItemRContentId());
            values.put(MMACHINEDESCRIPTID,bean.getMMachineDescriptId());
            values.put(MDESCRIPTNAME,bean.getMDescriptName());
            database.insert(TABLE_ELMACHINECONTENTBYDESCRIPT, NEWID, values);
        }
        closeDB();
    }
    /**
     * 获取机电描述根据检修内容id
     * @param contentId
     * @return
     */
    public List<ELMachineContentByDescriptBean> getDataByContentId(String contentId) {
        List<ELMachineContentByDescriptBean> list = new ArrayList<>();
        if (contentId == null) {
            return list;
        }
        SQLiteDatabase database = getDataBase();
        String sql = "select *from " + TABLE_ELMACHINECONTENTBYDESCRIPT + " where " + MMACHINEITEMRCONTENTID + "=?";
        Cursor c = database.rawQuery(sql, new String[]{contentId});
        while (c != null && c.moveToNext()) {
            ELMachineContentByDescriptBean bean = new ELMachineContentByDescriptBean();
            bean.setNewId(c.getString(c.getColumnIndex(NEWID)));
            bean.setMMachineItemRContentId(c.getString(c.getColumnIndex(MMACHINEITEMRCONTENTID)));
            bean.setMMachineDescriptId(c.getString(c.getColumnIndex(MMACHINEDESCRIPTID)));
            bean.setMDescriptName(c.getString(c.getColumnIndex(MDESCRIPTNAME)));
            list.add(bean);
        }
        if (c != null) {
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
        database.execSQL("delete from " + TABLE_ELMACHINECONTENTBYDESCRIPT);
        closeDB();
    }
}
