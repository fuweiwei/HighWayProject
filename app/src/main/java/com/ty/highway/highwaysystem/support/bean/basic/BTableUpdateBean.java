package com.ty.highway.highwaysystem.support.bean.basic;

import java.io.Serializable;

/**
 * Created by fuweiwei on 2015/9/16.
 */
public class BTableUpdateBean implements Serializable {
    private String UpdateType;
    private String UpdateTime;
    public   BTableUpdateBean(String type,String time){
        UpdateType = type;
        UpdateTime = time;
    }
    public String getUpdateType() {
        return UpdateType;
    }

    public void setUpdateType(String updateType) {
        UpdateType = updateType;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        UpdateTime = updateTime;
    }
}
