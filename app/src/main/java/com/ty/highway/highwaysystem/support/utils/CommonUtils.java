package com.ty.highway.highwaysystem.support.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.text.SimpleDateFormat;
import java.util.UUID;

/**
 * Created by fuweiwei on 2015/9/17.
 * 时间操作类
 */
public class CommonUtils {
    private static final SimpleDateFormat  df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    /**
     * 判断本地时间是否在后台时间之前
     * @param localTime 本地时间
     * @param netTime  后台时间
     * @return
     */
    public static Boolean IsBefore(String localTime,String netTime){
        Boolean flag = false;
       /* try {
            Date date1 = df.parse(localTime);
            Date date2 = df.parse(netTime);
            if(date1.before(date2)){
                flag=true;
            }else {
                flag = false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        if(localTime==null){
            return true;
        }
        if(!localTime.equals(netTime)){
            flag=true;
        }else{
            flag = false;
        }
        return  flag;
    }

    /**
     * 获取版本名称
     * @param context
     * @return
     */
    public static String getVersionName(Context context){
        PackageInfo pkg = null;
        try {
            pkg = context.getPackageManager().getPackageInfo(context.getApplicationContext().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String appName = pkg.applicationInfo.loadLabel(context.getPackageManager()).toString();
        String versionName = pkg.versionName;
        return versionName;
    }
    /**
     * 获取版本号
     * @param context
     * @return
     */
    public static int getVersionCode(Context context){
        PackageInfo pkg = null;
        try {
            pkg = context.getPackageManager().getPackageInfo(context.getApplicationContext().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String appName = pkg.applicationInfo.loadLabel(context.getPackageManager()).toString();
        int versionCode = pkg.versionCode;
        return versionCode;
    }
    /**
     * 随机生成一个GUID
     * @return
     */
    public static String getGuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
    /**
     * 获取android中的DeviceId
     * @param mContext
     * @return
     */
    public static String getDeviceId(Context mContext){
        return ((TelephonyManager)mContext.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }
    /**
     * 获取android中的SN号
     * @param mContext
     * @return
     */
    public static String getSN(Context mContext){
        return android.provider.Settings.System.getString(mContext.getContentResolver(), "android_id");
    }
    /*
     * 判断网络是否链接
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cManager != null) {
            NetworkInfo localNetworkInfo = cManager.getActiveNetworkInfo();
            if (localNetworkInfo != null) {
                return localNetworkInfo.isConnectedOrConnecting();
            }
        }
        return false;
    }

}
