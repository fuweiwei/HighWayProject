package com.ty.highway.highwaysystem.base;

import android.app.Activity;
import android.app.Application;

import com.ty.highway.highwaysystem.support.bean.basic.CTUserBean;

import java.util.ArrayList;

/**
 * Created by ${fuweiwei} on 2015/9/1.
 */
public class HWApplication extends Application{
    public static  int mCheckType = Constants.CHECK_TYPE_DAILY;
    public static CTUserBean userBean;
    public static ArrayList<Activity> mStackActList = new ArrayList<Activity>();
    @Override
    public void onCreate() {
        super.onCreate();
    }
    public static  void setFTPDocument(){
    }
    public static void setmCheckType(int state){
        mCheckType = state;
    }
    public  static  int getmCheckType(){
        return  mCheckType;
    }
    public  static  String getCheckNameType(){
        String name = null;
        if(mCheckType== Constants.CHECK_TYPE_DAILY){
            name ="日常检查";
        }else if(mCheckType== Constants.CHECK_TYPE_SPECIAL){
            name ="专项检查";
        }
        else if(mCheckType== Constants.CHECK_TYPE_OFTEN){
            name ="经常检查";
        }else if(mCheckType== Constants.CHECK_TYPE_REGULAR){
            name ="定期检查";
        }else if(mCheckType== Constants.CHECK_TYPE_URGENT){
            name ="应急检查";
        }
        return  name;
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
    public static void addActivity(Activity act) {
        mStackActList.add(act);
    }

    public static void closeSingleActivity(Class act) {
        for(Activity  act0 : mStackActList) {
            if(act0 != null && act0.getClass() == act)
                act0.finish();
        }
    }


    public static void exit() {
        for(Activity  act : mStackActList) {
            if(act != null)
                act.finish();
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}

