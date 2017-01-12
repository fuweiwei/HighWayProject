package com.ty.highway.highwaysystem.ui.activity.machine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ty.highway.frameworklibrary.support.zxing.CaptureActivity;
import com.ty.highway.highwaysystem.R;
import com.ty.highway.highwaysystem.base.Constants;
import com.ty.highway.highwaysystem.support.adapter.TabFragmentAdapter;
import com.ty.highway.highwaysystem.support.adapter.machine.MachineCheckItemAdapter;
import com.ty.highway.highwaysystem.support.bean.machine.ELMachineBean;
import com.ty.highway.highwaysystem.support.bean.machine.ELMachineTaskBean;
import com.ty.highway.highwaysystem.support.bean.machine.ELMachineTypeByItemBean;
import com.ty.highway.highwaysystem.support.bean.machine.ELTunnelDeployBean;
import com.ty.highway.highwaysystem.support.db.basedata.DBBTunnelDao;
import com.ty.highway.highwaysystem.support.db.machine.DBELMachineByTypeDao;
import com.ty.highway.highwaysystem.support.db.machine.DBELMachineDao;
import com.ty.highway.highwaysystem.support.db.machine.DBELMachineItemByContentDao;
import com.ty.highway.highwaysystem.support.db.machine.DBELMachineTaskDao;
import com.ty.highway.highwaysystem.support.db.machine.DBELMachineTypeByItemDao;
import com.ty.highway.highwaysystem.support.db.machine.DBELTunnelDeployDao;
import com.ty.highway.highwaysystem.support.listener.BaseCallBack;
import com.ty.highway.highwaysystem.support.utils.PreferencesUtils;
import com.ty.highway.highwaysystem.support.utils.ToastUtils;
import com.ty.highway.highwaysystem.ui.activity.basic.BaseActivity;
import com.ty.highway.highwaysystem.ui.dialog.machine.MachineSearchDialog;
import com.ty.highway.highwaysystem.ui.fragment.basic.BaseFragment;
import com.ty.highway.highwaysystem.ui.fragment.machine.MachineDamageFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuweiwei on 2016/1/6.
 */
