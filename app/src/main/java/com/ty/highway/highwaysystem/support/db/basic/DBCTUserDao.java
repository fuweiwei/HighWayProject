package com.ty.highway.highwaysystem.support.db.basic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ty.highway.highwaysystem.support.bean.basic.CTUserBean;

/**
 * Created by fuweiwei on 2015/9/14.
 */
public class DBCTUserDao extends DBDao implements TableColumns.CTUser {
    private static DBCTUserDao INSTANCE;
    protected DBCTUserDao(Context context) {
        super(context);
    }
    public static DBCTUserDao getInstance(Context context){
        if(INSTANCE==null){
            synchronized (DBCTUserDao.class) {
                if(INSTANCE==null){
                    //因为context大多来自activity，无法回收，这里我们使用context.getApplicationContext()，因为ApplicationContext的声明周期跟我们的单例一样
                    INSTANCE = new DBCTUserDao(context.getApplicationContext());
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 添加PUserBean
     * @param bean
     */
    public void addUserInfo(CTUserBean bean){
        if(bean ==null){
            return ;
        }
        deleteInfo(bean.getUserID());
        SQLiteDatabase database = getDataBase();
        ContentValues values = new ContentValues();
        values.put(USERID, bean.getUserID());
        values.put(USERNAME, bean.getUserName());
        values.put(DPTID, bean.getDptId());
        values.put(DPTNAME, bean.getDptName());
        values.put(USERACCOUNT, bean.getUserAccount());
        values.put(EROLEID, bean.getERoleId());
        values.put(FTPPATH, bean.getFtpPath());
        values.put(FTPIP, bean.getFtpIp());
        values.put(FTPUSER, bean.getFtpUser());
        values.put(FTPPWD, bean.getFtpPwd());
        values.put(ISLOGIN, bean.isLogin()?"1":"0");
        values.put(LEGALIZEKEY, bean.getLegalizeKey());
        values.put(FTPREADPATH, bean.getFtpReadPath());
        database.insert(TABLE_CTUSER,USERID,values);
        closeDB();
    }

    public CTUserBean getPUserInfo(String userAccount){
        CTUserBean info = new CTUserBean();
        SQLiteDatabase db = getDataBase();
        String sql = "select * from "+ TABLE_CTUSER +" where "+USERACCOUNT+"=?";
        Cursor cursor = db.rawQuery(sql,new String[]{userAccount});
        if(cursor != null && cursor.moveToFirst()){
            info.setUserID(cursor.getString(cursor.getColumnIndex(USERID)));
            info.setUserName(cursor.getString(cursor.getColumnIndex(USERNAME)));
            info.setDptId(cursor.getString(cursor.getColumnIndex(DPTID)));
            info.setDptName(cursor.getString(cursor.getColumnIndex(DPTNAME)));
            info.setUserAccount(cursor.getString(cursor.getColumnIndex(USERACCOUNT)));
            info.setERoleId(cursor.getString(cursor.getColumnIndex(EROLEID)));
            info.setFtpIp(cursor.getString(cursor.getColumnIndex(FTPIP)));
            info.setFtpPath(cursor.getString(cursor.getColumnIndex(FTPPATH)));
            info.setFtpUser(cursor.getString(cursor.getColumnIndex(FTPUSER)));
            info.setFtpPwd(cursor.getString(cursor.getColumnIndex(FTPPWD)));
            info.setIsLogin(cursor.getString(cursor.getColumnIndex(FTPPWD)).endsWith("1")?true:false);
            info.setLegalizeKey(cursor.getString(cursor.getColumnIndex(LEGALIZEKEY)));
            info.setFtpReadPath(cursor.getString(cursor.getColumnIndex(FTPREADPATH)));
        }
        if(cursor!=null){
            cursor.close();
        }
        closeDB();
        return info;
    }

    /**
     * 删除一个任务
     * @param id
     */
    public  void deleteInfo(String id){
        SQLiteDatabase database = getDataBase();
        database.delete(TABLE_CTUSER, USERID +"=?", new String[] { id });
        closeDB();
    }
}
