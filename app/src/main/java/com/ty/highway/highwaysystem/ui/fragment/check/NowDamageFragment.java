package com.ty.highway.highwaysystem.ui.fragment.check;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ty.highway.highwaysystem.R;
import com.ty.highway.highwaysystem.base.Constants;
import com.ty.highway.highwaysystem.support.adapter.DamageItemAdapter;
import com.ty.highway.highwaysystem.support.bean.basedata.CTTunnelVsItemBean;
import com.ty.highway.highwaysystem.support.bean.check.CTDiseaseInfoBean;
import com.ty.highway.highwaysystem.support.db.check.DBCTDiseaseInfoDao;
import com.ty.highway.highwaysystem.support.listener.BaseCallBack;
import com.ty.highway.highwaysystem.support.utils.PreferencesUtils;
import com.ty.highway.highwaysystem.ui.fragment.basic.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuweiwei on 2015/11/2.
 */
public class NowDamageFragment extends BaseFragment{
    private View mView;
    private ListView mLvDamage;
    private CTTunnelVsItemBean mBean;
    private String mTaskId;
    private List<CTDiseaseInfoBean> mList = new ArrayList<CTDiseaseInfoBean>();
    private BaseCallBack mCallBack;
    private DamageItemAdapter mAdapter;
    private TextView mTv;
    private Context mContext ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_now_danage, container,false);
        mLvDamage = (ListView) mView.findViewById(R.id.fragment_now_damage_lv);
        mTv = (TextView) mView.findViewById(R.id.fragment_now_damage_tv);
        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        mBean = (CTTunnelVsItemBean) getArguments().getSerializable("info");
        mTaskId = getArguments().getString("taskId");
        mCallBack = new BaseCallBack() {
            @Override
            public void callBack(Object o) {
                Boolean flag = (Boolean) o;
                if (flag) {
                    mList = DBCTDiseaseInfoDao.getInstance(getActivity()).getInfoById(mTaskId,mBean.getItemName(), PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                    mAdapter.setData(mList);
                    if(mList!=null&&mList.size()>0){
                        mTv.setVisibility(View.GONE);
                    }else {
                        mTv.setText("暂时没有" + mBean.getItemName() + "病害，请手动添加");
                        mTv.setVisibility(View.VISIBLE);
                    }

                }
            }
        };
        mAdapter = new DamageItemAdapter(getActivity(),mBean,mCallBack);
        mLvDamage.setAdapter(mAdapter);
    }

    /**
     * 填充数据
     * @param taskId
     * @param bean
     */
    public void setData(String taskId,CTTunnelVsItemBean bean){
        mTaskId = taskId;
        mBean = bean;
        mCallBack = new BaseCallBack() {
            @Override
            public void callBack(Object o) {
                Boolean flag = (Boolean) o;
                if (flag) {
                    mList = DBCTDiseaseInfoDao.getInstance(getActivity()).getInfoById(mTaskId,mBean.getItemName(), PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                    mAdapter.setData(mList);
                    if(mList!=null&&mList.size()>0){
                        mTv.setVisibility(View.GONE);
                    }else {
                        mTv.setText("暂时没有" + mBean.getItemName() + "病害，请手动添加");
                        mTv.setVisibility(View.VISIBLE);
                    }

                }
            }
        };
        mAdapter = new DamageItemAdapter(getActivity(),mBean,mCallBack);
        mLvDamage.setAdapter(mAdapter);
        refreshData();
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
        mList = DBCTDiseaseInfoDao.getInstance(getActivity()).getInfoById(mTaskId,mBean.getItemName(), PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
        mAdapter.setData(mList);
        if(mList!=null&&mList.size()>0){
            mTv.setVisibility(View.GONE);
        }else {
            mTv.setText("暂时没有" + mBean.getItemName() + "病害，请手动添加");
            mTv.setVisibility(View.VISIBLE);
        }

    }
}
