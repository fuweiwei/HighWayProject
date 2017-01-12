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
import com.ty.highway.highwaysystem.support.listener.BaseCallBack;
import com.ty.highway.highwaysystem.support.utils.ScreenUtils;
import com.ty.highway.highwaysystem.ui.dialog.machine.MachineDamageOperateDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuweiwei on 2015/9/16.
 */
public class MachineDamageItemAdapter extends BaseAdapter {
    private Context mContext;
    private List<ELMachineDiseaseBean> mList = new ArrayList<ELMachineDiseaseBean>();
    private ELMachineTypeByItemBean mBean;
    private BaseCallBack mCallBack;
    private ELMachineBean mMachineBean;
    public MachineDamageItemAdapter(Context context,ELMachineBean machineBean, ELMachineTypeByItemBean bean, BaseCallBack callBack){
        mContext = context;
        mBean = bean;
        mCallBack = callBack;
        mMachineBean = machineBean;
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
            convertView =  LayoutInflater.from(mContext).inflate(R.layout.adapter_machine_damage_item, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mTvContent = (TextView) convertView.findViewById(R.id.adapter_machine_damage_item_content);
            mViewHolder.mTvJudge = (TextView) convertView.findViewById(R.id.adapter_machine_damage_item_judge);
            mViewHolder.mTvIsUse = (TextView) convertView.findViewById(R.id.adapter_machine_damage_item_isuse);
            mViewHolder.mMar = (MaterialRippleLayout) convertView.findViewById(R.id.adapter_machine_damage_item_mrl);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mViewHolder.mMar.getLayoutParams();
        params.height = ScreenUtils.getScreenHeight(mContext)/10;
        mViewHolder.mMar.setLayoutParams(params);

        final ELMachineDiseaseBean info = getItem(i);
        mViewHolder.mMar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MachineDamageOperateDialog damageOperateDialog = new MachineDamageOperateDialog(mContext,mMachineBean,mBean,info,mCallBack);
                damageOperateDialog.show();;
            }
        });
        if(info!=null){
            mViewHolder.mTvContent.setText(info.getContentString());
            mViewHolder.mTvJudge.setText(info.getLevelString());
            if("1".equals(info.getIsUse())){
                mViewHolder.mTvIsUse.setText("已使用");
            }else{
                mViewHolder.mTvIsUse.setText("未使用");

            }
        }

        return convertView;
    }
    public class ViewHolder {
        private TextView mTvContent;
        private TextView mTvJudge;
        private TextView mTvIsUse;
        private MaterialRippleLayout mMar;
    }
}
