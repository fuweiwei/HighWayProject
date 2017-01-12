package com.ty.highway.highwaysystem.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.ty.highway.highwaysystem.R;
import com.ty.highway.highwaysystem.base.Constants;
import com.ty.highway.highwaysystem.base.HWApplication;
import com.ty.highway.highwaysystem.support.adapter.TaskRelateItemAdapter;
import com.ty.highway.highwaysystem.support.bean.check.TaskInfoBean;
import com.ty.highway.highwaysystem.support.db.basedata.DBCTDictionaryDao;
import com.ty.highway.highwaysystem.support.db.check.DBCTDiseaseInfoDao;
import com.ty.highway.highwaysystem.support.db.check.DBCTDiseasePhotoDao;
import com.ty.highway.highwaysystem.support.db.check.DBTaskDao;
import com.ty.highway.highwaysystem.support.listener.BaseCallBack;
import com.ty.highway.highwaysystem.support.utils.PreferencesUtils;
import com.ty.highway.highwaysystem.support.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuweiwei on 2015/9/18.
 */
public class RelatedTaskDialog extends BaseDialog implements View.OnClickListener {
    private ListView mLvTask;
    private Button mButRelate;
    private List<TaskInfoBean> mListTask = new ArrayList<>();
    private Context mContext ;
    private TaskRelateItemAdapter mAdapter;
    private int mSelectItem= -1;
    private TaskInfoBean mRelatedbean;
    private BaseCallBack mCallBack;
    private String mTunnelId;

    public RelatedTaskDialog(Context context,TaskInfoBean bean,BaseCallBack callBack,String tunnelId) {
        super(context, R.style.mDialog);
        mContext = context;
        mRelatedbean = bean;
        mCallBack = callBack;
        mTunnelId = tunnelId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_relatedtask);
        mLvTask = (ListView) findViewById(R.id.dialog_relatedtask_lv);
        mButRelate = (Button) findViewById(R.id.dialog_relatedtask_but);
        mButRelate.setOnClickListener(this);
        mListTask = DBTaskDao.getInstance(mContext).getTasrelatekList(Constants.TASK_STATE_NOSTART,  DBCTDictionaryDao.getInstance(mContext).getIDBySort(HWApplication.getmCheckType()),0, PreferencesUtils.getString(mContext, Constants.SP_USER_ID),mTunnelId);
        mAdapter = new TaskRelateItemAdapter(mContext);
        mLvTask.setAdapter(mAdapter);
        mAdapter.setData(mListTask);
        mLvTask.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mAdapter.setSelectItem(i);
                mSelectItem = i;

            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.dialog_relatedtask_but:
                if(mSelectItem<0){
                    ToastUtils.show(mContext, "请选择要关联的任务");
                }else{
                    TaskInfoBean bean = mListTask.get(mSelectItem);
                    DBTaskDao.getInstance(mContext).updateRelatedTask(bean.getNewId(),mRelatedbean.getNewId(), PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                    DBTaskDao.getInstance(mContext).updateRelatedTask(mRelatedbean.getNewId(), bean.getNewId(), PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                    DBTaskDao.getInstance(mContext).setState(bean.getNewId(), mRelatedbean.getUpdateState(), PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                    //绑定病害信息
                    DBCTDiseaseInfoDao.getInstance(mContext).relatedTask(mRelatedbean.getNewId(), bean.getNewId(), bean.getTunnelId(), PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                    //绑定病害图片信息
                    DBCTDiseasePhotoDao.getInstance(mContext).relatedTaskState(mRelatedbean.getNewId(),bean.getNewId());
                    ToastUtils.show(mContext, "关联任务成功");
                    mCallBack.callBack(true);
                    hide();
                }

                break;
        }
    }
}
