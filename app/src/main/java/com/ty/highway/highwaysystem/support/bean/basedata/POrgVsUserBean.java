package com.ty.highway.highwaysystem.support.bean.basedata;

/**
 * Created by fuweiwei on 2015/9/14.
 * 用户和组织关系
 */
public class POrgVsUserBean {
    private String NewId;
    private String OrganizeId;
    private String UserId;
    private String RoleId;
    private Boolean Stutas;
    private String WorkDate;
    private Boolean IsDefault;
    private String CreateDate;
    private String UpdateDate;

    public String getNewId() {
        return NewId;
    }

    public void setNewId(String newId) {
        NewId = newId;
    }

    public String getOrganizeId() {
        return OrganizeId;
    }

    public void setOrganizeId(String organizeId) {
        OrganizeId = organizeId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getRoleId() {
        return RoleId;
    }

    public void setRoleId(String roleId) {
        RoleId = roleId;
    }

    public Boolean getStutas() {
        return Stutas;
    }

    public void setStutas(Boolean stutas) {
        Stutas = stutas;
    }

    public String getWorkDate() {
        return WorkDate;
    }

    public void setWorkDate(String workDate) {
        WorkDate = workDate;
    }

    public Boolean getIsDefault() {
        return IsDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        IsDefault = isDefault;
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
