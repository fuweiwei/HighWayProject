/**
 *
 */
package com.ty.highway.highwaysystem.ui.activity.basic;


import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ty.highway.highwaysystem.R;
import com.ty.highway.highwaysystem.base.Constants;
import com.ty.highway.highwaysystem.support.db.basedata.DBBTableUpdateDao;
import com.ty.highway.highwaysystem.support.db.basedata.DBCTDictionaryDao;
import com.ty.highway.highwaysystem.support.db.check.DBCTDiseaseInfoDao;
import com.ty.highway.highwaysystem.support.db.check.DBCTDiseasePhotoDao;
import com.ty.highway.highwaysystem.support.db.check.DBCTHistroyDiseaseInfoDao;
import com.ty.highway.highwaysystem.support.db.check.DBCTNearTaskInfoDao;
import com.ty.highway.highwaysystem.support.db.check.DBTaskDao;
import com.ty.highway.highwaysystem.support.db.machine.DBELMachineDiseaseDao;
import com.ty.highway.highwaysystem.support.db.machine.DBELMachineDiseasePhotoDao;
import com.ty.highway.highwaysystem.support.db.machine.DBELMachineTaskDao;
import com.ty.highway.highwaysystem.support.utils.CommonUtils;
import com.ty.highway.highwaysystem.support.utils.PreferencesUtils;
import com.ty.highway.highwaysystem.support.utils.ToastUtils;
import com.ty.highway.highwaysystem.system.callback.BaseDataCallBack;
import com.ty.highway.highwaysystem.system.service.HistoryDamageService;


