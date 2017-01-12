package com.ty.highway.highwaysystem.support.db.basic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ty.highway.highwaysystem.support.bean.basic.PAccountBean;

/**
 * Created by fuweiwei on 2015/12/3.
 * 账号信息表操作类
 */
public class DBPAccountDao extends  DBDao implements TableColumns.PAccount{
    private static DBPAccountDao INSTANCE;
    private Context mContext;
    protected DBPAccountDao(Context context) {
        super(context);
        mContext = context;
    }
    public static DBPAccountDao getInstance(Context context){
        if(INSTANCE==null){
            synchronized (DBPAccountDao.class) {
                if(INSTANCE==null){
                    INSTANCE = new DBPAccountDao(context.getApplicationContext());
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 插入一条账号
     * @param bean
     */
    public  void addInfo(PAccountBean bean){
        if(bean==null){
            return;
        }
        deleteInfo(bean.getName());
        SQLiteDatabase database = getDataBase();
        ContentValues values = new ContentValues();
        values.put(NAME,bean.getName());
        values.put(PASSWORD,bean.getPassword());
        values.put(ISSAVE,bean.getIssave());
        database.insert(TABLE_PACCOUNT,NAME,values);
        closeDB();

    }

    /**
     * 查询账号信息
     * @param name
     * @return
     */
    public PAccountBean getInfo(String name){
        if(name==null){
            return null;
        }
        PAccountBean info = new PAccountBean();
        SQLiteDatabase database = getDataBase();
        String sql = "select * from "+TABLE_PACCOUNT+" where "+NAME+"=?";
        Cursor cursor = database.rawQuery(sql,new String[]{name});
        if(cursor != null && cursor.moveToFirst()){
            info.setName(cursor.getString(cursor.getColumnIndex(NAME)));
            info.setPassword(cursor.getString(cursor.getColumnIndex(PASSWORD)));
            info.setIssave(cursor.getString(cursor.getColumnIndex(ISSAVE)));
        }
        if(cursor!=null){
            cursor.close();
        }
        closeDB();
        return info;
    }
    /**
     * 删除一个账号
     * @param name
     */
    public  void deleteInfo(String name){
        SQLiteDatabase database = getDataBase();
        database.delete(TABLE_PACCOUNT, NAME +"=?", new String[] { name });
        closeDB();
    }
}
