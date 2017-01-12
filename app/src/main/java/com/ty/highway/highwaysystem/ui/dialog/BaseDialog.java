package com.ty.highway.highwaysystem.ui.dialog;

import android.app.Dialog;
import android.content.Context;

import com.ty.highway.highwaysystem.support.utils.exception.ExceptionUtils;
import com.ty.highway.highwaysystem.ui.widget.sportsdialog.SpotsDialog;

/**
 * Created by fuweiwei on 2015/12/28.
 */
public class BaseDialog extends Dialog {
    //下载进度提示框
    protected TaskProgressDialog mProrgessDialog;
    //缓冲提示框
    protected SpotsDialog mSpotsDialog;
    private Context mContext ;
    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
        mContext =context;
    }
    /**
     * 显示进度对话框
     */
    public TaskProgressDialog showProgressDialog(String str, boolean iscan){
        try {
            mProrgessDialog = new TaskProgressDialog(mContext);
            mProrgessDialog.show();
            mProrgessDialog.setContent(str);
            mProrgessDialog.setCancelable(true);
            mProrgessDialog.setCanceledOnTouchOutside(iscan);
        } catch (Exception e) {
            new ExceptionUtils().doExecInfo(e.toString(), mContext);
        }
        return mProrgessDialog;

    }

    public void setDialogProgress(int progress){
        mProrgessDialog.setProgress(progress);
    }
    /*
     * 显示进度对话框
     */
    public TaskProgressDialog showProgressDialog(){
        try {
            mProrgessDialog = new TaskProgressDialog(mContext);
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
     * 隐藏进度对话框
     */
    public void  hideProgressDialog(){
        if (mProrgessDialog != null && mProrgessDialog.isShowing()) {
            mProrgessDialog.dismiss();
        }
    }

    /**
     * 显示缓冲框
     * @param str 弹出框内容
     * @param iscan 点击外边是否取消弹出框
     * @return
     */
    public SpotsDialog showSpotsDialog(String str,Boolean iscan){
        try {
            mSpotsDialog = new SpotsDialog(mContext);
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
     * 显示缓冲框
     * @return
     */
    public SpotsDialog showSpotsDialog(){
        try {
            mSpotsDialog = new SpotsDialog(mContext);
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
     * 隐藏缓冲对话框
     */
    public void  hideSpotsDialog(){
        if (mSpotsDialog != null && mSpotsDialog.isShowing()) {
            mSpotsDialog.dismiss();
        }
    }
}
