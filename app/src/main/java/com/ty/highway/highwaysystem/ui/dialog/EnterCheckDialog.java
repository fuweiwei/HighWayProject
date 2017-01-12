package com.ty.highway.highwaysystem.ui.dialog;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.ty.highway.highwaysystem.R;
import com.ty.highway.highwaysystem.base.Constants;
import com.ty.highway.highwaysystem.support.bean.check.TaskInfoBean;
import com.ty.highway.highwaysystem.support.db.check.DBTaskDao;
import com.ty.highway.highwaysystem.support.utils.PreferencesUtils;
import com.ty.highway.highwaysystem.support.utils.StringUtils;
import com.ty.highway.highwaysystem.support.utils.ToastUtils;
import com.ty.highway.highwaysystem.ui.activity.check.DamageListActivity;
import com.ty.highway.highwaysystem.ui.widget.MSpinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ${dzm} on 2015/9/17 0017.
 */
public class EnterCheckDialog extends BaseDialog implements View.OnClickListener, AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener {

    private TextView checkDate;
    private MSpinner spinnerWeather;
    private Button btnEnterCheck;
    private CheckBox checkBoxWeather;
    private EditText inputWeather;
    private ArrayAdapter<String> adapterWeather;
    private ArrayList<String> listWeather = new ArrayList<String>();
    private TaskInfoBean mTaskInfoBean;
    private String weather = null;
    private String date = null;
    private String mNearId;
    private  int mYear=0,mDay=0,mMonth=0;
    private Intent intent;
    private Context mContext;

    public EnterCheckDialog(Context context, TaskInfoBean mTaskInfoBean) {
        super(context, R.style.mDialog);
        this.mTaskInfoBean = mTaskInfoBean;
        mNearId = mTaskInfoBean.getNearTaskId();
        mContext = context;
    }

    public EnterCheckDialog(Context context, int themeResId) {
        super(context, themeResId);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_enter_check);
        listWeather.add("晴");
        listWeather.add("多云");
        listWeather.add("雷阵雨");
        listWeather.add("小雨");
        listWeather.add("雪");
        initView();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(new Date());
        checkDate.setText(date);
    }

    private void initView() {
        checkDate = (TextView) findViewById(R.id.checkdate);
        checkDate.setOnClickListener(this);
        btnEnterCheck = (Button) findViewById(R.id.btnEnterCheck);
        btnEnterCheck.setOnClickListener(this);
        inputWeather = (EditText) findViewById(R.id.inputWeather);
        checkBoxWeather = (CheckBox) findViewById(R.id.checkboxWeather);
        checkBoxWeather.setOnCheckedChangeListener(this);
        spinnerWeather = (MSpinner) findViewById(R.id.spinner_weather);
        spinnerWeather.setMSpinnerData(listWeather);
        spinnerWeather.setOnItemSelectedListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.checkdate:
                if(mYear==0){
                    Calendar c = Calendar.getInstance();
                    int year = c.get(Calendar.YEAR);
                    int month = c.get(Calendar.MONTH);
                    int day = c.get(Calendar.DAY_OF_MONTH);
                    new DatePickerDialog(getContext(), listener, year, month, day).show();
                }else{
                    new DatePickerDialog(getContext(), listener, mYear, mMonth, mDay).show();
                }
                break;
            case R.id.btnEnterCheck:
                if (updateTask()) {
                    intent = new Intent(getContext(), DamageListActivity.class);
                    intent.putExtra("info", mTaskInfoBean);
                    intent.putExtra("taskId", mTaskInfoBean.getNewId());
                    getContext().startActivity(intent);
                    hide();
                }
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (listWeather != null && listWeather.size() > 0) {
            weather = listWeather.get(i);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private boolean updateTask() {
        date = checkDate.getText().toString();
        if (StringUtils.isEmpty(date)) {
            ToastUtils.show(getContext(), "请选择日期");
            return false;
        }
        if (checkBoxWeather.isChecked()) {
            weather = inputWeather.getText().toString();
            if (StringUtils.isEmpty(weather)) {
                ToastUtils.show(getContext(), "请输入天气");
                return false;
            }
        }
        mTaskInfoBean.setCheckDate(date);
        mTaskInfoBean.setCheckWeather(weather);
        mTaskInfoBean.setUpdateState(1);
        DBTaskDao.getInstance(getContext()).updateTask(mTaskInfoBean, PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
        DBTaskDao.getInstance(getContext()).setState(mNearId, Constants.TASK_STATE_WORKING, PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
        return true;
    }

    public DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            String mMonth = (monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : String.valueOf(monthOfYear + 1);
            String mDay = dayOfMonth < 10 ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
            checkDate.setText(year + "-" + mMonth + "-" + mDay);
        }
    };

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(b){
            spinnerWeather.setClickable(false);
            spinnerWeather.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.et_bg_unclick));
            spinnerWeather.setTextColor(getContext().getResources().getColor(R.color.listbgcolorhs));
            inputWeather.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.et_bg));
            inputWeather.setEnabled(true);
        }else{
            spinnerWeather.setClickable(true);
            spinnerWeather.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.et_bg));
            spinnerWeather.setTextColor(getContext().getResources().getColor(R.color.black));
            inputWeather.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.et_bg_unclick));
            inputWeather.setEnabled(false);
        }
    }
}