/**
 * @ClassName: SettingActivity
 * @Description: TODO(设置对话框)
 * @author fuweiwei
 * @date 2015-12-28 下午5:24:27
 *
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener{
	private Button mButIp,mButUpadte,mButFix,mButOpten,mButAbout,mButCancel;
	private Context mContext = SettingActivity.this ;
	private  boolean isUpdateFix,isUpdateOften;
	private HistoryDamageService.HistroyDamageBind mHistroyBind;
	private boolean isBinder ;
	private String mCheckWayId;
	private int mCheckId;
	private String mUserId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		initTitle();
		mButIp = (Button) findViewById(R.id.dialog_setting_ip);
		mButUpadte = (Button) findViewById(R.id.dialog_setting_update);
		mButFix = (Button) findViewById(R.id.dialog_setting_fix);
		mButOpten = (Button) findViewById(R.id.dialog_setting_often);
		mButAbout = (Button) findViewById(R.id.dialog_setting_about);
		mButIp.setOnClickListener(this);
		mButUpadte.setOnClickListener(this);
		mButFix.setOnClickListener(this);
		mButOpten.setOnClickListener(this);
		mButAbout.setOnClickListener(this);
	}
	public void initTitle(){
		setLTBtnText("返回");
		setLTBtnVisiable(View.VISIBLE);
		setRTBtnVisiable(View.INVISIBLE);
		setTitleText("更多");
		setTitleVisiable(View.VISIBLE);
	}
	private BaseDataCallBack mCallBack = new BaseDataCallBack() {
		@Override
		public void onResult(int count, String s) {
			mProrgessDialog.setProgress(count);
			mProrgessDialog.setContent(s);
			if(count==100){
				hideProgressDialog();
				ToastUtils.show(mContext, "更新完毕");
				if(isBinder){
					isBinder =false;
					mContext.unbindService(mHistroyConn);
				}
				onResume();
			}
		}

		@Override
		public void onError(int count) {

		}
	};
	private ServiceConnection mHistroyConn = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mHistroyBind = (HistoryDamageService.HistroyDamageBind) service;
			mHistroyBind.setCallBack(mCallBack);
			mHistroyBind.downloadData(mCheckWayId,mCheckId,mUserId);
			isBinder =true;
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			isBinder = false;
		}
	};
	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id){
			case R.id.dialog_setting_ip:
				final EditText et = new EditText(mContext);
				et.setText(PreferencesUtils.getString(mContext, Constants.SP_WEBURL,Constants.WEBURL));
				AlertDialog.Builder build = new AlertDialog.Builder(mContext);
				build.setTitle("设置网络请求地址：")
						.setView(et)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
														int which) {
										if(TextUtils.isEmpty(et.getText().toString().trim())){
											ToastUtils.show(mContext, "请输入内容");
										}else{
											DBTaskDao.getInstance(mContext).clearData();
											DBCTNearTaskInfoDao.getInstance(mContext).clearData();
											DBCTDiseaseInfoDao.getInstance(mContext).clearData();
											DBCTHistroyDiseaseInfoDao.getInstance(mContext).clearData();
											DBCTDiseasePhotoDao.getInstance(mContext).clearData();
											DBBTableUpdateDao.getInstance(mContext).clearData();
											DBELMachineDiseaseDao.getInstance(mContext).clearData();
											DBELMachineDiseasePhotoDao.getInstance(mContext).clearData();
											DBELMachineTaskDao.getInstance(mContext).clearData();
											PreferencesUtils.putString(mContext, Constants.SP_WEBURL, et.getText().toString());
											mButIp.setText("IP地址：" + et.getText().toString());
											ToastUtils.show(mContext, "设置成功");
										}
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
			case  R.id.dialog_setting_update:
				isSettingUpdate = true;
				updateVersion();
				break;
			case  R.id.dialog_setting_fix:
				if(!CommonUtils.isNetworkConnected(mContext)){
					ToastUtils.show(mContext,"没有网络连接");
					return;
				}
				mUserId =PreferencesUtils.getString(mContext,Constants.SP_USER_ID);
				if(mUserId==null){
					ToastUtils.show(mContext,"请先登录系统");
					return;
				}
				if(!isUpdateFix){
					ToastUtils.show(mContext,"病害库暂无更新");
					return;
				}
				mCheckWayId = DBCTDictionaryDao.getInstance(mContext).getIDBySort(Constants.CHECK_TYPE_REGULAR);
				mCheckId =  Constants.CHECK_TYPE_REGULAR;
				showProgressDialog("正在更新定期检查病害库...", false) ;
				Intent intent = new Intent(mContext,HistoryDamageService.class);
				mContext.bindService(intent, mHistroyConn, Context.BIND_AUTO_CREATE);
				break;
			case  R.id.dialog_setting_often:
				if(!CommonUtils.isNetworkConnected(mContext)){
					ToastUtils.show(mContext,"没有网络连接");
					return;
				}
				mUserId =PreferencesUtils.getString(mContext,Constants.SP_USER_ID);
				if(mUserId==null){
					ToastUtils.show(mContext,"请先登录系统");
					return;
				}
				if(!isUpdateOften){
					ToastUtils.show(mContext,"病害库暂无更新");
					return;
				}
				mCheckWayId = DBCTDictionaryDao.getInstance(mContext).getIDBySort(Constants.CHECK_TYPE_OFTEN);
				mCheckId =  Constants.CHECK_TYPE_OFTEN;
				showProgressDialog("正在更新定期检查病害库...", false) ;
				Intent intent2 = new Intent(mContext,HistoryDamageService.class);
				mContext.bindService(intent2, mHistroyConn, Context.BIND_AUTO_CREATE);
				break;
			case  R.id.dialog_setting_about:
				final TextView textView = new TextView(mContext);
				textView.setText("上海同岩土木工程科技有限公司");
				textView.setTextSize(20);
				textView.setTextColor(Color.BLACK);
				textView.setPadding(10,20,0,0);
				AlertDialog.Builder build2 = new AlertDialog.Builder(mContext);
				build2.setTitle("关于")
						.setView(textView)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
														int which) {
									}
								}).show();
				break;
			case  R.id.btn_back:
				finish();
				break;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		mButIp.setText("IP地址：" + PreferencesUtils.getString(mContext, Constants.SP_WEBURL, Constants.WEBURL));
		mButUpadte.setText("当前版本：v"+ CommonUtils.getVersionName(mContext));
		isUpdateFix = CommonUtils.IsBefore(DBBTableUpdateDao.getInstance(mContext).getTime("histroyTimeFix"), PreferencesUtils.getString(mContext,Constants.SP_HOSTROY_TIME));
		isUpdateOften = CommonUtils.IsBefore(DBBTableUpdateDao.getInstance(mContext).getTime("histroyTimeOften"), PreferencesUtils.getString(mContext,Constants.SP_HOSTROY_TIME));
		if(isUpdateFix){
			mButFix.setText("定期检查病害库：有更新");
		}else{
			mButFix.setText("定期检查病害库：无更新");
		}
		if(isUpdateOften){
			mButOpten.setText("经常检查病害库：有更新");
		}else{
			mButOpten.setText("经常检查病害库：无更新");
		}

	}


	@Override
	public void onLtBtnClick() {
		super.onLtBtnClick();
		finish();
	}

}
