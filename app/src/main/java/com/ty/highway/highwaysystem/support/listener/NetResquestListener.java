package com.ty.highway.highwaysystem.support.listener;

/**
 * Created by fuweiwei on 2015/9/6.
 * 网络请求回调接口
 */
public interface NetResquestListener {
    public void onSuccess(String response);

    public void onError(String errormsg);
}
