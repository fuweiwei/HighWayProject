package com.ty.highway.highwaysystem.support.bean.basic;

import java.io.Serializable;

/**
 * 单位信息
 * Created by ${dzm} on 2015/9/7 0007.
 */
public class POrganizeBean implements Serializable{

    private String NewId;//单位ID
    private String OrganizeName ;//单位名称
    private String OrganizeAddress ;
    private String OrganizeNature  ;
    private String OrganizePhone  ;
    private String Pid  ;
    private String CreateDate ;
    private String UpdateDate;
    private String OrganizeNumber ;
    private String SimpleName ;
    private String OrganizeType   ;

    public String getNewId() {
        return NewId;
    }

    public void setNewId(String newId) {
        NewId = newId;
    }

    public String getOrganizeName() {
        return OrganizeName;
    }

    public void setOrganizeName(String organizeName) {
        OrganizeName = organizeName;
    }

    public String getOrganizeAddress() {
        return OrganizeAddress;
    }

    public void setOrganizeAddress(String organizeAddress) {
        OrganizeAddress = organizeAddress;
    }

    public String getOrganizeNature() {
        return OrganizeNature;
    }

    public void setOrganizeNature(String organizeNature) {
        OrganizeNature = organizeNature;
    }

    public String getOrganizePhone() {
        return OrganizePhone;
    }

    public void setOrganizePhone(String organizePhone) {
        OrganizePhone = organizePhone;
    }

    public String getPid() {
        return Pid;
    }

    public void setPid(String pid) {
        Pid = pid;
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

    public String getOrganizeNumber() {
        return OrganizeNumber;
    }

    public void setOrganizeNumber(String organizeNumber) {
        OrganizeNumber = organizeNumber;
    }

    public String getSimpleName() {
        return SimpleName;
    }

    public void setSimpleName(String simpleName) {
        SimpleName = simpleName;
    }

    public String getOrganizeType() {
        return OrganizeType;
    }

    public void setOrganizeType(String organizeType) {
        OrganizeType = organizeType;
    }
}
