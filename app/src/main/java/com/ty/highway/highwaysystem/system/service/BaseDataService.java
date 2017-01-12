package com.ty.highway.highwaysystem.system.service;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.google.gson.Gson;
import com.ty.highway.highwaysystem.base.Constants;
import com.ty.highway.highwaysystem.support.bean.basedata.CTCheckItemVsDiseaseTypeBean;
import com.ty.highway.highwaysystem.support.bean.basedata.CTCheckItemVsDiseaseTypeResultBean;
import com.ty.highway.highwaysystem.support.bean.basedata.CTCheckPositionBean;
import com.ty.highway.highwaysystem.support.bean.basedata.CTCheckPositionResultBean;
import com.ty.highway.highwaysystem.support.bean.basedata.CTDictionaryBean;
import com.ty.highway.highwaysystem.support.bean.basedata.CTDiseaseLevelBean;
import com.ty.highway.highwaysystem.support.bean.basedata.CTDiseaseTypeByDescriptBean;
import com.ty.highway.highwaysystem.support.bean.basedata.CTDiseaseTypeByDescriptResultBean;
import com.ty.highway.highwaysystem.support.bean.basedata.CTDiseaseTypeVsDamageDescBean;
import com.ty.highway.highwaysystem.support.bean.basedata.CTDiseaseTypeVsDamageDescResultBean;
import com.ty.highway.highwaysystem.support.bean.basedata.CTTunnelVsItemBean;
import com.ty.highway.highwaysystem.support.bean.basedata.CTTunnelVsItemResultBean;
import com.ty.highway.highwaysystem.support.bean.basedata.POrgVsUserBean;
import com.ty.highway.highwaysystem.support.bean.basedata.POrgVsUserResultBean;
import com.ty.highway.highwaysystem.support.bean.basedata.PUserCheckPermissionBean;
import com.ty.highway.highwaysystem.support.bean.basedata.PUserCheckPermissionResultBean;
import com.ty.highway.highwaysystem.support.bean.basic.BRoadBean;
import com.ty.highway.highwaysystem.support.bean.basic.BRoadResutBean;
import com.ty.highway.highwaysystem.support.bean.basic.BSectionBean;
import com.ty.highway.highwaysystem.support.bean.basic.BSectionResultBean;
import com.ty.highway.highwaysystem.support.bean.basic.BTableUpdateBean;
import com.ty.highway.highwaysystem.support.bean.basic.BTunnelBean;
import com.ty.highway.highwaysystem.support.bean.basic.BUserTunnelResultBean;
import com.ty.highway.highwaysystem.support.bean.basic.BaseResultBean;
import com.ty.highway.highwaysystem.support.bean.basic.POrganizeBean;
import com.ty.highway.highwaysystem.support.bean.basic.POrganizeResultBean;
import com.ty.highway.highwaysystem.support.db.basedata.DBBRoadDao;
import com.ty.highway.highwaysystem.support.db.basedata.DBBSectionDao;
import com.ty.highway.highwaysystem.support.db.basedata.DBBTableUpdateDao;
import com.ty.highway.highwaysystem.support.db.basedata.DBBTunnelDao;
import com.ty.highway.highwaysystem.support.db.basedata.DBCTCheckItemVsDiseaseTypeDao;
import com.ty.highway.highwaysystem.support.db.basedata.DBCTCheckPositionDao;
import com.ty.highway.highwaysystem.support.db.basedata.DBCTDictionaryDao;
import com.ty.highway.highwaysystem.support.db.basedata.DBCTDiseaseLevelDao;
import com.ty.highway.highwaysystem.support.db.basedata.DBCTDiseaseTypeByDescriptDao;
import com.ty.highway.highwaysystem.support.db.basedata.DBCTDiseaseTypeVsDamageDescDao;
import com.ty.highway.highwaysystem.support.db.basedata.DBCTTunnelVsItemDao;
import com.ty.highway.highwaysystem.support.db.basedata.DBPOrgVsUserDao;
import com.ty.highway.highwaysystem.support.db.basedata.DBPOrganizeDao;
import com.ty.highway.highwaysystem.support.db.basedata.DBPUserCheckPermissionDao;
import com.ty.highway.highwaysystem.support.listener.NetResquestListener;
import com.ty.highway.highwaysystem.support.net.BaseNetAsyncTask;
import com.ty.highway.highwaysystem.support.net.BaseNetworkHelper;
import com.ty.highway.highwaysystem.support.utils.CommonUtils;
import com.ty.highway.highwaysystem.support.utils.PreferencesUtils;
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
 * Created by dzm on 2015/9/10 0010.
 */
