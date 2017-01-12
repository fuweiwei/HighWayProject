package com.ty.highway.highwaysystem.support.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ty.highway.highwaysystem.R;
import com.ty.highway.highwaysystem.base.HWApplication;
import com.ty.highway.highwaysystem.support.bean.check.TaskInfoBean;
import com.ty.highway.highwaysystem.support.db.basedata.DBBTunnelDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuweiwei on 2015/9/11.
 */
public class TaskRelateItemAdapter extends BaseAdapter {
    private List<TaskInfoBean> mLists = new ArrayList<TaskInfoBean>();
    private Context mContext;
    private int mSelectItem= -1;

    public TaskRelateItemAdapter(Context context){
        this.mContext = context;
    }
    public  void setData(List<TaskInfoBean> list){
        this.mLists = list;
        notifyDataSetChanged();
    }

    public  void setSelectItem(int item){
        mSelectItem = item;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mLists.size();
    }

    @Override
    public TaskInfoBean getItem(int i) {
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
            convertView =  LayoutInflater.from(mContext).inflate(R.layout.adapter_related_item, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mTvName = (TextView) convertView.findViewById(R.id.adapter_related_item_name);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        TaskInfoBean infoBean = mLists.get(i);
        String tunnelName = DBBTunnelDao.getInstance(mContext).getTunnelById(infoBean.getTunnelId());
        if(TextUtils.isEmpty(tunnelName)){
            tunnelName="隧道不明";
        }
        mViewHolder.mTvName.setText(infoBean.getCheckNo()+"-"+tunnelName+"-检查-"+ HWApplication.getCheckNameType());
        if(i==mSelectItem){
            convertView.setBackgroundResource(R.color.common_theme_color);
            mViewHolder.mTvName.setTextColor(mContext.getResources().getColor(R.color.white));
        }else{
            convertView.setBackgroundResource(R.color.transparent);
            mViewHolder.mTvName.setTextColor(mContext.getResources().getColor(R.color.black));
        }
        return convertView;
    }

    public class ViewHolder {
        public TextView mTvName;
    }
}
