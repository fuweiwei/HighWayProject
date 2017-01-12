package com.ty.highway.highwaysystem.ui.fragment.basic;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;

import com.ty.highway.highwaysystem.support.utils.exception.ExceptionUtils;
import com.ty.highway.highwaysystem.ui.dialog.TaskProgressDialog;
import com.ty.highway.highwaysystem.ui.widget.sportsdialog.SpotsDialog;

/**
 * Created by fuweiwei on 2015/9/2.
 * 基类Fragment 所有Fragment都继承它
 */
public class BaseFragment extends Fragment{

    //下载进度提示框
    protected TaskProgressDialog mProrgessDialog;
    //缓冲提示框
    protected SpotsDialog mSpotsDialog;
    private Context mContext;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();

    }
    /**
     *
     * @Title: showProgress
    //	 * @Description: TODO(显示进度对话框)
     */
    public TaskProgressDialog showProgressDialog(String str, boolean iscan){
        try {
            mProrgessDialog = new TaskProgressDialog(getActivity());
            mProrgessDialog.show();
            mProrgessDialog.setContent(str);
            mProrgessDialog.setCancelable(true);
            mProrgessDialog.setCanceledOnTouchOutside(iscan);
        } catch (Exception e) {
            new ExceptionUtils().doExecInfo(e.toString(), mContext);
        }
        return mProrgessDialog;

    }
    /**
     *
     * @Title: showProgress
    //	 * @Description: TODO(显示进度对话框)
     */
    public TaskProgressDialog showProgressDialog(){
        try {
            mProrgessDialog = new TaskProgressDialog(getActivity());
            mProrgessDialog.show();
            mProrgessDialog.setContent("正在加载...");
            mProrgessDialog.setCancelable(true);
            mProrgessDialog.setCanceledOnTouchOutside(false);
        } catch (Exception e) {
            new ExceptionUtils().doExecInfo(e.toString(), mContext);
        }
        return mProrgessDialog;

    }
    /**
     *
     * @Title: hideProgress
     * @Description: TODO(隐藏进度对话框)
     */
    public void  hideProgressDialog(){
        if (mProrgessDialog != null && mProrgessDialog.isShowing()) {
            mProrgessDialog.dismiss();
        }
    }

    /**
     * @param str 弹出框内容
     * @param iscan 点击外边是否取消弹出框
     * @return
     */
    public SpotsDialog showSpotsDialog(String str,Boolean iscan){
        try {
            mSpotsDialog = new SpotsDialog(getActivity());
            mSpotsDialog.show();
            mSpotsDialog.setContent(str);
            mSpotsDialog.setCancelable(true);
            mSpotsDialog.setCanceledOnTouchOutside(iscan);
        } catch (Exception e) {
            new ExceptionUtils().doExecInfo(e.toString(), mContext);
        }

        return mSpotsDialog;
    }
    /**
     * @return
     */
    public SpotsDialog showSpotsDialog(){
        try {
            mSpotsDialog = new SpotsDialog(getActivity());
            mSpotsDialog.show();
            mSpotsDialog.setContent("正在加载");
            mSpotsDialog.setCancelable(true);
            mSpotsDialog.setCanceledOnTouchOutside(false);
        } catch (Exception e) {
            new ExceptionUtils().doExecInfo(e.toString(), mContext);
        }

        return mSpotsDialog;
    }
    /**
     *
     * @Title: hideSpotsDialog
     * @Description: TODO(隐藏缓冲对话框)
     */
    public void  hideSpotsDialog(){
        if (mSpotsDialog != null && mSpotsDialog.isShowing()) {
            mSpotsDialog.dismiss();
        }
    }

    private MHandler handler = new MHandler();

    protected void handleOtherMessage(int flag) {
        switch (flag) {
        }
    }

    public void sendMessage(int flag) {
        handler.sendEmptyMessage(flag);
    }

    public void sendMessageDely(int flag, long delayMillis) {
        handler.sendEmptyMessageDelayed(flag, delayMillis);
    }



    public interface ICallbackResult {
        public void OnBackResult(Object result);
    }


    private class MHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (!Thread.currentThread().isInterrupted()) {
                switch (msg.what) {
                    default:
                        handleOtherMessage(msg.what);
                }
            }
        }

    }


}
