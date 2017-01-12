package com.ty.highway.highwaysystem.system.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.RemoteViews;

import com.ty.highway.highwaysystem.R;
import com.ty.highway.highwaysystem.base.Constants;
import com.ty.highway.highwaysystem.support.listener.BaseCallBack;
import com.ty.highway.highwaysystem.support.utils.PreferencesUtils;
import com.ty.highway.highwaysystem.support.utils.exception.ExceptionUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by fuweiwei on 2015/10/9.
 */
public class AppDownloadService extends MFinalService {


    private static final int NOTIFY_ID = 0;
    private int progress;
    private NotificationManager mNotificationManager;
    private boolean canceled;
	/* 下载包安装路径 */
    private static final String savePath = Environment.getExternalStorageDirectory().getPath()+Constants.SD_APK_PATH;
    private static final String saveFileName = savePath + "HighWay.apk";
    private BaseCallBack callback;
    private DownloadBinder binder;
    private boolean serviceIsDestroy = false;
    private String apkUrll = null;
    private Context mContext = this;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    // 下载完毕
                    // 取消通知
                    mNotificationManager.cancel(NOTIFY_ID);
                    installApk();
                    break;
                case 2:
                    // 这里是用户界面手动取消，所以会经过activity的onDestroy();方法
                    // 取消通知
                    mNotificationManager.cancel(NOTIFY_ID);
                    break;
                case 1:
                    int rate = msg.arg1;
                    if (rate < 100) {
                        RemoteViews contentview = mNotification.contentView;
                        contentview.setTextViewText(R.id.tv_progress, rate + "%");
                        contentview.setProgressBar(R.id.progressbar, 100, rate, false);
                    } else {
                        // 下载完毕后变换通知形式
					/*
					 * mNotification.flags = Notification.FLAG_AUTO_CANCEL;
					 * mNotification.contentView = null; Intent intent = new
					 * Intent(mContext, UpdateVersionUtils.class); // 告知已完成
					 * intent.putExtra("completed", "yes"); //
					 * 更新参数,注意flags要使用FLAG_UPDATE_CURRENT PendingIntent
					 * contentIntent = PendingIntent.getActivity(mContext, 0,
					 * intent, PendingIntent.FLAG_UPDATE_CURRENT);
					 * mNotification.setLatestEventInfo(mContext, "下载完成",
					 * "文件已下载完毕", contentIntent); serviceIsDestroy = true;
					 */
                        stopSelf();// 停掉服务自身
                    }
                    mNotificationManager.notify(NOTIFY_ID, mNotification);
                    break;
            }
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 假如被销毁了，无论如何都默认取消了。
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        binder = new DownloadBinder();
        mNotificationManager = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);
        // setForeground(true);// 这个不确定是否有作用
    }

    public class DownloadBinder extends Binder {
        public void start() {
            if (downLoadThread == null || !downLoadThread.isAlive()) {
                progress = 0;
                setUpNotification();
                new Thread() {
                    public void run() {
                        // 下载
                        startDownload();
                    };
                }.start();
            }
        }

        public void cancel() {
            canceled = true;
        }

        public int getProgress() {
            return progress;
        }

        public void setUrl(String url) {
            apkUrll = url;
        }

        public boolean isCanceled() {
            return canceled;
        }

        public boolean serviceIsDestroy() {
            return serviceIsDestroy;
        }

        public void cancelNotification() {
            mHandler.sendEmptyMessage(2);
        }

        public void addCallback(BaseCallBack callback) {
            AppDownloadService.this.callback = callback;
        }
    }

    private void startDownload() {
        canceled = false;
        downloadApk();
    }

    //
    Notification mNotification;

    // 通知栏
    /**
     * 创建通知
     */
    private void setUpNotification() {
        int icon = R.drawable.ic_launcher;
        CharSequence tickerText = "开始下载";
        long when = System.currentTimeMillis();
        mNotification = new Notification(icon, tickerText, when);
        // 放置在"正在运行"栏目中
        mNotification.flags = Notification.FLAG_ONGOING_EVENT;

        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.common_download_notification);
        contentView.setTextViewText(R.id.name, " 正在下载...");
        // 指定个性化视图
        mNotification.contentView = contentView;
        // Intent intent = new Intent(this, UpdateVersionUtils.class);
        // 下面两句是 在按home后，点击通知栏，返回之前activity 状态;
        // 有下面两句的话，假如service还在后台下载， 在点击程序图片重新进入程序时，直接到下载界面，相当于把程序MAIN 入口改了 - -
        // 是这么理解么。。。
        // intent.setAction(Intent.ACTION_MAIN);
        // intent.addCategory(Intent.CATEGORY_LAUNCHER);
        // PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
        // intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // 指定内容意图
        // mNotification.contentIntent = contentIntent;
        mNotificationManager.notify(NOTIFY_ID, mNotification);
    }

    //
    /**
     * 下载apk
     *
     * @param url
     */
    private Thread downLoadThread;

    private void downloadApk() {
        downLoadThread = new Thread(mdownApkRunnable);
        downLoadThread.start();
    }

    /**
     * 安装apk
     */
    private void installApk() {
        File apkfile = new File(saveFileName);
        if (!apkfile.exists()) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        mContext.startActivity(i);

    }

    private int lastRate = 0;
    private Runnable mdownApkRunnable = new Runnable() {
        @Override
        public void run() {
            try {

                String apkUrl =  "http://"+PreferencesUtils.getString(mContext, Constants.SP_WEBURL, Constants.WEBURL)+apkUrll.replace("\\", "/");
                URL url = new URL(apkUrl);

                // URL url = new
                // URL("http://192.168.0.105/UploadFile/AppVersionFile/20140627/TYSubwayInspection.apk");
                System.out.println(apkUrl);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                int length = conn.getContentLength();
                InputStream is = conn.getInputStream();
                File file = new File(savePath);
                if (!file.exists()) {
                    file.mkdirs();
                }
                String apkFile = saveFileName;
                File ApkFile = new File(apkFile);
                FileOutputStream fos = new FileOutputStream(ApkFile);
                int count = 0;
                byte buf[] = new byte[1024];
                do {
                    int numread = is.read(buf);
                    count += numread;
                    progress = (int) (((float) count / length) * 100);
                    // 更新进度
                    Message msg = mHandler.obtainMessage();
                    msg.what = 1;
                    msg.arg1 = progress;
                    if (progress >= lastRate + 1) {
                        mHandler.sendMessage(msg);
                        lastRate = progress;
                        if (callback != null)
                            callback.callBack(progress);
                    }
                    if (numread <= 0) {
                        // 下载完成通知安装
                        mHandler.sendEmptyMessage(0);
                        // 下载完了，cancelled也要设置
                        canceled = true;
                        break;
                    }
                    fos.write(buf, 0, numread);
                } while (!canceled);// 点击取消就停止下载.

                fos.close();
                is.close();
            } catch (MalformedURLException e) {
                new ExceptionUtils().doExecInfo(e.toString(), mContext);
            } catch (IOException e) {
                new ExceptionUtils().doExecInfo(e.toString(), mContext);
            }

        }
    };




}
