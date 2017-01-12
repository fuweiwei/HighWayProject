package com.ty.highway.highwaysystem.support.adapter;

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
import com.ty.highway.highwaysystem.support.bean.basedata.CTTunnelVsItemBean;
import com.ty.highway.highwaysystem.support.db.check.DBCTDiseaseInfoDao;
import com.ty.highway.highwaysystem.support.db.check.DBCTHistroyDiseaseInfoDao;
import com.ty.highway.highwaysystem.support.listener.BaseCallBack;
import com.ty.highway.highwaysystem.support.utils.PreferencesUtils;
import com.ty.highway.highwaysystem.support.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuweiwei on 2015/9/16.
 */
public class CheckItemAdapter extends BaseAdapter {
    private Context mContext;
    private List<CTTunnelVsItemBean> mList = new ArrayList<CTTunnelVsItemBean>();
    private String mTaskId;
    private String mTunnelId;
    private BaseCallBack mCallBack;
    private DrawerLayout mDrawerLayout;
    public CheckItemAdapter(Context context,String taskId,String tunnelId,BaseCallBack callBack,DrawerLayout drawerLayout){
        mContext = context;
        mTaskId = taskId;
        mTunnelId = tunnelId;
        mCallBack = callBack;
        mDrawerLayout = drawerLayout;
    }
    public void setData(List<CTTunnelVsItemBean> list){
        mList = list;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public CTTunnelVsItemBean getItem(int i) {
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

        mViewHolder.mMrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent = new Intent(mContext, DamageListActivity.class);
                intent.putExtra("info",getItem(i));
                intent.putExtra("taskId",mTaskId);
                intent.putExtra("tunnelId",mTunnelId);
                mContext.startActivity(intent);*/
                mCallBack.callBack(i);
                mDrawerLayout.closeDrawers();
            }
        });
        CTTunnelVsItemBean info = getItem(i);
        if(info!=null){
            int num = DBCTDiseaseInfoDao.getInstance(mContext).getDiseaseNum(mTaskId,info.getItemName(), PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
            int hisNum = DBCTHistroyDiseaseInfoDao.getInstance(mContext).getDiseaseNum(mTunnelId, info.getItemId());
            mViewHolder.mTvName.setText(info.getItemName());
            mViewHolder.mTvNum.setText((num+hisNum)+"");
            mViewHolder.mTvNumh.setText("("+num+")");
        }

        return convertView;
    }
    public class ViewHolder {
        public TextView mTvName;
        public TextView mTvNum;
        public TextView mTvNumh;
        public MaterialRippleLayout mMrl;
    }
}
