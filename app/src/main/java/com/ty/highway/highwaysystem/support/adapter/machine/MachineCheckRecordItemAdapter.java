package com.ty.highway.highwaysystem.support.adapter.machine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.ty.highway.highwaysystem.R;
import com.ty.highway.highwaysystem.support.bean.machine.ELMachineBean;
import com.ty.highway.highwaysystem.support.bean.machine.ELMachineDiseaseBean;
import com.ty.highway.highwaysystem.support.bean.machine.ELMachineTypeByItemBean;
import com.ty.highway.highwaysystem.support.db.machine.DBELMachineDao;
import com.ty.highway.highwaysystem.support.db.machine.DBELMachineTypeByItemDao;
import com.ty.highway.highwaysystem.support.listener.BaseCallBack;
import com.ty.highway.highwaysystem.support.utils.ScreenUtils;
import com.ty.highway.highwaysystem.ui.dialog.machine.MachineDamageOperateDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuweiwei on 2015/9/16.
 */
public class MachineCheckRecordItemAdapter extends BaseAdapter {
    private Context mContext;
    private BaseCallBack mCallBack;
    private List<ELMachineDiseaseBean> mList = new ArrayList<ELMachineDiseaseBean>();
    private ELMachineTypeByItemBean mBean;
    private ELMachineBean mMachineBean;
    public MachineCheckRecordItemAdapter(Context context, BaseCallBack callBack){
        mContext = context;
        mCallBack = callBack;
    }
    public void setData(List<ELMachineDiseaseBean> list){
        mList = list;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public ELMachineDiseaseBean getItem(int i) {
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
            convertView =  LayoutInflater.from(mContext).inflate(R.layout.adapter_machine_checkrecord_item, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mTvDevice = (TextView) convertView.findViewById(R.id.adapter_machine_checkrecord_item_device);
            mViewHolder.mTvIsUse = (TextView) convertView.findViewById(R.id.adapter_machine_checkrecord_item_isuse);
            mViewHolder.mTvItem = (TextView) convertView.findViewById(R.id.adapter_machine_checkrecord_item_item);
            mViewHolder.mTvJudge = (TextView) convertView.findViewById(R.id.adapter_machine_checkrecord_item_judge);
            mViewHolder.mTvContent = (TextView) convertView.findViewById(R.id.adapter_machine_checkrecord_item_content);
            mViewHolder.mMrl = (MaterialRippleLayout) convertView.findViewById(R.id.adapter_machine_checkrecord_item_mrl);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mViewHolder.mMrl.getLayoutParams();
        params.height = ScreenUtils.getScreenHeight(mContext) / 10;
        mViewHolder.mMrl.setLayoutParams(params);

        final ELMachineDiseaseBean info = getItem(i);
        mViewHolder.mMrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMachineBean = DBELMachineDao.getInstance(mContext).getDataByNewId(info.getMMachineDeviceId());
                mBean = DBELMachineTypeByItemDao.getInstance(mContext).getDataByItemId(info.getMMachineItemId());
                MachineDamageOperateDialog damageOperateDialog = new MachineDamageOperateDialog(mContext,mMachineBean,mBean ,info,mCallBack);
                damageOperateDialog.show();
            }
        });
        if(info!=null){
            mViewHolder.mTvDevice.setText(info.getDeviceString());
            mViewHolder.mTvJudge.setText(info.getLevelString());
            mViewHolder.mTvContent.setText(info.getContentString());
            mViewHolder.mTvItem.setText(info.getItemString());
            if("1".equals(info.getIsUse())){
                mViewHolder.mTvIsUse.setText("已使用");
            }else{
                mViewHolder.mTvIsUse.setText("未使用");
            }
        }

        return convertView;
    }
    public class ViewHolder {
        public TextView mTvDevice;
        public TextView mTvItem;
        public TextView mTvContent;
        public TextView mTvIsUse;
        public TextView mTvJudge;
        public MaterialRippleLayout mMrl;
    }
}
