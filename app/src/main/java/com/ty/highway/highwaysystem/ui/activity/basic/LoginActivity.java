package com.ty.highway.highwaysystem.ui.activity.basic;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ty.highway.highwaysystem.R;
import com.ty.highway.highwaysystem.base.Constants;
import com.ty.highway.highwaysystem.base.HWApplication;
import com.ty.highway.highwaysystem.support.bean.basic.BaseResultBean;
import com.ty.highway.highwaysystem.support.bean.basic.CTUserBean;
import com.ty.highway.highwaysystem.support.bean.basic.MenuBean;
import com.ty.highway.highwaysystem.support.bean.basic.MenuChildrenBean;
import com.ty.highway.highwaysystem.support.bean.basic.MenuChildrenResultBean;
import com.ty.highway.highwaysystem.support.bean.basic.PAccountBean;
import com.ty.highway.highwaysystem.support.bean.basic.PUserResultBean;
import com.ty.highway.highwaysystem.support.db.basedata.DBBTableUpdateDao;
import com.ty.highway.highwaysystem.support.db.basic.DBCTUserDao;
import com.ty.highway.highwaysystem.support.db.basic.DBPAccountDao;
import com.ty.highway.highwaysystem.support.db.machine.DBELMenuDao;
import com.ty.highway.highwaysystem.support.listener.BaseCallBack;
import com.ty.highway.highwaysystem.support.listener.NetResquestListener;
import com.ty.highway.highwaysystem.support.net.BaseNetAsyncTask;
import com.ty.highway.highwaysystem.support.utils.CommonUtils;
import com.ty.highway.highwaysystem.support.utils.FileUtils;
import com.ty.highway.highwaysystem.support.utils.PreferencesUtils;
import com.ty.highway.highwaysystem.support.utils.SnackbarUtils;
import com.ty.highway.highwaysystem.support.utils.ToastUtils;
import com.ty.highway.highwaysystem.support.utils.exception.ExceptionUtils;
import com.ty.highway.highwaysystem.system.callback.BaseDataCallBack;
import com.ty.highway.highwaysystem.system.service.BaseDataMachineService;
import com.ty.highway.highwaysystem.system.service.BaseDataService;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by fuweiwei on 2015/9/2.
 *  登录界面
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private Button mRvLogin;
    private CheckBox mCbSavePw;
    private TextInputLayout mTnlUsername,mTnlPassword;
    private BaseDataService.BaseDataBinder mBaseDataBinder;
    private BaseDataMachineService.BaseDataBinder mBaseDataManchineBinder;
    private Boolean isSavePassword =false;
    private Context mContext = LoginActivity.this;
    private boolean isVerification = false;//认证设备编号
    private boolean isDataBinded,isDataMachineBinded;


    private TextView mTvSetting;
    private TextView mDeviceVerificationNo;

    private BaseCallBack mUpdateCallBack;
    private ExecutorService mExecutorService = Executors.newCachedThreadPool();
    /*菜单图片存储位置*/
    private static final String savePath = Environment.getExternalStorageDirectory().getPath()+Constants.SD_MENUIMG_PATH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        try {
            new ExceptionUtils().upAndDelFile(mContext, PreferencesUtils.getString(mContext, Constants.SP_USER_NAME,""));
        } catch (Exception e) {
            e.printStackTrace();
        }
