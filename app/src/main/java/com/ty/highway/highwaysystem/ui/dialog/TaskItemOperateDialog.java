package com.ty.highway.highwaysystem.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.ty.highway.highwaysystem.R;
import com.ty.highway.highwaysystem.base.Constants;
import com.ty.highway.highwaysystem.support.bean.basic.BaseResultBean;
import com.ty.highway.highwaysystem.support.bean.basic.CTUserBean;
import com.ty.highway.highwaysystem.support.bean.check.CTDiseasePhotoBean;
import com.ty.highway.highwaysystem.support.bean.check.TaskInfoBean;
import com.ty.highway.highwaysystem.support.bean.check.TaskUploadInfoResultBean;
import com.ty.highway.highwaysystem.support.db.basic.DBCTUserDao;
import com.ty.highway.highwaysystem.support.db.check.DBCTDiseaseInfoDao;
import com.ty.highway.highwaysystem.support.db.check.DBCTDiseasePhotoDao;
import com.ty.highway.highwaysystem.support.db.check.DBTaskDao;
import com.ty.highway.highwaysystem.support.listener.BaseCallBack;
import com.ty.highway.highwaysystem.support.listener.NetResquestListener;
import com.ty.highway.highwaysystem.support.net.BaseNetAsyncTask;
import com.ty.highway.highwaysystem.support.net.ftp.FTP;
import com.ty.highway.highwaysystem.support.utils.CommonUtils;
import com.ty.highway.highwaysystem.support.utils.PreferencesUtils;
import com.ty.highway.highwaysystem.support.utils.SnackbarUtils;
import com.ty.highway.highwaysystem.support.utils.ToastUtils;
import com.ty.highway.highwaysystem.support.utils.exception.ExceptionUtils;
import com.ty.highway.highwaysystem.ui.activity.check.DamageListActivity;
import com.ty.highway.highwaysystem.ui.activity.check.TaskListActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by fuweiwei on 2015/9/9.
 */
public class TaskItemOperateDialog extends BaseDialog implements View.OnClickListener {
    private Button mButDetail, mButUpload, mButDelate, mButCheck, mButCancel;
    private Context mContext;
    private TaskInfoBean mTaskInfoBean;
    //缓冲提示框
    private BaseCallBack mCallBack;
    private final String TAG = "TaskItemOperateDialog";
    private int successCount = 0;//文件上传成功个数
    private int failCount = 0;//文件上传失败个数
    private int fileCounts = 0;//文件总个数
    private int currentPeogress;//当前上传进度

