package com.ty.highway.highwaysystem.support.bean.basic;

import java.io.Serializable;

/**
 * Created by fuweiwei on 2015/12/3.
 */
public class PAccountBean implements Serializable{
    private String name;
    private String password;
    private String issave;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIssave() {
        return issave;
    }

    public void setIssave(String issave) {
        this.issave = issave;
    }
}
