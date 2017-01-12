package com.ty.highway.highwaysystem.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ty.highway.highwaysystem.R;
import com.ty.highway.highwaysystem.support.bean.basic.BTunnelBean;
import com.ty.highway.highwaysystem.support.db.basedata.DBBRoadDao;

/**
 * Created by fuweiwei on 2015/12/29.
 */
public class TunnelDetailDialog extends  BaseDialog implements View.OnClickListener{
    private TextView mTvName,mTvRoad,mTvCode,mTvStart,mTvEnd,mTvLength,mTvHeigth,mTvWidth,mTvDate,mTvRemarks;
    private BTunnelBean mBean;
    private Context mContext ;
    private Button mButCancel;
    public TunnelDetailDialog(Context context, BTunnelBean bean) {
        super(context,  R.style.mDialog);
        mContext = context;
        mBean = bean;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_tunneldetails);
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
        mButCancel = (Button) findViewById(R.id.activity_tunneldetails_cancel);
        mTvRemarks = (TextView) findViewById(R.id.activity_tunneldetails_remarks);
        mButCancel.setOnClickListener(this);
        if(mBean==null){
            return;
        }
        mTvName.setText(mBean.getTunnelName());
        mTvCode.setText(mBean.getTunnelCode());
        mTvRoad.setText(DBBRoadDao.getInstance(mContext).getRoadNameById(mBean.getRoadId()));
        mTvStart.setText(mBean.getStartMileage());
        mTvEnd.setText(mBean.getEndMileage());
        mTvLength.setText(mBean.getTunnelLength()+" m");
        mTvHeigth.setText(mBean.getTunnelHeight()+" m");
        mTvWidth.setText(mBean.getTunnelWidth()+" m");
        mTvDate.setText(mBean.getCompletionDate());
        mTvRemarks.setText(mBean.getRemark());
    }
    @Override
    public void onClick(View view) {
        hide();
        int id = view.getId();
        switch (id){
            case R.id.activity_tunneldetails_cancel:
                break;
            default:
                break;
        }
    }
}
