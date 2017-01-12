package com.ty.highway.highwaysystem.ui.dialog.machine;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import com.ty.highway.highwaysystem.R;
import com.ty.highway.highwaysystem.base.Constants;
import com.ty.highway.highwaysystem.support.bean.machine.ELMachineBean;
import com.ty.highway.highwaysystem.support.bean.machine.ELMachineDiseaseBean;
import com.ty.highway.highwaysystem.support.bean.machine.ELMachineTypeByItemBean;
import com.ty.highway.highwaysystem.support.db.machine.DBELMachineDiseaseDao;
import com.ty.highway.highwaysystem.support.db.machine.DBELMachineDiseasePhotoDao;
import com.ty.highway.highwaysystem.support.db.machine.DBELMachineTaskDao;
import com.ty.highway.highwaysystem.support.listener.BaseCallBack;
import com.ty.highway.highwaysystem.support.utils.PreferencesUtils;
import com.ty.highway.highwaysystem.support.utils.ToastUtils;
import com.ty.highway.highwaysystem.ui.activity.machine.MachineAddDamageActivity;
import com.ty.highway.highwaysystem.ui.activity.machine.MachineLookDamageActivity;
import com.ty.highway.highwaysystem.ui.dialog.BaseDialog;

/**
 * Created by fuweiwei on 2015/9/30 0016.
 */

public class MachineDamageOperateDialog extends BaseDialog implements View.OnClickListener {
    private ELMachineTypeByItemBean mItemBean;
    private ELMachineDiseaseBean mDiseaseInfoBean;
    private ELMachineBean mMachineBean;
    private Context mContext;
    private Button mButLook,mButDelete,mButCancel,mButAlter;
    private BaseCallBack mCallBack;

    public MachineDamageOperateDialog(Context context, ELMachineBean machineBean,ELMachineTypeByItemBean ctTunnelVsItemBean, ELMachineDiseaseBean ctDiseaseInfoBean, BaseCallBack callBack) {
        super(context, R.style.mDialog);
        mContext = context;
        mCallBack = callBack;
        mMachineBean = machineBean;
        mDiseaseInfoBean = ctDiseaseInfoBean;
        mItemBean = ctTunnelVsItemBean;
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
                Intent intent = new Intent(mContext, MachineAddDamageActivity.class);
                intent.putExtra("diseaseInfo", mDiseaseInfoBean);
                intent.putExtra("taskInfo", DBELMachineTaskDao.getInstance(mContext).getTaskById(mDiseaseInfoBean.getTaskId(),PreferencesUtils.getString(mContext, Constants.SP_USER_ID)));
                intent.putExtra("itemInfo", mItemBean);
                intent.putExtra("machineInfo", mMachineBean);
                mContext.startActivity(intent);
                break;
            case R.id.dialog_damage_operate_look:
                hide();
                Intent intent2 = new Intent(mContext, MachineLookDamageActivity.class);
                intent2.putExtra("diseaseInfo", mDiseaseInfoBean);
                mContext.startActivity(intent2);
                break;
            case R.id.dialog_damage_operate_delete:
                hide();
                AlertDialog.Builder build = new AlertDialog.Builder(mContext);
                build.setTitle("注意")
                        .setMessage("确定要删除该病害吗？")
                        .setPositiveButton("确定",
                                new OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        DBELMachineDiseaseDao.getInstance(mContext).clearDataByNewId(mDiseaseInfoBean.getNewId(), PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                                        DBELMachineDiseasePhotoDao.getInstance(mContext).clearDataById(mDiseaseInfoBean.getNewId());
                                        ToastUtils.show(mContext, "删除成功");
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
            case R.id.dialog_damage_operate_cancel:
                hide();
                break;
        }
    }


}