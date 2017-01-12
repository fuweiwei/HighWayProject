package com.ty.highway.highwaysystem.support.olddata;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.ty.highway.highwaysystem.R;
import com.ty.highway.highwaysystem.base.Constants;
import com.ty.highway.highwaysystem.support.bean.basic.CTUserBean;
import com.ty.highway.highwaysystem.support.db.basic.DBCTUserDao;
import com.ty.highway.highwaysystem.support.listener.NetResquestListener;
import com.ty.highway.highwaysystem.support.net.BaseNetAsyncTask;
import com.ty.highway.highwaysystem.support.net.ftp.FTP;
import com.ty.highway.highwaysystem.support.utils.CommonUtils;
import com.ty.highway.highwaysystem.support.utils.PreferencesUtils;
import com.ty.highway.highwaysystem.support.utils.ToastUtils;
import com.ty.highway.highwaysystem.support.utils.exception.ExceptionUtils;
import com.ty.highway.highwaysystem.ui.activity.basic.BaseActivity;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by fuweiwei on 2015/10/16.
 */
public class UploadOldDataActivity extends BaseActivity {
    private Button mBtnUpdata,mBtnUppic;
    private int mDataCount,mPiccount;
    private int mDataNum,mPicNum;
    private Context mContext = UploadOldDataActivity.this;
    private final String TAG = "UploadOldDataActivity";
    private int successCount = 0;//文件上传成功个数
    private int failPicCount = 0;//图片失败个数
    private int failDataCount = 0;//数据失败个数
    private int currentPeogress = 0;//当前上传进度
    private final int UPLOAD_NOTNET = 0x00001;//是否有网
    private final int UPLOAD_RESULT = 0x00002;//上传结果
    private final int UPLOAD_PROGRESS = 0x00003;//当前进度
    private  boolean isUpdata= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_olddata);
        mBtnUpdata = (Button) findViewById(R.id.btn_uploaddata);
        mBtnUppic = (Button) findViewById(R.id.btn_uploadpic);
        mBtnUpdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSpotsDialog("上传数据中...",false);
                List<UploadBean> list = DBServiceOld.getInstance(UploadOldDataActivity.this).getUploadList();
                mDataNum = list.size();
                for (UploadBean bean : list) {
                    String content = new Gson().toJson(bean);
                    HashMap<String, String> map = new HashMap<String, String>();
                    HashMap<String, String> mapName = new HashMap<String, String>();
                    HashMap<String, String> mapType = new HashMap<String, String>();
                    map.put("content", content);
                    mapName.put("methodName", Constants.METHOD_CHECKCOTENT);
                    mapType.put("urlType", Constants.SERVICEURL_TYPE_CHECKCONTENTFORTEMPORARY);
                    BaseNetAsyncTask asyncTask = new BaseNetAsyncTask(new NetResquestListener() {
                        @Override
                        public void onSuccess(String response) {
                            if (response.contains("\"r\":\"ok")){
                                ++mDataCount;
                                if(mDataCount==mDataNum){
                                    isUpdata =true;
                                    hideSpotsDialog();
                                    Log.v(TAG, "数据失败个数："+failDataCount);
                                }
                            }
                        }

                        @Override
                        public void onError(String errormsg) {
                            ++failDataCount;
                            ++mDataCount;
                        }
                    }, UploadOldDataActivity.this);
                    asyncTask.execute(map, mapName, mapType);
                }
            }
        });
        mBtnUppic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(true){
                    LinkedList<File> files = DBServiceOld.getInstance(mContext).getUploadPicInfos();
                    mPicNum = files.size();
                    showProgressDialog("正在上传图片(共"+mPicNum+"张)...",false);
                    upload(files, "Document/old/TJJC-08");
                  //  upload(new File(mapList.get(1).get("file")),mapList.get(1).get("document"));
                }else{
                    ToastUtils.show(mContext, "数据暂未上传完毕，不能上传图片");
                }
            }
        });
    }
    /**
     * 上传图片和视屏文件
     */
    private void upload( final LinkedList<File> files, final String documentFile) {
        final CTUserBean userBean = DBCTUserDao.getInstance(mContext).getPUserInfo(PreferencesUtils.getString(mContext, Constants.SP_USER_NAME));
        final String ftpIp = userBean.getFtpIp().substring(userBean.getFtpIp().lastIndexOf("/") + 1, userBean.getFtpIp().lastIndexOf(":"));
        final int serverPort = Integer.parseInt(userBean.getFtpIp().substring(userBean.getFtpIp().lastIndexOf(":") + 1));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    new FTP(ftpIp, serverPort, userBean.getFtpUser(), userBean.getFtpPwd()).uploadMultiFile(files, documentFile, new FTP.UploadProgressListener() {
                        @Override
                        public void onUploadProgress(String currentStep, long uploadSize, File file) {
                            switch (currentStep) {
                                case FTP.FTP_UPLOAD_SUCCESS:
                                    successCount++;
                                    Log.v(TAG, currentStep);
                                    Log.v(TAG, (successCount) + "个文件上传成功");
                                    Log.v(TAG, file.getPath());
                                    currentPeogress = (successCount * 100) / mPicNum;
                                    DBServiceOld.getInstance(mContext).setPicState(file.toString());
                                    handler.sendEmptyMessage(UPLOAD_RESULT);
                                    break;
                                case FTP.FTP_UPLOAD_LOADING:
                                    if (CommonUtils.isNetworkConnected(mContext)) {
                                        handler.sendEmptyMessage(UPLOAD_PROGRESS);
                                    } else {
                                        Log.v(TAG, "-----网络断开连接-------------------------");
                                        handler.sendEmptyMessage(UPLOAD_NOTNET);
                                        Thread.interrupted();
                                    }
                                    break;
                                case FTP.FTP_UPLOAD_FAIL:
                                    successCount++;
                                    failPicCount++;
                                    Log.v(TAG, currentStep);
                                    Log.v(TAG, (failPicCount) + "个文件上传失败");
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
                    handler.sendEmptyMessage(UPLOAD_RESULT);
                } catch (IOException e) {
                    new ExceptionUtils().doExecInfo(e.toString(), mContext);
                }
            }
        }).start();

    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPLOAD_RESULT:
                    mProrgessDialog.setProgress(currentPeogress);
                    mProrgessDialog.setContent("正在上传图片(共"+mPicNum+"张,已上传"+successCount+"张)...");
                    if (currentPeogress == 100 ) {
                        mProrgessDialog.hide();
                        Log.v(TAG, (failPicCount) + "个文件上传失败");
                    }
                    break;
                case UPLOAD_PROGRESS:
                    break;
                case UPLOAD_NOTNET:
                    ToastUtils.show(mContext, "网络不稳定或者断开连接,请检查网络");
                    mProrgessDialog.hide();
                    break;
            }
        }
    };
}
