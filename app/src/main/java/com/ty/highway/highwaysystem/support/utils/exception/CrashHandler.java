package com.ty.highway.highwaysystem.support.utils.exception;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.ty.highway.highwaysystem.base.Constants;
import com.ty.highway.highwaysystem.base.HWApplication;
import com.ty.highway.highwaysystem.support.utils.CommonUtils;
import com.ty.highway.highwaysystem.support.utils.PreferencesUtils;
import com.ty.highway.highwaysystem.support.utils.ToastUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告. 
 * @author fww
 */
public class CrashHandler implements UncaughtExceptionHandler{
    public static final String TAG = "CrashHandler";

    //系统默认的UncaughtException处理类   
    private UncaughtExceptionHandler mDefaultHandler;
    //CrashHandler实例  
    private static CrashHandler INSTANCE = new CrashHandler();
    //程序的Context对象  
    private Context mContext ;
    //用来存储设备信息和异常信息  
    private Map<String, String> infos = new HashMap<String, String>();

    //用于格式化日期,作为日志文件名的一部分  
    private SimpleDateFormat formatter = new SimpleDateFormat(Constants.FULL_DATE_FORMAT,Locale.CHINESE);

    private StringBuffer sb;

    /** 保证只有一个CrashHandler实例 */
    private CrashHandler() {
    }

    /** 获取CrashHandler实例 ,单例模式 */
    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化 
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        //获取系统默认的UncaughtException处理器  
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器  
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理 
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理  
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false. 
     */
    private boolean handleException(final Throwable ex) {
        if (ex == null) {
            return false;
        }
        //使用Toast来显示异常信息  
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                //收集设备参数信息
                collectDeviceInfo(mContext);
                //保存日志文件   
                saveCrashInfo2File(ex);
                Looper.loop();
            }
        }.start();

        return true;
    }
    /**
     * 收集设备参数信息 
     * @param ctx
     */
    public void collectDeviceInfo(Context ctx) {
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                String mFieldName = field.getName();
                if(mFieldName != null) {
                	/*if("TIME".equals(mFieldName)) {
                    	infos.put(mFieldName, formatter.format(new Date()));
                	}
                	
                	if("MODEL".equals(mFieldName)) {
                     	infos.put(mFieldName, field.get(null).toString());
                    }
                	
                	if("DEVICE".equals(mFieldName)) {
                     	infos.put(mFieldName, field.get(null).toString());
                    }
                	
                	if("CPU_ABI".equals(mFieldName)) {
                     	infos.put(mFieldName, field.get(null).toString());
                    }
                	
                	if("BRAND".equals(mFieldName)) {
                     	infos.put(mFieldName, field.get(null).toString());
                    }*/

                    infos.put(mFieldName, field.get(null).toString());
                }
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }

    /**
     * 保存错误信息到文件中 
     *
     * @param ex
     * @return  返回文件名称,便于将文件传送到服务器
     */
    private String saveCrashInfo2File(Throwable ex) {

        sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        final String result = writer.toString();
        sb.append(result);
        Log.e(TAG, result);
        try {
            final long timestamp = System.currentTimeMillis();
            final String time = formatter.format(new Date());
            final String fileName = "crash_" + time + "_" + timestamp + ".log";

            if(!CommonUtils.isNetworkConnected(mContext)) {//无网
                ToastUtils.show(mContext,"程序出现异常,即将退出系统");
                writeToSD(fileName);
                exitFromProgram();
            }else {//有网

                AlertDialog.Builder build = new AlertDialog.Builder(mContext);
                build.setTitle("异常")
                        .setMessage("程序出现异常,是否将异常信息上传服务器？")
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        try{
                                            writeToSD(fileName);
                                            new ExceptionUtils().upLoadFile(fileName, mContext, sb.toString(), PreferencesUtils.getString(mContext, Constants.SP_USER_NAME,""));
                                        }catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        exitFromProgram();
                                    }
                                })
                        .setNegativeButton("取消",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        Log.e("CrasgHandler", sb.toString());
                                        writeToSD(fileName);
                                        exitFromProgram();
                                    }
                                }).show();
            }
            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
        }
        return null;
    }
    /**
     * 将异常写入SD卡
     * @param fileName
     */
    public void writeToSD(String fileName){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String path = Environment.getExternalStorageDirectory().getPath() + Constants.SD_EXCEPTION_PATH;
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            try {
                FileOutputStream fos = new FileOutputStream(path + fileName);
                fos.write(sb.toString().getBytes());
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 退出程序
     */
    public void exitFromProgram(){
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                HWApplication.exit();
            }
        }).start();

    }

}
