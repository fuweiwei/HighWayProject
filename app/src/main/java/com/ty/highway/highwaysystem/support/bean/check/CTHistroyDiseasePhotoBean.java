package com.ty.highway.highwaysystem.support.bean.check;

import java.io.Serializable;

/**
 * Created by fuweiwei on 2015/11/4.
 * 历史病害照片信息基础bean
 */
public class CTHistroyDiseasePhotoBean implements Serializable{
    private String NewId;
    private String DocumentPath;
    private String ObjectId;
    private String DisplayName;
    private String ExtensionName;
    private String IsCovert;
    private String CovertStatus;
    private String CreateDate;
    private String UpdateDate;
    private String ReadCount;
    private String PageCount;

    public String getNewId() {
        return NewId;
    }

    public void setNewId(String newId) {
        NewId = newId;
    }

    public String getDocumentPath() {
        return DocumentPath;
    }

    public void setDocumentPath(String documentPath) {
        DocumentPath = documentPath;
    }

    public String getObjectId() {
        return ObjectId;
    }

    public void setObjectId(String objectId) {
        ObjectId = objectId;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }

    public String getExtensionName() {
        return ExtensionName;
    }

    public void setExtensionName(String extensionName) {
        ExtensionName = extensionName;
    }

    public String getIsCovert() {
        return IsCovert;
    }

    public void setIsCovert(String isCovert) {
        IsCovert = isCovert;
    }

    public String getCovertStatus() {
        return CovertStatus;
    }

    public void setCovertStatus(String covertStatus) {
        CovertStatus = covertStatus;
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

    public String getReadCount() {
        return ReadCount;
    }

    public void setReadCount(String readCount) {
        ReadCount = readCount;
    }

    public String getPageCount() {
        return PageCount;
    }

    public void setPageCount(String pageCount) {
        PageCount = pageCount;
    }
}