//        updateVersion();
    }
    public  void initView(){
        mDeviceVerificationNo = (TextView) findViewById(R.id.txtDeviceNo);
        mTnlUsername = (TextInputLayout)findViewById(R.id.textInput_name);
        mTnlPassword = (TextInputLayout)findViewById(R.id.textInput_password);
        mTvSetting = (TextView)findViewById(R.id.activity_login_setting);
        mRvLogin = (Button) findViewById(R.id.login_btn);
        mCbSavePw = (CheckBox) findViewById(R.id.login_checkbox);
        mTnlUsername.setHint("账号:");
        mTnlPassword.setHint("密码:");
        mTvSetting.setOnClickListener(this);
        mRvLogin.setOnClickListener(this);
        mCbSavePw.setOnClickListener(this);
        isSavePassword = PreferencesUtils.getBoolean(mContext,Constants.SP_IS_SAVEPW,false);
        isVerification = PreferencesUtils.getBoolean(mContext,Constants.SP_IS_VERIFICATION,false);
        if(isSavePassword){
            mCbSavePw.setChecked(true);
            isSavePassword=true;
            mTnlUsername.getEditText().setText(PreferencesUtils.getString(mContext,Constants.SP_USER_NAME));
            mTnlPassword.getEditText().setText(PreferencesUtils.getString(mContext,Constants.SP_USER_PW));
        }else{
            mCbSavePw.setChecked(false);
            isSavePassword=false;

        }
        mTnlUsername.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    mTnlUsername.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    public  void login(){
        final String username = mTnlUsername.getEditText().getText().toString();
        final String userpassword = mTnlPassword.getEditText().getText().toString();
        HashMap<String, String> map = new HashMap<String, String>();
        HashMap<String, String> mapName = new HashMap<String, String>();
        HashMap<String, String> mapType = new HashMap<String, String>();
        map.put("key", Constants.KEY);
        map.put("userAcoucct ", username);
        map.put("userPassword  ", userpassword);
        mapName.put("methodName",Constants.METHOD_LOGIN);
        mapType.put("urlType",Constants.SERVICEURL_TYPE_BASEINFO);
        BaseNetAsyncTask asyncTask = new BaseNetAsyncTask(new NetResquestListener() {
            @Override
            public void onSuccess(String response) {
                hideSpotsDialog();
                if(response!=null){
                    if (response.contains("\"r\":\"ok")){
                        ToastUtils.show(mContext, "登录成功");
                        PUserResultBean resultbean = new Gson().fromJson(response, PUserResultBean.class);
                        CTUserBean bean = resultbean.getS();
                        HWApplication.userBean = bean;
                        DBCTUserDao.getInstance(mContext).addUserInfo(bean);
                        String key = resultbean.getS().getLegalizeKey();
                        //更换用户后清空之前用户的数据
                        if(!username.equals(PreferencesUtils.getString(mContext,Constants.SP_USER_NAME))){
                           /* DBTaskDao.getInstance(mContext).clearData();
                            DBCTNearTaskInfoDao.getInstance(mContext).clearData();
                            DBCTDiseaseInfoDao.getInstance(mContext).clearData();
                            DBCTHistroyDiseaseInfoDao.getInstance(mContext).clearData();
                            DBCTDiseasePhotoDao.getInstance(mContext).clearData();*/
                            DBBTableUpdateDao.getInstance(mContext).deleteTaskInfo(Constants.HISTROY_FIX_TIME);
                            DBBTableUpdateDao.getInstance(mContext).deleteTaskInfo(Constants.HISTROY_OFTEN_TIME);
                        }
                        PreferencesUtils.putString(mContext, Constants.SP_LOGIN_KEY, key);
                        PreferencesUtils.putString(mContext, Constants.SP_USER_ID, bean.getUserID());
                        PreferencesUtils.putString(mContext, Constants.SP_DPT_ID, bean.getDptId());
                        PreferencesUtils.putString(mContext, Constants.SP_USER_NAME, username);
                        PreferencesUtils.putString(mContext, Constants.SP_USER_PW, userpassword);
                        PAccountBean pAccountBean = new PAccountBean();
                        pAccountBean.setIssave(isSavePassword ? "1" : "0");
                        pAccountBean.setName(username);
                        pAccountBean.setPassword(userpassword);
                        DBPAccountDao.getInstance(mContext).addInfo(pAccountBean);
                        doMenu(Constants.KEY,bean.getERoleId());
                    }else {
                        BaseResultBean resultbean = new Gson().fromJson(response, BaseResultBean.class);
                        SnackbarUtils.show(mContext, resultbean.getS());
                    }
                }

            }

            @Override
            public void onError(String errormsg) {
                hideSpotsDialog();
                ToastUtils.show(mContext, "请检查网络和网络请求地址");
            }
        },mContext);
        asyncTask.execute(map,mapName,mapType);
    }
    public  void doMenu(String key,String roleId){
        showSpotsDialog("正在检查权限...", false) ;
        HashMap<String, String> map = new HashMap<String, String>();
        HashMap<String, String> mapName = new HashMap<String, String>();
        HashMap<String, String> mapType = new HashMap<String, String>();
        map.put("key", Constants.KEY);
        map.put("roleId ", roleId);
        mapName.put("methodName",Constants.METHOD_GETMENU);
        mapType.put("urlType",Constants.SERVICEURL_TYPE_BASEINFO);
        BaseNetAsyncTask asyncTask = new BaseNetAsyncTask(new NetResquestListener() {
            @Override
            public void onSuccess(String response) {
                hideSpotsDialog();
                if(response!=null){
                    if (response.contains("\"r\":\"ok")){
                        MenuChildrenResultBean resultbean = new Gson().fromJson(response, MenuChildrenResultBean.class);
                        List<MenuChildrenBean> menuChildrenBeanList = resultbean.getS();
                        DBELMenuDao.getInstance(mContext).clearData();
                        DBELMenuDao.getInstance(mContext).addData(menuChildrenBeanList);
                        mExecutorService.execute(new Runnable() {
                            @Override
                            public void run() {
                                List<MenuBean> list = DBELMenuDao.getInstance(mContext).getAllMenu();
                                for(MenuBean bean:list){
                                    if(bean.getMenuUrl()!=null&&bean.getMenuUrl().startsWith("/")){
                                        String file = savePath+bean.getMenuNo()+".png";
                                        downLoadPic(bean.getMenuUrl(),file);
                                    }
                                }

                            }
                        });
                        if(DBELMenuDao.getInstance(mContext).isHasType(Constants.MENU_TYPE_CHECK)){
                            showProgressDialog("正在更新检查线路信息...", false) ;
                            Intent service = new Intent(mContext, BaseDataService.class);
                            bindService(service, conn, Context.BIND_AUTO_CREATE);
                            return;
                        }
                        if(!DBELMenuDao.getInstance(mContext).isHasType(Constants.MENU_TYPE_CHECK)&&
                                DBELMenuDao.getInstance(mContext).isHasType(Constants.MENU_TYPE_CHECK_OFEN)){
                            showProgressDialog("正在更新机电设施信息...", false) ;
                            Intent service = new Intent(mContext, BaseDataMachineService.class);
                            bindService(service, connMachine, Context.BIND_AUTO_CREATE);
                            return;
                        }
                        SnackbarUtils.show(mContext,"该账户没有权限，请更换账户");
                    }else {
                        BaseResultBean resultbean = new Gson().fromJson(response, BaseResultBean.class);
                        SnackbarUtils.show(mContext, resultbean.getS());
                    }
                }

            }

            @Override
            public void onError(String errormsg) {
                hideSpotsDialog();
                ToastUtils.show(mContext, "请检查网络和网络请求地址");
            }
        },mContext);
        asyncTask.execute(map,mapName,mapType);
    }

    /**
     * 下载图片
     * @param url
     * @param file
     * @return
     */
    public boolean downLoadPic(String url,String file){
        url = "http://"+ PreferencesUtils.getString(mContext, Constants.SP_WEBURL, Constants.WEBURL)+url;
        if(FileUtils.isFileExist(file)){
            FileUtils.deleteFile(file);
        }
        boolean flag = false;
        try {
            URL picUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) picUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            InputStream isInputStream = conn.getInputStream();
            flag = FileUtils.writeFile(file, isInputStream);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mBaseDataBinder = (BaseDataService.BaseDataBinder)iBinder;
            isDataBinded = true;
            mBaseDataBinder.setCallBack(baseDataCallBack);
            mBaseDataBinder.dowmLoadBaseData();

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isDataBinded = false;
        }
    };
    private ServiceConnection connMachine = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mBaseDataManchineBinder = (BaseDataMachineService.BaseDataBinder)iBinder;
            isDataMachineBinded = true;
            mBaseDataManchineBinder.setCallBack(baseDataMachineCallBack);
            mBaseDataManchineBinder.dowmLoadBaseData();

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isDataMachineBinded = false;
        }
    };
    private BaseDataCallBack baseDataCallBack = new BaseDataCallBack() {
        @Override
        public void onResult(int count, String s) {
            mProrgessDialog.setProgress(count);
            mProrgessDialog.setContent(s);
            if(count==100){
                if(isDataBinded){
                    isDataBinded =false;
                    unbindService(conn);
                }
                hideProgressDialog();
                if(DBELMenuDao.getInstance(mContext).isHasType(Constants.MENU_TYPE_MACHINE)){
                    showProgressDialog("正在更新机电信息...", false);
                    Intent service = new Intent(mContext, BaseDataMachineService.class);
                    bindService(service, connMachine, Context.BIND_AUTO_CREATE);
                }else{
                    Intent intent = new Intent(mContext, MainActivity.class);
                    startActivity(intent);
                }
            }
        }

        @Override
        public void onError(int count) {

        }
    };
    private BaseDataCallBack baseDataMachineCallBack = new BaseDataCallBack() {
        @Override
        public void onResult(int count, String s) {
            mProrgessDialog.setProgress(count);
            mProrgessDialog.setContent(s);
            if(count==100){
                if(isDataMachineBinded){
                    isDataMachineBinded =false;
                    unbindService(connMachine);
                }
                hideProgressDialog();
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
            }
        }

        @Override
        public void onError(int count) {

        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(isDataBinded){
            unbindService(conn);
        }
        if(isDataMachineBinded){
            unbindService(connMachine);
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.login_btn:
                if (TextUtils.isEmpty(mTnlUsername.getEditText().getText().toString())) {
                    mTnlUsername.setError("用户名不能为空！");
                } else {
                    //隐藏软键盘
                    ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow
                            (LoginActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    if(/*!isVerification*/false){//如果没有认证过
                        if(CommonUtils.isNetworkConnected(mContext)){
                            verificationDeviceCode();
                        }else{
                            SnackbarUtils.show(mContext, "没有网络连接，无法登录");
                        }
                    }else {
                        String name1 = mTnlUsername.getEditText().getText().toString();
                        String password1 = mTnlPassword.getEditText().getText().toString();
                        if("admin".equals(name1)&&"admin".equals(password1)){
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            return;
                        }else{

                        }
//                        if(CommonUtils.isNetworkConnected(mContext)){
//                            showSpotsDialog("正在登录...", false);
//                            login();
//                        }else{
//                            String name = mTnlUsername.getEditText().getText().toString();
//                            String password  = mTnlPassword.getEditText().getText().toString();
//                            if(TextUtils.isEmpty(name)||TextUtils.isEmpty(password)){
//                                SnackbarUtils.show(mContext, "请输入用户密码");
//                                return;
//                            }
//                            PAccountBean info = DBPAccountDao.getInstance(mContext).getInfo(name);
//                            if("1".equals(info.getIssave())&&password.equals(info.getPassword())){
//                                CTUserBean userBean = DBCTUserDao.getInstance(mContext).getPUserInfo(info.getName());
//                                HWApplication.userBean = userBean;
//                                PreferencesUtils.putString(mContext, Constants.SP_LOGIN_KEY, userBean.getLegalizeKey());
//                                PreferencesUtils.putString(mContext, Constants.SP_USER_ID, userBean.getUserID());
//                                PreferencesUtils.putString(mContext, Constants.SP_DPT_ID, userBean.getDptId());
//                                Intent intent = new Intent(mContext, MainActivity.class);
//                                startActivity(intent);
//                            }else{
//                                SnackbarUtils.show(mContext, "没有网络连接，无法登录");
//                            }
//                        }
                    }
                }

                break;
            case R.id.login_checkbox:
                if(isSavePassword){
                    PreferencesUtils.putBoolean(mContext,Constants.SP_IS_SAVEPW,false);
                    isSavePassword =false;
                }else{
                    PreferencesUtils.putBoolean(mContext,Constants.SP_IS_SAVEPW,true);
                    isSavePassword =true;
                }
                break;
            case R.id.activity_login_setting:
               /* final EditText et = new EditText(mContext);
                et.setText(PreferencesUtils.getString(mContext,Constants.SP_WEBURL,Constants.WEBURL));
                AlertDialog.Builder build = new AlertDialog.Builder(this);
                build.setTitle("设置网络请求地址：")
                        .setView(et)
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        if(TextUtils.isEmpty(et.getText().toString().trim())){
                                            ToastUtils.show(mContext,"请输入内容");
                                        }else{
                                            DBTaskDao.getInstance(mContext).clearData();
                                            DBCTNearTaskInfoDao.getInstance(mContext).clearData();
                                            DBCTDiseaseInfoDao.getInstance(mContext).clearData();
                                            DBCTHistroyDiseaseInfoDao.getInstance(mContext).clearData();
                                            DBCTDiseasePhotoDao.getInstance(mContext).clearData();
                                            DBBTableUpdateDao.getInstance(mContext).clearData();
                                            PreferencesUtils.putString(mContext,Constants.SP_WEBURL,et.getText().toString());
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
                                }).show();*/
                Intent intent = new Intent(mContext,SettingActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                AlertDialog.Builder build = new AlertDialog.Builder(this);
                build.setTitle("注意")
                        .setMessage("确定要退出系统吗？")
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        HWApplication.exit();
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

            default:
                break;
        }
        return false;
    }
    private void verificationDeviceCode(){
        TelephonyManager manager = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        String deviceID = manager.getDeviceId();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("deviceCode", deviceID);
        HashMap<String, String> mapName = new HashMap<String, String>();
        mapName.put("methodName", Constants.METHOD_LOGINAUTHENTICATION);
        HashMap<String, String> mapType = new HashMap<String, String>();
        mapType.put("urlType",Constants.SERVICEURL_TYPE_BASEINFO);
        BaseNetAsyncTask asyncTask = new BaseNetAsyncTask(new NetResquestListener() {
            @Override
            public void onSuccess(String response) {
                if(response!=null) {
                    if (response.contains("\"r\":\"ok")) {
                        isVerification = true;
                        mDeviceVerificationNo.setVisibility(View.GONE);
                        PreferencesUtils.putBoolean(mContext, Constants.SP_IS_VERIFICATION, isVerification);
                        showSpotsDialog("正在登录...", false);
                        login();
                    }else{
                        SnackbarUtils.show(mContext,"设备没有认证，请联系管理员");
                        BaseResultBean resultbean = new Gson().fromJson(response, BaseResultBean.class);
                        mDeviceVerificationNo.setVisibility(View.VISIBLE);
                        mDeviceVerificationNo.setText("未认证（您的认证号）："+resultbean.getS());
                    }
                }
            }

            @Override
            public void onError(String errormsg) {
                ToastUtils.show(mContext, "请检查网络请求地址");
            }
        },mContext);
        asyncTask.execute(map, mapName, mapType);
    }

}
