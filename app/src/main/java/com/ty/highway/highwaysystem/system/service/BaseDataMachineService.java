package com.ty.highway.highwaysystem.system.service;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.google.gson.Gson;
import com.ty.highway.highwaysystem.base.Constants;
import com.ty.highway.highwaysystem.support.bean.basic.BTableUpdateBean;
import com.ty.highway.highwaysystem.support.bean.machine.ELMachineBean;
import com.ty.highway.highwaysystem.support.bean.machine.ELMachineByTypeBean;
import com.ty.highway.highwaysystem.support.bean.machine.ELMachineByTypeResultBean;
import com.ty.highway.highwaysystem.support.bean.machine.ELMachineContentByDescriptBean;
import com.ty.highway.highwaysystem.support.bean.machine.ELMachineContentByDescriptResultBean;
import com.ty.highway.highwaysystem.support.bean.machine.ELMachineDecisionLevelBean;
import com.ty.highway.highwaysystem.support.bean.machine.ELMachineItemByContentBean;
import com.ty.highway.highwaysystem.support.bean.machine.ELMachineItemByContentResultBean;
import com.ty.highway.highwaysystem.support.bean.machine.ELMachineResultBean;
import com.ty.highway.highwaysystem.support.bean.machine.ELMachineTypeBean;
import com.ty.highway.highwaysystem.support.bean.machine.ELMachineTypeByItemBean;
import com.ty.highway.highwaysystem.support.bean.machine.ELMachineTypeByItemResultBean;
import com.ty.highway.highwaysystem.support.bean.machine.ELMachineTypeResultBean;
import com.ty.highway.highwaysystem.support.bean.machine.ELTunnelDeployBean;
import com.ty.highway.highwaysystem.support.bean.machine.ELTunnelDeployResultBean;
import com.ty.highway.highwaysystem.support.db.basedata.DBBTableUpdateDao;
import com.ty.highway.highwaysystem.support.db.machine.DBELMachineByTypeDao;
import com.ty.highway.highwaysystem.support.db.machine.DBELMachineContentByDescriptDao;
import com.ty.highway.highwaysystem.support.db.machine.DBELMachineDao;
import com.ty.highway.highwaysystem.support.db.machine.DBELMachineDecisionLevelDao;
import com.ty.highway.highwaysystem.support.db.machine.DBELMachineItemByContentDao;
import com.ty.highway.highwaysystem.support.db.machine.DBELMachineTypeByItemDao;
import com.ty.highway.highwaysystem.support.db.machine.DBELMachineTypeDao;
import com.ty.highway.highwaysystem.support.db.machine.DBELTunnelDeployDao;
import com.ty.highway.highwaysystem.support.listener.NetResquestListener;
import com.ty.highway.highwaysystem.support.net.BaseNetAsyncTask;
import com.ty.highway.highwaysystem.support.net.BaseNetworkHelper;
import com.ty.highway.highwaysystem.support.utils.CommonUtils;
import com.ty.highway.highwaysystem.support.utils.ToastUtils;
import com.ty.highway.highwaysystem.support.utils.exception.ExceptionUtils;
import com.ty.highway.highwaysystem.system.callback.BaseDataCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fuweiwei on 2016/1/6
 */
public class BaseDataMachineService extends MFinalService{
    private  static  final String TAG="BaseDataMachineService";
    private Context mContext = this;
    private Binder mBinder;
    private BaseDataCallBack mCallBack;
    private int mTaskNum;
    private int mTaskCount=9;
    private Map<String,Boolean> mIsLoad = new HashMap<String,Boolean>();
    private String mMachineTaskTime,mMaxMachineTime,mMinMachineTime,mMachineTypeTime,mBtunnelDeployTime,mMachineByTime,mDIcTime ;

    @Override
    public void onCreate() {
        super.onCreate();
        mBinder = new BaseDataBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
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
        return mBinder;
    }

    public class BaseDataBinder extends Binder {


        public void setCallBack(BaseDataCallBack callBack) {
            mCallBack = callBack;
        }

