package com.ty.highway.highwaysystem.ui.dialog.machine;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.ty.highway.highwaysystem.R;
import com.ty.highway.highwaysystem.base.Constants;
import com.ty.highway.highwaysystem.support.bean.machine.ELMachineBean;
import com.ty.highway.highwaysystem.support.db.machine.DBELMachineDiseaseDao;
import com.ty.highway.highwaysystem.support.listener.BaseCallBack;
import com.ty.highway.highwaysystem.support.utils.PreferencesUtils;
import com.ty.highway.highwaysystem.support.utils.ScreenUtils;
import com.ty.highway.highwaysystem.ui.dialog.BaseDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuweiwei on 2016/01/22.
 */
public class MachineSearchDialog extends  BaseDialog implements View.OnClickListener {
    private SearchView mSv;
    private ListView mLv;
    private TextView mTvTip;
    private Context mContext;
    private  List<ELMachineBean> mListInfo;
    private SearchAdapter mAdapter;
    private String mTaskId;
    private BaseCallBack mCallBack;
    public MachineSearchDialog(Context context,String taskId,List<ELMachineBean> list,BaseCallBack callback) {
        super(context,  R.style.mDialog);
        mContext = context;
        mListInfo = list;
        mTaskId =taskId;
        mCallBack = callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_search);
        initView();
    }
    private void initView(){
        mSv = (SearchView) findViewById(R.id.dialog_search_sv);
        mLv = (ListView) findViewById(R.id.dialog_search_lv);
        mTvTip = (TextView) findViewById(R.id.dialog_search_tip);
        mAdapter = new SearchAdapter();
        mLv.setAdapter(mAdapter);
        mSv.onActionViewExpanded();
        mSv.clearFocus();
        mSv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)) {
                    List<ELMachineBean> data = getFilterData(mListInfo,newText);
                    if(data!=null&&data.size()>0){
                        mTvTip.setVisibility(View.GONE);
                    }else{
                        mTvTip.setVisibility(View.VISIBLE);
                    }
                    mAdapter.setData(data);
                }else{
                    mAdapter.clearData();
                }
                return true;
            }
        });
    }

    /**
     * 获取搜索词配对的数据
     * @param list
     * @param str
     * @return
     */
    public List<ELMachineBean> getFilterData(List<ELMachineBean> list,String str){
        List<ELMachineBean> data = new ArrayList<>();
        for(ELMachineBean bean:list){
            if(bean.getDeviceName().contains(str)){
                data.add(bean);
            }
        }
        return data;
    }
    @Override
    public void onClick(View view) {
        hide();
        int id = view.getId();
        switch (id){
            case R.id.activity_taskdetails_look:

                break;
            case R.id.dialog_taskdetails_cancel:

                break;
            default:
                break;
        }
    }
    public class SearchAdapter extends BaseAdapter{

        private  List<ELMachineBean> mData;
        public  SearchAdapter(){
        }
        @Override
        public int getCount() {
            return mData ==null?0: mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData ==null?null: mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder mViewHolder = null;
            if (convertView == null) {
                mViewHolder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_search_item, null);
                mViewHolder.mMrl = (MaterialRippleLayout) convertView.findViewById(R.id.adapter_search_item_mrl);
                mViewHolder.mTvName = (TextView) convertView.findViewById(R.id.adapter_search_item_name);
                mViewHolder.mTvNum = (TextView) convertView.findViewById(R.id.adapter_search_item_num);
                convertView.setTag(mViewHolder);
            }else{
                mViewHolder = (ViewHolder) convertView.getTag();
            }
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mViewHolder.mMrl.getLayoutParams();
            params.height = ScreenUtils.getScreenHeight(mContext) / 16;
            mViewHolder.mMrl.setLayoutParams(params);
            final ELMachineBean bean = (ELMachineBean) getItem(position);
            if(bean!=null){
            mViewHolder.mMrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MachineSearchDialog.this.hide();
                    mCallBack.callBack(bean);
                }
            });

                mViewHolder.mTvName.setText(bean.getDeviceName());
                int num = DBELMachineDiseaseDao.getInstance(mContext).getDiseaseNum(mTaskId, bean.getNewId(), PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                mViewHolder.mTvNum.setText(num+"");
            }
            return convertView;
        }
        public  void setData( List<ELMachineBean> list){
            mData = list;
            notifyDataSetChanged();
        }
        public  void clearData(){
            mData.clear();
            notifyDataSetChanged();
        }
        public class ViewHolder {
            public TextView mTvName;
            public TextView mTvNum;
            public MaterialRippleLayout mMrl;
        }
    }
}
