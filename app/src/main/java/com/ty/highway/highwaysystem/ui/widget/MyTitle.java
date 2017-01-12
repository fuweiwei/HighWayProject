package com.ty.highway.highwaysystem.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ty.highway.highwaysystem.R;
import com.ty.highway.highwaysystem.ui.activity.basic.BaseActivity;

/**
 * Created by fuweiwei on 2016/2/20.
 * 基类BaseActivity用到的TitleBar，用来返回页面和展示当前页内容的Title信息
 */
public class MyTitle extends RelativeLayout implements View.OnClickListener {
    private BaseActivity mContext;
    private Button mBtnLeft;
    private TextView mTvTitle;
    private Button mBtnRight;
    public MyTitle(Context context) {
        super(context,null);
    }

    public MyTitle(Context context, AttributeSet attrs) {
        super(context, attrs,0);
    }

    public MyTitle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = (BaseActivity) getContext();
    }
    /**
     * 初始化控件
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        super.onFinishInflate();
        mContext = (BaseActivity) getContext();
        mBtnLeft = (Button) findViewById(R.id.btn_back);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mBtnRight = (Button) findViewById(R.id.btn_add);
        mBtnLeft.setOnClickListener(this);
        mTvTitle.setOnClickListener(this);
        mBtnRight.setOnClickListener(this);
        mTvTitle.setClickable(false);
    }
    /**
     * 右边的控件是否可见
     */
    public void setRTBtnVisiable(int visiable) {
        mBtnRight.setVisibility(visiable);
    }

    /**
     * 右边控件的文版
     */
    public void setRTBtnText(String title) {
        mBtnRight.setText(title);
    }
    /**
     * 左边的控件是否可见
     */
    public void setLTBtnVisiable(int visiable) {
        mBtnLeft.setVisibility(visiable);
    }

    /**
     * 左边控件的文版
     */
    public void setLTBtnText(String title) {
        mBtnLeft.setText(title);
    }
    /**
     * 中间标题是否可见
     */
    public void setTitTvVisiable(int visiable) {
        mTvTitle.setVisibility(visiable);
    }

    /**
     * 中间标题的文版
     */
    public void setTitTvText(String title) {
        mTvTitle.setText(title);
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_back:
                mContext.onLtBtnClick();
                break;
            case R.id.tv_title:
                mContext.onCenterClick();
                break;
            case R.id.btn_add:
                mContext.onRtBtnClick();
                break;
            default:
                break;
        }
    }
}
