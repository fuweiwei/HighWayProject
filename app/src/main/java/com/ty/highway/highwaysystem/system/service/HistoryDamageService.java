package com.ty.highway.highwaysystem.system.service;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.Gson;
import com.ty.highway.highwaysystem.base.Constants;
import com.ty.highway.highwaysystem.support.bean.basic.BTableUpdateBean;
import com.ty.highway.highwaysystem.support.bean.basic.BTunnelBean;
import com.ty.highway.highwaysystem.support.bean.check.CTHistroyDiseaseInfoResultBean;
import com.ty.highway.highwaysystem.support.db.basedata.DBBTableUpdateDao;
import com.ty.highway.highwaysystem.support.db.basedata.DBBTunnelDao;
import com.ty.highway.highwaysystem.support.db.check.DBCTHistroyDiseaseInfoDao;
import com.ty.highway.highwaysystem.support.listener.NetResquestListener;
import com.ty.highway.highwaysystem.support.net.BaseNetworkHelper;
import com.ty.highway.highwaysystem.support.utils.PreferencesUtils;
import com.ty.highway.highwaysystem.system.callback.BaseDataCallBack;

import java.util.HashMap;
import java.util.List;

/**
 * Created by fuweiwei on 2015/12/23.
 */
public class HistoryDamageService extends MFinalService {
    private Binder mBinder;
    private Context mContext = this;
    private  int mCount;//隧道总数
    private int mNum;//已下载的隧道数
    private BaseDataCallBack mCallBack;
    private int mCheckId;
    private BaseNetworkHelper mNetHelper;


    @Override
    public void onCreate() {
        super.onCreate();
        mBinder = new HistroyDamageBind();
        Log.d("test","------------onCreate");
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.d("test", "------------onRebind");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("test", "------------onBind");
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("test", "------------onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("test", "------------onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d("test", "------------onDestroy");
        super.onDestroy();
    }

    public  class HistroyDamageBind extends Binder{
        public void setCallBack(BaseDataCallBack callBack){
            mCallBack = callBack;
        }
        public void downloadData(String checkWay,int id ,String userId){
            mCheckId = id;
            getUserTunnel(checkWay,userId);
        }
    }
    /**
     * 获取历史病害
     */
    public void getHistroyData(List<BTunnelBean> list,String checkWayId){
        if(DBCTHistroyDiseaseInfoDao.getInstance(mContext).isHasDataByCheckWay(checkWayId)){
            DBCTHistroyDiseaseInfoDao.getInstance(mContext).clearDataByCheckWay(checkWayId);
        }
        for(BTunnelBean bean:list){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("key", Constants.KEY);
            map.put("tunnelId",bean.getNewId());
            map.put("CheckWayId", checkWayId);
            mNetHelper =  BaseNetworkHelper.getInstance(new NetResquestListener() {
                @Override
                public void onSuccess(String response) {
                    if(response!=null){
                        if (response.contains("\"r\":\"ok")){
                            CTHistroyDiseaseInfoResultBean resultbean = new Gson().fromJson(response, CTHistroyDiseaseInfoResultBean.class);
                            DBCTHistroyDiseaseInfoDao.getInstance(mContext).addData(resultbean.getS().getData());
                        }else{
                        }
                    }
                    sendMessage(Constants.SUCCESS);
                }

                @Override
                public void onError(String errormsg) {
                    sendMessage(Constants.SUCCESS);
                }
            },mContext);
            mNetHelper.doPost(map, Constants.METHOD_GETDIEASEBASE, Constants.SERVICEURL_TYPE_CHECK);
        }
    }

    /**
     * 获取用户权限的隧道
     */
    public void getUserTunnel(final String checkWayId,String userId){
        List<BTunnelBean> list = DBBTunnelDao.getInstance(mContext).getAllTunnel(userId);
        mCount = list.size();
        mCallBack.onResult(0,"总共"+mCount+"条隧道，已下载"+mNum+"条隧道的历史病害...");
        if (list!=null&&list.size()>0) {
            getHistroyData(list, checkWayId);
        }
    }
    @Override
    protected void handleOtherMessage(int flag) {
        super.handleOtherMessage(flag);
        switch (flag){
            case Constants.SUCCESS:
                mNum++;
                mCallBack.onResult((mNum*100)/mCount,"总共"+mCount+"条隧道，已下载"+mNum+"条隧道的历史病害...");
                if(mNum==mCount){
                    mNum=0;
                    String time = PreferencesUtils.getString(mContext, Constants.SP_HOSTROY_TIME);
                    if(mCheckId==Constants.CHECK_TYPE_OFTEN){
                        BTableUpdateBean info = new BTableUpdateBean(Constants.HISTROY_OFTEN_TIME,time);
                        DBBTableUpdateDao.getInstance(mContext).addData(info);
                    }else if(mCheckId==Constants.CHECK_TYPE_REGULAR){
                        BTableUpdateBean info = new BTableUpdateBean(Constants.HISTROY_FIX_TIME,time);
                        DBBTableUpdateDao.getInstance(mContext).addData(info);
                    }
                }
        }
    }
}
