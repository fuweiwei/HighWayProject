package com.ty.highway.highwaysystem.ui.activity.check;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ty.highway.highwaysystem.R;
import com.ty.highway.highwaysystem.base.Constants;
import com.ty.highway.highwaysystem.base.HWApplication;
import com.ty.highway.highwaysystem.support.adapter.CheckItemAdapter;
import com.ty.highway.highwaysystem.support.bean.basedata.CTTunnelVsItemBean;
import com.ty.highway.highwaysystem.support.bean.check.TaskInfoBean;
import com.ty.highway.highwaysystem.support.db.basedata.DBBTunnelDao;
import com.ty.highway.highwaysystem.support.db.basedata.DBCTCheckItemVsDiseaseTypeDao;
import com.ty.highway.highwaysystem.support.db.basedata.DBCTTunnelVsItemDao;
import com.ty.highway.highwaysystem.support.db.check.DBTaskDao;
import com.ty.highway.highwaysystem.support.utils.PreferencesUtils;
import com.ty.highway.highwaysystem.ui.activity.basic.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuweiwei on 2015/9/10.
 */
public class CheckListActivity extends BaseActivity implements View.OnClickListener{

    private ListView mLvCheckItem;
    private TaskInfoBean mTaskInfoBean;
    private TextView mTvTitle;
    private Context mContext=this;
    private String mTaskId;
    private List<CTTunnelVsItemBean> mListInfo = new ArrayList<CTTunnelVsItemBean>();
    private CheckItemAdapter mAdapter;
    private Button mButCheckRecord,mButFinish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);
        mTaskInfoBean = (TaskInfoBean) getIntent().getExtras().getSerializable("info");
        mTaskId = getIntent().getExtras().getString("taskId");
        initView();
    }
    public void initView(){
        mLvCheckItem = (ListView) findViewById(R.id.activity_checklist_lv);
        mTvTitle = (TextView) findViewById(R.id.activity_checklist_title);
        mButCheckRecord = (Button) findViewById(R.id.activity_checklist_checkbut);
        mButFinish = (Button) findViewById(R.id.activity_checklist_finishcheck);
//        findViewById(R.id.btn_back).setOnClickListener(this);
        mButCheckRecord.setOnClickListener(this);
        mButFinish.setOnClickListener(this);
        if(mTaskInfoBean.getIsNearTask()==1){
            mTvTitle.setText("临时任务-"+mTaskInfoBean.getTunnelId()+"-检查-"+HWApplication.getCheckNameType());
        }else{
            String tunnelName = DBBTunnelDao.getInstance(mContext).getTunnelById(mTaskInfoBean.getTunnelId());
            if(TextUtils.isEmpty(tunnelName)){
                tunnelName="隧道不明";
            }
            mTvTitle.setText(mTaskInfoBean.getCheckNo() + "-" + tunnelName + "-检查-" + HWApplication.getCheckNameType());
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mListInfo = DBCTTunnelVsItemDao.getInstance(mContext).getAllInfoById(mTaskInfoBean.getTunnelId(),mTaskInfoBean.getCheckWayId());
        if(mListInfo!=null&&mListInfo.size()>0){
        }else{
            mListInfo = DBCTTunnelVsItemDao.getInstance(mContext).getAllInfoByCheckId(mTaskInfoBean.getCheckWayId());
        }
        mListInfo = filterData(mListInfo);
        mAdapter = new CheckItemAdapter(mContext,mTaskId,mTaskInfoBean.getTunnelId(),null,null);
        mLvCheckItem.setAdapter(mAdapter);
        mAdapter.setData(mListInfo);
    }

    /**
     * 过滤检查项
     */
    public  List<CTTunnelVsItemBean> filterData( List<CTTunnelVsItemBean> list){
        List<CTTunnelVsItemBean> ctTunnelVsItemBeanList = new ArrayList<>();
        for(CTTunnelVsItemBean bean : list){
            if(DBCTCheckItemVsDiseaseTypeDao.getInstance(mContext).getAllInfoById(bean.getNewId()).size()>0){
                ctTunnelVsItemBeanList.add(bean);
            }
        }
        return ctTunnelVsItemBeanList;
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btn_back:
                finish();
                break;
            case R.id.activity_checklist_finishcheck:
                DBTaskDao.getInstance(mContext).setState(mTaskInfoBean.getNewId(), Constants.TASK_STATE_FINISH, PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                DBTaskDao.getInstance(mContext).setState(mTaskInfoBean.getNearTaskId(), Constants.TASK_STATE_FINISH, PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                finish();
                break;
            case R.id.activity_checklist_checkbut:
                Intent intent = new Intent(mContext,CheckRecordActivity.class);
                intent.putExtra("taskId",mTaskId);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
