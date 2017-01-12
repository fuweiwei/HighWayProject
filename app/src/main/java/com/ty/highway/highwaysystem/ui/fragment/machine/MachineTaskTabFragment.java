package com.ty.highway.highwaysystem.ui.fragment.machine;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ty.highway.highwaysystem.R;
import com.ty.highway.highwaysystem.base.Constants;
import com.ty.highway.highwaysystem.base.HWApplication;
import com.ty.highway.highwaysystem.support.adapter.machine.MachineTaskTabItemAdapter;
import com.ty.highway.highwaysystem.support.bean.machine.ELMachineTaskBean;
import com.ty.highway.highwaysystem.support.db.basedata.DBCTDictionaryDao;
import com.ty.highway.highwaysystem.support.db.machine.DBELMachineTaskDao;
import com.ty.highway.highwaysystem.support.listener.BaseCallBack;
import com.ty.highway.highwaysystem.support.utils.PreferencesUtils;
import com.ty.highway.highwaysystem.ui.fragment.basic.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuweiwei on 2015/9/9.
 */
public class MachineTaskTabFragment extends BaseFragment {
    private int mStateTag;
    private View mView;
    private ListView mLvTask;
    private TextView mTvHint;
    private MachineTaskTabItemAdapter mAdapter;
    private List<ELMachineTaskBean> mListData = new ArrayList<ELMachineTaskBean>();
    private String mCheckWayId;
    private BaseCallBack mCallBack;
    private String mTunnelId;
    private boolean isAll;
    private Context mContext  ;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_tasktab_item, container,false);
        mLvTask = (ListView) mView.findViewById(R.id.fragment_tasktab_item_lv);
        mTvHint = (TextView) mView.findViewById(R.id.fragment_tasktab_item_tv);
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        Bundle bundle = getArguments();
        mTunnelId = bundle.getString("tunnelId");
        mStateTag = bundle.getInt("tag");
        isAll = bundle.getBoolean("isAll");
        int tag = HWApplication.getmCheckType();
        mCheckWayId = DBCTDictionaryDao.getInstance(getActivity()).getIDBySort(tag);
        if(isAll){
            mListData = DBELMachineTaskDao.getInstance(getActivity()).getTaskByState(PreferencesUtils.getString(mContext, Constants.SP_USER_ID), mCheckWayId,mStateTag);
        }else{
            mListData = DBELMachineTaskDao.getInstance(getActivity()).getTaskByState(PreferencesUtils.getString(mContext, Constants.SP_USER_ID), mCheckWayId, mStateTag,mTunnelId);
        }
       // Collections.sort(mListData, new TaskComparator());
        if(mListData!=null&&mListData.size()>0){
            mTvHint.setVisibility(View.GONE);
        }else {
            mTvHint.setText("暂时没有数据");
            mTvHint.setVisibility(View.VISIBLE);
        }
        mCallBack = new BaseCallBack() {
            @Override
            public void callBack(Object o) {
                Boolean flag = (Boolean) o;
                if(flag){
                    if(isAll){
                        mListData = DBELMachineTaskDao.getInstance(getActivity()).getTaskByState(PreferencesUtils.getString(mContext, Constants.SP_USER_ID), mCheckWayId, mStateTag);
                    }else{
                        mListData = DBELMachineTaskDao.getInstance(getActivity()).getTaskByState(PreferencesUtils.getString(mContext, Constants.SP_USER_ID), mCheckWayId, mStateTag, mTunnelId);
                    }
                   // Collections.sort(mListData, new TaskComparator());
                    if(mListData!=null&&mListData.size()>0){
                        mTvHint.setVisibility(View.GONE);
                    }else {
                        mTvHint.setText("暂时没有数据");
                        mTvHint.setVisibility(View.VISIBLE);
                    }
                    mAdapter.setData(mListData);
                }
            }
        };
        mAdapter = new MachineTaskTabItemAdapter(getActivity(),mCallBack,mTunnelId);
        mLvTask.setAdapter(mAdapter);
        mAdapter.setData(mListData);
    }

}
