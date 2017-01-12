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
import com.ty.highway.highwaysystem.support.bean.check.CTDiseaseInfoBean;
import com.ty.highway.highwaysystem.support.db.basedata.DBCTTunnelVsItemDao;
import com.ty.highway.highwaysystem.support.listener.BaseCallBack;
import com.ty.highway.highwaysystem.support.utils.ScreenUtils;
import com.ty.highway.highwaysystem.ui.dialog.DamageOperateDialog;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuweiwei on 2015/9/16.
 */
public class CheckRecordItemAdapter extends BaseAdapter {
    private Context mContext;
    private BaseCallBack mCallBack;
    private List<CTDiseaseInfoBean> mList = new ArrayList<CTDiseaseInfoBean>();
    public CheckRecordItemAdapter(Context context,BaseCallBack callBack){
        mContext = context;
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
            convertView =  LayoutInflater.from(mContext).inflate(R.layout.adapter_checkrecord_item, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mTvName = (TextView) convertView.findViewById(R.id.adapter_checkrecord_item_name);
            mViewHolder.mTvTag = (TextView) convertView.findViewById(R.id.adapter_checkrecord_item_tag);
            mViewHolder.mTvMileage = (TextView) convertView.findViewById(R.id.adapter_checkrecord_item_meilage);
            mViewHolder.mTvJudge = (TextView) convertView.findViewById(R.id.adapter_checkrecord_item_judge);
            mViewHolder.mTvContent = (TextView) convertView.findViewById(R.id.adapter_checkrecord_item_content);
            mViewHolder.mTvData = (TextView) convertView.findViewById(R.id.adapter_checkrecord_item_data);
            mViewHolder.mMrl = (MaterialRippleLayout) convertView.findViewById(R.id.adapter_checkrecord_item_mrl);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mViewHolder.mMrl.getLayoutParams();
        params.height = ScreenUtils.getScreenHeight(mContext) / 10;
        mViewHolder.mMrl.setLayoutParams(params);

        final CTDiseaseInfoBean info = getItem(i);
        mViewHolder.mMrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DamageOperateDialog damageOperateDialog = new DamageOperateDialog(mContext, DBCTTunnelVsItemDao.getInstance(mContext).getInfoByNewId(info.getCheckItemId()),info,mCallBack);
                damageOperateDialog.show();
            }
        });
     /*   mViewHolder.mMrl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                AlertDialog.Builder build = new AlertDialog.Builder(mContext);
                build.setTitle("注意")
                        .setMessage("确定要删除该病害吗？")
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        DBCTDiseaseInfoDao.getInstance(mContext).clearDataByTaskId(info.getGuid());
                                        DBCTDiseasePhotoDao.getInstance(mContext).clearDataByTaskId(info.getGuid());
                                        mList.remove(i);
                                        ToastUtils.show(mContext, "删除成功");
                                        notifyDataSetChanged();
                                    }
                                })
                        .setNegativeButton("取消",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {

                                    }
                                }).show();
                return false;
            }
        });*/
        if(info!=null){
            mViewHolder.mTvName.setText(info.getBelongPro());
            if(!TextUtils.isEmpty(info.getMileage())){
                double m  = Double.parseDouble(info.getMileage());
                DecimalFormat fnum = new DecimalFormat("##0.00");
                String sizeTemp = fnum.format(m%1000);
                mViewHolder.mTvMileage.setText("K"+(int)m/1000+"+"+sizeTemp);
            }
            mViewHolder.mTvJudge.setText(info.getLevelContent());
            mViewHolder.mTvContent.setText(info.getCheckType()+":");
            mViewHolder.mTvData.setText(info.getCheckData());
            if(info.isRepeat()){
                mViewHolder.mTvTag.setText("（修改）");
            }else{
                mViewHolder.mTvTag.setText("（新增）");

            }
        }

        return convertView;
    }
    public class ViewHolder {
        public TextView mTvName;
        public TextView mTvTag;
        public TextView mTvContent;
        public TextView mTvMileage;
        public TextView mTvJudge;
        public TextView mTvData;
        public MaterialRippleLayout mMrl;
    }
}
