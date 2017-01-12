package com.ty.highway.highwaysystem.ui.activity.basic;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ty.highway.frameworklibrary.support.percent.PercentRelativeLayout;
import com.ty.highway.highwaysystem.R;
import com.ty.highway.highwaysystem.base.Constants;
import com.ty.highway.highwaysystem.base.HWApplication;
import com.ty.highway.highwaysystem.support.bean.basic.BTableUpdateBean;
import com.ty.highway.highwaysystem.support.bean.basic.BaseResultBean;
import com.ty.highway.highwaysystem.support.bean.check.TaskInfoBean;
import com.ty.highway.highwaysystem.support.bean.check.TaskListBean;
import com.ty.highway.highwaysystem.support.bean.machine.ELMachineTaskBean;
import com.ty.highway.highwaysystem.support.bean.machine.ELMachineTaskResultBean;
import com.ty.highway.highwaysystem.support.db.basedata.DBBTableUpdateDao;
import com.ty.highway.highwaysystem.support.db.basedata.DBCTDictionaryDao;
import com.ty.highway.highwaysystem.support.db.check.DBTaskDao;
import com.ty.highway.highwaysystem.support.db.machine.DBELMachineTaskDao;
import com.ty.highway.highwaysystem.support.db.machine.DBELMenuDao;
import com.ty.highway.highwaysystem.support.listener.NetResquestListener;
import com.ty.highway.highwaysystem.support.net.BaseNetAsyncTask;
import com.ty.highway.highwaysystem.support.utils.CommonUtils;
import com.ty.highway.highwaysystem.support.utils.FileUtils;
import com.ty.highway.highwaysystem.support.utils.PreferencesUtils;
import com.ty.highway.highwaysystem.support.utils.SnackbarUtils;
import com.ty.highway.highwaysystem.support.utils.ToastUtils;
import com.ty.highway.highwaysystem.ui.activity.check.TaskListActivity;
import com.ty.highway.highwaysystem.ui.activity.check.TreeListActivity;
import com.ty.highway.highwaysystem.ui.activity.machine.MachineTreeListActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends BaseActivity implements OnClickListener{

    private PercentRelativeLayout mRelCheckRegular, mRelCheckOften, mRelMachineRegular, mRelMachineOften;
    private TextView mCheckRegularN, mCheckOftenN, mMachineRegularN, mMachineOftenN;
    private ImageView mIvCheckRegular,mIvCheckOften,mIvMachineRegular,mIvMachineOften;
    private Context mContext=this;
    private Boolean flag = false ;
    /*菜单图片存储位置*/
    private static final String savePath = Environment.getExternalStorageDirectory().getPath()+Constants.SD_MENUIMG_PATH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTitle();
        initView();
        if(CommonUtils.isNetworkConnected(mContext)){
            if(DBELMenuDao.getInstance(mContext).isHasType(Constants.MENU_TYPE_CHECK)){
                getData();
            }
            if(DBELMenuDao.getInstance(mContext).isHasType(Constants.MENU_TYPE_MACHINE)){
                getMachineData();
            }
        }else{
            initCount();
        }
    }
    public  void initCount(){
        int count1 = DBTaskDao.getInstance(mContext).getTaskListByState(-1,DBCTDictionaryDao.getInstance(mContext).getIDBySort(Constants.CHECK_TYPE_REGULAR), PreferencesUtils.getString(mContext, Constants.SP_USER_ID)).size();
        int count2 = DBTaskDao.getInstance(mContext).getTaskListByState(-1, DBCTDictionaryDao.getInstance(mContext).getIDBySort(Constants.CHECK_TYPE_OFTEN), PreferencesUtils.getString(mContext, Constants.SP_USER_ID)).size();
        int count3 = DBELMachineTaskDao.getInstance(mContext).getTaskByCheckWay(PreferencesUtils.getString(mContext, Constants.SP_USER_ID), DBCTDictionaryDao.getInstance(mContext).getIDBySort(Constants.CHECK_TYPE_REGULAR)).size();
        int count4 = DBELMachineTaskDao.getInstance(mContext).getTaskByCheckWay(PreferencesUtils.getString(mContext, Constants.SP_USER_ID),DBCTDictionaryDao.getInstance(mContext).getIDBySort(Constants.CHECK_TYPE_OFTEN)).size();
        if(count1==0){
            mCheckRegularN.setVisibility(View.INVISIBLE);
        }else{
            mCheckRegularN.setVisibility(View.VISIBLE);
            mCheckRegularN.setText(count1 + "");
        }
        if(count2==0){
            mCheckOftenN.setVisibility(View.INVISIBLE);
        }else{
            mCheckOftenN.setVisibility(View.VISIBLE);
            mCheckOftenN.setText(count2 + "");
        }
        if(count3==0){
            mMachineRegularN.setVisibility(View.INVISIBLE);
        }else{
            mMachineRegularN.setVisibility(View.VISIBLE);
            mMachineRegularN.setText(count3 + "");
        }
        if(count4==0){
            mMachineOftenN.setVisibility(View.INVISIBLE);
        }else{
            mMachineOftenN.setVisibility(View.VISIBLE);
            mMachineOftenN.setText(count4 + "");
        }
    }
    public void initTitle(){
        setLTBtnText("返回");
        setLTBtnVisiable(View.VISIBLE);
        setRTBtnVisiable(View.VISIBLE);
        setRTBtnText("刷新");
        setTitleText("菜单模块");
        setTitleVisiable(View.VISIBLE);
    }
    public void initView(){
        mRelCheckRegular = (PercentRelativeLayout) findViewById(R.id.rel_check_regular);
        mRelCheckOften = (PercentRelativeLayout) findViewById(R.id.rel_check_often);
        mRelMachineRegular = (PercentRelativeLayout) findViewById(R.id.rel_machine_regular);
        mRelMachineOften = (PercentRelativeLayout) findViewById(R.id.rel_machine_often);
        mCheckRegularN = (TextView) findViewById(R.id.check_regular_num);
        mCheckOftenN = (TextView) findViewById(R.id.check_often_num);
        mMachineRegularN = (TextView) findViewById(R.id.machine_regular_num);
        mMachineOftenN = (TextView) findViewById(R.id.machine_often_num);
        mIvCheckRegular = (ImageView) findViewById(R.id.image_check_regular);
        mIvCheckOften = (ImageView) findViewById(R.id.image_check_often);
        mIvMachineRegular = (ImageView) findViewById(R.id.image_machine_regular);
        mIvMachineOften = (ImageView) findViewById(R.id.image_machine_often);
        mRelCheckRegular.setOnClickListener(this);
        mRelCheckOften.setOnClickListener(this);
        mRelMachineRegular.setOnClickListener(this);
        mRelMachineOften.setOnClickListener(this);
        String fileCheckRegular = savePath+Constants.MENU_TYPE_CHECK_FIX+".png";
        if(FileUtils.isFileExist(fileCheckRegular)){
            mIvCheckRegular.setImageBitmap(BitmapFactory.decodeFile(fileCheckRegular));
        }else{
            mIvCheckRegular.setImageResource(R.drawable.main_icon1);
        }
        String fileCheckOften = savePath+Constants.MENU_TYPE_CHECK_OFEN+".png";
        if(FileUtils.isFileExist(fileCheckOften)){
            mIvCheckOften.setImageBitmap(BitmapFactory.decodeFile(fileCheckOften));
        }else{
            mIvCheckOften.setImageResource(R.drawable.main_icon2);
        }
        String fileMachineRegular = savePath+Constants.MENU_TYPE_MACHINE_FIX+".png";
        if(FileUtils.isFileExist(fileMachineRegular)){
            mIvMachineRegular.setImageBitmap(BitmapFactory.decodeFile(fileMachineRegular));
        }else{
            mIvMachineRegular.setImageResource(R.drawable.main_icon3);
        }
        String fileMachineOften = savePath+Constants.MENU_TYPE_MACHINE_OFEN+".png";
        if(FileUtils.isFileExist(fileMachineOften)){
            mIvMachineOften.setImageBitmap(BitmapFactory.decodeFile(fileMachineOften));
        }else{
            mIvMachineOften.setImageResource(R.drawable.main_icon4);
        }
    }
    public  void getData(){
        String key = PreferencesUtils.getString(mContext, Constants.SP_LOGIN_KEY, Constants.KEY);
        HashMap<String, String> map = new HashMap<String, String>();
        HashMap<String, String> mapName = new HashMap<String, String>();
        HashMap<String, String> mapType = new HashMap<String, String>();
        map.put("key", key);
        map.put("userId", PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
        map.put("depId", PreferencesUtils.getString(mContext, Constants.SP_DPT_ID));
        mapName.put("methodName", Constants.METHOD_GETALLCHECKTASKINFO);
        mapType.put("urlType", Constants.SERVICEURL_TYPE_CHECK);
        BaseNetAsyncTask asyncTask = new BaseNetAsyncTask(new NetResquestListener() {
            @Override
            public void onSuccess(String response) {
                if(response != null){
                    if(response.contains("\"r\":\"ok")){
                        TaskListBean resultbean = new Gson().fromJson(response, TaskListBean.class);
                        ToastUtils.show(mContext, "更新任务成功");
                        List<TaskInfoBean> list = resultbean.getS().getData();
                        List<String> checkNoList =new ArrayList<>();
                        for(TaskInfoBean info:list){
                            checkNoList.add(info.getCheckNo());
                            if(DBTaskDao.getInstance(mContext).isHasTaskById(info.getCheckNo(),PreferencesUtils.getString(mContext, Constants.SP_USER_ID))){
                            }else{
                                DBTaskDao.getInstance(mContext).addTaskInfo(info, PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                            }
                        }
                           List<TaskInfoBean> list1 = DBTaskDao.getInstance(mContext).getTaskList(PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                        for(TaskInfoBean info:list1){
                            if((!checkNoList.contains(info.getCheckNo()))&&info.getIsNearTask()==0){
                                DBTaskDao.getInstance(mContext).deleteTaskInfoByNo(info.getCheckNo(),PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                            }
                        }
                        initCount();
                    }else{
                        BaseResultBean resultbean = new Gson().fromJson(response, BaseResultBean.class);
                        ToastUtils.show(mContext, resultbean.getS());
                    }
                }
            }

            @Override
            public void onError(String errormsg) {
                ToastUtils.show(mContext, errormsg);
            }
        },mContext);
        asyncTask.execute(map, mapName, mapType);
    }
    public  void getMachineData(){
        showSpotsDialog("更新任务中...",false);
        String key = PreferencesUtils.getString(mContext, Constants.SP_LOGIN_KEY, Constants.KEY);
        HashMap<String, String> map = new HashMap<String, String>();
        HashMap<String, String> mapName = new HashMap<String, String>();
        HashMap<String, String> mapType = new HashMap<String, String>();
        map.put("key", key);
        map.put("userId", PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
        map.put("depId", PreferencesUtils.getString(mContext, Constants.SP_DPT_ID));
        mapName.put("methodName", Constants.METHOD_GETMACHINETASK);
        mapType.put("urlType", Constants.SERVICEURL_TYPE_MACHINECHECK);
        BaseNetAsyncTask asyncTask = new BaseNetAsyncTask(new NetResquestListener() {
            @Override
            public void onSuccess(String response) {
                if(response != null){
                    if(response.contains("\"r\":\"ok")){
                        ELMachineTaskResultBean resultbean = new Gson().fromJson(response, ELMachineTaskResultBean.class);
                        ToastUtils.show(mContext, "更新机电任务成功");
                        List<ELMachineTaskBean> list = resultbean.getS().getData();
                        String time = resultbean.getS().getUpdateTime();
                        BTableUpdateBean bTableUpdateBean = new BTableUpdateBean("MachineTaskTime",time);

                        List<String> checkNoList =new ArrayList<>();
                        for(ELMachineTaskBean info:list){
                            checkNoList.add(info.getCheckNo());
                            if(DBELMachineTaskDao.getInstance(mContext).isHasTaskById(info.getCheckNo(),PreferencesUtils.getString(mContext, Constants.SP_USER_ID))){
                               if( CommonUtils.IsBefore(DBBTableUpdateDao.getInstance(mContext).getTime("MachineTaskTime"), time)){
                                   DBELMachineTaskDao.getInstance(mContext).updateTaskInfo(info,PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                                }
                            }else{
                                DBELMachineTaskDao.getInstance(mContext).addData(info, PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                            }
                        }
                           List<ELMachineTaskBean> list1 = DBELMachineTaskDao.getInstance(mContext).getAllTask(PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                        for(ELMachineTaskBean info:list1){
                            if(!checkNoList.contains(info.getCheckNo())){
                                DBELMachineTaskDao.getInstance(mContext).deleteTaskInfoByNo(info.getCheckNo(),PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                            }
                        }
                        DBBTableUpdateDao.getInstance(mContext).addData(bTableUpdateBean);
                        initCount();
                        hideSpotsDialog();
                    }else{
                        BaseResultBean resultbean = new Gson().fromJson(response, BaseResultBean.class);
                        ToastUtils.show(mContext, resultbean.getS());
                    }
                }
                hideSpotsDialog();
            }

            @Override
            public void onError(String errormsg) {
                hideSpotsDialog();
                ToastUtils.show(mContext, errormsg);
            }
        },mContext);
        asyncTask.execute(map, mapName, mapType);
    }

    @Override
    protected void onPause() {
        super.onPause();
        flag = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        flag = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(flag){
            initCount();
        }
    }
;

    @Override
    public void onClick(View view) {
        int id = view.getId();
        final String content1;
        final String content2;
        switch (id){
            case R.id.rel_check_regular:
//                if (!DBELMenuDao.getInstance(mContext).isHasType(Constants.MENU_TYPE_CHECK_FIX)){
//                    SnackbarUtils.show(mContext, "暂无该权限，不能使用");
//                    return;
//                }
                HWApplication.setmCheckType(Constants.CHECK_TYPE_REGULAR);
//                String checkWayId = DBCTDictionaryDao.getInstance(mContext).getIDBySort(Constants.CHECK_TYPE_REGULAR);
                Intent intent = new Intent(mContext, TaskListActivity.class);
//                intent.putExtra("checkWayId",checkWayId);
                startActivity(intent);

                break;
            case R.id.rel_check_often:
                if (!DBELMenuDao.getInstance(mContext).isHasType(Constants.MENU_TYPE_CHECK_OFEN)){
                    SnackbarUtils.show(mContext, "暂无该权限，不能使用");
                    return;
                }
                HWApplication.setmCheckType(Constants.CHECK_TYPE_OFTEN);
                String checkWayId2 = DBCTDictionaryDao.getInstance(mContext).getIDBySort(Constants.CHECK_TYPE_OFTEN);
                Intent intent2 = new Intent(mContext, TreeListActivity.class);
                intent2.putExtra("checkWayId",checkWayId2);
                startActivity(intent2);
                break;
            case R.id.rel_machine_often:
                if (!DBELMenuDao.getInstance(mContext).isHasType(Constants.MENU_TYPE_MACHINE_OFEN)){
                    SnackbarUtils.show(mContext, "暂无该权限，不能使用");
                    return;
                }
                HWApplication.setmCheckType(Constants.CHECK_TYPE_OFTEN);
                String checkWayId3 = DBCTDictionaryDao.getInstance(mContext).getIDBySort(Constants.CHECK_TYPE_OFTEN);
                Intent intent3 = new Intent(mContext, MachineTreeListActivity.class);
                intent3.putExtra("checkWayId",checkWayId3);
                startActivity(intent3);
                break;
            case R.id.rel_machine_regular:
                if (!DBELMenuDao.getInstance(mContext).isHasType(Constants.MENU_TYPE_MACHINE_FIX)){
                    SnackbarUtils.show(mContext, "暂无该权限，不能使用");
                    return;
                }
                HWApplication.setmCheckType(Constants.CHECK_TYPE_REGULAR);
                String     checkWayId4 = DBCTDictionaryDao.getInstance(mContext).getIDBySort(Constants.CHECK_TYPE_REGULAR);
                Intent intent4 = new Intent(mContext, MachineTreeListActivity.class);
                intent4.putExtra("checkWayId",checkWayId4);
                startActivity(intent4);
                break;
            default:
                break;
        }
    }

    @Override
    public void onLtBtnClick() {
        super.onLtBtnClick();
        finish();
    }

    @Override
    public void onRtBtnClick() {
        super.onRtBtnClick();
        if(CommonUtils.isNetworkConnected(mContext)){
            if(DBELMenuDao.getInstance(mContext).isHasType(Constants.MENU_TYPE_CHECK)){
                getData();
            }
            if(DBELMenuDao.getInstance(mContext).isHasType(Constants.MENU_TYPE_MACHINE)){
                getMachineData();
            }
        }else{
            SnackbarUtils.show(mContext, "没有网络连接");
        }
    }
}


