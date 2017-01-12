package com.ty.highway.highwaysystem.support.bean.machine;

import java.io.Serializable;

/**
 * Created by fuweiwei on 2016/1/5.
 */
public class ELMachineTypeBean implements Serializable {
    private String NewId;
    private String MachineTypeName;
    private String Remark;
    private double Sort;
    private String Pid;
    private String UpdateDate;
    private String CreateDate;
    private double Type;

    public String getNewId() {
        return NewId;
    }

    public void setNewId(String newId) {
        NewId = newId;
    }

    public String getMachineTypeName() {
        return MachineTypeName;
    }

    public void setMachineTypeName(String machineTypeName) {
        MachineTypeName = machineTypeName;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public double getSort() {
        return Sort;
    }

    public void setSort(double sort) {
        Sort = sort;
    }

    public String getPid() {
        return Pid;
    }

    public void setPid(String pid) {
        Pid = pid;
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

    public double getType() {
        return Type;
    }

    public void setType(double type) {
        Type = type;
    }
}
