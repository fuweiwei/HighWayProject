package com.ty.highway.highwaysystem.support.bean.basedata;

/**
 * Created by fuweiwei on 2015/9/14.
 * 病害类型与病害等及描述的关系
 */
public class CTDiseaseTypeVsDamageDescBean {
    private String NewId;//
    private String TunnelId;
    private String CheckWayId;
    private String CreateDate;
    private String ItemName;
    private String ContentName;//
    private String DiseaseName;//病害描述Id
    private String ItemId;//病害类型关联ID
    private String ContentId;//病害描述ID
    private String DiseaseId;
    private int AuditCount;

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
