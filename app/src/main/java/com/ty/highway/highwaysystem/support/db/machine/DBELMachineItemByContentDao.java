package com.ty.highway.highwaysystem.support.db.machine;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ty.highway.highwaysystem.support.bean.machine.ELMachineItemByContentBean;
import com.ty.highway.highwaysystem.support.db.basic.DBDao;
import com.ty.highway.highwaysystem.support.db.basic.TableColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuweiwei on 2016/1/5.
 */
public class DBELMachineItemByContentDao extends DBDao implements TableColumns.ELMachineItemByContent {
    private final static String TAG = "DBELMachineItemByContentDao";
    private static DBELMachineItemByContentDao instance;

    private DBELMachineItemByContentDao(Context context) {
        super(context);
    }

    public static DBELMachineItemByContentDao getInstance(Context context) {
        if (instance == null) {
            synchronized (DBELMachineItemByContentDao.class) {
                instance = new DBELMachineItemByContentDao(context.getApplicationContext());
            }
        }
        return instance;
    }

    /**
     * 添加数据
     * @param list
     */
    public  void addData(List<ELMachineItemByContentBean> list){
        if(list ==null){
            return ;
        }
        SQLiteDatabase database = getDataBase();
        for(ELMachineItemByContentBean bean :list){
            ContentValues values = new ContentValues();
            values.put(NEWID,bean.getNewId());
            values.put(MMACHINERITEMID,bean.getMMachineRItemId());
            values.put(MMACHINECONTENTID,bean.getMMachineContentId());
            values.put(MCONTENTNAME,bean.getMContentName());
            database.insert(TABLE_ELMACHINEITEMBYCONTENT, NEWID, values);
        }
        closeDB();
    }

    /**
     * 获取检修内容根据检修项目id
     * @param itemId
     * @return
     */
    public List<ELMachineItemByContentBean> getDataByItemId(String itemId){
        List<ELMachineItemByContentBean> list = new ArrayList<>();
        if(itemId==null){
            return  list;
        }
        SQLiteDatabase database = getDataBase();
        String sql = "select *from "+TABLE_ELMACHINEITEMBYCONTENT+" where "+MMACHINERITEMID +"=?";
        Cursor c = database.rawQuery(sql, new String[]{itemId});
        while (c!=null&&c.moveToNext()){
            ELMachineItemByContentBean bean = new ELMachineItemByContentBean();
            bean.setNewId(c.getString(c.getColumnIndex(NEWID)));
            bean.setMMachineRItemId(c.getString(c.getColumnIndex(MMACHINERITEMID)));
            bean.setMMachineContentId(c.getString(c.getColumnIndex(MMACHINECONTENTID)));
            bean.setMContentName(c.getString(c.getColumnIndex(MCONTENTNAME)));
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
        database.execSQL("delete from " + TABLE_ELMACHINEITEMBYCONTENT);
        closeDB();
    }
}
