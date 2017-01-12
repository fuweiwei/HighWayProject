/**
 * 
 */
package com.ty.highway.highwaysystem.ui.dialog;


import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.ty.highway.highwaysystem.R;
import com.ty.highway.highwaysystem.ui.widget.NumberProgressBar;


/** 
 * @ClassName: ProgtessDialog 
 * @Description: TODO(进度加载对话框) 
 * @author fuweiwei
 * @date 2015-7-28 下午5:24:27 
 *  
 */
public class TaskProgressDialog extends BaseDialog {
	private NumberProgressBar mNumberProgressBar;
	private TextView mTvContent;

	public TaskProgressDialog(Context context) {
		super(context, R.style.mDialog);
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_progress_dialog);
		mNumberProgressBar = (NumberProgressBar) findViewById(R.id.common_dialog_progressBar);
		mTvContent = (TextView) findViewById(R.id.common_dialog_content);
	}

	public void setProgress(int count){
		mNumberProgressBar.setProgress(count);

	}
	public void setContent(String content){
		mTvContent.setText(content);
	}
	@Override
	public void show() {
		super.show();
	}
}