public class BaseDataService extends MFinalService{
    private Context mContext = this;
    private Binder mBinder;
    private BaseDataCallBack mCallBack;
    private static final int mTaskNum = 9;
    private int mProgress = 0;
    private Map<String,Boolean> mIsLoad = new HashMap<String,Boolean>();
    private String mLineTime,mRoadTime,mTunnelTime,mDictionTime,mCheckPositionTime,mTunnelTCheckItemTime,mDiseaseTypeByDescriptTime;

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
            HashMap<String, String> map = new HashMap<String, String>();
            HashMap<String, String> mapName = new HashMap<String, String>();
            HashMap<String, String> mapType = new HashMap<String, String>();
            map.put("key", Constants.KEY);
            mapName.put("methodName",Constants.METHOD_GETISDATAUPDATE);
            mapType.put("urlType", Constants.SERVICEURL_TYPE_BTUNNELINFO);
            BaseNetAsyncTask asyncTask = new BaseNetAsyncTask(new NetResquestListener() {
                @Override
                public void onSuccess(String response) {
                    if (response.contains("\"r\":\"ok")){
                        try {
                            JSONObject json = new JSONObject(response);
                            JSONObject jsonObjects =json.getJSONObject("s");
                            JSONArray jsonArray = jsonObjects.getJSONArray("data");

                            JSONObject jsonObject0 = jsonArray.getJSONObject(0);
                            mLineTime = jsonObject0.getString("LineTime");

                            JSONObject jsonObject1 = jsonArray.getJSONObject(1);
                            mRoadTime = jsonObject1.getString("RoadTime");

                            JSONObject jsonObject2 = jsonArray.getJSONObject(2);
                            mTunnelTime = jsonObject2.getString("TunnelTime");

                            JSONObject jsonObject3 = jsonArray.getJSONObject(5);
                            mCheckPositionTime = jsonObject3.getString("CheckPositionTime");

                            JSONObject jsonObject4 = jsonArray.getJSONObject(7);
                            mTunnelTCheckItemTime = jsonObject4.getString("TunnelTCheckItemTime");

                            JSONObject jsonObject5 = jsonArray.getJSONObject(4);
                            mDictionTime = jsonObject5.getString("DictionTime");


                            JSONObject jsonObject6 = jsonArray.getJSONObject(8);
                            mDiseaseTypeByDescriptTime = jsonObject6.getString("DiseaseTypeByDescriptTime");

                            JSONObject jsonObject7 = jsonArray.getJSONObject(10);
                            PreferencesUtils.putString(mContext, Constants.SP_HOSTROY_TIME, jsonObject7.getString("CCheckDiseaseDataBaseEnityTime"));


                        } catch (JSONException e) {
                            new ExceptionUtils().doExecInfo(e.toString(), mContext);
                        }
                        if(!DBBTableUpdateDao.getInstance(mContext).isHasInfo("LineTime")){
                            mIsLoad.put("LineTime",true);
                        }else{
                            if (CommonUtils.IsBefore(DBBTableUpdateDao.getInstance(mContext).getTime("LineTime"), mLineTime)){
                                mIsLoad.put("LineTime",true);
                            }else{
                                mIsLoad.put("LineTime",false);
                            }
                        }

                        if(!DBBTableUpdateDao.getInstance(mContext).isHasInfo("RoadTime")){
                            mIsLoad.put("RoadTime",true);
                        }else{
                            if (CommonUtils.IsBefore(DBBTableUpdateDao.getInstance(mContext).getTime("RoadTime"), mRoadTime)){
                                mIsLoad.put("RoadTime",true);
                            }else{
                                mIsLoad.put("RoadTime",false);
                            }
                        }

                        if(!DBBTableUpdateDao.getInstance(mContext).isHasInfo("TunnelTime")){
                            mIsLoad.put("TunnelTime",true);
                        }else{
                            if (CommonUtils.IsBefore(DBBTableUpdateDao.getInstance(mContext).getTime("TunnelTime"), mTunnelTime)){
                                mIsLoad.put("TunnelTime",true);
                            }else{
                                mIsLoad.put("TunnelTime",false);
                            }
                        }

                        if(!DBBTableUpdateDao.getInstance(mContext).isHasInfo("CheckPositionTime")){
                            mIsLoad.put("CheckPositionTime",true);
                        }else{
                            if (CommonUtils.IsBefore(DBBTableUpdateDao.getInstance(mContext).getTime("CheckPositionTime"), mCheckPositionTime)){
                                mIsLoad.put("CheckPositionTime",true);
                            }else{
                                mIsLoad.put("CheckPositionTime",false);
                            }
                        }

                        if(!DBBTableUpdateDao.getInstance(mContext).isHasInfo("TunnelTCheckItemTime")){
                            mIsLoad.put("TunnelTCheckItemTime",true);
                        }else{
                            if (CommonUtils.IsBefore(DBBTableUpdateDao.getInstance(mContext).getTime("TunnelTCheckItemTime"), mTunnelTCheckItemTime)){
                                mIsLoad.put("TunnelTCheckItemTime",true);
                            }else{
                                mIsLoad.put("TunnelTCheckItemTime",false);
                            }
                        }

                        if(!DBBTableUpdateDao.getInstance(mContext).isHasInfo("DictionTime")){
                            mIsLoad.put("DictionTime",true);
                        }else{
                            if (CommonUtils.IsBefore(DBBTableUpdateDao.getInstance(mContext).getTime("DictionTime"), mDictionTime)){
                                mIsLoad.put("DictionTime",true);
                            }else{
                                mIsLoad.put("DictionTime",false);
                            }
                        }

                        if(!DBBTableUpdateDao.getInstance(mContext).isHasInfo("DiseaseTypeByDescriptTime")){
                            mIsLoad.put("DiseaseTypeByDescriptTime",true);
                        }else{
                            if (CommonUtils.IsBefore(DBBTableUpdateDao.getInstance(mContext).getTime("DiseaseTypeByDescriptTime"), mDiseaseTypeByDescriptTime)){
                                mIsLoad.put("DiseaseTypeByDescriptTime",true);
                            }else{
                                mIsLoad.put("DiseaseTypeByDescriptTime",false);
                            }
                        }


                        if(mIsLoad.get("LineTime")){
                            getAllBRoadInfo();
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
//---------------------------------------------更新基础数据----------------------------------------
    /**
     * 下载所有线路信息
     */
    public  void getAllBRoadInfo(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("key", Constants.KEY);
        BaseNetworkHelper.getInstance(new NetResquestListener() {
            @Override
            public void onSuccess(String response) {
                if (response.contains("\"r\":\"ok")){
                    //解析数据 并保存到本地数据库
                    BRoadResutBean bean =  new Gson().fromJson(response,BRoadResutBean.class);
                    List<BRoadBean> list= bean.getS().getData();
                    String time = bean.getS().getUpdateTime();
                    DBBRoadDao.getInstance(mContext).clearData();
                    DBBRoadDao.getInstance(mContext).addData(list);
                    BTableUpdateBean info = new BTableUpdateBean("LineTime",time);
                    DBBTableUpdateDao.getInstance(mContext).addData(info);
                }else{
                    BaseResultBean baseResultBean = new Gson().fromJson(response, BaseResultBean.class);
                }
                sendMessage(Constants.MSG_0x2001);
            }

            @Override
            public void onError(String errormsg) {
                sendMessage(Constants.MSG_0x2001E);
            }
        },mContext).doPost(map, Constants.METHOD_GETALLBLOADINFO, Constants.SERVICEURL_TYPE_BTUNNELINFO);
    }
    /**
     * 下载所有路段信息
     */
    public  void getAllBSectionInfo(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("key", Constants.KEY);
        BaseNetworkHelper.getInstance(new NetResquestListener() {
            @Override
            public void onSuccess(String response) {
                if (response.contains("\"r\":\"ok")){
                    //解析数据 并保存到本地数据库
                    BSectionResultBean bean =  new Gson().fromJson(response, BSectionResultBean.class);
                    List<BSectionBean> list= bean.getS().getData();
                    DBBSectionDao.getInstance(mContext).clearData();
                    DBBSectionDao.getInstance(mContext).addData(list);
                    String time = bean.getS().getUpdateTime();
                    BTableUpdateBean info = new BTableUpdateBean("RoadTime",time);
                    DBBTableUpdateDao.getInstance(mContext).addData(info);
                }else{
                    BaseResultBean baseResultBean = new Gson().fromJson(response, BaseResultBean.class);
                }
                sendMessage(Constants.MSG_0x2002);
            }
            @Override
            public void onError(String errormsg) {
                sendMessage(Constants.MSG_0x2002E);
            }
        },mContext).doPost(map, Constants.METHOD_GETALLBSECTIONINFO, Constants.SERVICEURL_TYPE_BTUNNELINFO);

    }
    /**
     * 下载所有隧道信息
     */
    public  void getAllBTunnelInfo(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("key", Constants.KEY);
        map.put("userId", PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
        map.put("orgId",PreferencesUtils.getString(mContext, Constants.SP_DPT_ID));
        BaseNetworkHelper.getInstance(new NetResquestListener() {
            @Override
            public void onSuccess(String response) {
                if (response.contains("\"r\":\"ok")){
                    //解析数据 并保存到本地数据库
                    BUserTunnelResultBean bean =  new Gson().fromJson(response,BUserTunnelResultBean.class);
                    List<BTunnelBean> list= bean.getS();
                    DBBTunnelDao.getInstance(mContext).clearData(PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                    DBBTunnelDao.getInstance(mContext).addData(list,PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                    /*String time = bean.getS().getUpdateTime();
                    BTablepdateBean info = new BTableUpdateBean("TunnelTime",time);
                    DBBTableUpdateDao.getInstance(mContext).addData(info);*/
                }else{
                    BaseResultBean baseResultBean = new Gson().fromJson(response, BaseResultBean.class);
                }
                sendMessage(Constants.MSG_0x2003);
            }

            @Override
            public void onError(String errormsg) {
                sendMessage(Constants.MSG_0x2003E);
            }
        },mContext).doPost(map, Constants.METHOD_GETUSERCHECKTUNNELPERMISSION, Constants.SERVICEURL_TYPE_BTUNNELINFO);
    }
    /**
     * 下载所有组织信息
     */
    public  void getAllByPOrganize(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("key", Constants.KEY);
        BaseNetworkHelper.getInstance(new NetResquestListener() {
            @Override
            public void onSuccess(String response) {
                if (response.contains("\"r\":\"ok")){
                    //解析数据 并保存到本地数据库
                    POrganizeResultBean bean =  new Gson().fromJson(response,POrganizeResultBean.class);
                    List<POrganizeBean> list= bean.getS().getData();
                    DBPOrganizeDao.getInstance(mContext).clearData();
                    for(POrganizeBean info:list){
                        DBPOrganizeDao.getInstance(mContext).addData(info);
                    }
                    String time = bean.getS().getUpdateTime();
                    BTableUpdateBean info = new BTableUpdateBean("OrganizationTime",time);
                    DBBTableUpdateDao.getInstance(mContext).addData(info);
                }else{
                    BaseResultBean baseResultBean = new Gson().fromJson(response, BaseResultBean.class);
                }
            }

            @Override
            public void onError(String errormsg) {
                sendMessage(Constants.ERRER);
            }
        },mContext).doPost(map, Constants.METHOD_GETALLBYPORGANIZE, Constants.SERVICEURL_TYPE_BASEINFO);
    }
    /**
     * 下载用户和组织关系
     */
    public void getAllPOrgVsUser(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("key", Constants.KEY);
        BaseNetworkHelper.getInstance(new NetResquestListener() {
            @Override
            public void onSuccess(String response) {
                if (response.contains("\"r\":\"ok")){
                    POrgVsUserResultBean bean =  new Gson().fromJson(response,POrgVsUserResultBean.class);
                    List<POrgVsUserBean> list= bean.getS().getData();
                    DBPOrgVsUserDao.getInstance(mContext).clearData();
                    for(POrgVsUserBean info:list){
                        DBPOrgVsUserDao.getInstance(mContext).addData(info);
                    }
                    String time = bean.getS().getUpdateTime();
                    BTableUpdateBean info = new BTableUpdateBean("OrgTUserTime",time);
                    DBBTableUpdateDao.getInstance(mContext).addData(info);
                }else{
                    BaseResultBean baseResultBean = new Gson().fromJson(response, BaseResultBean.class);
                }
            }

            @Override
            public void onError(String errormsg) {
                sendMessage(Constants.ERRER);
            }
        },mContext).doPost(map, Constants.METHOD_GETALLUSERVSORGANIZATION, Constants.SERVICEURL_TYPE_BASEINFO);
    }
    /**
     * 下载用户和隧道权限
     */
    public void getPUserCheckPermission(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("key", Constants.KEY);
        BaseNetworkHelper.getInstance(new NetResquestListener() {
            @Override
            public void onSuccess(String response) {
                if (response.contains("\"r\":\"ok")){
                    PUserCheckPermissionResultBean bean =  new Gson().fromJson(response, PUserCheckPermissionResultBean.class);
                    List<PUserCheckPermissionBean> list= bean.getS().getData();
                    DBPUserCheckPermissionDao.getInstance(mContext).clearData();
                    for(PUserCheckPermissionBean info:list){
                        DBPUserCheckPermissionDao.getInstance(mContext).addData(info);
                    }
                }else{
                    BaseResultBean baseResultBean = new Gson().fromJson(response, BaseResultBean.class);
                }
            }

            @Override
            public void onError(String errormsg) {
                sendMessage(Constants.ERRER);
            }
        },mContext).doPost(map, Constants.METHOD_GETUSERCHECKTUNNELPERMISSION, Constants.SERVICEURL_TYPE_BTUNNELINFO);
    }
    /**
     * 下载检查位置信息
     */
    public void getCTCheckPosition(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("key", Constants.KEY);
        BaseNetworkHelper.getInstance(new NetResquestListener() {
            @Override
            public void onSuccess(String response) {
                if (response.contains("\"r\":\"ok")){
                    CTCheckPositionResultBean bean =  new Gson().fromJson(response,CTCheckPositionResultBean.class);
                    List<CTCheckPositionBean> list= bean.getS().getData();
                    DBCTCheckPositionDao.getInstance(mContext).clearData();
                    DBCTCheckPositionDao.getInstance(mContext).addData(list);
                    String time = bean.getS().getUpdateTime();
                    BTableUpdateBean info = new BTableUpdateBean("CheckPositionTime",time);
                    DBBTableUpdateDao.getInstance(mContext).addData(info);
                }else{
                    BaseResultBean baseResultBean = new Gson().fromJson(response, BaseResultBean.class);
                }
                sendMessage(Constants.MSG_0x2004);
            }

            @Override
            public void onError(String errormsg) {
                sendMessage(Constants.MSG_0x2004E);
            }
        },mContext).doPost(map, Constants.METHOD_CTCHECKPOSITION, Constants.SERVICEURL_TYPE_CHECK);
    }
    /**
     * 下载隧道对应检查项
     */
    public void getGetCheckTunnelVsCheckItem(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("key", Constants.KEY);
        BaseNetworkHelper.getInstance(new NetResquestListener() {
            @Override
            public void onSuccess(String response) {
                if (response.contains("\"r\":\"ok")){
                    CTTunnelVsItemResultBean bean =  new Gson().fromJson(response,CTTunnelVsItemResultBean.class);
                    List<CTTunnelVsItemBean> list= bean.getS().getData();
                    DBCTTunnelVsItemDao.getInstance(mContext).clearData();
                    DBCTTunnelVsItemDao.getInstance(mContext).addData(list);
                    String time = bean.getS().getUpdateTime();
                    BTableUpdateBean info = new BTableUpdateBean("TunnelTCheckItemTime",time);
                    DBBTableUpdateDao.getInstance(mContext).addData(info);
                }else{
                    BaseResultBean baseResultBean = new Gson().fromJson(response, BaseResultBean.class);
                }
                sendMessage(Constants.MSG_0x2005);
            }

            @Override
            public void onError(String errormsg) {
                sendMessage(Constants.MSG_0x2005E);
            }
        },mContext).doPost(map, Constants.METHOD_GETCHECKTUNNELVSCHECKITEM, Constants.SERVICEURL_TYPE_CHECK);
    }
    /**
     * 下载检查项对应病害类型
     */
    public void getGetCheckItemVsDiseaseType(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("key", Constants.KEY);
        BaseNetworkHelper.getInstance(new NetResquestListener() {
            @Override
            public void onSuccess(String response) {
                if (response.contains("\"r\":\"ok")){
                    CTCheckItemVsDiseaseTypeResultBean bean =  new Gson().fromJson(response, CTCheckItemVsDiseaseTypeResultBean.class);
                    List<CTCheckItemVsDiseaseTypeBean> list= bean.getS();
                    DBCTCheckItemVsDiseaseTypeDao.getInstance(mContext).clearData();
                    DBCTCheckItemVsDiseaseTypeDao.getInstance(mContext).addData(list);
                }else{
                    BaseResultBean baseResultBean = new Gson().fromJson(response, BaseResultBean.class);
                }
                sendMessage(Constants.MSG_0x2006);
            }

            @Override
            public void onError(String errormsg) {
                sendMessage(Constants.MSG_0x2006E);
            }
        },mContext).doPost(map, Constants.METHOD_GETCHECKITEMVSDISEASETYPE, Constants.SERVICEURL_TYPE_CHECK);
    }
    /**
     * 下载病害类型对应病害描述
     */
    public void getGetCheckTypeVsDiseaseDesc(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("key", Constants.KEY);
        BaseNetworkHelper.getInstance(new NetResquestListener() {
            @Override
            public void onSuccess(String response) {
                if (response.contains("\"r\":\"ok")){
                    CTDiseaseTypeVsDamageDescResultBean bean =  new Gson().fromJson(response,CTDiseaseTypeVsDamageDescResultBean.class);
                    List<CTDiseaseTypeVsDamageDescBean> list= bean.getS();
                    DBCTDiseaseTypeVsDamageDescDao.getInstance(mContext).clearData();
                    DBCTDiseaseTypeVsDamageDescDao.getInstance(mContext).addData(list);
                }else{
                    BaseResultBean baseResultBean = new Gson().fromJson(response, BaseResultBean.class);
                }
                sendMessage(Constants.MSG_0x2007);
            }

            @Override
            public void onError(String errormsg) {
                sendMessage(Constants.MSG_0x2007E);
            }
        },mContext).doPost(map, Constants.METHOD_GETCHECKTYPEVSDISEASEDESC, Constants.SERVICEURL_TYPE_CHECK);
    }
    /**
     * 下载病害类型对应病害描述
     */
    public void getDiseaseTypeByDescript(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("key", Constants.KEY);
        BaseNetworkHelper.getInstance(new NetResquestListener() {
            @Override
            public void onSuccess(String response) {
                if (response.contains("\"r\":\"ok")){
                    CTDiseaseTypeByDescriptResultBean bean =  new Gson().fromJson(response,CTDiseaseTypeByDescriptResultBean.class);
                    List<CTDiseaseTypeByDescriptBean> list= bean.getS().getData();
                    DBCTDiseaseTypeByDescriptDao.getInstance(mContext).clearData();

                    DBCTDiseaseTypeByDescriptDao.getInstance(mContext).addData(list);

                    String time = bean.getS().getUpdateTime();
                    BTableUpdateBean info = new BTableUpdateBean("DiseaseTypeByDescriptTime",time);
                    DBBTableUpdateDao.getInstance(mContext).addData(info);
                }else{
                    BaseResultBean baseResultBean = new Gson().fromJson(response, BaseResultBean.class);
                }
                sendMessage(Constants.MSG_0x2009);
            }

            @Override
            public void onError(String errormsg) {
                sendMessage(Constants.MSG_0x2009E);
            }
        },mContext).doPost(map, Constants.METHOD_GETDISEASETYPEBYDESCRIPT, Constants.SERVICEURL_TYPE_CHECK);
    }
    /**
     * 获取字典数据(目前只有病害等级、检查方式)
     */
    public void GetDictionary(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("key", Constants.KEY);
        BaseNetworkHelper.getInstance(new NetResquestListener() {

            @Override
            public void onSuccess(String response) {
                if (response.contains("\"r\":\"ok")) {
                    DBCTDictionaryDao.getInstance(mContext).clearData();
                    DBCTDiseaseLevelDao.getInstance(mContext).clearData();
                    try {
                        JSONObject json = new JSONObject(response);
                        JSONObject jsonObjects =json.getJSONObject("s");
                        String time = jsonObjects.getString("updateTime");
                        JSONArray jsonArray = jsonObjects.getJSONArray("data");
                        JSONObject jsonObjectcheckWay = jsonArray.getJSONObject(0);
                        JSONArray jsonArraycheckWay = jsonObjectcheckWay.getJSONArray("checkWay");

                        JSONObject jsonObjectofter = jsonArray.getJSONObject(1);
                        JSONArray jsonArrayofter = jsonObjectofter.getJSONArray("ofter");

                        JSONObject jsonObjectofterControl = jsonArray.getJSONObject(2);
                        JSONArray jsonArrayofterControl = jsonObjectofterControl.getJSONArray("ofterControl");

                        JSONObject jsonObjectregular = jsonArray.getJSONObject(3);
                        JSONArray jsonArrayregular = jsonObjectregular.getJSONArray("regular");

                        for(int i=0;i<jsonArraycheckWay.length();i++){
                            CTDictionaryBean bean =new CTDictionaryBean();
                            JSONObject jsonObject1 = jsonArraycheckWay.getJSONObject(i);
                            bean.setNewId(jsonObject1.getString("NewId"));
                            bean.setName(jsonObject1.getString("Name"));
                            bean.setType(jsonObject1.getString("Type"));
                            bean.setSort(jsonObject1.getString("Sort"));
                            DBCTDictionaryDao.getInstance(mContext).addData(bean);
                        }
                        for(int i=0;i<jsonArrayofter.length();i++){
                            CTDiseaseLevelBean bean = new CTDiseaseLevelBean();
                            JSONObject jsonObject2 = jsonArrayofter.getJSONObject(i);
                            bean.setNewId(jsonObject2.getString("NewId"));
                            bean.setName(jsonObject2.getString("Name"));
                            bean.setType(jsonObject2.getString("Type"));
                            bean.setSort(jsonObject2.getString("Sort"));
                            DBCTDiseaseLevelDao.getInstance(mContext).addData(bean);
                        }
                        for(int i=0;i<jsonArrayregular.length();i++){
                            CTDiseaseLevelBean bean = new CTDiseaseLevelBean();
                            JSONObject jsonObject3 = jsonArrayregular.getJSONObject(i);
                            bean.setNewId(jsonObject3.getString("NewId"));
                            bean.setName(jsonObject3.getString("Name"));
                            bean.setType(jsonObject3.getString("Type"));
                            bean.setSort(jsonObject3.getString("Sort"));
                            DBCTDiseaseLevelDao.getInstance(mContext).addData(bean);
                        }
                        for(int i=0;i<jsonArrayofterControl.length();i++){
                            CTDiseaseLevelBean bean = new CTDiseaseLevelBean();
                            JSONObject jsonObject4 = jsonArrayofterControl.getJSONObject(i);
                            bean.setNewId(jsonObject4.getString("NewId"));
                            bean.setName(jsonObject4.getString("Name"));
                            bean.setType(jsonObject4.getString("Type"));
                            bean.setSort(jsonObject4.getString("Sort"));
                            DBCTDiseaseLevelDao.getInstance(mContext).addData(bean);
                        }
                        BTableUpdateBean info = new BTableUpdateBean("DictionTime",time);
                        DBBTableUpdateDao.getInstance(mContext).addData(info);
                    }catch (Exception e){
                        new ExceptionUtils().doExecInfo(e.toString(), mContext);
                    }
                }else{
                    BaseResultBean baseResultBean = new Gson().fromJson(response, BaseResultBean.class);
                }
                sendMessage(Constants.MSG_0x2008);
            }

            @Override
            public void onError (String errormsg){
                sendMessage(Constants.MSG_0x2008E);
            }
        }

                ,mContext).doPost(map, Constants.METHOD_GETDICTIONARY, Constants.SERVICEURL_TYPE_CHECK);
    }
    //--------------------------------------------------------------------------------------------

    @Override
    protected void handleOtherMessage(int flag) {
        super.handleOtherMessage(flag);
        switch (flag){
            case Constants.MSG_0x2001:
                mProgress++;
                mCallBack.onResult((mProgress * 100) / mTaskNum, "正在更新路段信息...");
                if(mIsLoad.get("RoadTime")){
                    getAllBSectionInfo();
                }else{
                    sendMessage(Constants.MSG_0x2002);
                }

                break;
            case Constants.MSG_0x2002:
                mProgress++;
                mCallBack.onResult((mProgress * 100) / mTaskNum, "正在更新隧道信息...");
                if(mIsLoad.get("TunnelTime")){
                    getAllBTunnelInfo();
                }else{
                    sendMessage(Constants.MSG_0x2003);
                }

                break;
            case Constants.MSG_0x2003:
                mProgress++;
                mCallBack.onResult((mProgress * 100) / mTaskNum, "正在更新检查位置信息...");
                if(mIsLoad.get("CheckPositionTime")){
                    getCTCheckPosition();
                }else{
                    sendMessage(Constants.MSG_0x2004);
                }

                break;
            case Constants.MSG_0x2004:
                mProgress++;
                mCallBack.onResult((mProgress * 100) / mTaskNum, "正在更新隧道对应检查项信息...");
                if(mIsLoad.get("TunnelTCheckItemTime")){
                    getGetCheckTunnelVsCheckItem();
                }else{
                    sendMessage(Constants.MSG_0x2005);
                }

                break;
            case Constants.MSG_0x2005:
                mProgress++;
                mCallBack.onResult((mProgress * 100) / mTaskNum, "正在更新病害类型信息...");
                if(mIsLoad.get("TunnelTCheckItemTime")){
                    getGetCheckItemVsDiseaseType();
                }else{
                    sendMessage(Constants.MSG_0x2006);
                }

                break;
            case Constants.MSG_0x2006:
                mProgress++;
                mCallBack.onResult((mProgress * 100) / mTaskNum, "正在更新病害描述信息...");
                if(mIsLoad.get("TunnelTCheckItemTime")){
                    getGetCheckTypeVsDiseaseDesc();
                }else{
                    sendMessage(Constants.MSG_0x2007);
                }

                break;
            case Constants.MSG_0x2007:
                mProgress++;
                mCallBack.onResult((mProgress * 100) / mTaskNum, "正在更新字典表信息...");
                if(mIsLoad.get("DictionTime")){
                    GetDictionary();
                }else{
                    sendMessage(Constants.MSG_0x2008);
                }

                break;
            case Constants.MSG_0x2008:
                mProgress++;
                mCallBack.onResult((mProgress * 100) / mTaskNum, "正在更新定期详细描述信息...");
                if(mIsLoad.get("DiseaseTypeByDescriptTime")){
                    getDiseaseTypeByDescript();
                }else{
                    sendMessage(Constants.MSG_0x2009);
                }
                break;
            case Constants.MSG_0x2009:
                mProgress++;
                mCallBack.onResult((mProgress * 100) / mTaskNum,"更新完成");
                break;
            case Constants.MSG_0x2001E:
                mProgress++;
                mCallBack.onResult((mProgress * 100) / mTaskNum,"线路信息更新失败，正在更新路段...");
                sendMessage(Constants.MSG_0x2001);
                break;
            case Constants.MSG_0x2002E:
                mProgress++;
                mCallBack.onResult((mProgress * 100) / mTaskNum,"路段信息更新失败，正在更新隧道路段...");
                sendMessage(Constants.MSG_0x2002);
                break;
            case Constants.MSG_0x2003E:
                mProgress++;
                mCallBack.onResult((mProgress * 100) / mTaskNum,"隧道信息更新失败，正在更新检查位置...");
                sendMessage(Constants.MSG_0x2003);
                break;
            case Constants.MSG_0x2004E:
                mProgress++;
                mCallBack.onResult((mProgress * 100) / mTaskNum,"检查位置信息更新失败，正在更新检查项...");
                sendMessage(Constants.MSG_0x2004);
                break;
            case Constants.MSG_0x2005E:
                mProgress++;
                mCallBack.onResult((mProgress * 100) / mTaskNum,"检查项信息更新失败，正在更新病害类型...");
                sendMessage(Constants.MSG_0x2005);
                break;
            case Constants.MSG_0x2006E:
                mProgress++;
                mCallBack.onResult((mProgress * 100) / mTaskNum,"病害类型更新失败，正在更新病害描述...");
                sendMessage(Constants.MSG_0x2006);
                break;
            case Constants.MSG_0x2007E:
                mProgress++;
                mCallBack.onResult((mProgress * 100) / mTaskNum,"病害描述更新失败，正在更新字典表...");
                sendMessage(Constants.MSG_0x2007);
                break;
            case Constants.MSG_0x2008E:
                mProgress++;
                mCallBack.onResult((mProgress * 100) / mTaskNum, "更新字典表失败...正在更新病害详细描述表..");
                sendMessage(Constants.MSG_0x2008);
                break;
            case Constants.MSG_0x2009E:
                mProgress++;
                mCallBack.onResult((mProgress * 100) / mTaskNum, "更新病害详细描述失败...");
                ToastUtils.show(mContext,"更新病害详细描述失败...");
                break;
            case Constants.ERRER:
                break;
            default:
                break;
        }
    }
}