        public void dowmLoadBaseData() {
            mTaskNum=0;
            HashMap<String, String> map = new HashMap<String, String>();
            HashMap<String, String> mapName = new HashMap<String, String>();
            HashMap<String, String> mapType = new HashMap<String, String>();
            map.put("key", Constants.KEY);
            mapName.put("methodName",Constants.METHOD_GETMACHINEISUPDATE);
            mapType.put("urlType", Constants.SERVICEURL_TYPE_MACHINECHECK);
            BaseNetAsyncTask asyncTask = new BaseNetAsyncTask(new NetResquestListener() {
                @Override
                public void onSuccess(String response) {
                    if (response.contains("\"r\":\"ok")){
                        try {
                            JSONObject json = new JSONObject(response);
                            JSONObject jsonObjects =json.getJSONObject("s");
                            JSONArray jsonArray = jsonObjects.getJSONArray("data");

                            JSONObject jsonObject0 = jsonArray.getJSONObject(0);
                            mMachineTaskTime = jsonObject0.getString("TaskTime");

                            JSONObject jsonObject1 = jsonArray.getJSONObject(1);
                            mMaxMachineTime = jsonObject1.getString("MaxMachineTime");

                            JSONObject jsonObject2 = jsonArray.getJSONObject(2);
                            mMinMachineTime = jsonObject2.getString("MinMachineTime");

                            JSONObject jsonObject3 = jsonArray.getJSONObject(3);
                            mMachineTypeTime = jsonObject3.getString("MachineTypeTime");

                            JSONObject jsonObject4 = jsonArray.getJSONObject(4);
                            mBtunnelDeployTime = jsonObject4.getString("MTunnelDeviceTime");

                            JSONObject jsonObject6 = jsonArray.getJSONObject(6);
                            mMachineByTime = jsonObject6.getString("MachineTypeRWayTime");

                            JSONObject jsonObject5 = jsonArray.getJSONObject(5);
                            mDIcTime = jsonObject5.getString("DIcTime");

                        } catch (JSONException e) {
                            new ExceptionUtils().doExecInfo(e.toString(), mContext);
                        }
                        if(!DBBTableUpdateDao.getInstance(mContext).isHasInfo("MaxMachineTime")){
                            mIsLoad.put("MaxMachineTime",true);
                        }else{
                            if (CommonUtils.IsBefore(DBBTableUpdateDao.getInstance(mContext).getTime("MaxMachineTime"), mMaxMachineTime)){
                                mIsLoad.put("MaxMachineTime",true);
                            }else{
                                mIsLoad.put("MaxMachineTime",false);
                            }
                        }
                        if(!DBBTableUpdateDao.getInstance(mContext).isHasInfo("MinMachineTime")){
                            mIsLoad.put("MinMachineTime",true);
                        }else{
                            if (CommonUtils.IsBefore(DBBTableUpdateDao.getInstance(mContext).getTime("MinMachineTime"), mMinMachineTime)){
                                mIsLoad.put("MinMachineTime",true);
                            }else{
                                mIsLoad.put("MinMachineTime",false);
                            }
                        }

                        if(!DBBTableUpdateDao.getInstance(mContext).isHasInfo("MachineTypeTime")){
                            mIsLoad.put("MachineTypeTime",true);
                        }else{
                            if (CommonUtils.IsBefore(DBBTableUpdateDao.getInstance(mContext).getTime("MachineTypeTime"), mMachineTypeTime)){
                                mIsLoad.put("MachineTypeTime",true);
                            }else{
                                mIsLoad.put("MachineTypeTime",false);
                            }
                        }

                        if(!DBBTableUpdateDao.getInstance(mContext).isHasInfo("BtunnelDeployTime")){
                            mIsLoad.put("BtunnelDeployTime",true);
                        }else{
                            if (CommonUtils.IsBefore(DBBTableUpdateDao.getInstance(mContext).getTime("BtunnelDeployTime"), mBtunnelDeployTime)){
                                mIsLoad.put("BtunnelDeployTime",true);
                            }else{
                                mIsLoad.put("BtunnelDeployTime",false);
                            }
                        }

                        if(!DBBTableUpdateDao.getInstance(mContext).isHasInfo("MachineByTime")){
                            mIsLoad.put("MachineByTime",true);
                        }else{
                            if (CommonUtils.IsBefore(DBBTableUpdateDao.getInstance(mContext).getTime("MachineByTime"), mMachineByTime)){
                                mIsLoad.put("MachineByTime",true);
                            }else{
                                mIsLoad.put("MachineByTime",false);
                            }
                        }
                        if(!DBBTableUpdateDao.getInstance(mContext).isHasInfo("DIcTime")){
                            mIsLoad.put("DIcTime",true);
                        }else{
                            if (CommonUtils.IsBefore(DBBTableUpdateDao.getInstance(mContext).getTime("DIcTime"), mDIcTime)){
                                mIsLoad.put("DIcTime",true);
                            }else{
                                mIsLoad.put("DIcTime",false);
                            }
                        }
                        if(mIsLoad.get("MaxMachineTime")){
                            GetMaxAllMachine();
                        }else{
                            sendMessage(Constants.MSG_0x2001);
                        }
                    }
                }

                @Override
                public void onError(String errormsg) {

                }
            },mContext);
            asyncTask.execute(map, mapName, mapType);

        }
    }
//---------------------------------------------更新机电检修基础数据----------------------------------------
    /**
     * 下载所有机电库信息
     */
    public  void GetMaxAllMachine(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("key", Constants.KEY);
        map.put("type", "1");
        BaseNetworkHelper.getInstance(new NetResquestListener() {
            @Override
            public void onSuccess(String response) {
                if (response.contains("\"r\":\"ok")){
                    //解析数据 并保存到本地数据库
                    ELMachineResultBean bean =  new Gson().fromJson(response,ELMachineResultBean.class);
                    List<ELMachineBean> list= bean.getS().getData();
                    String time = bean.getS().getUpdateTime();
                    DBELMachineDao.getInstance(mContext).clearData("1");
                    DBELMachineDao.getInstance(mContext).addData(list,"1");
                    BTableUpdateBean info = new BTableUpdateBean("MaxMachineTime",time);
                    DBBTableUpdateDao.getInstance(mContext).addData(info);
                }else{
                }
                sendMessage(Constants.MSG_0x2001);
            }

            @Override
            public void onError(String errormsg) {
                sendMessage(Constants.MSG_0x2001E);
            }
        },mContext).doPost(map, Constants.METHOD_GETALLMACHINE, Constants.SERVICEURL_TYPE_MACHINECHECK);
    }
    /**
     * 下载所有机电库信息
     */
    public  void GetMinAllMachine(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("key", Constants.KEY);
        map.put("type", "0");
        BaseNetworkHelper.getInstance(new NetResquestListener() {
            @Override
            public void onSuccess(String response) {
                if (response.contains("\"r\":\"ok")){
                    //解析数据 并保存到本地数据库
                    ELMachineResultBean bean =  new Gson().fromJson(response,ELMachineResultBean.class);
                    List<ELMachineBean> list= bean.getS().getData();
                    String time = bean.getS().getUpdateTime();
                    DBELMachineDao.getInstance(mContext).clearData("0");
                    DBELMachineDao.getInstance(mContext).addData(list,"0");
                    BTableUpdateBean info = new BTableUpdateBean("MinMachineTime",time);
                    DBBTableUpdateDao.getInstance(mContext).addData(info);
                }else{
                }
                sendMessage(Constants.MSG_0x2002);
            }

            @Override
            public void onError(String errormsg) {
                sendMessage(Constants.MSG_0x2002E);
            }
        },mContext).doPost(map, Constants.METHOD_GETALLMACHINE, Constants.SERVICEURL_TYPE_MACHINECHECK);
    }
    /**
     * 获取机电类型信息
     */
    public  void GetAllMachineType(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("key", Constants.KEY);
        BaseNetworkHelper.getInstance(new NetResquestListener() {
            @Override
            public void onSuccess(String response) {
                if (response.contains("\"r\":\"ok")){
                    //解析数据 并保存到本地数据库
                    ELMachineTypeResultBean bean =  new Gson().fromJson(response, ELMachineTypeResultBean.class);
                    List<ELMachineTypeBean> list= bean.getS().getData();
                    DBELMachineTypeDao.getInstance(mContext).clearData();
                    DBELMachineTypeDao.getInstance(mContext).addData(list);
                    String time = bean.getS().getUpdateTime();
                    BTableUpdateBean info = new BTableUpdateBean("MachineTypeTime",time);
                    DBBTableUpdateDao.getInstance(mContext).addData(info);
                }else{
                }
                sendMessage(Constants.MSG_0x2003);
            }
            @Override
            public void onError(String errormsg) {
                sendMessage(Constants.MSG_0x2003E);
            }
        },mContext).doPost(map, Constants.METHOD_GETALLMACHINETYPE, Constants.SERVICEURL_TYPE_MACHINECHECK);

    }
    /**
     * 获取所有隧道机电配置
     */
    public  void GetBtunnelDeploy(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("key", Constants.KEY);
        BaseNetworkHelper.getInstance(new NetResquestListener() {
            @Override
            public void onSuccess(String response) {
                if (response.contains("\"r\":\"ok")){
                    //解析数据 并保存到本地数据库
                    ELTunnelDeployResultBean bean =  new Gson().fromJson(response,ELTunnelDeployResultBean.class);
                    List<ELTunnelDeployBean> list= bean.getS().getData();
                    DBELTunnelDeployDao.getInstance(mContext).clearData();
                    DBELTunnelDeployDao.getInstance(mContext).addData(list);
                    String time = bean.getS().getUpdateTime();
                    BTableUpdateBean info = new BTableUpdateBean("BtunnelDeployTime",time);
                    DBBTableUpdateDao.getInstance(mContext).addData(info);
                }else{
                }
                sendMessage(Constants.MSG_0x2004);
            }

            @Override
            public void onError(String errormsg) {
                sendMessage(Constants.MSG_0x2004E);
            }
        },mContext).doPost(map, Constants.METHOD_GETBTUNNELDEPLOY, Constants.SERVICEURL_TYPE_MACHINECHECK);
    }
    /**
     * 获取机电类型关系信息
     */
    public  void GetMachineByType(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("key", Constants.KEY);
        BaseNetworkHelper.getInstance(new NetResquestListener() {
            @Override
            public void onSuccess(String response) {
                if (response.contains("\"r\":\"ok")){
                    //解析数据 并保存到本地数据库
                    ELMachineByTypeResultBean bean =  new Gson().fromJson(response,ELMachineByTypeResultBean.class);
                    List<ELMachineByTypeBean> list= bean.getS().getData();
                    DBELMachineByTypeDao.getInstance(mContext).clearData();
                    DBELMachineByTypeDao.getInstance(mContext).addData(list);
                    String time = bean.getS().getUpdateTime();
                    BTableUpdateBean info = new BTableUpdateBean("MachineByTime",time);
                    DBBTableUpdateDao.getInstance(mContext).addData(info);
                }else{
                }
                sendMessage(Constants.MSG_0x2005);
            }

            @Override
            public void onError(String errormsg) {
                sendMessage(Constants.MSG_0x2005E);
            }
        },mContext).doPost(map, Constants.METHOD_GETMACHINEBYTYPE, Constants.SERVICEURL_TYPE_MACHINECHECK);
    }
    /**
     * 获取机电类型项目关系表
     */
    public  void GetMachineTypeByItem(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("key", Constants.KEY);
        BaseNetworkHelper.getInstance(new NetResquestListener() {
            @Override
            public void onSuccess(String response) {
                if (response.contains("\"r\":\"ok")){
                    //解析数据 并保存到本地数据库
                    ELMachineTypeByItemResultBean bean =  new Gson().fromJson(response, ELMachineTypeByItemResultBean.class);
                    List<ELMachineTypeByItemBean> list= bean.getS();
                    DBELMachineTypeByItemDao.getInstance(mContext).clearData();
                    DBELMachineTypeByItemDao.getInstance(mContext).addData(list);
                }else{
                }
                sendMessage(Constants.MSG_0x2006);
            }

            @Override
            public void onError(String errormsg) {
                sendMessage(Constants.MSG_0x2006E);
            }
        },mContext).doPost(map, Constants.METHOD_GETMACHINETYPEBYITEM, Constants.SERVICEURL_TYPE_MACHINECHECK);
    }

