package com.ty.highway.highwaysystem.system.service;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * 上传图片和视频的服务
 * Created by fuweiwei on 2015/9/29.
 */
public class UploadPhotoService  extends  MFinalService{
    private final String TAG = "UploadPhotoService";
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public  class UploadPhotoBinder extends Binder{

    }
    @Override
    protected void handleOtherMessage(int flag) {
        super.handleOtherMessage(flag);
    }
}
