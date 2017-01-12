package com.ty.highway.highwaysystem.system.service;


import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
/**
 * 
 * @Title: MFinalService.java 
 * @author fuweiwei
 * @date 2014-11-27 AM 10:44:14 
 * @version V1.0 
 * @Description: TODO
 */
public class MFinalService extends Service{

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	
	private MHandler handler = new MHandler();

	protected void handleOtherMessage(int flag) {
		switch (flag) {
		   default:
			   break;
		}
	}

	public void sendMessage(int flag) {
		handler.sendEmptyMessage(flag);
	}

	public void sendMessageDely(int flag, long delayMillis) {
		handler.sendEmptyMessageDelayed(flag, delayMillis);
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
