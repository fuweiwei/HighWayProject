package com.ty.highway.highwaysystem.ui.widget;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.ty.highway.highwaysystem.R;

/**
 * Created by ${dzm} on 2015/9/17 0017.
 */
public class MSpinner extends Spinner {

    private Context mContext;
    private TextView textView;
    private ArrayList<String> mDataList = new ArrayList<String>();

    public MSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = getContext();
        //为MSpinner设置adapter，主要用于初始化显示spinner的text值
        this.setAdapter(mAdapter);
    }

    public void setMSpinnerData(ArrayList<String> list) {
        this.mDataList = list;
        mAdapter.notifyDataSetChanged();
    }

    public void setTextColor(int colorID){
        textView.setTextColor(colorID);
    }

    public String getText(){
        return null;
    }

    private BaseAdapter mAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return mDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return mDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.common_spinner, null);
            }
            textView = (TextView) convertView.findViewById(R.id.spinner_id);
            if (mDataList.get(position) != null) {
                textView.setText(mDataList.get(position));
            }
            return convertView;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.common_spinner_dropdown_item, null);
            }
            textView = (TextView) convertView.findViewById(R.id.common_spinner_down_item);
            if (mDataList.get(position) != null) {
                textView.setText(mDataList.get(position));
            }
            return convertView;
        }
    };

}

