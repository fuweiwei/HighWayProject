package com.ty.highway.highwaysystem.ui.activity.check;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ty.highway.highwaysystem.R;
import com.ty.highway.highwaysystem.base.Constants;
import com.ty.highway.highwaysystem.support.bean.basic.BTunnelBean;
import com.ty.highway.highwaysystem.support.db.basedata.DBBRoadDao;
import com.ty.highway.highwaysystem.support.db.basedata.DBBTunnelDao;
import com.ty.highway.highwaysystem.support.utils.PreferencesUtils;
import com.ty.highway.highwaysystem.ui.activity.basic.BaseActivity;

/**
 * Created by fuweiwei on 2015/12/4.
 */
public class TunnelDetailActivity extends BaseActivity implements View.OnClickListener{
    private TextView mTvName,mTvRoad,mTvCode,mTvStart,mTvEnd,mTvLength,mTvHeigth,mTvWidth,mTvDate,mTvRemarks;
    private BTunnelBean mBean;
    private Context mContext =this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tunneldetails);
        String tunnelId = getIntent().getExtras().getString("tunnelId");
        mBean = DBBTunnelDao.getInstance(mContext).getInfoById(tunnelId, PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
        initView();
    }

    public void initView(){
        mTvCode = (TextView) findViewById(R.id.activity_tunneldetails_code);
        mTvName = (TextView) findViewById(R.id.activity_tunneldetails_name);
        mTvRoad = (TextView) findViewById(R.id.activity_tunneldetails_road);
        mTvStart = (TextView) findViewById(R.id.activity_tunneldetails_start);
        mTvEnd = (TextView) findViewById(R.id.activity_tunneldetails_end);
        mTvLength = (TextView) findViewById(R.id.activity_tunneldetails_length);
        mTvHeigth = (TextView) findViewById(R.id.activity_tunneldetails_heigth);
        mTvWidth = (TextView) findViewById(R.id.activity_tunneldetails_width);
        mTvDate = (TextView) findViewById(R.id.activity_tunneldetails_date);
        mTvRemarks = (TextView) findViewById(R.id.activity_tunneldetails_remarks);
        findViewById(R.id.btn_back).setOnClickListener(this);
        if(mBean==null){
            return;
        }
        mTvName.setText(mBean.getTunnelName());
        mTvCode.setText(mBean.getTunnelCode());
        mTvRoad.setText(DBBRoadDao.getInstance(mContext).getRoadNameById(mBean.getRoadId()));
        mTvStart.setText(mBean.getStartMileage());
        mTvEnd.setText(mBean.getEndMileage());
        mTvLength.setText(mBean.getTunnelLength()+"");
        mTvHeigth.setText(mBean.getTunnelHeight()+"");
        mTvWidth.setText(mBean.getTunnelWidth()+"");
        mTvDate.setText(mBean.getCompletionDate());
        mTvRemarks.setText(mBean.getRemark());
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btn_back:
                finish();
                break;
            default:
                break;
        }
    }
}
