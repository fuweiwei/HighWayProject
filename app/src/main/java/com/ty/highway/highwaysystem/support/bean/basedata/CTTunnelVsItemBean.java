package com.ty.highway.highwaysystem.support.bean.basedata;

import java.io.Serializable;

/**
 * Created by fuweiwei on 2015/9/14.
 * 隧道和检查项关系
 */
public class CTTunnelVsItemBean implements Serializable{
    private String NewId;//
    private String TunnelId;//隧道ID
    private String CheckWayId;//检查方式ID
    private String CreateDate;//
    private String ItemName;//检查项目名称
    private String ContentName;
    private String DiseaseName;
    private String ItemId;//检查项目关联ID’
    private String ContentId;
    private String DiseaseId;
    private int AuditCount;//0通用权限，1隧道权限

    public String getNewId() {
        return NewId;
    }

    public void setNewId(String newId) {
        NewId = newId;
    }

    public String getTunnelId() {
        return TunnelId;
    }

    public void setTunnelId(String tunnelId) {
        TunnelId = tunnelId;
    }

    public String getCheckWayId() {
        return CheckWayId;
    }

    public void setCheckWayId(String checkWayId) {
        CheckWayId = checkWayId;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getContentName() {
        return ContentName;
    }

    public void setContentName(String contentName) {
        ContentName = contentName;
    }

    public String getDiseaseName() {
        return DiseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        DiseaseName = diseaseName;
    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public String getContentId() {
        return ContentId;
    }

    public void setContentId(String contentId) {
        ContentId = contentId;
    }

    public String getDiseaseId() {
        return DiseaseId;
    }

    public void setDiseaseId(String diseaseId) {
        DiseaseId = diseaseId;
    }

    public int getAuditCount() {
        return AuditCount;
    }

    public void setAuditCount(int auditCount) {
        AuditCount = auditCount;
    }
}
