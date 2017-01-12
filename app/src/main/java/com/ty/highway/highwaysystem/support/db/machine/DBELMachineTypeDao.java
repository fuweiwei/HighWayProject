package com.ty.highway.highwaysystem.support.db.machine;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ty.highway.highwaysystem.support.bean.machine.ELMachineTypeBean;
import com.ty.highway.highwaysystem.support.db.basic.DBDao;
import com.ty.highway.highwaysystem.support.db.basic.TableColumns;

import java.util.List;

/**
 * Created by fuweiwei on 2016/1/5.
 */
public class DBELMachineTypeDao extends DBDao implements TableColumns.ELMachineType {
    private final static String TAG = "DBELMachineTypeDao";
    private static DBELMachineTypeDao instance;

    private DBELMachineTypeDao(Context context) {
        super(context);
    }

    public static DBELMachineTypeDao getInstance(Context context) {
        if (instance == null) {
            synchronized (DBELMachineTypeDao.class) {
                instance = new DBELMachineTypeDao(context.getApplicationContext());
            }
        }
        return instance;
    }
    /**
     * 添加数据
     * @param list
     */
    public  void addData(List<ELMachineTypeBean> list){
        if(list ==null){
            return ;
        }
        SQLiteDatabase database = getDataBase();
        for(ELMachineTypeBean bean:list){
            ContentValues values = new ContentValues();
            values.put(NEWID,bean.getNewId());
            values.put(MACHINETYPENAME,bean.getMachineTypeName());
            values.put(PID,bean.getPid());
            values.put(TYPE,bean.getType());
            database.insert(TABLE_ELMACHINETYPE, NEWID, values);
        }
        closeDB();
    }

    /**
     * 清空表数据
     */
    public  void clearData(){
        SQLiteDatabase database = getDataBase();
        database.execSQL("delete from " + TABLE_ELMACHINETYPE);
        closeDB();
    }
}
