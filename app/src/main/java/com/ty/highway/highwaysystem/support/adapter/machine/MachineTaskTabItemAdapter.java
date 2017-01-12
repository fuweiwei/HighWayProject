package com.ty.highway.highwaysystem.support.adapter.machine;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.ty.highway.highwaysystem.R;
import com.ty.highway.highwaysystem.base.Constants;
import com.ty.highway.highwaysystem.base.HWApplication;
import com.ty.highway.highwaysystem.support.bean.machine.ELMachineTaskBean;
import com.ty.highway.highwaysystem.support.db.basedata.DBBTunnelDao;
import com.ty.highway.highwaysystem.support.listener.BaseCallBack;
import com.ty.highway.highwaysystem.support.utils.ScreenUtils;
import com.ty.highway.highwaysystem.ui.dialog.machine.MachineTaskItemOperateDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuweiwei on 2015/9/11.
 */
public class MachineTaskTabItemAdapter extends BaseAdapter {
    private List<ELMachineTaskBean> mLists = new ArrayList<ELMachineTaskBean>();
    private Context mContext;
    private BaseCallBack mCallBack;
    private String mTunnelId;

    public MachineTaskTabItemAdapter(Context context, BaseCallBack callBack, String tunnelId){
        this.mContext = context;
        this.mCallBack = callBack;
        this.mTunnelId = tunnelId;
    }
    public  void setData(List<ELMachineTaskBean> list){
        this.mLists = list;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mLists.size();
    }

    @Override
    public ELMachineTaskBean getItem(int i) {
        return mLists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        ViewHolder mViewHolder = null;
        if (convertView == null) {
            convertView =  LayoutInflater.from(mContext).inflate(R.layout.adapter_machine_task_item, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mTvName = (TextView) convertView.findViewById(R.id.adapter_task_item_name);
            mViewHolder.mTvState = (TextView) convertView.findViewById(R.id.adapter_task_item_state);
            mViewHolder.mTvDate = (TextView) convertView.findViewById(R.id.adapter_task_item_date);
            mViewHolder.mMrl = (MaterialRippleLayout) convertView.findViewById(R.id.adapter_task_item_mrl);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mViewHolder.mMrl.getLayoutParams();
        params.height = ScreenUtils.getScreenHeight(mContext)/10;
        mViewHolder.mMrl.setLayoutParams(params);
        final ELMachineTaskBean infoBean = mLists.get(i);

        mViewHolder.mMrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    MachineTaskItemOperateDialog dialog = new MachineTaskItemOperateDialog(mContext,infoBean,mCallBack);
                    dialog.show();
            }
        });
        String tunnelName = DBBTunnelDao.getInstance(mContext).getTunnelById(infoBean.getTunnelId());
        if(TextUtils.isEmpty(tunnelName)){
            tunnelName="隧道不明";
        }
        if(HWApplication.mCheckType==Constants.CHECK_TYPE_OFTEN){
            mViewHolder.mTvName.setText(infoBean.getCheckNo() + "-" + tunnelName + "-机电检修-经常检修" );
        }else if(HWApplication.mCheckType==Constants.CHECK_TYPE_REGULAR){
            mViewHolder.mTvName.setText(infoBean.getCheckNo() + "-" + tunnelName + "-机电检修-定期检修" );
        }else{
            mViewHolder.mTvName.setText(infoBean.getCheckNo() + "-" + tunnelName + "-机电检修" );
        }
        String state = null;
        int updateState = infoBean.getUpdateState();
        if(updateState== Constants.TASK_STATE_NOSTART){
            state="未开始";
        }else if (updateState==Constants.TASK_STATE_WORKING){
            state="进行中";
        }else if (updateState==Constants.TASK_STATE_FINISH){
            state="已完成";
        }else if (updateState==Constants.TASK_STATE_HASLOADING){
            state="已上传";
        }
        mViewHolder.mTvState.setText(state);
        if(!TextUtils.isEmpty(infoBean.getUpdateDate())){
            mViewHolder.mTvDate.setText("创建日期："+infoBean.getUpdateDate());
        }else{
            mViewHolder.mTvDate.setText("创建日期：暂无数据");
        }
        return convertView;
    }

    public class ViewHolder {
        public TextView mTvName;
        public TextView mTvState;
        public TextView mTvDate;
        public MaterialRippleLayout mMrl;
    }
}
