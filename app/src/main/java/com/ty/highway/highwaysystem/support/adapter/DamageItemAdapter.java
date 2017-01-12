package com.ty.highway.highwaysystem.support.adapter;

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
import com.ty.highway.highwaysystem.support.bean.basedata.CTTunnelVsItemBean;
import com.ty.highway.highwaysystem.support.bean.check.CTDiseaseInfoBean;
import com.ty.highway.highwaysystem.support.db.basedata.DBCTCheckPositionDao;
import com.ty.highway.highwaysystem.support.listener.BaseCallBack;
import com.ty.highway.highwaysystem.support.utils.ScreenUtils;
import com.ty.highway.highwaysystem.ui.dialog.DamageOperateDialog;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuweiwei on 2015/9/16.
 */
public class DamageItemAdapter extends BaseAdapter {
    private Context mContext;
    private List<CTDiseaseInfoBean> mList = new ArrayList<CTDiseaseInfoBean>();
    private CTTunnelVsItemBean mBean;
    private BaseCallBack mCallBack;
    public DamageItemAdapter(Context context,CTTunnelVsItemBean bean,BaseCallBack callBack){
        mContext = context;
        mBean = bean;
        mCallBack = callBack;
    }
    public void setData(List<CTDiseaseInfoBean> list){
        mList = list;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public CTDiseaseInfoBean getItem(int i) {
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
            convertView =  LayoutInflater.from(mContext).inflate(R.layout.adapter_damage_item, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mTvName = (TextView) convertView.findViewById(R.id.adapter_damage_item_name);
            mViewHolder.mTvPostion = (TextView) convertView.findViewById(R.id.adapter_damage_item_postion);
            mViewHolder.mTvMileage = (TextView) convertView.findViewById(R.id.adapter_damage_item_meilage);
            mViewHolder.mTvJudge = (TextView) convertView.findViewById(R.id.adapter_damage_item_judge);
            mViewHolder.mTvTag = (TextView) convertView.findViewById(R.id.adapter_damage_item_tag);
            mViewHolder.mMar = (MaterialRippleLayout) convertView.findViewById(R.id.adapter_damage_item_mrl);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mViewHolder.mMar.getLayoutParams();
        params.height = ScreenUtils.getScreenHeight(mContext)/10;
        mViewHolder.mMar.setLayoutParams(params);

        final CTDiseaseInfoBean info = getItem(i);
        mViewHolder.mMar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DamageOperateDialog damageOperateDialog = new DamageOperateDialog(mContext,mBean,info,mCallBack);
                damageOperateDialog.show();
            }
        });
        if(info!=null){
            mViewHolder.mTvName.setText(info.getCheckType());
            mViewHolder.mTvPostion.setText(DBCTCheckPositionDao.getInstance(mContext).getPosrionString(info.getCheckPostion()));
            if(!TextUtils.isEmpty(info.getMileage())){
                double m  = Double.parseDouble(info.getMileage());
                DecimalFormat fnum = new DecimalFormat("##0.00");
                String sizeTemp = fnum.format(m%1000);
                mViewHolder.mTvMileage.setText("K"+(int)m/1000+"+"+sizeTemp);
            }
            mViewHolder.mTvJudge.setText(info.getLevelContent());
            if(info.isRepeat()){
                mViewHolder.mTvTag.setText("修改");
            }else{
                mViewHolder.mTvTag.setText("新增");

            }
        }

        return convertView;
    }
    public class ViewHolder {
        private TextView mTvName;
        private TextView mTvPostion;
        private TextView mTvMileage;
        private TextView mTvJudge;
        private TextView mTvTag;
        private MaterialRippleLayout mMar;
    }
}