    /**
     * 获取机电类型项目内容关系表
     */
    public  void GetMachineItemByContent(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("key", Constants.KEY);
        BaseNetworkHelper.getInstance(new NetResquestListener() {
            @Override
            public void onSuccess(String response) {
                if (response.contains("\"r\":\"ok")){
                    //解析数据 并保存到本地数据库
                    ELMachineItemByContentResultBean bean =  new Gson().fromJson(response,ELMachineItemByContentResultBean.class);
                    List<ELMachineItemByContentBean> list= bean.getS();
                    DBELMachineItemByContentDao.getInstance(mContext).clearData();
                    DBELMachineItemByContentDao.getInstance(mContext).addData(list);
                }else{
                }
                sendMessage(Constants.MSG_0x2007);
            }

            @Override
            public void onError(String errormsg) {
                sendMessage(Constants.MSG_0x2007E);
            }
        },mContext).doPost(map, Constants.METHOD_GETMACHINEITEMBYCONTENT, Constants.SERVICEURL_TYPE_MACHINECHECK);
    }
    /**
     * 获取机电描述关系表
     */
    public  void GetMachineContentByDescript(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("key", Constants.KEY);
        BaseNetworkHelper.getInstance(new NetResquestListener() {
            @Override
            public void onSuccess(String response) {
                if (response.contains("\"r\":\"ok")){
                    //解析数据 并保存到本地数据库
                    ELMachineContentByDescriptResultBean bean =  new Gson().fromJson(response,ELMachineContentByDescriptResultBean.class);
                    List<ELMachineContentByDescriptBean> list= bean.getS();
                    DBELMachineContentByDescriptDao.getInstance(mContext).clearData();
                    DBELMachineContentByDescriptDao.getInstance(mContext).addData(list);
                }else{
                }
                sendMessage(Constants.MSG_0x2008);
            }

            @Override
            public void onError(String errormsg) {
                sendMessage(Constants.MSG_0x2008E);
            }
        },mContext).doPost(map, Constants.METHOD_GETMACHINECONTENTBYDESCRIPT, Constants.SERVICEURL_TYPE_MACHINECHECK);
    }

