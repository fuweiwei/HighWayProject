package com.ty.highway.highwaysystem.support.bean.basic;

import java.io.Serializable;

/**
 * Created by fuweiwei on 2015/9/6.
 * 登陆返回用户信息bean
 */
public class PUserResultBean implements Serializable{
    private  String r;
    private CTUserBean s;

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public CTUserBean getS() {
        return s;
    }

    public void setS(CTUserBean s) {
        this.s = s;
    }

    }
