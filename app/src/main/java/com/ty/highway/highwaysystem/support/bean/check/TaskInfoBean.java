package com.ty.highway.highwaysystem.support.bean.check;

import java.io.Serializable;

/**
 * Created by fuweiwei on 2015/9/11.
 */
public class TaskInfoBean implements Serializable {
    private  String NewId; //id
    private  String CheckNo;//检查编号
    private  String TunnelId;//隧道ID
    private  String CheckEmp;//检查人
    private  String CheckDate;//检查时间
    private  String CheckWeather;//检查天气
    private  String CheckWayId;//检查方式
    private  String Remark;//监察人
    private  String RecordEmp;//创建日期
    private  String CreateDate;
    private  int AuditCount;//养护机构
    private int UpdateState;//上传状态
    private String NearTaskId;//绑定临时任务ID
    private int IsNearTask;//是否是临时任务 0：不是，1：是

    public String getNearTaskId() {
        return NearTaskId;
    }

    public void setNearTaskId(String nearTaskId) {
        NearTaskId = nearTaskId;
    }

    public int getIsNearTask() {
        return IsNearTask;
    }

    public void setIsNearTask(int isNearTask) {
        IsNearTask = isNearTask;
    }

    public int getUpdateState() {
        return UpdateState;
    }

    public void setUpdateState(int updateState) {
        UpdateState = updateState;
    }

    public String getMaintenanceOrgan() {
        return MaintenanceOrgan;
    }

    public void setMaintenanceOrgan(String maintenanceOrgan) {
        MaintenanceOrgan = maintenanceOrgan;
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

    private  String MaintenanceOrgan;

}