    /**
     * 机电等级判定
     */
    public void GetMachineDecisionLevel(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("key", Constants.KEY);
        BaseNetworkHelper.getInstance(new NetResquestListener() {

            @Override
            public void onSuccess(String response) {
                if (response.contains("\"r\":\"ok")) {
                    DBELMachineDecisionLevelDao.getInstance(mContext).clearData();
                    try {
                        JSONObject json = new JSONObject(response);
                        JSONObject jsonObjects =json.getJSONObject("s");
                        String time = jsonObjects.getString("updateTime");
                        JSONArray jsonArray = jsonObjects.getJSONArray("data");

                        JSONObject jsonObjectofter = jsonArray.getJSONObject(0);
                        JSONArray jsonArrayofter = jsonObjectofter.getJSONArray("ofter");
                        for(int i=0;i<jsonArrayofter.length();i++){
                            ELMachineDecisionLevelBean bean = new ELMachineDecisionLevelBean();
                            JSONObject jsonObject2 = jsonArrayofter.getJSONObject(i);
                            bean.setNewId(jsonObject2.getString("NewId"));
                            bean.setName(jsonObject2.getString("Name"));
                            bean.setType(jsonObject2.getString("Type"));
                            bean.setSort(jsonObject2.getString("Sort"));
                            DBELMachineDecisionLevelDao.getInstance(mContext).addData(bean);
                        }
                      /*  if(jsonArray.length()>1){
                            JSONObject jsonObjectregular = jsonArray.getJSONObject(1);
                            JSONArray jsonArrayregular = jsonObjectregular.getJSONArray("regular");
                            for(int i=0;i<jsonArrayregular.length();i++){
                                ELMachineDecisionLevelBean bean = new ELMachineDecisionLevelBean();
                                JSONObject jsonObject3 = jsonArrayregular.getJSONObject(i);
                                bean.setNewId(jsonObject3.getString("NewId"));
                                bean.setName(jsonObject3.getString("Name"));
                                bean.setType(jsonObject3.getString("Type"));
                                bean.setSort(jsonObject3.getString("Sort"));
                                DBELMachineDecisionLevelDao.getInstance(mContext).addData(bean);
                            }
                        }*/
                        if(jsonArray.length()>1){
                            JSONObject jsonObjectConservation = jsonArray.getJSONObject(1);
                            JSONArray jsonArrayConservation = jsonObjectConservation.getJSONArray("Conservation");
                            for(int i=0;i<jsonArrayConservation.length();i++){
                                ELMachineDecisionLevelBean bean = new ELMachineDecisionLevelBean();
                                JSONObject jsonObject3 = jsonArrayConservation.getJSONObject(i);
                                bean.setNewId(jsonObject3.getString("NewId"));
                                bean.setName(jsonObject3.getString("Name"));
                                bean.setType(jsonObject3.getString("Type"));
                                bean.setSort(jsonObject3.getString("Sort"));
                                DBELMachineDecisionLevelDao.getInstance(mContext).addData(bean);
                            }
                        }
                        BTableUpdateBean info = new BTableUpdateBean("DIcTime",time);
                        DBBTableUpdateDao.getInstance(mContext).addData(info);
                    }catch (Exception e){
                        new ExceptionUtils().doExecInfo(e.toString(), mContext);
                    }
                }else{
                }
                sendMessage(Constants.MSG_0x2009);
            }

            @Override
            public void onError (String errormsg){
                sendMessage(Constants.MSG_0x2009E);
            }
        }

                ,mContext).doPost(map, Constants.METHOD_GETMACHINEDECISIONLEVEL, Constants.SERVICEURL_TYPE_MACHINECHECK);
    }
    //--------------------------------------------------------------------------------------------

