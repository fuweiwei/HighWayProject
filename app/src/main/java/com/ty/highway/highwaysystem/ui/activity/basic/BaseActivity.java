/**
 * Copyright (c) 2012-2013, Michael Yang.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ty.highway.highwaysystem.ui.activity.basic;


import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;

import com.google.gson.Gson;
import com.ty.highway.highwaysystem.R;
import com.ty.highway.highwaysystem.base.Constants;
import com.ty.highway.highwaysystem.base.HWApplication;
import com.ty.highway.highwaysystem.support.bean.basic.AppVersionResultBean;
import com.ty.highway.highwaysystem.support.listener.NetResquestListener;
import com.ty.highway.highwaysystem.support.net.BaseNetAsyncTask;
import com.ty.highway.highwaysystem.support.utils.CommonUtils;
import com.ty.highway.highwaysystem.support.utils.SnackbarUtils;
import com.ty.highway.highwaysystem.support.utils.ToastUtils;
import com.ty.highway.highwaysystem.support.utils.exception.ExceptionUtils;
import com.ty.highway.highwaysystem.system.service.AppDownloadService;
import com.ty.highway.highwaysystem.ui.dialog.TaskProgressDialog;
import com.ty.highway.highwaysystem.ui.widget.MyTitle;
import com.ty.highway.highwaysystem.ui.widget.sportsdialog.SpotsDialog;

import java.util.HashMap;

//基类Activtiy 所以Activty都继承它
public class BaseActivity extends FragmentActivity {

	//下载进度提示框
	protected TaskProgressDialog mProrgessDialog;
	//缓冲提示框
	protected SpotsDialog mSpotsDialog;
	private Context mContext = this;
	//标题栏
	private MyTitle mTitleBar;

	protected static final int UPDATE_DIALOGPROGRESS = 0x001;
  //应用更新
	protected boolean isAppBinded;
	protected boolean isSettingUpdate ;
	protected AppDownloadService.DownloadBinder mDownloadBinder;
	protected String mApkUrl;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		HWApplication.addActivity(this);
	/*	CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(this);*/

	}
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		initView();

	}
	private ServiceConnection mDownloadConn = new ServiceConnection() {
		@Override
		public void onServiceDisconnected(ComponentName name) {
			isAppBinded = false;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder iBinder) {
			mDownloadBinder = (AppDownloadService.DownloadBinder) iBinder;
			// 开始下载
			isAppBinded = true;
			mDownloadBinder.setUrl(mApkUrl);
			mDownloadBinder.start();

		}
	};
	public void updateVersion(){
		if(CommonUtils.isNetworkConnected(mContext)){
			showSpotsDialog("正在检查更新...",false);
			HashMap<String, String> map = new HashMap<String, String>();
			HashMap<String, String> mapName = new HashMap<String, String>();
			HashMap<String, String> mapType = new HashMap<String, String>();
			map.put("key", Constants.KEY);
			mapName.put("methodName",Constants.METHOD_LOADAPPVERSION);
			mapType.put("urlType",Constants.SERVICEURL_TYPE_BASEINFO);
			BaseNetAsyncTask asyncTask = new BaseNetAsyncTask(new NetResquestListener() {
				@Override
				public void onSuccess(String response) {
					if(response!=null){
						if (response.contains("\"r\":\"ok")) {
							AppVersionResultBean bean = new Gson().fromJson(response,AppVersionResultBean.class);
							AppVersionResultBean.AppVersionBean info = bean.getS();
							int versionCode = Integer.parseInt(info.getAppVersion());
							if(versionCode>CommonUtils.getVersionCode(mContext)){
								mApkUrl = info.getAppPath();
								AlertDialog.Builder build = new AlertDialog.Builder(mContext);
								build.setTitle("注意")
										.setMessage("发现新版本，是否要更新？")
										.setPositiveButton("确定",
												new DialogInterface.OnClickListener() {

													@Override
													public void onClick(DialogInterface dialog,
																		int which) {
														Intent it = new Intent(mContext, AppDownloadService.class);
														bindService(it, mDownloadConn, Context.BIND_AUTO_CREATE);
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
								if(isSettingUpdate){
									isSettingUpdate =false;
									ToastUtils.show(mContext, "未发现新版本");
								}
							}
						}else{
						}
						hideSpotsDialog();
					}
				}

				@Override
				public void onError(String errormsg) {
					hideSpotsDialog();
				}
			},mContext);
			asyncTask.execute(map,mapName,mapType);
		}else{
			SnackbarUtils.show(mContext, "没有网络连接");
		}
	}
	/**
	 *
	 * @Title: showProgress
	//	 * @Description: TODO(显示进度对话框)
	 */
	public TaskProgressDialog showProgressDialog(String str, boolean iscan){
		try {
			mProrgessDialog = new TaskProgressDialog(this);
			mProrgessDialog.show();
			mProrgessDialog.setContent(str);
			mProrgessDialog.setCancelable(true);
			mProrgessDialog.setCanceledOnTouchOutside(iscan);
		} catch (Exception e) {
			new ExceptionUtils().doExecInfo(e.toString(), mContext);
		}
		return mProrgessDialog;

	}

	public void setDialogProgress(int progress){
		mProrgessDialog.setProgress(progress);
	}
	/**
	 *
	 * @Title: showProgress
	//	 * @Description: TODO(显示进度对话框)
	 */
	public TaskProgressDialog showProgressDialog(){
		try {
			mProrgessDialog = new TaskProgressDialog(this);
			mProrgessDialog.show();
			mProrgessDialog.setContent("正在加载...");
			mProrgessDialog.setCancelable(true);
			mProrgessDialog.setCanceledOnTouchOutside(false);
		} catch (Exception e) {
			new ExceptionUtils().doExecInfo(e.toString(), mContext);
		}
		return mProrgessDialog;

	}

	/**
	 * 隐藏进度对话框
	 */
	public void  hideProgressDialog(){
		if (mProrgessDialog != null && mProrgessDialog.isShowing()) {
			mProrgessDialog.dismiss();
		}
	}

	/**
	 * 显示缓冲框
	 * @param str 弹出框内容
	 * @param iscan 点击外边是否取消弹出框
	 * @return
	 */
	public SpotsDialog showSpotsDialog(String str,Boolean iscan){
		try {
			mSpotsDialog = new SpotsDialog(this);
			mSpotsDialog.show();
			mSpotsDialog.setContent(str);
			mSpotsDialog.setCancelable(true);
			mSpotsDialog.setCanceledOnTouchOutside(iscan);
		} catch (Exception e) {
			new ExceptionUtils().doExecInfo(e.toString(), mContext);
		}

		return mSpotsDialog;
	}
	/**
	 * 显示缓冲框
	 * @return
	 */
	public SpotsDialog showSpotsDialog(){
		try {
			mSpotsDialog = new SpotsDialog(this);
			mSpotsDialog.show();
			mSpotsDialog.setContent("正在加载");
			mSpotsDialog.setCancelable(true);
			mSpotsDialog.setCanceledOnTouchOutside(false);
		} catch (Exception e) {
			new ExceptionUtils().doExecInfo(e.toString(), mContext);
		}

		return mSpotsDialog;
	}

	/**
	 * 隐藏缓冲对话框
	 */
	public void  hideSpotsDialog(){
		if (mSpotsDialog != null && mSpotsDialog.isShowing()) {
			mSpotsDialog.dismiss();
		}
	}
	public void setContentView(View view, LayoutParams params) {
		super.setContentView(view, params);
		initView();
	}

	public void setContentView(View view) {
		super.setContentView(view);
		initView();
	}
	private void initView(){
		mTitleBar = (MyTitle) findViewById(R.id.titlebar);
	}

	/**
	 * 设置右边按钮是否显示
	 * @param visiable
	 */
	public void setRTBtnVisiable(int visiable) {
		if (mTitleBar != null) {
			mTitleBar.setRTBtnVisiable(visiable);
		}

	}
	/**
	 * 设置右边按钮文字
	 * @param text
	 */
	public void setRTBtnText(String text) {
		if (mTitleBar != null) {
			mTitleBar.setRTBtnText(text);
		}
	}
	/**
	 * 设置左边按钮是否显示
	 * @param visiable
	 */
	public void setLTBtnVisiable(int visiable) {
		if (mTitleBar != null) {
			mTitleBar.setLTBtnVisiable(visiable);
		}

	}
	/**
	 * 设置左边按钮文字
	 * @param text
	 */
	public void setLTBtnText(String text) {
		if (mTitleBar != null) {
			mTitleBar.setLTBtnText(text);
		}
	}

	/**
	 * 设置标题是否显示
	 * @param visiable
	 */
	public void setTitleVisiable(int visiable) {
		if (mTitleBar != null) {
			mTitleBar.setTitTvVisiable(visiable);
		}

	}
	/**
	 * 设置标题文字
	 * @param text
	 */
	public void setTitleText(String text) {
		if (mTitleBar != null) {
			mTitleBar.setTitTvText(text);
		}
	}
	public void onRtBtnClick() {
		// titlebar右button的click事件
	}
	public void onLtBtnClick() {
		// titlebar左button的click事件
	}

	public void onCenterClick() {
		// titlebar中间的title点击事件

	}

	private MHandler handler = new MHandler();

	protected void handleOtherMessage(int flag) {
		switch (flag) {
		}
	}

	public void sendMessage(int flag) {
		handler.sendEmptyMessage(flag);
	}

	public void sendMessage(int flag, int progress) {
		Message message = new Message();
		message.obj = progress;
		message.what = flag;
		handler.sendMessage(message);
	}

	public void sendMessageDely(int flag, long delayMillis) {
		handler.sendEmptyMessageDelayed(flag, delayMillis);
	}



	public interface ICallbackResult {
		public void OnBackResult(Object result);
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		HWApplication.closeSingleActivity(this.getClass());
		if (isAppBinded) {
			unbindService(mDownloadConn);
		}
	}

	private class MHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			if (!Thread.currentThread().isInterrupted()) {
				switch (msg.what) {
					case UPDATE_DIALOGPROGRESS:
						mProrgessDialog.setProgress((Integer)msg.obj);
					default:
						handleOtherMessage(msg.what);
				}
			}
		}

	}

	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0  ) {
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
