package com.ty.highway.highwaysystem.ui.fragment.machine;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ty.highway.highwaysystem.R;
import com.ty.highway.highwaysystem.base.Constants;
import com.ty.highway.highwaysystem.support.adapter.machine.MachineDamageItemAdapter;
import com.ty.highway.highwaysystem.support.bean.machine.ELMachineBean;
import com.ty.highway.highwaysystem.support.bean.machine.ELMachineDiseaseBean;
import com.ty.highway.highwaysystem.support.bean.machine.ELMachineTypeByItemBean;
import com.ty.highway.highwaysystem.support.db.machine.DBELMachineDiseaseDao;
import com.ty.highway.highwaysystem.support.listener.BaseCallBack;
import com.ty.highway.highwaysystem.support.utils.PreferencesUtils;
import com.ty.highway.highwaysystem.ui.fragment.basic.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuweiwei on 2015/11/2.
 */
public class MachineDamageFragment extends BaseFragment{
    private View mView;
    private ListView mLvDamage;
    private TextView mTvTag;
    private List<ELMachineDiseaseBean> mListData = new ArrayList<>();
    private Context mContext;
    private ELMachineTypeByItemBean mBean = new ELMachineTypeByItemBean();
    private String mTaskId;
    private MachineDamageItemAdapter mAdapter;
    private ELMachineBean mMachineBean;
    private BaseCallBack mCallBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_machine_danage, container,false);
        mLvDamage = (ListView) mView.findViewById(R.id.fragment_machine_damage_lv);
        mTvTag = (TextView) mView.findViewById(R.id.fragment_machine_damage_tv);
        return mView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        if(getArguments()!=null){
            mBean = (ELMachineTypeByItemBean) getArguments().getSerializable("itemInfo");
            mTaskId = getArguments().getString("taskId");
            mMachineBean =  (ELMachineBean) getArguments().getSerializable("machineInfo");
        }
        mCallBack = new BaseCallBack() {
            @Override
            public void callBack(Object o) {
                Boolean flag = (Boolean) o;
                if (flag) {
                    mListData = DBELMachineDiseaseDao.getInstance(getActivity()).getDataByItemId(mTaskId, mMachineBean.getNewId(),mBean.getMMachineItemId(), PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                    mAdapter.setData(mListData);
                    if(mListData!=null&&mListData.size()>0){
                        mTvTag.setVisibility(View.GONE);
                    }else {
                        String s = mMachineBean.getDeviceName()+"-"+mBean.getMMachineItemName() + "暂时没有检修记录，请手动添加";
                        mTvTag.setText(s);
                        mTvTag.setVisibility(View.VISIBLE);
                    }

                }
            }
        };
        mAdapter = new MachineDamageItemAdapter(mContext,mMachineBean,mBean,mCallBack);
        mLvDamage.setAdapter(mAdapter);
    }
    @Override
    public void onResume() {
        super.onResume();
        refreshData();

    }
    /**
     * 刷新数据
     */
    public void refreshData(){
        mListData = DBELMachineDiseaseDao.getInstance(getActivity()).getDataByItemId(mTaskId,mMachineBean.getNewId(), mBean.getMMachineItemId(), PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
        mAdapter.setData(mListData);
        if(mListData!=null&&mListData.size()>0){
            mTvTag.setVisibility(View.GONE);
        }else {
            String s = mMachineBean.getDeviceName()+"-"+mBean.getMMachineItemName() + "暂时没有检修记录，请手动添加";
            mTvTag.setText(s);
            mTvTag.setVisibility(View.VISIBLE);
        }

    }
}
