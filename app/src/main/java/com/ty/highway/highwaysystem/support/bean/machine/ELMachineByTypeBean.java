package com.ty.highway.highwaysystem.support.bean.machine;

import java.io.Serializable;

/**
 * Created by fuweiwei on 2016/1/5.
 */
public class ELMachineByTypeBean implements Serializable {
    private String NewId;
    private String WayId;
    private String MMachineTypeId;
    private int DeployType;
    private String TunnelId;
    private String CreateDate;
    private String UpdateDate;

    public String getNewId() {
        return NewId;
    }

    public void setNewId(String newId) {
        NewId = newId;
    }

    public String getWayId() {
        return WayId;
    }

    public void setWayId(String wayId) {
        WayId = wayId;
    }

    public String getMMachineTypeId() {
        return MMachineTypeId;
    }

    public void setMMachineTypeId(String MMachineTypeId) {
        this.MMachineTypeId = MMachineTypeId;
    }

    public int getDeployType() {
        return DeployType;
    }

    public void setDeployType(int deployType) {
        DeployType = deployType;
    }

    public String getTunnelId() {
        return TunnelId;
    }

    public void setTunnelId(String tunnelId) {
        TunnelId = tunnelId;
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
}
