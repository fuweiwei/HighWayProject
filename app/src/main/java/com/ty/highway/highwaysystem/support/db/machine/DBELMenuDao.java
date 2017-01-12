package com.ty.highway.highwaysystem.support.db.machine;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ty.highway.highwaysystem.support.bean.basic.MenuBean;
import com.ty.highway.highwaysystem.support.bean.basic.MenuChildrenBean;
import com.ty.highway.highwaysystem.support.db.basic.DBDao;
import com.ty.highway.highwaysystem.support.db.basic.TableColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuweiwei on 2016/1/5.
 */
public class DBELMenuDao extends DBDao implements TableColumns.ELMenu {
    private final static String TAG = "DBELMenuDao";
    private static DBELMenuDao instance;

    private DBELMenuDao(Context context) {
        super(context);
    }

    public static DBELMenuDao getInstance(Context context) {
        if (instance == null) {
            synchronized (DBELMenuDao.class) {
                instance = new DBELMenuDao(context.getApplicationContext());
            }
        }
        return instance;
    }

    /**
     * 添加数据
     * @param list
     */
    public  void addData(List<MenuChildrenBean> list){
        if(list ==null){
            return ;
        }
        SQLiteDatabase database = getDataBase();
        for(MenuChildrenBean bean:list){
            ContentValues values = new ContentValues();
            values.put(NEWID,bean.getNewId());
            values.put(MENUNAME,bean.getMenuName());
            values.put(PID,bean.getPid());
            values.put(MENUURL, bean.getMenuUrl());
            values.put(MENUNO, bean.getMenuNo());
            values.put(SORT, bean.getSort()+"");
            database.insert(TABLE_ELMENU, NEWID, values);
            List<MenuChildrenBean> childrenBeans = bean.getChildren();
            if(childrenBeans!=null&&childrenBeans.size()>0){
                addData(childrenBeans,database);
            }
        }
        closeDB();
    }

    public  void addData(List<MenuChildrenBean> list,SQLiteDatabase database){
        if(list ==null){
            return ;
        }
        for(MenuChildrenBean bean:list){
            ContentValues values = new ContentValues();
            values.put(NEWID,bean.getNewId());
            values.put(MENUNAME,bean.getMenuName());
            values.put(PID,bean.getPid());
            values.put(MENUNO, bean.getMenuNo());
            values.put(MENUURL, bean.getMenuUrl());
            values.put(SORT, bean.getSort()+"");
            database.insert(TABLE_ELMENU, NEWID, values);
            List<MenuChildrenBean> childrenBeans = bean.getChildren();
            if(childrenBeans!=null&&childrenBeans.size()>0){
                addData(childrenBeans);
            }
        }
    }
    /**
     *获取所有的菜单
     * @return
     */
    public List<MenuBean> getAllMenu(){
        List<MenuBean> list = new ArrayList<>();
        SQLiteDatabase database = getDataBase();
        String sql = "select *from "+TABLE_ELMENU;
        Cursor cursor= database.rawQuery(sql, null);
        while (cursor!=null&&cursor.moveToNext()){
            MenuBean bean = new MenuBean();
            bean.setMenuName(cursor.getString(cursor.getColumnIndex(MENUNAME)));
            bean.setNewId(cursor.getString(cursor.getColumnIndex(NEWID)));
            bean.setPid(cursor.getString(cursor.getColumnIndex(PID)));
            bean.setMenuUrl(cursor.getString(cursor.getColumnIndex(MENUURL)));
            bean.setMenuNo(cursor.getString(cursor.getColumnIndex(MENUNO)));
            bean.setSort(cursor.getInt(cursor.getColumnIndex(SORT)));
            list.add(bean);
        }
        if(cursor != null) {
            cursor.close();
        }
        closeDB();
        return  list;
    }
    /**
     * 是否存在该类型
     * @param menuNo
     * @return
     */
    public boolean isHasType(String menuNo){
        if(menuNo==null){
            return false;
        }
        SQLiteDatabase database = getDataBase();
        String sql = "select count(*) from "+TABLE_ELMENU+" where "+ MENUNO+"=? ";
        Cursor cursor = database.rawQuery(sql,new String[]{menuNo});
        int count=0;
        while (cursor!=null&&cursor.moveToNext()){
            count = cursor.getInt(0);
        }
        if(cursor!=null){
            cursor.close();
        }
        closeDB();
        return count!=0;
    }
    /**
     * 清空表数据
     */
    public  void clearData(){
        SQLiteDatabase database = getDataBase();
        database.execSQL("delete from " + TABLE_ELMENU);
        closeDB();
    }
}
