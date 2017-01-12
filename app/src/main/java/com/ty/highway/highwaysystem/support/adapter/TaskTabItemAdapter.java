package com.ty.highway.highwaysystem.support.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.ty.highway.frameworklibrary.support.percent.PercentRelativeLayout;
import com.ty.highway.highwaysystem.R;
import com.ty.highway.highwaysystem.base.Constants;
import com.ty.highway.highwaysystem.base.HWApplication;
import com.ty.highway.highwaysystem.support.bean.check.TaskInfoBean;
import com.ty.highway.highwaysystem.support.db.basedata.DBBTunnelDao;
import com.ty.highway.highwaysystem.support.db.check.DBCTDiseaseInfoDao;
import com.ty.highway.highwaysystem.support.db.check.DBCTDiseasePhotoDao;
import com.ty.highway.highwaysystem.support.db.check.DBTaskDao;
import com.ty.highway.highwaysystem.support.listener.BaseCallBack;
import com.ty.highway.highwaysystem.support.utils.PreferencesUtils;
import com.ty.highway.highwaysystem.support.utils.ScreenUtils;
import com.ty.highway.highwaysystem.support.utils.ToastUtils;
import com.ty.highway.highwaysystem.ui.dialog.RelatedTaskDialog;
import com.ty.highway.highwaysystem.ui.dialog.TaskItemOperateDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuweiwei on 2015/9/11.
 */
public class TaskTabItemAdapter extends BaseAdapter {
    private List<TaskInfoBean> mLists = new ArrayList<TaskInfoBean>();
    private Context mContext;
    private BaseCallBack mCallBack;
    private String mTunnelId;

