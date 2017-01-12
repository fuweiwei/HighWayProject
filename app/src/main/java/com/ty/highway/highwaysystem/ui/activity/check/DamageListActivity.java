package com.ty.highway.highwaysystem.ui.activity.check;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ty.highway.highwaysystem.R;
import com.ty.highway.highwaysystem.base.Constants;
import com.ty.highway.highwaysystem.base.HWApplication;
import com.ty.highway.highwaysystem.support.adapter.CheckItemAdapter;
import com.ty.highway.highwaysystem.support.adapter.TabFragmentAdapter;
import com.ty.highway.highwaysystem.support.bean.basedata.CTTunnelVsItemBean;
import com.ty.highway.highwaysystem.support.bean.check.TaskInfoBean;
import com.ty.highway.highwaysystem.support.db.basedata.DBBTunnelDao;
import com.ty.highway.highwaysystem.support.db.basedata.DBCTCheckItemVsDiseaseTypeDao;
import com.ty.highway.highwaysystem.support.db.basedata.DBCTTunnelVsItemDao;
import com.ty.highway.highwaysystem.support.db.check.DBTaskDao;
import com.ty.highway.highwaysystem.support.listener.BaseCallBack;
import com.ty.highway.highwaysystem.support.utils.PreferencesUtils;
import com.ty.highway.highwaysystem.ui.activity.basic.BaseActivity;
import com.ty.highway.highwaysystem.ui.fragment.basic.BaseFragment;
import com.ty.highway.highwaysystem.ui.fragment.check.HistroyDamageFragment;
import com.ty.highway.highwaysystem.ui.fragment.check.NowDamageFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuweiwei on 2015/9/10.
 */
