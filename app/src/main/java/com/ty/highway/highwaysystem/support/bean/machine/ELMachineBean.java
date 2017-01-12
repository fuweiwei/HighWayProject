package com.ty.highway.highwaysystem.support.bean.machine;

import java.io.Serializable;

/**
 * Created by fuweiwei on 2016/1/5.
 */
public class ELMachineBean implements Serializable {
    private String NewId;
    private String DeviceName;
    private String DeviceNo;
    private String MMachineId;
    private String ManuFacturer;
    private double UseAge;
    private String BuyDate;
    private String UseDate;
    private String CreateDate;
    private String UpdateDate;
    private String Remark;
    private String Price;
    private String OrgId;
    private double Count;
    private String LeaveDate;
    private boolean IsUse    ;
    private String TwoCode;
    private String MMachineTypeId  ;
    private String MMachineDeviceDId;
    private String Type;

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getMMachineDeviceDId() {
        return MMachineDeviceDId;
    }

    public void setMMachineDeviceDId(String MMachineDeviceDId) {
        this.MMachineDeviceDId = MMachineDeviceDId;
    }

    public String getNewId() {
        return NewId;
    }

    public void setNewId(String newId) {
        NewId = newId;
    }

    public String getDeviceName() {
        return DeviceName;
    }

    public void setDeviceName(String deviceName) {
        DeviceName = deviceName;
    }

    public String getDeviceNo() {
        return DeviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        DeviceNo = deviceNo;
    }

    public String getMMachineId() {
        return MMachineId;
    }

    public void setMMachineId(String MMachineId) {
        this.MMachineId = MMachineId;
    }

    public String getManuFacturer() {
        return ManuFacturer;
    }

    public void setManuFacturer(String manuFacturer) {
        ManuFacturer = manuFacturer;
    }

    public double getUseAge() {
        return UseAge;
    }

    public void setUseAge(double useAge) {
        UseAge = useAge;
    }

    public String getBuyDate() {
        return BuyDate;
    }

    public void setBuyDate(String buyDate) {
        BuyDate = buyDate;
    }

    public String getUseDate() {
        return UseDate;
    }

    public void setUseDate(String useDate) {
        UseDate = useDate;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getUpdateDate() {
        return UpdateDate;
    }

    public void setUpdateDate(String updateDate) {
        UpdateDate = updateDate;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getOrgId() {
        return OrgId;
    }

    public void setOrgId(String orgId) {
        OrgId = orgId;
    }

    public double getCount() {
        return Count;
    }

    public void setCount(double count) {
        Count = count;
    }

    public String getLeaveDate() {
        return LeaveDate;
    }

    public void setLeaveDate(String leaveDate) {
        LeaveDate = leaveDate;
    }

    public boolean isUse() {
        return IsUse;
    }

    public void setIsUse(boolean isUse) {
        IsUse = isUse;
    }

    public String getTwoCode() {
        return TwoCode;
    }

    public void setTwoCode(String twoCode) {
        TwoCode = twoCode;
    }

    public String getMMachineTypeId() {
        return MMachineTypeId;
    }

    public void setMMachineTypeId(String MMachineTypeId) {
        this.MMachineTypeId = MMachineTypeId;
    }
}
