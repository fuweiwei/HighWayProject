package com.ty.highway.highwaysystem.ui.fragment.check;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ty.highway.highwaysystem.R;
import com.ty.highway.highwaysystem.support.adapter.DamageHistroyItemAdapter;
import com.ty.highway.highwaysystem.support.bean.basedata.CTTunnelVsItemBean;
import com.ty.highway.highwaysystem.support.bean.check.CTHistroyDiseaseInfoBean;
import com.ty.highway.highwaysystem.support.comparator.HistroyDamageComparator;
import com.ty.highway.highwaysystem.support.db.check.DBCTHistroyDiseaseInfoDao;
import com.ty.highway.highwaysystem.ui.fragment.basic.BaseFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by fuweiwei on 2015/11/2.
 */
public class HistroyDamageFragment extends BaseFragment{
    private View mView;
    private CTTunnelVsItemBean mBean;
    private ListView mLvDamage;
    private List<CTHistroyDiseaseInfoBean> mList = new ArrayList<CTHistroyDiseaseInfoBean>();
    private DamageHistroyItemAdapter mAdapter;
    private String mTunnelId;
    private String mTaskId;
    private TextView mTv;
    private Context mContext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_histroy_danage, container,false);
        mLvDamage = (ListView) mView.findViewById(R.id.fragment_histroy_damage_lv);
        mTv = (TextView) mView.findViewById(R.id.fragment_histroy_damage_tv);
        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        mBean = (CTTunnelVsItemBean) getArguments().getSerializable("info");
        mTunnelId = getArguments().getString("tunnelId");
        mTaskId = getArguments().getString("taskId");
        refreshData();
    }
    public void setData(String taskId,String tunnelId,CTTunnelVsItemBean bean){
        mTaskId = taskId;
        mTunnelId = tunnelId;
        mBean = bean;
        refreshData();
    }
    /**
     * 刷新数据
     */
    public void refreshData(){
        mList = DBCTHistroyDiseaseInfoDao.getInstance(getActivity()).getInfosById(mTunnelId, mBean.getItemId());
        Collections.sort(mList, new HistroyDamageComparator());
        mAdapter = new DamageHistroyItemAdapter(getActivity(),mBean,mTaskId);
        mLvDamage.setAdapter(mAdapter);
        if(mList!=null&&mList.size()>0){
            mTv.setVisibility(View.GONE);
        }else{
            mTv.setText("暂时没有历史病害信息");
            mTv.setVisibility(View.VISIBLE);
        }
        mAdapter.setData(mList);

    }
}
