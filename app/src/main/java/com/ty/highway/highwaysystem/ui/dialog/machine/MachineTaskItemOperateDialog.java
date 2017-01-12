package com.ty.highway.highwaysystem.ui.dialog.machine;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
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
import com.ty.highway.highwaysystem.support.bean.machine.ELMachineDiseasePhotoBean;
import com.ty.highway.highwaysystem.support.bean.machine.ELMachineTaskBean;
import com.ty.highway.highwaysystem.support.bean.machine.MachineCheckRechodBean;
import com.ty.highway.highwaysystem.support.db.basic.DBCTUserDao;
import com.ty.highway.highwaysystem.support.db.machine.DBELMachineDiseaseDao;
import com.ty.highway.highwaysystem.support.db.machine.DBELMachineDiseasePhotoDao;
import com.ty.highway.highwaysystem.support.db.machine.DBELMachineTaskDao;
import com.ty.highway.highwaysystem.support.listener.BaseCallBack;
import com.ty.highway.highwaysystem.support.listener.NetResquestListener;
import com.ty.highway.highwaysystem.support.net.BaseNetAsyncTask;
import com.ty.highway.highwaysystem.support.net.ftp.FTP;
import com.ty.highway.highwaysystem.support.utils.CommonUtils;
import com.ty.highway.highwaysystem.support.utils.PreferencesUtils;
import com.ty.highway.highwaysystem.support.utils.SnackbarUtils;
import com.ty.highway.highwaysystem.support.utils.ToastUtils;
import com.ty.highway.highwaysystem.support.utils.exception.ExceptionUtils;
import com.ty.highway.highwaysystem.ui.activity.machine.MachineDamageListActivity;
import com.ty.highway.highwaysystem.ui.activity.machine.MachineTaskListActivity;
import com.ty.highway.highwaysystem.ui.dialog.BaseDialog;

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
public class MachineTaskItemOperateDialog extends BaseDialog implements View.OnClickListener {
    private Button mButDetail, mButUpload, mButDelate, mButCheck, mButCancel;
    private Context mContext;
    private ELMachineTaskBean mTaskInfoBean;
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
    private String mDocument;
    public MachineTaskItemOperateDialog(Context context, ELMachineTaskBean infoBean, BaseCallBack callBack) {
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


    @Override
    public void onClick(View view) {
        hide();
        int id = view.getId();
        Intent intent = null;
        switch (id) {
            case R.id.dialog_taskitem_operate_check:
                if (mTaskInfoBean.getUpdateState() == Constants.TASK_STATE_NOSTART) {
                    MachineEnterCheckDialog dialog = new MachineEnterCheckDialog(getContext(), mTaskInfoBean);
                    dialog.show();
                }else if(mTaskInfoBean.getUpdateState() == Constants.TASK_STATE_HASLOADING){
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
                                            DBELMachineTaskDao.getInstance(getContext()).setState(mTaskInfoBean.getNewId(), Constants.TASK_STATE_WORKING,PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                                            Intent intent = new Intent(getContext(), MachineDamageListActivity.class);
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
                }else{
                    intent = new Intent(getContext(), MachineDamageListActivity.class);
                    intent.putExtra("info", mTaskInfoBean);
                    intent.putExtra("taskId", mTaskInfoBean.getNewId());
                    getContext().startActivity(intent);
                }
                break;
            case R.id.dialog_taskitem_operate_upload:
                if(MachineTaskListActivity.isUpload){
                    ToastUtils.show(mContext, "任务正在上传！");
                    return;
                }
                if (mTaskInfoBean.getUpdateState() == Constants.TASK_STATE_HASLOADING) {
                    ToastUtils.show(mContext, "任务已经上传");

                } else if (mTaskInfoBean.getUpdateState() == Constants.TASK_STATE_WORKING ||
                        mTaskInfoBean.getUpdateState() == Constants.TASK_STATE_NOSTART) {
                    ToastUtils.show(mContext, "任务未完成，不能上传");
                } else {
                    if(CommonUtils.isNetworkConnected(mContext)){
                        showSpotsDialog("正在检查任务能否上传...",false);
                        HashMap<String, String> map = new HashMap<String, String>();
                        HashMap<String, String> mapName = new HashMap<String, String>();
                        HashMap<String, String> mapType = new HashMap<String, String>();
                        map.put("key", PreferencesUtils.getString(mContext, Constants.SP_LOGIN_KEY));
                        map.put("checkNo", mTaskInfoBean.getCheckNo());
                        mapName.put("methodName", Constants.METHOD_GETMACHINETASKISREPORT);
                        mapType.put("urlType", Constants.SERVICEURL_TYPE_MACHINECHECK);
                        BaseNetAsyncTask asyncTask = new BaseNetAsyncTask(new NetResquestListener() {
                            @Override
                            public void onSuccess(String response) {
                                hideSpotsDialog();
                                if (response != null) {
                                    if (response.contains("\"r\":\"ok")) {
                                        BaseResultBean bean = new Gson().fromJson(response, BaseResultBean.class);
                                        if ("true".equals(bean.getS())){
                                            MachineTaskListActivity.isUpload=true;
                                            uploadFile();
                                        } else {
                                            ToastUtils.show(mContext, "检查任务数据不能上传");
                                        }
                                    }else {
                                        ToastUtils.show(mContext, "检查任务数据不能上传");
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
                break;
            case R.id.dialog_taskitem_operate_delete:
                final CheckBox checkBox = new CheckBox(view.getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.leftMargin = 20;
                LinearLayout linearLayout = new LinearLayout(mContext);
                checkBox.setTextColor(mContext.getResources().getColor(R.color.common_theme_color));
                checkBox.setText("删除病害信息");
                checkBox.setTextSize(15);
                linearLayout.addView(checkBox,params);
                AlertDialog.Builder build = new AlertDialog.Builder(mContext);
                build.setTitle("注意")
                        .setView(linearLayout)
                        .setMessage("确定要删除该任务吗？")
                        .setPositiveButton("确定",
                                new OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        if(checkBox.isChecked()){
                                            DBELMachineDiseaseDao.getInstance(mContext).clearDataByTaskId(mTaskInfoBean.getNewId(), PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                                            DBELMachineDiseasePhotoDao.getInstance(mContext).clearDataByTaskId(mTaskInfoBean.getNewId());
                                        }
                                        DBELMachineTaskDao.getInstance(mContext).deleteTaskInfoByNo(mTaskInfoBean.getCheckNo(), PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                                        mCallBack.callBack(true);
                                    }
                                })
                        .setNegativeButton("取消",
                                new OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {

                                    }
                                }).show();
                break;
            case R.id.dialog_taskitem_operate_cancel:

                break;
            case R.id.dialog_taskitem_operate_details:
                MachineTaskDetailDialog detailDialog = new MachineTaskDetailDialog(mContext,mTaskInfoBean);
                detailDialog.show();
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
        List<ELMachineDiseasePhotoBean> diseasePhotoBeans = DBELMachineDiseasePhotoDao.getInstance(mContext).getAllByTaskId(mTaskInfoBean.getNewId());
        LinkedList<File> files = new LinkedList<>();
        for (ELMachineDiseasePhotoBean diseasePhotoBean : diseasePhotoBeans) {
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
            showProgressDialog("正在上传机电检修图片和视频(共"+fileCounts+"个文件)...",false);
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
                                        DBELMachineDiseasePhotoDao.getInstance(mContext).updatePhotoInfo(file.getPath(), 1,fileS);
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

    /**
     * 上传任务检查数据
     */
    public void uploadTaskData(){
        showSpotsDialog("正在上传数据...", false);
        MachineCheckRechodBean  checkRechodBean = new MachineCheckRechodBean();
        checkRechodBean.setCheckNo(mTaskInfoBean.getCheckNo());
        checkRechodBean.setTunnelId(mTaskInfoBean.getTunnelId());
        checkRechodBean.setCheckEmp(mTaskInfoBean.getCheckEmp());
        checkRechodBean.setCheckDate(mTaskInfoBean.getCheckDate());
        checkRechodBean.setCheckWeather(mTaskInfoBean.getCheckWeather());
        checkRechodBean.setCheckWayId(mTaskInfoBean.getCheckWayId());
        checkRechodBean.setRemark(mTaskInfoBean.getRemark());
        checkRechodBean.setRecordEmp(mTaskInfoBean.getRecordEmp());
        checkRechodBean.setCreateDate(mTaskInfoBean.getCreateDate());
        checkRechodBean.setAuditCount(mTaskInfoBean.getAuditCount());
        checkRechodBean.setMaintenanceOrgan(mTaskInfoBean.getMaintenanceOrgan());
        checkRechodBean.setMCheckMachineDevice(DBELMachineDiseaseDao.getInstance(mContext).getMachineDeviceByTaskId(checkRechodBean,
                mTaskInfoBean.getNewId(),mTaskInfoBean.getTunnelId(),PreferencesUtils.getString(mContext, Constants.SP_USER_ID)));
        String json = new Gson().toJson(checkRechodBean);
        String jsonResult = "[" + json + "]";
        HashMap<String, String> map = new HashMap<String, String>();
        HashMap<String, String> mapName = new HashMap<String, String>();
        HashMap<String, String> mapType = new HashMap<String, String>();
        map.put("key", PreferencesUtils.getString(mContext, Constants.SP_LOGIN_KEY));
        map.put("json", jsonResult);
        mapName.put("methodName", Constants.METHOD_SAVEMACHINELETTER);
        mapType.put("urlType", Constants.SERVICEURL_TYPE_MACHINECHECK);
        BaseNetAsyncTask asyncTask = new BaseNetAsyncTask(new NetResquestListener() {
            @Override
            public void onSuccess(String response) {
                hideSpotsDialog();
                if (response != null) {
                    if (response.contains("\"r\":\"ok")) {
                        BaseResultBean bean = new Gson().fromJson(response, BaseResultBean.class);
                        ToastUtils.show(mContext, bean.getS());
                        DBELMachineTaskDao.getInstance(mContext).setState(mTaskInfoBean.getNewId(), Constants.TASK_STATE_HASLOADING, PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                        mCallBack.callBack(true);
                        MachineTaskListActivity.isUpload=false;
                    }else{
                        BaseResultBean bean = new Gson().fromJson(response, BaseResultBean.class);
                        ToastUtils.show(mContext, bean.getS());
                        MachineTaskListActivity.isUpload=false;
                    }
                }
            }

            @Override
            public void onError(String errormsg) {
                hideSpotsDialog();
                ToastUtils.show(mContext, errormsg);
                MachineTaskListActivity.isUpload=false;
            }
        },mContext);
        asyncTask.execute(map, mapName, mapType);
    }
}