public class MachineDamageListActivity extends BaseActivity implements View.OnClickListener{
    private Button mButShow;
    private FloatingActionButton mFbtnSear,mFbtnZx;
    private String mTaskId;
    private String mCheckWayId;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private List<String> mTabList = new ArrayList<>();
    private DrawerLayout mDrawerLayout;
    private BaseCallBack mCallBack;
    private ELMachineBean mBean = new ELMachineBean();
    private ELMachineTaskBean mTaskBean = new ELMachineTaskBean();
    private ELMachineTypeByItemBean mItemBean = new ELMachineTypeByItemBean();
    private List<ELMachineTypeByItemBean> mListItem = new ArrayList<>();
    //侧边栏上的控件
    private TextView mTvName;
    private ListView mLvCheckItem;
    private ELMachineTaskBean mTaskInfoBean = new ELMachineTaskBean();
    private List<ELMachineBean> mListInfo = new ArrayList<ELMachineBean>();
    private MachineCheckItemAdapter mAdapter;
    private Button mButCheckRecord,mButFinish;
    private Context mContext = MachineDamageListActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine_damagelist);
        initTitle();
        mTaskId = getIntent().getExtras().getString("taskId");
        mTaskInfoBean = (ELMachineTaskBean) getIntent().getExtras().getSerializable("info");
        mTaskBean = DBELMachineTaskDao.getInstance(mContext).getTaskById(mTaskId,PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
        mCheckWayId = mTaskBean.getCheckWayId();
        initView();

    }
    public void initTitle(){
        setLTBtnText("返回");
        setLTBtnVisiable(View.VISIBLE);
        setRTBtnVisiable(View.VISIBLE);
        setTitleText("检修列表");
        setRTBtnText("添加");
        setTitleVisiable(View.VISIBLE);
    }
    public  void initView(){
        mViewPager = (ViewPager) findViewById(R.id.activity_damagelist_viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.activity_damagelist_tablayout);
        mFbtnZx = (FloatingActionButton) findViewById(R.id.activity_damagelist_zxing);
        mFbtnSear = (FloatingActionButton) findViewById(R.id.activity_damagelist_search);
        mLvCheckItem = (ListView) findViewById(R.id.activity_checklist_lv);
        mTvName = (TextView) findViewById(R.id.activity_checklist_title);
        mButCheckRecord = (Button) findViewById(R.id.activity_checklist_checkbut);
        mButFinish = (Button) findViewById(R.id.activity_checklist_finishcheck);
        mButShow = (Button) findViewById(R.id.activity_damagelist_show);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        mButCheckRecord.setOnClickListener(this);
        mButFinish.setOnClickListener(this);
        mButShow.setOnClickListener(this);
        mFbtnZx.setOnClickListener(this);
        mFbtnSear.setOnClickListener(this);
        String tunnelName = DBBTunnelDao.getInstance(mContext).getTunnelById(mTaskInfoBean.getTunnelId());
        if(TextUtils.isEmpty(tunnelName)){
            tunnelName="隧道不明";
        }
        mTvName.setText(mTaskInfoBean.getCheckNo() + "-" + tunnelName + "-机电检修");
        List<ELTunnelDeployBean> list = DBELTunnelDeployDao.getInstance(mContext).getDataByTunnelId(mTaskInfoBean.getTunnelId());
        for(ELTunnelDeployBean bean:list){

            if(bean.getDeviceType()==0){
                ELMachineBean elMachineBean = DBELMachineDao.getInstance(mContext).getDataByNewId(bean.getMinDeviceId());
                if(elMachineBean.getNewId()!=null){
                    elMachineBean.setMMachineDeviceDId(bean.getNewId());
                    mListInfo.add(elMachineBean);
                }
            }else if(bean.getDeviceType()==1){
                ELMachineBean elMachineBean = DBELMachineDao.getInstance(mContext).getDataByNewId(bean.getMaxDeviceId());
                if(elMachineBean.getNewId()!=null){
                    elMachineBean.setMMachineDeviceDId(bean.getNewId());
                    mListInfo.add(elMachineBean);
                }
            }
        }
        mBean = mListInfo.get(0);
        setData();
    }

    public  void setData(){
        if(mListInfo.size()<1){
            return;
        }
        mListItem = DBELMachineTypeByItemDao.getInstance(mContext).getDataByRWayId(DBELMachineByTypeDao.getInstance(mContext).getDataByTypeId(mBean.getMMachineTypeId(),mCheckWayId,mTaskBean.getTunnelId()).getNewId());
        List<ELMachineTypeByItemBean> removeList = new ArrayList<>();
        for(ELMachineTypeByItemBean bean:mListItem){
            if(DBELMachineItemByContentDao.getInstance(mContext).getDataByItemId(bean.getNewId()).size()<=0){
                removeList.add(bean);
            }
        }
        mListItem.removeAll(removeList);
        if(mListItem.size()>0){
            mItemBean = mListItem.get(0);
        }
        if(mItemBean.getMMachineItemName()!=null){
            setTitleText(mBean.getDeviceName() + "-" + mItemBean.getMMachineItemName() + "-检修列表");
        }else{
            setTitleText(mBean.getDeviceName() + "无检查项目");
        }
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        List<BaseFragment> fragmentList = new ArrayList<>();
        for(ELMachineTypeByItemBean bean:mListItem){
            mTabList.add(bean.getMMachineItemName());
            mTabLayout.addTab(mTabLayout.newTab().setText(bean.getMMachineItemName()));//添加tab选项卡
            MachineDamageFragment fragment = new MachineDamageFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("itemInfo",bean);
            bundle.putSerializable("machineInfo", mBean);
            bundle.putString("taskId", mTaskId);
            fragment.setArguments(bundle);
            fragmentList.add(fragment);
        }
        TabFragmentAdapter fragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager(), fragmentList, mTabList);
        mViewPager.setAdapter(fragmentAdapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        mTabLayout.setTabsFromPagerAdapter(fragmentAdapter);//给Tabs设置适配器
        mCallBack = new BaseCallBack() {
            @Override
            public void callBack(Object o) {
                mBean = (ELMachineBean) o;
                setData();
            }
        };
        mAdapter = new MachineCheckItemAdapter(mContext,mTaskId,mTaskInfoBean.getTunnelId(),mCallBack,mDrawerLayout);
        mLvCheckItem.setAdapter(mAdapter);
        mAdapter.setData(mListInfo);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(mListItem.get(position).getMMachineItemName()!=null){
                    setTitleText(mBean.getDeviceName() + "-" + mListItem.get(position).getMMachineItemName() + "-检修列表");
                }else{
                    setTitleText(mBean.getDeviceName()+"无检查项目");
                }
                mItemBean = mListItem.get(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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

    @Override
    protected void onResume() {
        super.onResume();
        String zXingId = PreferencesUtils.getString(mContext,"ZXingId","");
        if(!TextUtils.isEmpty(zXingId)){
            mBean = DBELMachineDao.getInstance(mContext).getDataByNewId(zXingId);
            for(ELMachineBean bean:mListInfo){
                if(bean.getNewId().equals(zXingId)){
                    setData();
                    PreferencesUtils.putString(mContext, "ZXingId", "");
                    return;
                }
            }
            ToastUtils.show(mContext,"该任务下没有该设备");
            PreferencesUtils.putString(mContext, "ZXingId", "");
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.activity_checklist_finishcheck:
                mDrawerLayout.closeDrawers();
                DBELMachineTaskDao.getInstance(mContext).setState(mTaskId, Constants.TASK_STATE_FINISH, PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                finish();
                break;
            case R.id.activity_checklist_checkbut:
                Intent intent1 = new Intent(mContext,MachineCheckRecordActivity.class);
                intent1.putExtra("taskId", mTaskId);
                startActivity(intent1);
                mDrawerLayout.closeDrawers();
                break;
            case R.id.activity_damagelist_show:
                mDrawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.activity_damagelist_zxing:
                Intent intent2 = new Intent(MachineDamageListActivity.this, CaptureActivity.class);
                startActivity(intent2);
                break;
            case R.id.activity_damagelist_search:
                MachineSearchDialog machineSearchDialog = new MachineSearchDialog(mContext,mTaskId,mListInfo,mCallBack);
                machineSearchDialog.show();
                break;
            default:
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
        Intent intent = new Intent(MachineDamageListActivity.this,MachineAddDamageActivity.class);
        intent.putExtra("taskInfo",mTaskInfoBean);
        intent.putExtra("itemInfo",mItemBean);
        intent.putExtra("machineInfo", mBean);
        startActivity(intent);
    }
}
