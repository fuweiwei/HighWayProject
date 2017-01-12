package com.ty.highway.highwaysystem.support.bean.basic;

import java.io.Serializable;

/**
 * Created by ${dzm} on 2015/9/11 0011.
 */
public class BSectionBean implements Serializable{

    private String NewId;
    private String SectionName;
    private String RoadId;
    private String RoadTypeId;
    private String StartMileage;
    private String EndMileage;
    private double Sort;
    private String Remark;
    private double StartMileageNum;
    private double EndMileageNum;
    private String SkillLevel;
    private String CreateDate;
    private String UpdateDate;
    private double SectionlLength ;

    public String getNewId() {
        return NewId;
    }

    public void setNewId(String newId) {
        NewId = newId;
    }

    public String getSectionName() {
        return SectionName;
    }

    public void setSectionName(String sectionName) {
        SectionName = sectionName;
    }

    public String getRoadId() {
        return RoadId;
    }

    public void setRoadId(String roadId) {
        RoadId = roadId;
    }

    public String getRoadTypeId() {
        return RoadTypeId;
    }

    public void setRoadTypeId(String roadTypeId) {
        RoadTypeId = roadTypeId;
    }

    public String getStartMileage() {
        return StartMileage;
    }

    public void setStartMileage(String startMileage) {
        StartMileage = startMileage;
    }

    public String getEndMileage() {
        return EndMileage;
    }

    public void setEndMileage(String endMileage) {
        EndMileage = endMileage;
    }

    public double getSort() {
        return Sort;
    }

    public void setSort(double sort) {
        Sort = sort;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public double getStartMileageNum() {
        return StartMileageNum;
    }

    public void setStartMileageNum(double startMileageNum) {
        StartMileageNum = startMileageNum;
    }

    public double getEndMileageNum() {
        return EndMileageNum;
    }

    public void setEndMileageNum(double endMileageNum) {
        EndMileageNum = endMileageNum;
    }

    public String getSkillLevel() {
        return SkillLevel;
    }

    public void setSkillLevel(String skillLevel) {
        SkillLevel = skillLevel;
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

    public double getSectionlLength() {
        return SectionlLength;
    }

    public void setSectionlLength(double sectionlLength) {
        SectionlLength = sectionlLength;
    }
}