    private final int UPLOAD_NOTNET = 0x00001;//是否有网
    private final int UPLOAD_RESULT = 0x00002;//上传结果
    private final int UPLOAD_PROGRESS = 0x00003;//当前进度
    //当前时间
    private SimpleDateFormat mFormatter  = new SimpleDateFormat ("yyyy/MM/dd");
    public TaskItemOperateDialog(Context context, TaskInfoBean infoBean, BaseCallBack callBack) {
        super(context, R.style.mDialog);
        mContext = context;
        mTaskInfoBean = infoBean;
        mCallBack = callBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_taskitem_operate);
        initView();
    }

    public void initView() {
        mButDetail = (Button) findViewById(R.id.dialog_taskitem_operate_details);
        mButUpload = (Button) findViewById(R.id.dialog_taskitem_operate_upload);
        mButDelate = (Button) findViewById(R.id.dialog_taskitem_operate_delete);
        mButCheck = (Button) findViewById(R.id.dialog_taskitem_operate_check);
        mButCancel = (Button) findViewById(R.id.dialog_taskitem_operate_cancel);
        mButDetail.setOnClickListener(this);
        mButUpload.setOnClickListener(this);
        mButDelate.setOnClickListener(this);
        mButCheck.setOnClickListener(this);
        mButCancel.setOnClickListener(this);
    }

 /*   *//* 显示缓冲框
    * @return
            *//*
    public SpotsDialog showSpotsDialog() {
        try {
            mSpotsDialog = new SpotsDialog(mContext);
            mSpotsDialog.show();
            mSpotsDialog.setContent("正在上传数据...");
            mSpotsDialog.setCancelable(true);
            mSpotsDialog.setCanceledOnTouchOutside(false);
        } catch (Exception e) {
            new ExceptionUtils().doExecInfo(e.toString(), mContext);
        }

        return mSpotsDialog;
    }

    *//**
     * 隐藏缓冲对话框
     *//*
    public void hideSpotsDialog() {
        if (mSpotsDialog != null && mSpotsDialog.isShowing()) {
            mSpotsDialog.dismiss();
        }
    }*/

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Intent intent = null;
        switch (id) {
            case R.id.dialog_taskitem_operate_check:
                if (mTaskInfoBean.getUpdateState() == Constants.TASK_STATE_NOSTART) {
                    EnterCheckDialog dialog = new EnterCheckDialog(getContext(), mTaskInfoBean);
                    dialog.show();
                } else if(mTaskInfoBean.getUpdateState() == Constants.TASK_STATE_HASLOADING){
                    ToastUtils.show(mContext,"任务已经上传");

                }else if(mTaskInfoBean.getUpdateState() == Constants.TASK_STATE_FINISH){
                    AlertDialog.Builder build = new AlertDialog.Builder(mContext);
                    build.setTitle("注意")
                            .setMessage("任务已经完成，是否再次进入检查吗？")
                            .setPositiveButton("确定",
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            DBTaskDao.getInstance(getContext()).setState(mTaskInfoBean.getNewId(), Constants.TASK_STATE_WORKING,PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                                            DBTaskDao.getInstance(getContext()).setState(mTaskInfoBean.getNearTaskId(), Constants.TASK_STATE_WORKING,PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                                            Intent intent = new Intent(getContext(), DamageListActivity.class);
                                            intent.putExtra("info", mTaskInfoBean);
                                            intent.putExtra("taskId", mTaskInfoBean.getNewId());
                                            getContext().startActivity(intent);
                                        }
                                    })
                            .setNegativeButton("取消",
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {

                                        }
                                    }).show();
                }
                else {
                    intent = new Intent(getContext(), DamageListActivity.class);
                    intent.putExtra("info", mTaskInfoBean);
                    intent.putExtra("taskId", mTaskInfoBean.getNewId());
                    getContext().startActivity(intent);
                }
                hide();
                break;
            case R.id.dialog_taskitem_operate_upload:
                hide();
                if(TaskListActivity.isUpload){
                    ToastUtils.show(mContext,"有任务正在上传！");
                    return;
                }
                if(mTaskInfoBean.getIsNearTask()==1&& TextUtils.isEmpty(mTaskInfoBean.getNearTaskId())){
                    ToastUtils.show(mContext,"临时任务没有关联，不能上传");
                }else {
                    if (mTaskInfoBean.getUpdateState() == Constants.TASK_STATE_HASLOADING) {
                        ToastUtils.show(mContext, "任务已经上传");

                    } else if (mTaskInfoBean.getUpdateState() == Constants.TASK_STATE_WORKING ||
                            mTaskInfoBean.getUpdateState() == Constants.TASK_STATE_NOSTART) {
                        ToastUtils.show(mContext, "任务未完成，不能上传");
                    } else {
                        if(CommonUtils.isNetworkConnected(mContext)){
                            showSpotsDialog("请稍候...",false);
                            HashMap<String, String> map = new HashMap<String, String>();
                            HashMap<String, String> mapName = new HashMap<String, String>();
                            HashMap<String, String> mapType = new HashMap<String, String>();
                            map.put("key", PreferencesUtils.getString(mContext, Constants.SP_LOGIN_KEY));
                            map.put("checkNo", mTaskInfoBean.getCheckNo());
                            mapName.put("methodName", Constants.METHOD_GETISREPORT);
                            mapType.put("urlType", Constants.SERVICEURL_TYPE_CHECK);
                            BaseNetAsyncTask asyncTask = new BaseNetAsyncTask(new NetResquestListener() {
                                @Override
                                public void onSuccess(String response) {
                                    if (response != null) {
                                        if (response.contains("\"r\":\"ok")) {
                                            BaseResultBean bean = new Gson().fromJson(response, BaseResultBean.class);
                                            if ("true".equals(bean.getS())){
                                                TaskListActivity.isUpload=true;
                                                hideSpotsDialog();
                                                uploadFile();
                                            } else {
                                                ToastUtils.show(mContext, "检查任务数据不能上传");
                                                hideSpotsDialog();
                                            }
                                        }else {
                                            ToastUtils.show(mContext, "检查任务数据不能上传");
                                            hideSpotsDialog();
                                        }
                                    }

                                }

                                @Override
                                public void onError(String errormsg) {
                                    hideSpotsDialog();
                                    ToastUtils.show(mContext, errormsg);
                                }
                            },mContext);
                            asyncTask.execute(map, mapName, mapType);
                        }else {
                            ToastUtils.show(mContext, "无网络连接，请检查网络");
                        }

                    }
                }
                break;
            case R.id.dialog_taskitem_operate_delete:
                hide();
                final CheckBox checkBox = new CheckBox(view.getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.leftMargin = 20;
                LinearLayout linearLayout = new LinearLayout(mContext);
                checkBox.setTextColor(mContext.getResources().getColor(R.color.common_theme_color));
                checkBox.setText("删除病害信息");
                checkBox.setTextSize(15);
                linearLayout.addView(checkBox, params);
                AlertDialog.Builder build = new AlertDialog.Builder(mContext);
                build.setTitle("注意")
                        .setView(linearLayout)
                        .setMessage("确定要删除该任务吗？")
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        if(mTaskInfoBean.getIsNearTask()==1){
                                            if(checkBox.isChecked()){
                                                DBCTDiseaseInfoDao.getInstance(mContext).clearDataByTaskId(mTaskInfoBean.getNewId(),PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                                                DBCTDiseasePhotoDao.getInstance(mContext).clearDataByTaskId(mTaskInfoBean.getNewId());
                                            }
                                            DBTaskDao.getInstance(mContext).deleteTaskInfoById(mTaskInfoBean.getNewId(),PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                                            DBTaskDao.getInstance(mContext).deleteTaskInfoById(mTaskInfoBean.getNearTaskId(),PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                                            mCallBack.callBack(true);
                                        }else{
                                            if(checkBox.isChecked()){
                                                DBCTDiseaseInfoDao.getInstance(mContext).clearDataByTaskId(mTaskInfoBean.getNewId(),PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                                                DBCTDiseasePhotoDao.getInstance(mContext).clearDataByTaskId(mTaskInfoBean.getNewId());
                                            }
                                            DBTaskDao.getInstance(mContext).deleteTaskInfoByNo(mTaskInfoBean.getCheckNo(),PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                                            DBTaskDao.getInstance(mContext).deleteTaskInfoById(mTaskInfoBean.getNearTaskId(),PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                                            mCallBack.callBack(true);
                                        }
                                        ToastUtils.show(mContext,"删除成功");
                                    }
                                })
                        .setNegativeButton("取消",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {

                                    }
                                }).show();
                break;
            case R.id.dialog_taskitem_operate_cancel:
                hide();
                break;
            case R.id.dialog_taskitem_operate_details:
                if (mTaskInfoBean.getIsNearTask() == 1 && TextUtils.isEmpty(mTaskInfoBean.getNearTaskId())) {
                    ToastUtils.show(mContext, "临时任务暂无详情");
                } else {
                   /* intent = new Intent(mContext, TaskDetailActivity.class);
                    intent.putExtra("info", mTaskInfoBean);
                    mContext.startActivity(intent);*/
                    TaskDetailDialog detailDialog = new TaskDetailDialog(mContext,mTaskInfoBean);
                    detailDialog.show();
                }
                hide();
                break;
            default:
                break;
        }
    }

    /**
     * 上传文件
     */
    public void uploadFile() {
        //上传图片和视屏文件
        List<CTDiseasePhotoBean> diseasePhotoBeans = DBCTDiseasePhotoDao.getInstance(mContext).getAllByTaskId(mTaskInfoBean.getNewId());
        LinkedList<File> files = new LinkedList<>();
        for (CTDiseasePhotoBean diseasePhotoBean : diseasePhotoBeans) {
            File file = new File(diseasePhotoBean.getPosition());
            if (file.exists()) {
                files.add(file);
            }
        }
        upload(files);
    }

    /**
     * 上传图片和视屏文件
     */
    private void upload(final LinkedList<File> files) {
        Date curDate   =   new Date(System.currentTimeMillis());//获取当前时间
        String  nowTime    =    mFormatter.format(curDate);
        if(files!=null && files.size()>0){
            fileCounts = files.size();
            showProgressDialog("正在上传病害图片和视频(共"+fileCounts+"个文件)...",false);
            final CTUserBean userBean = DBCTUserDao.getInstance(mContext).getPUserInfo(PreferencesUtils.getString(mContext, Constants.SP_USER_NAME));
            final String ftpIp = userBean.getFtpIp().substring(userBean.getFtpIp().lastIndexOf("/")+1, userBean.getFtpIp().lastIndexOf(":"));
            final int serverPort = Integer.parseInt(userBean.getFtpIp().substring(userBean.getFtpIp().lastIndexOf(":") + 1));
            final String remotePath = userBean.getFtpPath()+"/"+nowTime;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        new FTP(ftpIp, serverPort, userBean.getFtpUser(), userBean.getFtpPwd()).uploadMultiFile(files, remotePath, new FTP.UploadProgressListener() {
                            @Override
                            public void onUploadProgress(String currentStep, long uploadSize, File file) {
                                switch (currentStep) {
                                    case FTP.FTP_UPLOAD_SUCCESS:
                                        successCount++;
                                        Log.v(TAG, currentStep);
                                        Log.v(TAG, (successCount) + "个文件上传成功");
                                        Log.v(TAG, file.getPath());
                                        currentPeogress = ((successCount+failCount)*100)/fileCounts;
                                        String fileS = file.getPath();
                                        int length = fileS.length();
                                        int i = fileS.lastIndexOf("/");
                                        fileS = remotePath+"/"+fileS.substring(i+1);
                                        DBCTDiseasePhotoDao.getInstance(mContext).updatePhotoInfo(file.getPath(), 1,fileS);
                                        handler.sendEmptyMessage(UPLOAD_RESULT);
                                        break;
                                    case FTP.FTP_UPLOAD_LOADING:
                                        Log.v(TAG, "-----shangchuan---" + currentPeogress + "%-----");
                                        if (CommonUtils.isNetworkConnected(mContext)) {
                                            // handler.sendEmptyMessage(UPLOAD_PROGRESS);
                                        } else {
                                            Log.v(TAG, "-----网络断开连接-------------------------");
                                            Log.v(TAG, "-----当前---" + currentPeogress + "%-----");
                                            handler.sendEmptyMessage(UPLOAD_NOTNET);
                                            Thread.interrupted();
                                        }
                                        break;
                                    case FTP.FTP_UPLOAD_FAIL:
                                        ++failCount;
                                        Log.v(TAG, currentStep);
                                        Log.v(TAG, "-----失败进度---" + currentPeogress + "%-----");
                                        Log.v(TAG, (failCount) + "个文件上传失败");
                                        break;
                                    case FTP.FTP_DISCONNECT_SUCCESS:
                                        Log.v(TAG, currentStep);
                                        break;
                                    case FTP.FTP_CONNECT_FAIL:
                                        Log.v(TAG, currentStep);
                                        handler.sendEmptyMessage(UPLOAD_NOTNET);
                                        break;
                                    case FTP.FTP_CONNECT_SUCCESSS:
                                        Log.v(TAG, currentStep);
                                        break;
                                }
                            }
                        });
                    } catch (IOException e) {
                        new ExceptionUtils().doExecInfo(e.toString(), mContext);
                    }
                }
            }).start();
        }else {
            hide();
            uploadTaskData();
        }

    }

    /**
     * 上传任务检查数据
     */
    private void uploadTaskData() {
        showSpotsDialog("正在上传数据...",false);
        TaskUploadInfoResultBean taskUploadInfoResultBean = new TaskUploadInfoResultBean();
        taskUploadInfoResultBean.setCheckNo(mTaskInfoBean.getCheckNo());
        taskUploadInfoResultBean.setCheckEmp(mTaskInfoBean.getCheckEmp());
        taskUploadInfoResultBean.setCheckWayId(mTaskInfoBean.getCheckWayId());
        taskUploadInfoResultBean.setCheckWeather(mTaskInfoBean.getCheckWeather());
        taskUploadInfoResultBean.setTunnelId(mTaskInfoBean.getTunnelId());
        taskUploadInfoResultBean.setRecordEmp(mTaskInfoBean.getRecordEmp());
        taskUploadInfoResultBean.setCCheckRecordInterfaceItem(DBCTDiseaseInfoDao.getInstance(mContext).getCheckItem(mTaskInfoBean.getNewId(),PreferencesUtils.getString(mContext, Constants.SP_USER_ID)));
        String json = new Gson().toJson(taskUploadInfoResultBean);
        String jsonResult = "[" + json + "]";
        HashMap<String, String> map = new HashMap<String, String>();
        HashMap<String, String> mapName = new HashMap<String, String>();
        HashMap<String, String> mapType = new HashMap<String, String>();
        map.put("key", PreferencesUtils.getString(mContext, Constants.SP_LOGIN_KEY));
        map.put("json", jsonResult);
        mapName.put("methodName", Constants.METHOD_GETSAVECHECKDATA);
        mapType.put("urlType", Constants.SERVICEURL_TYPE_TUNNELCHECK);
        BaseNetAsyncTask asyncTask = new BaseNetAsyncTask(new NetResquestListener() {
            @Override
            public void onSuccess(String response) {
                hideSpotsDialog();
                if (response != null) {
                    if (response.contains("\"r\":\"ok")) {
                        BaseResultBean bean = new Gson().fromJson(response, BaseResultBean.class);
                        ToastUtils.show(mContext, bean.getS());
                        DBCTDiseaseInfoDao.getInstance(mContext).updateDiseaseState(mTaskInfoBean.getNewId(), PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                        DBTaskDao.getInstance(mContext).setState(mTaskInfoBean.getNewId(), Constants.TASK_STATE_HASLOADING,PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                        DBTaskDao.getInstance(mContext).setState(mTaskInfoBean.getNearTaskId(), Constants.TASK_STATE_HASLOADING, PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                        mCallBack.callBack(true);
                        TaskListActivity.isUpload=false;
                    }else{
                        BaseResultBean bean = new Gson().fromJson(response, BaseResultBean.class);
                        ToastUtils.show(mContext, bean.getS());
                        TaskListActivity.isUpload=false;
                    }
                }
            }

            @Override
            public void onError(String errormsg) {
                hideSpotsDialog();
                ToastUtils.show(mContext, errormsg);
                TaskListActivity.isUpload=false;
            }
        },mContext);
        asyncTask.execute(map, mapName, mapType);
    }

    /*private void showProgressDialog() {
        mProrgessDialog = new TaskProgressDialog(mContext);
        mProrgessDialog.show();
        mProrgessDialog.setContent("正在上传病害图片和视频(共"+fileCounts+"个文件)...");
        mProrgessDialog.setCancelable(true);
        mProrgessDialog.setCanceledOnTouchOutside(false);
    }*/

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPLOAD_RESULT:
                    mProrgessDialog.setProgress(currentPeogress);
                    mProrgessDialog.setContent("正在上传图片和视频(共" + fileCounts + "个文件,已上传" + successCount + "个)...");
                    if (currentPeogress == 100 ) {
                        hideProgressDialog();
                        Log.v(TAG, (failCount) + "个文件上传失败");
                        if (failCount == 0) {
                            uploadTaskData();
                        }else{
                            SnackbarUtils.show(mContext, failCount + "个文件上传失败,请重新上传");
                        }
                    }
                case UPLOAD_PROGRESS:
                    break;
                case UPLOAD_NOTNET:
                    ToastUtils.show(mContext, "网络不稳定或者断开连接,请检查网络");
                    hideProgressDialog();
                    break;
            }
        }
    };
}
