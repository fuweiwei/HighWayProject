package com.ty.highway.highwaysystem.ui.activity.check;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ty.highway.highwaysystem.R;
import com.ty.highway.highwaysystem.base.Constants;
import com.ty.highway.highwaysystem.base.HWApplication;
import com.ty.highway.highwaysystem.support.bean.basedata.CTDictionaryBean;
import com.ty.highway.highwaysystem.support.bean.check.TaskInfoBean;
import com.ty.highway.highwaysystem.support.db.basedata.DBCTDictionaryDao;
import com.ty.highway.highwaysystem.support.db.check.DBTaskDao;
import com.ty.highway.highwaysystem.support.utils.CommonUtils;
import com.ty.highway.highwaysystem.support.utils.PreferencesUtils;
import com.ty.highway.highwaysystem.ui.activity.basic.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuweiwei on 2015/9/9.
 */
public class AddTaskActivity extends BaseActivity implements View.OnClickListener{
    private EditText mEtName;
    private TextView mTvType, mTvRange;
    private List<CTDictionaryBean> mListDictionary = new ArrayList<>();
    private CTDictionaryBean mCTDictionaryBean = new CTDictionaryBean();
    private List<String>  mListType = new ArrayList<>();
    private List<String>  mListRange = new ArrayList<>();
    private Context mContext = this;
    private ArrayAdapter<String> mAdapterType;
    private ArrayAdapter<String> mAdapterRange;
    private Button mButAdd;
    private String mTunnelId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtask);
        initTitle();
        if(getIntent().getExtras()!=null){
            mTunnelId = getIntent().getExtras().getString("tunnelId");
        }
        initView();
    }
    public void initTitle(){
        setLTBtnText("返回");
        setLTBtnVisiable(View.VISIBLE);
        setRTBtnVisiable(View.INVISIBLE);
        setTitleText("添加临时任务");
        setTitleVisiable(View.VISIBLE);
    }
    public void initView(){
        mEtName = (EditText) findViewById(R.id.activity_addtask_name);
        mTvType = (TextView) findViewById(R.id.activity_addtask_type);
        mTvRange = (TextView) findViewById(R.id.activity_addtask_range);
        mButAdd = (Button) findViewById(R.id.activity_addtask_add);
        mButAdd.setOnClickListener(this);
        mListDictionary = DBCTDictionaryDao.getInstance(mContext).getAllInfo();
        mCTDictionaryBean = mListDictionary.get(HWApplication.getmCheckType() - 1);
        mListType.add(mCTDictionaryBean.getName());
        mListRange.add("检查");
        mTvType.setText(mCTDictionaryBean.getName());
        mTvRange.setText("检查");
       /* mAdapterType = new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_dropdown_item,mListType);
        mAdapterRange = new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_dropdown_item,mListRange);*/


    }
    @Override
    public void onLtBtnClick() {
        super.onLtBtnClick();
        finish();
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.activity_addtask_add:
                if(TextUtils.isEmpty(mEtName.getText().toString().trim())){
                    mEtName.setError("请输入临时任务名称");
                    return;
                }
                if(DBTaskDao.getInstance(mContext).isHasTaskName(mEtName.getText().toString().trim())){
                    mEtName.setError("该任务名称已存在");
                    mEtName.setText("");
                    return;
                }
                TaskInfoBean bean = new TaskInfoBean();
                bean.setCheckNo(mEtName.getText().toString().trim());
                if(mTunnelId==null){
                    bean.setTunnelId("");
                }else{
                    bean.setTunnelId(mTunnelId);
                }
                bean.setCheckWayId(mCTDictionaryBean.getNewId());
                bean.setIsNearTask(1);
                bean.setNewId(CommonUtils.getGuid());
                DBTaskDao.getInstance(mContext).addTaskInfo(bean, PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                finish();
                break;
            default:
                break;
        }
    }
}
