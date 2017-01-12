package com.ty.highway.highwaysystem.support.net;

import com.ty.highway.highwaysystem.support.listener.NetResquestListener;

import java.util.HashMap;

/**
 * Created by fuweiwei on 2015/9/6.
 *  API接口抽象类  版本1.0
 */
public interface HighWayApi {
    public void doPost(HashMap<String, String> map, String name, String urltype, NetResquestListener listener);

    //异常处理接口
    public void upLoadExecption(HashMap<String, String> map, String name, NetResquestListener listener);
}
