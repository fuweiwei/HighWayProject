package com.ty.highway.highwaysystem.ui.activity.machine;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.ty.highway.highwaysystem.R;
import com.ty.highway.highwaysystem.base.Constants;
import com.ty.highway.highwaysystem.support.adapter.machine.MachineCheckRecordItemAdapter;
import com.ty.highway.highwaysystem.support.bean.machine.ELMachineDiseaseBean;
import com.ty.highway.highwaysystem.support.db.machine.DBELMachineDiseaseDao;
import com.ty.highway.highwaysystem.support.listener.BaseCallBack;
import com.ty.highway.highwaysystem.support.utils.PreferencesUtils;
import com.ty.highway.highwaysystem.support.utils.SnackbarUtils;
import com.ty.highway.highwaysystem.ui.activity.basic.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuweiwei on 2015/9/10.
 */
public class MachineCheckRecordActivity extends BaseActivity implements View.OnClickListener{
    private ListView  mLvDamage;
    private MachineCheckRecordItemAdapter mAdapter;
    private Context mContext =this;
    private List<ELMachineDiseaseBean> mList = new ArrayList<ELMachineDiseaseBean>();
    private String mTaskId;
    private BaseCallBack mCallBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkrecord);
        initTitle();
        mTaskId = getIntent().getExtras().getString("taskId");
        initView();
    }
    public void initTitle(){
        setLTBtnText("返回");
        setLTBtnVisiable(View.VISIBLE);
        setRTBtnVisiable(View.INVISIBLE);
        setTitleText("检修记录单");
        setTitleVisiable(View.VISIBLE);
    }
    public  void initView(){
        mLvDamage = (ListView) findViewById(R.id.activity_checkrecord_lv);

        mCallBack = new BaseCallBack() {
            @Override
            public void callBack(Object o) {
                Boolean flag = (Boolean) o;
                if (flag) {
                    mList = DBELMachineDiseaseDao.getInstance(mContext).getAllDataByTaskId(mTaskId, PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                    mAdapter.setData(mList);
                    if (mList == null || mList.size() == 0) {
                        SnackbarUtils.show(mContext, "暂时没有检查记录，请先检查任务");
                    }
                }
            }
        };
        mAdapter = new MachineCheckRecordItemAdapter(mContext,mCallBack);
        mLvDamage.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mList = DBELMachineDiseaseDao.getInstance(mContext).getAllDataByTaskId(mTaskId, PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
        mAdapter.setData(mList);
        if(mList==null||mList.size()==0){
            SnackbarUtils.show(mContext, "暂时没有检查记录，请先检查任务");
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
    }
    @Override
    public void onLtBtnClick() {
        super.onLtBtnClick();
        finish();
    }
}