    public  TaskTabItemAdapter( Context context,BaseCallBack callBack,String tunnelId){
        this.mContext = context;
        this.mCallBack = callBack;
        this.mTunnelId = tunnelId;
    }
    public  void setData(List<TaskInfoBean> list){
        this.mLists = list;
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
            convertView =  LayoutInflater.from(mContext).inflate(R.layout.adapter_task_item, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mTvName = (TextView) convertView.findViewById(R.id.adapter_task_item_name);
            mViewHolder.mTvNameL = (TextView) convertView.findViewById(R.id.adapter_task_item_name_l);
            mViewHolder.mTvAbount = (TextView) convertView.findViewById(R.id.adapter_task_item_abount);
            mViewHolder.mTvState = (TextView) convertView.findViewById(R.id.adapter_task_item_state);
            mViewHolder.mTvDate = (TextView) convertView.findViewById(R.id.adapter_task_item_date);
            mViewHolder.mMrl = (MaterialRippleLayout) convertView.findViewById(R.id.adapter_task_item_mrl);
            mViewHolder.mMrlBut = (MaterialRippleLayout) convertView.findViewById(R.id.adapter_task_item_mar);
            mViewHolder.mBut = (Button) convertView.findViewById(R.id.adapter_task_item_but);
            mViewHolder.mButL = (Button) convertView.findViewById(R.id.adapter_task_item_but_l);
            mViewHolder.mRelL = (PercentRelativeLayout) convertView.findViewById(R.id.adapter_task_item_mrl_l);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mViewHolder.mMrl.getLayoutParams();
        params.height = ScreenUtils.getScreenHeight(mContext)/10;
        mViewHolder.mMrl.setLayoutParams(params);
        final TaskInfoBean infoBean = mLists.get(i);
        mViewHolder.mMrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(infoBean.getIsNearTask()==1&&!TextUtils.isEmpty(infoBean.getNearTaskId())){
                    TaskInfoBean   infoBean1 = DBTaskDao.getInstance(mContext).getTaskById(infoBean.getNearTaskId(), PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                    TaskItemOperateDialog dialog = new TaskItemOperateDialog(mContext,infoBean1,mCallBack);
                    dialog.show();
                }else{
                    TaskItemOperateDialog dialog = new TaskItemOperateDialog(mContext,infoBean,mCallBack);
                    dialog.show();
                }
            }
        });
        final ViewHolder finalMViewHolder = mViewHolder;
        mViewHolder.mBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(infoBean.getNearTaskId())){
                    if(  finalMViewHolder.mRelL.getVisibility()==View.VISIBLE){
                        finalMViewHolder.mRelL.setVisibility(View.GONE);
                    }else if(finalMViewHolder.mRelL.getVisibility()==View.GONE){
                        finalMViewHolder.mRelL.setVisibility(View.VISIBLE);
                    }

                }else{
                    finalMViewHolder.mRelL.setVisibility(View.GONE);
                    RelatedTaskDialog dialog = new RelatedTaskDialog(mContext,infoBean,mCallBack,mTunnelId);
                    dialog.show();
                }

            }
        });
        mViewHolder.mButL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder build = new AlertDialog.Builder(mContext);
                build.setTitle("注意")
                        .setMessage("确定要解除关联吗？")
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        if(infoBean.getUpdateState()==Constants.TASK_STATE_HASLOADING){
                                            ToastUtils.show(mContext,"已上传任务不能解除");
                                        }else{
                                            //解除病害信息 infoBean.getNearTaskId()为临时任务ID
                                            DBCTDiseaseInfoDao.getInstance(mContext).cancelRelatedTask(infoBean.getNewId(), infoBean.getNearTaskId(), PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                                            //解除病害图片信息
                                            DBCTDiseasePhotoDao.getInstance(mContext).relatedTaskState(infoBean.getNewId(), infoBean.getNearTaskId());
                                            DBTaskDao.getInstance(mContext).setState(infoBean.getNewId(), Constants.TASK_STATE_NOSTART, PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                                            DBTaskDao.getInstance(mContext).cancelRelatedTask(infoBean.getNewId(), PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                                            DBTaskDao.getInstance(mContext).cancelRelatedTask(infoBean.getNearTaskId(), PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                                            mCallBack.callBack(true);
                                            ToastUtils.show(mContext, "解除成功");
                                            finalMViewHolder.mRelL.setVisibility(View.GONE);
                                        }
                                    }
                                })
                        .setNegativeButton("取消",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {

                                    }
                                }).show();
            }
        });

        String tunnelName = DBBTunnelDao.getInstance(mContext).getTunnelById(infoBean.getTunnelId());
        if(TextUtils.isEmpty(tunnelName)){
            tunnelName="隧道不明";
        }
        String state = null;
        int updateState = infoBean.getUpdateState();
        if(updateState==Constants.TASK_STATE_NOSTART){
            state="未开始";
        }else if (updateState==Constants.TASK_STATE_WORKING){
            state="进行中";
        }else if (updateState==Constants.TASK_STATE_FINISH){
            state="已完成";
        }else if (updateState==Constants.TASK_STATE_HASLOADING){
            state="已上传";
        }
        if(infoBean.getIsNearTask()==1){
            mViewHolder.mTvName.setText("临时任务-"+infoBean.getCheckNo()+"-"+DBBTunnelDao.getInstance(mContext).getTunnelById(infoBean.getTunnelId())+"-土建检查-"+HWApplication.getCheckNameType());
            if(TextUtils.isEmpty(infoBean.getNearTaskId())){
                mViewHolder.mTvAbount.setText("未关联");
            }else{
                mViewHolder.mTvAbount.setText("关联：" + DBTaskDao.getInstance(mContext).getTaskById(infoBean.getNearTaskId(), PreferencesUtils.getString(mContext, Constants.SP_USER_ID)).getCheckNo());
            }
            mViewHolder.mMrlBut.setVisibility(View.VISIBLE);
            if(!TextUtils.isEmpty(infoBean.getNearTaskId())){
                mViewHolder.mBut.setText("解除");
            }else{
                mViewHolder.mBut.setText("关联");
            }
        }else{
            mViewHolder.mTvName.setText(infoBean.getCheckNo() + "-" + tunnelName + "-土建检查-" + HWApplication.getCheckNameType());
            if(TextUtils.isEmpty(infoBean.getNearTaskId())){
                mViewHolder.mTvAbount.setVisibility(View.GONE);
            }else{
                mViewHolder.mTvAbount.setText("已关联");
                mViewHolder.mTvAbount.setVisibility(View.VISIBLE);
            }
            if(!TextUtils.isEmpty(infoBean.getNearTaskId())){
                mViewHolder.mTvNameL.setText("临时任务-"+DBTaskDao.getInstance(mContext).getTaskById(infoBean.getNearTaskId(), PreferencesUtils.getString(mContext, Constants.SP_USER_ID)).getCheckNo()+"-"+DBBTunnelDao.getInstance(mContext).getTunnelById(infoBean.getTunnelId())+"-土建检查-"+HWApplication.getCheckNameType());
                mViewHolder.mBut.setText("查看");
                mViewHolder.mMrlBut.setVisibility(View.VISIBLE);
            }else{
                mViewHolder.mBut.setText("隐藏");
                mViewHolder.mMrlBut.setVisibility(View.GONE);
            }
        }
        mViewHolder.mTvState.setText(state);
        if(!TextUtils.isEmpty(infoBean.getCreateDate())){
            mViewHolder.mTvDate.setText("创建日期："+infoBean.getCreateDate());
        }else{
            mViewHolder.mTvDate.setText("创建日期：暂无数据");
        }
        return convertView;
    }

    public class ViewHolder {
        public TextView mTvName,mTvNameL;
        public TextView mTvAbount;
        public TextView mTvState;
        public TextView mTvDate;
        public MaterialRippleLayout mMrl;
        public MaterialRippleLayout mMrlBut;
        private Button mBut,mButL;
        private PercentRelativeLayout mRelL;
    }
}
