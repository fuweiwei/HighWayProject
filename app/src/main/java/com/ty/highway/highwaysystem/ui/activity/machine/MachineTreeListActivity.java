package com.ty.highway.highwaysystem.ui.activity.machine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.ty.highway.highwaysystem.R;
import com.ty.highway.highwaysystem.base.Constants;
import com.ty.highway.highwaysystem.support.adapter.SimpleTreeAdapter;
import com.ty.highway.highwaysystem.support.bean.basic.BRoadBean;
import com.ty.highway.highwaysystem.support.bean.basic.BSectionBean;
import com.ty.highway.highwaysystem.support.bean.basic.BTunnelBean;
import com.ty.highway.highwaysystem.support.bean.basic.BaseTreeBean;
import com.ty.highway.highwaysystem.support.comparator.RoadComparator;
import com.ty.highway.highwaysystem.support.db.basedata.DBBRoadDao;
import com.ty.highway.highwaysystem.support.db.basedata.DBBSectionDao;
import com.ty.highway.highwaysystem.support.db.basedata.DBBTunnelDao;
import com.ty.highway.highwaysystem.support.db.machine.DBELMachineTaskDao;
import com.ty.highway.highwaysystem.support.utils.PreferencesUtils;
import com.ty.highway.highwaysystem.support.utils.tree.Node;
import com.ty.highway.highwaysystem.support.utils.tree.TreeListViewAdapter;
import com.ty.highway.highwaysystem.ui.activity.basic.BaseActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by fuweiwei on 2015/12/24.
 */
public class MachineTreeListActivity extends BaseActivity implements View.OnClickListener{
    private ListView mLvTree;
    private TreeListViewAdapter mAdapter;
    private Context mContext = this;
    private List<BaseTreeBean> mDatas = new ArrayList<BaseTreeBean>();
    private String mCheckWayId;
    private ExecutorService mExecutorService = Executors.newCachedThreadPool();
    private int mCount;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treelist);
        initTitle();
        mLvTree = (ListView) findViewById(R.id.activity_treelist_lv);
        mCheckWayId = getIntent().getExtras().getString("checkWayId");
    }
    public void initTitle(){
        setLTBtnText("返回");
        setLTBtnVisiable(View.VISIBLE);
        setRTBtnVisiable(View.VISIBLE);
        setRTBtnText("跳过");
        setTitleText("贵州省高速结构图");
        setTitleVisiable(View.VISIBLE);
    }
    public void initData(){
        mDatas.clear();
        showSpotsDialog("请稍候...", false);
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                int allNum = DBELMachineTaskDao.getInstance(mContext).getTaskByCheckWay(PreferencesUtils.getString(mContext, Constants.SP_USER_ID), mCheckWayId).size();
                mDatas.add(new BaseTreeBean("1","0","贵州省高速公路",allNum));
                //添加路线
                List<BRoadBean> roadList = DBBRoadDao.getInstance(mContext).getAllData();
                for (BRoadBean bean:roadList){
                    int roadNum = DBELMachineTaskDao.getInstance(mContext).getTaskCountByRoad(mCheckWayId, PreferencesUtils.getString(mContext, Constants.SP_USER_ID), bean.getNewId());
                    mDatas.add(new BaseTreeBean(bean.getNewId(), "1", "路线：" + bean.getRoadCode() + "—" + bean.getRoadName(), roadNum));
                }
                Collections.sort(mDatas, new RoadComparator());
                mCount++;
                sendMessage(Constants.SUCCESS);
            }
        });

        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                //添加区段
                List<BSectionBean> sectionList = DBBSectionDao.getInstance(mContext).getAllData();
                for (BSectionBean bean:sectionList){
                    int sectionNum = DBELMachineTaskDao.getInstance(mContext).getTaskCountBySection(mCheckWayId, PreferencesUtils.getString(mContext, Constants.SP_USER_ID), bean.getNewId());
                    mDatas.add(new BaseTreeBean(bean.getNewId(),bean.getRoadId(),"区段："+bean.getSectionName(),sectionNum));
                }
                mCount++;
                sendMessage(Constants.SUCCESS);

            }
        });
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                //添加隧道
                List<BTunnelBean> tunenlList = DBBTunnelDao.getInstance(mContext).getAllTunnel(PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                for (BTunnelBean bean : tunenlList) {
                    int tunnelNum = DBELMachineTaskDao.getInstance(mContext).getTaskByTunenlId(PreferencesUtils.getString(mContext, Constants.SP_USER_ID), mCheckWayId, bean.getNewId()).size();
                    mDatas.add(new BaseTreeBean(bean.getNewId(), bean.getSectionId(), "隧道：" + bean.getTunnelName(), tunnelNum));
                }
                mCount++;
                sendMessage(Constants.SUCCESS);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    protected void handleOtherMessage(int flag) {
        super.handleOtherMessage(flag);
        switch (flag){
            case Constants.SUCCESS:
                if(mCount==3){
                    mCount=0;
                    hideSpotsDialog();
                    try
                    {
                        mAdapter = new SimpleTreeAdapter<BaseTreeBean>(mLvTree, mContext, mDatas, 3);
                        mAdapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener()
                        {
                            @Override
                            public void onClick(Node node, int position)
                            {
                                if (node.isLeaf()&&node.getLevel()==3)
                                {
                                    Intent intent  = new Intent(mContext,MachineTaskListActivity.class);
                                    intent.putExtra("tunnelId",node.getId());
                                    intent.putExtra("isAll",false);
                                    startActivity(intent);
                                }
                            }

                        });

                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    mLvTree.setAdapter(mAdapter);
                }
        }
    }

    @Override
    public void onClick(View v) {
        int id =v.getId();
    }
    @Override
    public void onLtBtnClick() {
        super.onLtBtnClick();
        finish();
    }

    @Override
    public void onRtBtnClick() {
        super.onRtBtnClick();
        Intent intent  = new Intent(mContext,MachineTaskListActivity.class);
        intent.putExtra("isAll",true);
        startActivity(intent);
    }
}
