package com.ty.highway.highwaysystem.ui.activity.machine;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.ty.highway.highwaysystem.R;
import com.ty.highway.highwaysystem.support.adapter.TabFragmentAdapter;
import com.ty.highway.highwaysystem.support.db.basedata.DBBTunnelDao;
import com.ty.highway.highwaysystem.ui.activity.basic.BaseActivity;
import com.ty.highway.highwaysystem.ui.fragment.basic.BaseFragment;
import com.ty.highway.highwaysystem.ui.fragment.machine.MachineTaskTabFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuweiwei on 2016/1/5.
 */
public class MachineTaskListActivity extends BaseActivity implements View.OnClickListener{
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Button mPrAddtask;
    private Context mContext = MachineTaskListActivity.this;
    private Boolean flag = false,isAll=false;
    private List<String> mTabList = new ArrayList<>();
    private String mTunenlId;
    public static boolean isUpload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasklist);
        initTitle();
        if(getIntent().getExtras()!=null){
            mTunenlId = getIntent().getExtras().getString("tunnelId");
            isAll = getIntent().getExtras().getBoolean("isAll",false);
        }
        init();
    }
    public void initTitle(){
        setLTBtnText("返回");
        setLTBtnVisiable(View.VISIBLE);
        setRTBtnVisiable(View.INVISIBLE);
        setTitleText("任务列表");
        setTitleVisiable(View.VISIBLE);
    }
    public void init(){
        mViewPager = (ViewPager) findViewById(R.id.activity_tasklist_viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.activity_tasklist_tablayout);
        mPrAddtask = (Button) findViewById(R.id.btn_add);
        mPrAddtask.setVisibility(View.INVISIBLE);
        if(!TextUtils.isEmpty(mTunenlId)){
            setTitleText(DBBTunnelDao.getInstance(mContext).getTunnelById(mTunenlId) + "-机电检修-任务列表");
        }else{
            setTitleText("机电检修-任务列表");
        }
        initView();


    }
    public void initView(){
        mTabList.add("  全部  ");
        mTabList.add(" 未开始 ");
        mTabList.add(" 进行中 ");
        mTabList.add(" 已完成 ");
        mTabList.add(" 已上传 ");
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        mTabLayout.addTab(mTabLayout.newTab().setText(mTabList.get(0)));//添加tab选项卡
        mTabLayout.addTab(mTabLayout.newTab().setText(mTabList.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTabList.get(2)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTabList.get(3)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTabList.get(4)));
        List<BaseFragment> fragmentList = new ArrayList<>();
        for (int i = 0; i < mTabList.size(); i++) {
            MachineTaskTabFragment f1 = new MachineTaskTabFragment();
            Bundle bundle = new Bundle();
            bundle.putString("tunnelId", mTunenlId);
            bundle.putInt("tag", i-1);
            bundle.putBoolean("isAll", isAll);
            f1.setArguments(bundle);
            fragmentList.add(f1);
        }
        TabFragmentAdapter fragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager(), fragmentList, mTabList);
        mViewPager.setAdapter(fragmentAdapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        mTabLayout.setTabsFromPagerAdapter(fragmentAdapter);//给Tabs设置适配器
        flag =true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(flag){
            List<BaseFragment> fragmentList = new ArrayList<>();
            for (int i = 0; i < mTabList.size(); i++) {
                MachineTaskTabFragment f1 = new MachineTaskTabFragment();
                Bundle bundle = new Bundle();
                bundle.putString("tunnelId", mTunenlId);
                bundle.putInt("tag", i - 1);
                bundle.putBoolean("isAll", isAll);
                f1.setArguments(bundle);
                fragmentList.add(f1);
            }
            TabFragmentAdapter fragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager(), fragmentList, mTabList);
            mViewPager.setAdapter(fragmentAdapter);//给ViewPager设置适配器
            mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
            mTabLayout.setTabsFromPagerAdapter(fragmentAdapter);//给Tabs设置适配器
        }
    }

    @Override
    public void onClick(View view) {
    }
    @Override
    public void onLtBtnClick() {
        super.onLtBtnClick();
        finish();
    }

}
