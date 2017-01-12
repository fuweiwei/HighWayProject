/**
 * 
 */
package com.ty.highway.highwaysystem.support.listener;

/** 
 * @ClassName: ProgressCallBack 
 * @Description: TODO(基础数据进度的回调接口)
 * @author fuweiwei
 * @date 2015-7-29 ����11:22:37 
 *  
 */
public interface ProgressCallBack {
	public void onResult(int count);
	public void onError(int count);

}
