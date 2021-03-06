package com.ty.highway.highwaysystem.ui.fragment.check;

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
import com.ty.highway.highwaysystem.support.adapter.TaskTabItemAdapter;
import com.ty.highway.highwaysystem.support.bean.check.TaskInfoBean;
import com.ty.highway.highwaysystem.support.comparator.TaskComparator;
import com.ty.highway.highwaysystem.support.db.basedata.DBCTDictionaryDao;
import com.ty.highway.highwaysystem.support.db.check.DBTaskDao;
import com.ty.highway.highwaysystem.support.listener.BaseCallBack;
import com.ty.highway.highwaysystem.support.utils.PreferencesUtils;
import com.ty.highway.highwaysystem.ui.fragment.basic.BaseFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by fuweiwei on 2015/9/9.
 */
public class TaskTabFragment extends BaseFragment {
    private int mStateTag;
    private View mView;
    private ListView mLvTask;
    private TextView mTvHint;
    private TaskTabItemAdapter mAdapter;
    private List<TaskInfoBean> mListData = new ArrayList<TaskInfoBean>();
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
            mListData = DBTaskDao.getInstance(getActivity()).getTaskListByState(mStateTag, mCheckWayId, PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
        }else{
            mListData = DBTaskDao.getInstance(getActivity()).getTaskListByState(mStateTag, mCheckWayId, PreferencesUtils.getString(mContext, Constants.SP_USER_ID),mTunnelId);
        }
        Collections.sort(mListData, new TaskComparator());
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
                        mListData = DBTaskDao.getInstance(getActivity()).getTaskListByState(mStateTag, mCheckWayId, PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                    }else{
                        mListData = DBTaskDao.getInstance(getActivity()).getTaskListByState(mStateTag, mCheckWayId, PreferencesUtils.getString(mContext, Constants.SP_USER_ID),mTunnelId);
                    }
                    Collections.sort(mListData, new TaskComparator());
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
        mAdapter = new TaskTabItemAdapter(getActivity(),mCallBack,mTunnelId);
        mLvTask.setAdapter(mAdapter);
        mAdapter.setData(mListData);
    }

}
