package com.ty.highway.highwaysystem.support.adapter.machine;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.ty.highway.highwaysystem.R;
import com.ty.highway.highwaysystem.base.Constants;
import com.ty.highway.highwaysystem.support.bean.machine.ELMachineBean;
import com.ty.highway.highwaysystem.support.db.machine.DBELMachineDiseaseDao;
import com.ty.highway.highwaysystem.support.listener.BaseCallBack;
import com.ty.highway.highwaysystem.support.utils.PreferencesUtils;
import com.ty.highway.highwaysystem.support.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuweiwei on 2015/9/16.
 */
public class MachineCheckItemAdapter extends BaseAdapter {
    private Context mContext;
    private List<ELMachineBean> mList = new ArrayList<ELMachineBean>();
    private String mTaskId;
    private String mTunnelId;
    private BaseCallBack mCallBack;
    private DrawerLayout mDrawerLayout;
    public MachineCheckItemAdapter(Context context, String taskId, String tunnelId, BaseCallBack callBack, DrawerLayout drawerLayout){
        mContext = context;
        mTaskId = taskId;
        mTunnelId = tunnelId;
        mCallBack = callBack;
        mDrawerLayout = drawerLayout;
    }
    public void setData(List<ELMachineBean> list){
        mList = list;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public ELMachineBean getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        ViewHolder mViewHolder = null;
        if (convertView == null) {
            convertView =  LayoutInflater.from(mContext).inflate(R.layout.adapter_checkitem_item, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mTvName = (TextView) convertView.findViewById(R.id.adapter_checkitem_item_name);
            mViewHolder.mTvNum = (TextView) convertView.findViewById(R.id.adapter_checkitem_item_num);
            mViewHolder.mTvNumh = (TextView) convertView.findViewById(R.id.adapter_checkitem_item_numh);
            mViewHolder.mMrl = (MaterialRippleLayout) convertView.findViewById(R.id.adapter_checkitem_item_mrl);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mViewHolder.mMrl.getLayoutParams();
        params.height = ScreenUtils.getScreenHeight(mContext)/10;
        mViewHolder.mMrl.setLayoutParams(params);
        ELMachineBean info = getItem(i);
        mViewHolder.mMrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallBack.callBack(getItem(i));
                mDrawerLayout.closeDrawers();
            }
        });

        int num = DBELMachineDiseaseDao.getInstance(mContext).getDiseaseNum(mTaskId, info.getNewId(), PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
        if(info!=null){
            mViewHolder.mTvName.setText(info.getDeviceName());
        }
        mViewHolder.mTvNumh.setVisibility(View.GONE);
        mViewHolder.mTvNum.setText(""+num);
        return convertView;
    }
    public class ViewHolder {
        public TextView mTvName;
        public TextView mTvNum;
        public TextView mTvNumh;
        public MaterialRippleLayout mMrl;
    }
}