    @Override
    protected void handleOtherMessage(int flag) {
        super.handleOtherMessage(flag);
        switch (flag){
            case Constants.MSG_0x2001:
                mTaskNum++;
                mCallBack.onResult((mTaskNum * 100) / mTaskCount, "正在更新机电小型设备信息...");
                if(mIsLoad.get("MinMachineTime")){
                    GetMinAllMachine();
                }else{
                    sendMessage(Constants.MSG_0x2002);
                }

                break;
            case Constants.MSG_0x2002:
                mTaskNum++;
                mCallBack.onResult((mTaskNum * 100) / mTaskCount, "正在更新机电类型信息...");
                if(mIsLoad.get("MachineTypeTime")){
                    GetAllMachineType();
                }else{
                    sendMessage(Constants.MSG_0x2003);
                }

                break;
            case Constants.MSG_0x2003:
                mTaskNum++;
                mCallBack.onResult((mTaskNum * 100) / mTaskCount, "正在更新机电隧道配置信息...");
                if(mIsLoad.get("BtunnelDeployTime")){
                    GetBtunnelDeploy();
                }else{
                    sendMessage(Constants.MSG_0x2004);
                }

                break;
            case Constants.MSG_0x2004:
                mTaskNum++;
                mCallBack.onResult((mTaskNum * 100) / mTaskCount, "正在更新机电类型关系信息...");
                if(mIsLoad.get("MachineByTime")){
                    GetMachineByType();
                }else{
                    sendMessage(Constants.MSG_0x2005);
                }

                break;
            case Constants.MSG_0x2005:
                mTaskNum++;
                mCallBack.onResult((mTaskNum * 100) / mTaskCount, "正在更新机电项目关系信息...");
                if(mIsLoad.get("MachineByTime")){
                    GetMachineTypeByItem();
                }else{
                    sendMessage(Constants.MSG_0x2006);
                }

                break;
            case Constants.MSG_0x2006:
                mTaskNum++;
                mCallBack.onResult((mTaskNum * 100) / mTaskCount, "正在更新机电内容关系信息...");
                if(mIsLoad.get("MachineByTime")){
                    GetMachineItemByContent();
                }else{
                    sendMessage(Constants.MSG_0x2007);
                }

                break;
            case Constants.MSG_0x2007:
                mTaskNum++;
                mCallBack.onResult((mTaskNum * 100) / mTaskCount, "正在更新机电描述关系信息...");
                if(mIsLoad.get("MachineByTime")){
                    GetMachineContentByDescript();
                }else{
                    sendMessage(Constants.MSG_0x2008);
                }

                break;
            case Constants.MSG_0x2008:
                mTaskNum++;
                mCallBack.onResult((mTaskNum * 100) / mTaskCount, "正在更新机电判定等级信息...");
                if(mIsLoad.get("DIcTime")){
                    GetMachineDecisionLevel();
                }else{
                    sendMessage(Constants.MSG_0x2009);
                }
                break;
            case Constants.MSG_0x2009:
                mTaskNum++;
                mCallBack.onResult((mTaskNum * 100) / mTaskCount,"更新完成");
                mTaskCount=0;
                break;
            case Constants.MSG_0x2001E:
                mTaskNum++;
                mCallBack.onResult((mTaskNum * 100) / mTaskCount,"机电大型设备更新失败，正在机电小型设备...");
                sendMessage(Constants.MSG_0x2001);
                break;
            case Constants.MSG_0x2002E:
                mTaskNum++;
                mCallBack.onResult((mTaskNum * 100) / mTaskCount,"机电小型设备更新失败，正在更新机电类型...");
                sendMessage(Constants.MSG_0x2002);
                break;
            case Constants.MSG_0x2003E:
                mTaskNum++;
                mCallBack.onResult((mTaskNum * 100) / mTaskCount,"机电类型更新失败，正在更新机电隧道配置信息...");
                sendMessage(Constants.MSG_0x2003);
                break;
            case Constants.MSG_0x2004E:
                mTaskNum++;
                mCallBack.onResult((mTaskNum * 100) / mTaskCount,"机电隧道配置信息更新失败，正在更新机电类型关系...");
                sendMessage(Constants.MSG_0x2004);
                break;
            case Constants.MSG_0x2005E:
                mTaskNum++;
                mCallBack.onResult((mTaskNum * 100) / mTaskCount,"机电类型关系更新失败，正在更新机电项目关系...");
                sendMessage(Constants.MSG_0x2005);
                break;
            case Constants.MSG_0x2006E:
                mTaskNum++;
                mCallBack.onResult((mTaskNum * 100) / mTaskCount,"机电项目关系更新失败，正在更新机电内容关系...");
                sendMessage(Constants.MSG_0x2006);
                break;
            case Constants.MSG_0x2007E:
                mTaskNum++;
                mCallBack.onResult((mTaskNum * 100) / mTaskCount,"机电内容关系更新失败，正在机电描述信息...");
                sendMessage(Constants.MSG_0x2007);
                break;
            case Constants.MSG_0x2008E:
                mTaskNum++;
                mCallBack.onResult((mTaskNum * 100) / mTaskCount, "机电描述信息更新失败...正在更新机电判定等级..");
                sendMessage(Constants.MSG_0x2008);
                break;
            case Constants.MSG_0x2009E:
                mTaskNum++;
                mCallBack.onResult((mTaskNum * 100) / mTaskCount, "更新机电判定等级失败...");
                ToastUtils.show(mContext, "更新机电判定等级失败...");
                mTaskCount=0;
                break;
            default:
                break;
        }
    }
}