public class DamageListActivity extends BaseActivity implements View.OnClickListener{
    private Context mContext =this;
    private CTTunnelVsItemBean mBean;
    private String mTaskId;
    private Button mButShow;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private List<String> mTabList = new ArrayList<>();
    private NowDamageFragment mNowDamageFragment;
    private HistroyDamageFragment mHistroyDamageFragment;
    private BaseCallBack mCallBack;
    private DrawerLayout mDrawerLayout;
    //侧边栏上的控件
    private TextView mTvName;
    private ListView mLvCheckItem;
    private TaskInfoBean mTaskInfoBean;
    private List<CTTunnelVsItemBean> mListInfo = new ArrayList<CTTunnelVsItemBean>();
    private CheckItemAdapter mAdapter;
    private Button mButCheckRecord,mButFinish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_damagelist);
        initTitle();
        Log.v("test", "onCreate-------------");
     //   mBean = (CTTunnelVsItemBean) getIntent().getExtras().getSerializable("info");
        mTaskId = getIntent().getExtras().getString("taskId");
     //   mTunnelID = getIntent().getExtras().getString("tunnelId");
        mTaskInfoBean = (TaskInfoBean) getIntent().getExtras().getSerializable("info");
        initView();
    }
    public void initTitle(){
        setLTBtnText("返回");
        setLTBtnVisiable(View.VISIBLE);
        setRTBtnVisiable(View.VISIBLE);
        setRTBtnText("添加");
        setTitleText("洞口病害列表");
        setTitleVisiable(View.VISIBLE);
    }
    public  void initView(){
        mViewPager = (ViewPager) findViewById(R.id.activity_damagelist_viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.activity_damagelist_tablayout);
        mLvCheckItem = (ListView) findViewById(R.id.activity_checklist_lv);
        mTvName = (TextView) findViewById(R.id.activity_checklist_title);
        mButCheckRecord = (Button) findViewById(R.id.activity_checklist_checkbut);
        mButFinish = (Button) findViewById(R.id.activity_checklist_finishcheck);
        mButShow = (Button) findViewById(R.id.activity_damagelist_show);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        mButCheckRecord.setOnClickListener(this);
        mButFinish.setOnClickListener(this);
        mButShow.setOnClickListener(this);
        if(mTaskInfoBean.getIsNearTask()==1){
            mTvName.setText("临时任务-"+mTaskInfoBean.getTunnelId()+"-"+ HWApplication.getCheckNameType());
        }else{
            String tunnelName = DBBTunnelDao.getInstance(mContext).getTunnelById(mTaskInfoBean.getTunnelId());
            if(TextUtils.isEmpty(tunnelName)){
                tunnelName="隧道不明";
            }
            mTvName.setText(mTaskInfoBean.getCheckNo() + "-" + tunnelName + "-" + HWApplication.getCheckNameType());
        }
        mListInfo = DBCTTunnelVsItemDao.getInstance(mContext).getAllInfoById(mTaskInfoBean.getTunnelId(), mTaskInfoBean.getCheckWayId());
        if(mListInfo!=null&&mListInfo.size()>0){
        }else{
            mListInfo = DBCTTunnelVsItemDao.getInstance(mContext).getAllInfoByCheckId(mTaskInfoBean.getCheckWayId());
        }
        mListInfo = filterData(mListInfo);
        if(mListInfo!=null&&mListInfo.size()<=0){
            return;
        }
        mBean = mListInfo.get(0);
        setTitleText(mBean.getItemName() + "病害列表");
        mTabList.add("   当前病害   ");
        mTabList.add("   历史病害   ");
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        mTabLayout.addTab(mTabLayout.newTab().setText(mTabList.get(0)));//添加tab选项卡
        mTabLayout.addTab(mTabLayout.newTab().setText(mTabList.get(1)));
        List<BaseFragment> fragmentList = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putSerializable("info", mBean);
        bundle.putString("taskId", mTaskId);
        bundle.putString("tunnelId", mTaskInfoBean.getTunnelId());
        mNowDamageFragment = new NowDamageFragment();
        mHistroyDamageFragment  = new HistroyDamageFragment ();
        mNowDamageFragment.setArguments(bundle);
        mHistroyDamageFragment.setArguments(bundle);
        fragmentList.add(mNowDamageFragment);
        fragmentList.add(mHistroyDamageFragment);
        TabFragmentAdapter fragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager(), fragmentList, mTabList);
        mViewPager.setAdapter(fragmentAdapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        mTabLayout.setTabsFromPagerAdapter(fragmentAdapter);//给Tabs设置适配器
        setData();
        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                mAdapter.setData(mListInfo);
            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    /**
     * 填充侧边栏数据
     */
    public void setData(){
        mListInfo = DBCTTunnelVsItemDao.getInstance(mContext).getAllInfoById(mTaskInfoBean.getTunnelId(), mTaskInfoBean.getCheckWayId());
        if(mListInfo!=null&&mListInfo.size()>0){
        }else{
            mListInfo = DBCTTunnelVsItemDao.getInstance(mContext).getAllInfoByCheckId(mTaskInfoBean.getCheckWayId());
        }
        mListInfo = filterData(mListInfo);
        mCallBack = new BaseCallBack() {
            @Override
            public void callBack(Object o) {
                int i = (int) o;
                mBean = mListInfo.get(i);
                setTitleText(mBean.getItemName()+"病害列表");
                mNowDamageFragment.setData(mTaskId,mBean);
                mHistroyDamageFragment.setData(mTaskId,mTaskInfoBean.getTunnelId(),mBean);

            }
        };
        mAdapter = new CheckItemAdapter(mContext,mTaskId,mTaskInfoBean.getTunnelId(),mCallBack,mDrawerLayout);
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
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.activity_checklist_finishcheck:
                mDrawerLayout.closeDrawers();
                DBTaskDao.getInstance(mContext).setState(mTaskInfoBean.getNewId(), Constants.TASK_STATE_FINISH, PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                DBTaskDao.getInstance(mContext).setState(mTaskInfoBean.getNearTaskId(), Constants.TASK_STATE_FINISH, PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                finish();
                break;
            case R.id.activity_checklist_checkbut:
                Intent intent1 = new Intent(mContext,CheckRecordActivity.class);
                intent1.putExtra("taskId", mTaskId);
                startActivity(intent1);
                mDrawerLayout.closeDrawers();
                break;
            case R.id.activity_damagelist_show:
                mDrawerLayout.openDrawer(Gravity.LEFT);
                break;
        }
    }
    @Override
    public void onLtBtnClick() {
        super.onLtBtnClick();
        finish();
    }
    @Override
    public void onRtBtnClick() {
        super.onRtBtnClick();
        Intent intent = new Intent(mContext,AddDamageActivity.class);
        intent.putExtra("info",mBean);
        intent.putExtra("taskId",mTaskId);
        startActivity(intent);
    }
}
