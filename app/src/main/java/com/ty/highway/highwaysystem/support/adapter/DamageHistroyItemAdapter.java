package com.ty.highway.highwaysystem.support.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.ty.highway.highwaysystem.support.bean.check.CTHistroyDiseaseInfoBean;
import com.ty.highway.highwaysystem.support.db.basedata.DBCTCheckPositionDao;
import com.ty.highway.highwaysystem.support.listener.BaseCallBack;
import com.ty.highway.highwaysystem.support.utils.ScreenUtils;
import com.ty.highway.highwaysystem.ui.activity.check.HistroyDamageActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuweiwei on 2015/9/16.
 */
public class DamageHistroyItemAdapter extends BaseAdapter {
    private Context mContext;
    private List<CTHistroyDiseaseInfoBean> mList = new ArrayList<CTHistroyDiseaseInfoBean>();
    private CTTunnelVsItemBean mBean;
    private String mTaskId;
    private BaseCallBack mCallBack;
    public DamageHistroyItemAdapter(Context context, CTTunnelVsItemBean bean,String taskId){
        mContext = context;
        mBean = bean;
        mTaskId = taskId;
    }
    public void setData(List<CTHistroyDiseaseInfoBean> list){
        mList = list;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public CTHistroyDiseaseInfoBean getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        ViewHolderHis mViewHolder = null;
        if (convertView == null) {
            convertView =  LayoutInflater.from(mContext).inflate(R.layout.adapter_histroydamage_item, null);
            mViewHolder = new ViewHolderHis();
            mViewHolder.mTvName = (TextView) convertView.findViewById(R.id.adapter_histroy_damage_item_name);
            mViewHolder.mTvPostion = (TextView) convertView.findViewById(R.id.adapter_histroy_damage_item_postion);
            mViewHolder.mTvMileage = (TextView) convertView.findViewById(R.id.adapter_histroy_damage_item_meilage);
            mViewHolder.mTvJudge = (TextView) convertView.findViewById(R.id.adapter_histroy_damage_item_judge);
            mViewHolder.mTvDate = (TextView) convertView.findViewById(R.id.adapter_histroy_damage_item_date);
            mViewHolder.mTvMan = (TextView) convertView.findViewById(R.id.adapter_histroy_damage_item_checkman);
            mViewHolder.mMar = (MaterialRippleLayout) convertView.findViewById(R.id.adapter_histroy_damage_item_mrl);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolderHis) convertView.getTag();
        }

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mViewHolder.mMar.getLayoutParams();
        params.height = ScreenUtils.getScreenHeight(mContext)/10;
        mViewHolder.mMar.setLayoutParams(params);

        final CTHistroyDiseaseInfoBean info = getItem(i);
        mViewHolder.mMar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, HistroyDamageActivity.class);
                intent.putExtra("diseaseInfo",info);
                intent.putExtra("taskId",mTaskId);
                intent.putExtra("checkItemInfo",mBean);
                mContext.startActivity(intent);
            }
        });
        if(info!=null){
            mViewHolder.mTvDate.setText(info.getCreateDate());
            mViewHolder.mTvMan.setText(info.getCheckName());
            mViewHolder.mTvName.setText(info.getContentName());
            if(TextUtils.isEmpty(info.getSpaceId())){
                mViewHolder.mTvPostion.setText("位置：无");
            }else{
                mViewHolder.mTvPostion.setText( DBCTCheckPositionDao.getInstance(mContext).getPosrionString(info.getSpaceId()));
            }
            mViewHolder.mTvMileage.setText(info.getStartMileageNum());
            mViewHolder.mTvJudge.setText(info.getLevelName());
        }

        return convertView;
    }
    public class ViewHolderHis {
        private TextView mTvName;
        private TextView mTvPostion;
        private TextView mTvMileage;
        private TextView mTvJudge;
        private TextView mTvDate;
        private TextView mTvMan;
        private MaterialRippleLayout mMar;
    }
}
