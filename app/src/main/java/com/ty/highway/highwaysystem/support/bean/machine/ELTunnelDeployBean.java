package com.ty.highway.highwaysystem.support.bean.machine;

import java.io.Serializable;

/**
 * Created by fuweiwei on 2016/1/5.
 */
public class ELTunnelDeployBean implements Serializable {
    private String NewId;
    private String Tunneld;
    private String OrgId;
    private String StartConstruction;
    private String EndConstruction;
    private String UpdateDate;
    private String CreateDate;
    private String UserDate;
    private boolean IsUser;
    private int Sort;
    private String Remark;
    private String MaxDeviceId;
    private String MinDeviceId;
    private int DeviceType;
    private double StartConstructionNum;
    private double EndConstructionNum;

    public double getStartConstructionNum() {
        return StartConstructionNum;
    }

    public void setStartConstructionNum(double startConstructionNum) {
        StartConstructionNum = startConstructionNum;
    }

    public double getEndConstructionNum() {
        return EndConstructionNum;
    }

    public void setEndConstructionNum(double endConstructionNum) {
        EndConstructionNum = endConstructionNum;
    }

    public String getNewId() {
        return NewId;
    }

    public void setNewId(String newId) {
        NewId = newId;
    }

    public String getTunneld() {
        return Tunneld;
    }

    public void setTunneld(String tunneld) {
        Tunneld = tunneld;
    }

    public String getOrgId() {
        return OrgId;
    }

    public void setOrgId(String orgId) {
        OrgId = orgId;
    }

    public String getStartConstruction() {
        return StartConstruction;
    }

    public void setStartConstruction(String startConstruction) {
        StartConstruction = startConstruction;
    }

    public String getEndConstruction() {
        return EndConstruction;
    }

    public void setEndConstruction(String endConstruction) {
        EndConstruction = endConstruction;
    }

    public String getUpdateDate() {
        return UpdateDate;
    }

    public void setUpdateDate(String updateDate) {
        UpdateDate = updateDate;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getUserDate() {
        return UserDate;
    }

    public void setUserDate(String userDate) {
        UserDate = userDate;
    }

    public boolean isUser() {
        return IsUser;
    }

    public void setIsUser(boolean isUser) {
        IsUser = isUser;
    }

    public int getSort() {
        return Sort;
    }

    public void setSort(int sort) {
        Sort = sort;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getMaxDeviceId() {
        return MaxDeviceId;
    }

    public void setMaxDeviceId(String maxDeviceId) {
        MaxDeviceId = maxDeviceId;
    }

    public String getMinDeviceId() {
        return MinDeviceId;
    }

    public void setMinDeviceId(String minDeviceId) {
        MinDeviceId = minDeviceId;
    }

    public int getDeviceType() {
        return DeviceType;
    }

    public void setDeviceType(int deviceType) {
        DeviceType = deviceType;
    }
}
