package com.ty.highway.highwaysystem.support.bean.machine;

import java.io.Serializable;

/**
 * Created by fuweiwei on 2016/1/6.
 */
public class ELMachineTaskBean implements Serializable {
    private String NewId;
    private String CheckNo;
    private String TunnelId;
    private String CheckEmp;
    private String CheckDate;
    private String CheckWeather;
    private String CheckWayId;
    private String Remark;
    private String RecordEmp;
    private String CreateDate;
    private int AuditCount;
    private String UpdateDate;
    private String MaintenanceOrgan;
    private String UserId;
    private  int UpdateState;

    public int getUpdateState() {
        return UpdateState;
    }

    public void setUpdateState(int updateState) {
        UpdateState = updateState;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getNewId() {
        return NewId;
    }

    public void setNewId(String newId) {
        NewId = newId;
    }

    public String getCheckNo() {
        return CheckNo;
    }

    public void setCheckNo(String checkNo) {
        CheckNo = checkNo;
    }

    public String getTunnelId() {
        return TunnelId;
    }

    public void setTunnelId(String tunnelId) {
        TunnelId = tunnelId;
    }

    public String getCheckEmp() {
        return CheckEmp;
    }

    public void setCheckEmp(String checkEmp) {
        CheckEmp = checkEmp;
    }

    public String getCheckDate() {
        return CheckDate;
    }

    public void setCheckDate(String checkDate) {
        CheckDate = checkDate;
    }

    public String getCheckWeather() {
        return CheckWeather;
    }

    public void setCheckWeather(String checkWeather) {
        CheckWeather = checkWeather;
    }

    public String getCheckWayId() {
        return CheckWayId;
    }

    public void setCheckWayId(String checkWayId) {
        CheckWayId = checkWayId;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getRecordEmp() {
        return RecordEmp;
    }

    public void setRecordEmp(String recordEmp) {
        RecordEmp = recordEmp;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public int getAuditCount() {
        return AuditCount;
    }

    public void setAuditCount(int auditCount) {
        AuditCount = auditCount;
    }

    public String getUpdateDate() {
        return UpdateDate;
    }

    public void setUpdateDate(String updateDate) {
        UpdateDate = updateDate;
    }

    public String getMaintenanceOrgan() {
        return MaintenanceOrgan;
    }

    public void setMaintenanceOrgan(String maintenanceOrgan) {
        MaintenanceOrgan = maintenanceOrgan;
    }
}
