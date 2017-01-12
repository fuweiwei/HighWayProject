package com.ty.highway.highwaysystem.support.net;

import android.content.Context;

import com.ty.highway.highwaysystem.support.listener.NetResquestListener;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by fuweiwei on 2015/9/6.
 * 网络请求操作类，不是异步的，需要使用Handler来更新UI
 */
public class BaseNetworkHelper {
    private static NetResquestListener mListener;
    private static HighWayApi mHighWayApi;
    private Context mContext;
    private  ExecutorService mExecutorService = Executors.newFixedThreadPool(10);
    private static  BaseNetworkHelper instance;
    public BaseNetworkHelper(){
    }
    public static synchronized  BaseNetworkHelper getInstance(NetResquestListener listener,Context context){
        if(instance==null){
            instance = new BaseNetworkHelper();
        }
        mListener = listener;
        mHighWayApi = new HighWayApiImpl(context.getApplicationContext());
        return instance;
    }

    /**
     * 关闭线程池中的线程
     */
    public void closeNetWork(){
        mExecutorService.shutdownNow();
    }

    /**
     * 统一post请求
     * @param map
     * @param name
     */
    public  void doPost(final HashMap<String, String> map ,final String name,final String urlType){
        if(mListener==null){
            return;
        }
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                mHighWayApi.doPost(map,name,urlType, mListener);
            }
        });
    }
    /**
     * 上传异常信息
     * @param map
     * @param name
     */
    public  void upLoadExecption(final HashMap<String, String> map ,final String name){
        if(mListener==null){
            return;
        }
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                mHighWayApi.upLoadExecption(map,name,mListener);
            }
        });
    }
}
