package com.ty.highway.highwaysystem.support.olddata;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by fuweiwei on 2015/10/16.
 */
public class DBServiceOld extends DBDaoOld{
    //当前时间
    private SimpleDateFormat mFormatter  = new SimpleDateFormat ("yyyy/MM/dd");
    protected DBServiceOld(Context context) {
        super(context);
    }
    private final static String TAG = "DBServiceOld";
    private static DBServiceOld instance;

    public static DBServiceOld getInstance(Context context) {
        if (instance == null) {
            synchronized (DBServiceOld.class) {
                instance = new DBServiceOld(context);
            }
        }
        return instance;
    }
    public List<HashMap<String,String>> getPicInfo(String id){
        List<HashMap<String,String>> list = new ArrayList<>();
        SQLiteDatabase database = getDataBase();
        String sql = "select *from BH_PIC where bh_id=?";
        Cursor cursor = database.rawQuery(sql,new String[]{id});
        while (cursor!=null&&cursor.moveToNext()){
            HashMap<String,String> map = new HashMap<>();
            map.put("pic_pistion",cursor.getString(cursor.getColumnIndex("pic_pistion")));
            map.put("pic_name",cursor.getString(cursor.getColumnIndex("pic_name")));
            map.put("update_id",cursor.getString(cursor.getColumnIndex("update_id")));
            map.put("bh_id",cursor.getString(cursor.getColumnIndex("bh_id")));
            map.put("guid",cursor.getString(cursor.getColumnIndex("guid")));
            list.add(map);
        }
        if(cursor!=null){
            cursor.close();

        }
        closeDB();

        return list;
    }

    public  HashMap<String,String> getTaskInfo(String update_id){
        SQLiteDatabase database = getDataBase();
        String sql = "select *from TASK where update_id=?";
        Cursor cursor = database.rawQuery(sql,new String[]{update_id});
        HashMap<String,String> map = new HashMap<>();
        while (cursor!=null&&cursor.moveToNext()){
            map.put("task_name",cursor.getString(cursor.getColumnIndex("task_name")));
            map.put("check_date", cursor.getString(cursor.getColumnIndex("check_date")));
        }
        if(cursor!=null){
            cursor.close();
        }
        closeDB();
        return map;
    }
    public List<UploadBean> getUploadList(){
        List<UploadBean> list = new ArrayList<>();
        SQLiteDatabase database = getDataBase();
        String sql = "select *from CILIV_CHECKCONTENT";
        Cursor cursor  = database.rawQuery(sql,null);
        while (cursor!=null&&cursor.moveToNext()){
            UploadBean bean = new UploadBean();
            HashMap<String, String> map= getTaskInfo(cursor.getString(cursor.getColumnIndex("task_id")));
            if(TextUtils.isEmpty(map.get("task_name"))){
                continue;
            }
            bean.setTunnelName(map.get("task_name"));
            bean.setCheckDate(map.get("check_date"));
            bean.setJudgeLevel(cursor.getString(cursor.getColumnIndex("judge_level")));
            bean.setCheckContent(cursor.getString(cursor.getColumnIndex("check_data")));
            bean.setDefectLocation(cursor.getString(cursor.getColumnIndex("check_position")));
            bean.setMileagePile(cursor.getString(cursor.getColumnIndex("mileage")));
            bean.setExceptionDescription(cursor.getString(cursor.getColumnIndex("level_content")));
            bean.setDescDetails(cursor.getString(cursor.getColumnIndex("BZ")));
            bean.setStructName(cursor.getString(cursor.getColumnIndex("belong_pro")));
            bean.setInputPerson("");
            bean.setCheckPerson("");
            bean.setWeather("晴天");
            List<UploadBean.Image> imageList = new ArrayList<>();
            List<HashMap<String,String>>list1 = getPicInfo(cursor.getString(cursor.getColumnIndex("_id")));
            Date curDate   =   new Date(System.currentTimeMillis());//获取当前时间
            String  nowTime = mFormatter.format(curDate);
            for(HashMap<String,String> map1:list1){
                UploadBean.Image imageBean = bean.new Image();
                imageBean.setDisplayName(map1.get("pic_name"));
                String src = map1.get("pic_pistion");
                String guid = map1.get("guid");
                String s[] =src.split("/");
                int count = s.length;
                StringBuffer ss = new StringBuffer();
                for(int i=0;i<count;i++){
                    if(i==count-1){

                    }else{
                        ss.append(s[i]).append("/");
                    }
                }
                String dest = ss+guid+".jpg";
                setPicName(src+".jpg",dest);
                String url = "Document/old/TJJC-08/"+guid+".jpg";
                setpicFile(dest, guid);
                setpicType(url, guid);
                imageBean.setUrl(url);
                imageList.add(imageBean);
            }
            bean.setImages(imageList);
            list.add(bean);

        }
        if(cursor!=null){
            cursor.close();
        }
        closeDB();
        return  list;
    }

    public  Boolean setPicName(String src ,String dest){
        File srcDir = new File(src);  //就文件夹路径
        boolean isOk = srcDir.renameTo(new File(dest));  //dest新文件夹路径，通过renameto修改
        return isOk;
    }
    public  void setpicFile(String file,String guid){
        SQLiteDatabase database = getDataBase();
        String sql = "update BH_PIC set pic_pistion='"+file+"' where guid='"+guid+"'";
        database.execSQL(sql);
    }
    public  void setpicType(String type,String guid){
        SQLiteDatabase database = getDataBase();
        String sql = "update BH_PIC set type='"+type+"' where guid='"+guid+"'";
        database.execSQL(sql);
    }
    public   LinkedList<File> getUploadPicInfos(){
        LinkedList<File> list = new LinkedList<>();
        SQLiteDatabase database = getDataBase();
        String sql = "select *from BH_PIC where type not in('内','外') and isupload = '0'";
        Cursor cursor = database.rawQuery(sql,null);
        while (cursor!=null&&cursor.moveToNext()){
            String file = cursor.getString(cursor.getColumnIndex("pic_pistion"));
            list.add(new File(file));
        }
        if(cursor!=null){
            cursor.close();
        }
        closeDB();
        return  list;
    }
    public void setPicState(String pistion){

        SQLiteDatabase database = getDataBase();
        String sql = "update BH_PIC set isupload=1 where pic_pistion=?";
        database.execSQL(sql ,new String[]{pistion});
    }
}
