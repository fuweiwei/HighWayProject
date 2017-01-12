package com.ty.highway.highwaysystem.ui.activity.check;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ty.highway.highwaysystem.R;
import com.ty.highway.highwaysystem.base.Constants;
import com.ty.highway.highwaysystem.base.HWApplication;
import com.ty.highway.highwaysystem.support.bean.basic.BTunnelBean;
import com.ty.highway.highwaysystem.support.bean.check.TaskInfoBean;
import com.ty.highway.highwaysystem.support.db.basedata.DBBRoadDao;
import com.ty.highway.highwaysystem.support.db.basedata.DBBTunnelDao;
import com.ty.highway.highwaysystem.support.utils.PreferencesUtils;
import com.ty.highway.highwaysystem.support.utils.exception.ExceptionUtils;
import com.ty.highway.highwaysystem.ui.activity.basic.BaseActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by ${dzm} on 2015/9/15 0015.
 */
public class TaskDetailActivity extends BaseActivity implements View.OnClickListener{

    private TextView inputCheckCode;
    private TextView inputTunnelName;
    private TextView inputSectionName;
    private TextView inputCheckRange;
    private TextView inputCheckType;
    private TextView inputCheckDate;
    private TextView inputCheckWeather;
    private TextView inputCheckStaff;
    private TextView inputEnterStaff;
    private TextView inputOrganize;
    private TextView mTvLook;
    private TaskInfoBean mTaskInfoBena;
    private Context mContext =this;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taskdetails);
        mTaskInfoBena = (TaskInfoBean) getIntent().getExtras().getSerializable("info");
        initView();
    }

    private void initView(){
        inputCheckCode = (TextView)findViewById(R.id.activity_taskdetails_checkno);
        inputTunnelName = (TextView)findViewById(R.id.activity_taskdetails_tunnelname);
        inputSectionName = (TextView)findViewById(R.id.activity_taskdetails_section_name);
        inputCheckRange = (TextView)findViewById(R.id.activity_taskdetails_check_range);
        inputCheckType = (TextView)findViewById(R.id.activity_taskdetails_check_type);
        inputCheckDate = (TextView)findViewById(R.id.activity_taskdetails_check_date);
        inputCheckWeather = (TextView)findViewById(R.id.activity_taskdetails_check_weather);
        inputCheckStaff = (TextView)findViewById(R.id.activity_taskdetails_check_staff);
        inputEnterStaff = (TextView)findViewById(R.id.activity_taskdetails_enter_staff);
        inputOrganize = (TextView)findViewById(R.id.activity_taskdetails_organize);
        mTvLook = (TextView)findViewById(R.id.activity_taskdetails_look);
        findViewById(R.id.btn_back).setOnClickListener(this);
        mTvLook.setOnClickListener(this);
        inputCheckCode.setText(mTaskInfoBena.getCheckNo());
        BTunnelBean info = DBBTunnelDao.getInstance(mContext).getInfoById(mTaskInfoBena.getTunnelId(),PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
        inputTunnelName.setText(info.getTunnelName());
        inputSectionName.setText(DBBRoadDao.getInstance(mContext).getRoadNameById(info.getRoadId()));
        inputCheckRange.setText("检查");
        inputCheckType.setText(HWApplication.getCheckNameType());
        try {
            inputCheckDate.setText(format.format(format.parse(mTaskInfoBena.getCheckDate())));
        } catch (ParseException e) {
            new ExceptionUtils().doExecInfo(e.toString(), mContext);
        }
        inputCheckWeather.setText(mTaskInfoBena.getCheckWeather());
        inputCheckStaff.setText(mTaskInfoBena.getCheckEmp());
        inputEnterStaff.setText(PreferencesUtils.getString(mContext, Constants.SP_USER_NAME));
        inputOrganize.setText(mTaskInfoBena.getMaintenanceOrgan());
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();  
        switch (id){
            case R.id.btn_back:
                finish();
                break;
            case R.id.activity_taskdetails_look:
                Intent intent = new Intent(mContext,TunnelDetailActivity.class);
                intent.putExtra("tunnelId",mTaskInfoBena.getTunnelId());
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
