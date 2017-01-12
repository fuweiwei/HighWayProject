package com.ty.highway.highwaysystem.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import com.ty.highway.highwaysystem.R;
import com.ty.highway.highwaysystem.base.Constants;
import com.ty.highway.highwaysystem.support.bean.basedata.CTTunnelVsItemBean;
import com.ty.highway.highwaysystem.support.bean.check.CTDiseaseInfoBean;
import com.ty.highway.highwaysystem.support.db.check.DBCTDiseaseInfoDao;
import com.ty.highway.highwaysystem.support.db.check.DBCTDiseasePhotoDao;
import com.ty.highway.highwaysystem.support.listener.BaseCallBack;
import com.ty.highway.highwaysystem.support.utils.PreferencesUtils;
import com.ty.highway.highwaysystem.support.utils.ToastUtils;
import com.ty.highway.highwaysystem.ui.activity.check.AddDamageActivity;
import com.ty.highway.highwaysystem.ui.activity.check.LookDamageActivity;

/**
 * Created by fuweiwei on 2015/9/30 0016.
 */

public class DamageOperateDialog extends BaseDialog implements View.OnClickListener {
    private CTTunnelVsItemBean mCTTunnelVsItemBean;
    private CTDiseaseInfoBean mCTDiseaseInfoBean;
    private Context mContext;
    private Button mButLook,mButDelete,mButCancel,mButAlter;
    private BaseCallBack mCallBack;

    public DamageOperateDialog(Context context,CTTunnelVsItemBean ctTunnelVsItemBean,CTDiseaseInfoBean ctDiseaseInfoBean,BaseCallBack callBack) {
        super(context, R.style.mDialog);
        mContext = context;
        mCallBack = callBack;
        mCTDiseaseInfoBean = ctDiseaseInfoBean;
        mCTTunnelVsItemBean = ctTunnelVsItemBean;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_damage_operate);
        mButLook = (Button) findViewById(R.id.dialog_damage_operate_look);
        mButAlter = (Button) findViewById(R.id.dialog_damage_operate_alter);
        mButDelete = (Button) findViewById(R.id.dialog_damage_operate_delete);
        mButCancel = (Button) findViewById(R.id.dialog_damage_operate_cancel);
        mButCancel.setOnClickListener(this);
        mButLook.setOnClickListener(this);
        mButDelete.setOnClickListener(this);
        mButAlter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.dialog_damage_operate_alter:
                hide();
                Intent intent = new Intent(mContext, AddDamageActivity.class);
                intent.putExtra("diseaseInfo",mCTDiseaseInfoBean);
                intent.putExtra("taskId",mCTDiseaseInfoBean.getTaskId());
                intent.putExtra("info",mCTTunnelVsItemBean);
                mContext.startActivity(intent);
                break;
            case R.id.dialog_damage_operate_look:
                hide();
                Intent intent2 = new Intent(mContext, LookDamageActivity.class);
                intent2.putExtra("diseaseInfo",mCTDiseaseInfoBean);
                intent2.putExtra("taskId",mCTDiseaseInfoBean.getTaskId());
                intent2.putExtra("info",mCTTunnelVsItemBean);
                mContext.startActivity(intent2);
                break;
            case R.id.dialog_damage_operate_delete:
                hide();
                AlertDialog.Builder build = new AlertDialog.Builder(mContext);
                build.setTitle("注意")
                        .setMessage("确定要删除该病害吗？")
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        DBCTDiseaseInfoDao.getInstance(mContext).clearDataById(mCTDiseaseInfoBean.getGuid(), PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                                        DBCTDiseasePhotoDao.getInstance(mContext).clearDataById(mCTDiseaseInfoBean.getGuid());
                                        ToastUtils.show(mContext, "删除成功");
                                        mCallBack.callBack(true);
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
            case R.id.dialog_damage_operate_cancel:
                hide();
                break;
        }
    }


}