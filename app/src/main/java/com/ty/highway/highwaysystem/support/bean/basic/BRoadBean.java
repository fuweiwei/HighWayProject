package com.ty.highway.highwaysystem.support.bean.basic;

import java.io.Serializable;

/**
 * 路线信息
 * Created by ${dzm} on 2015/9/7 0007.
 */
public class BRoadBean implements Serializable{

    public BRoadBean(){}

    private String NewId;//路线ID
    private String RoadName;//路线名称
    private String RoladLength;//路线长度
    private String ProvincesID;//省份ID
    private String Remark;//备注
    private String RoadNatrue;
    private int Sort;//排序
    private String RoadCode;//路线编号
    private String StartMileage;//起始桩号
    private double StartMileageNum;//起始桩号里程
    private String EndMileage;//结束桩号
    private double EndMileageNum;//终止桩号里程
    private String StartName;//起始名字
    private String EndName;//终止名字
    private String BuildYear;//建设年限
    private String OpenDate;//开通日期
    private String CreateDate;
    private String UpdateDate;

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

    public String getNewId() {
        return NewId;
    }

    public void setNewId(String newId) {
        NewId = newId;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getRoladLength() {
        return RoladLength;
    }

    public void setRoladLength(String roladLength) {
        RoladLength = roladLength;
    }

    public String getProvincesID() {
        return ProvincesID;
    }

    public void setProvincesID(String provincesID) {
        ProvincesID = provincesID;
    }

    public String getRoadCode() {
        return RoadCode;
    }

    public void setRoadCode(String roadCode) {
        RoadCode = roadCode;
    }

    public int getSort() {
        return Sort;
    }

    public void setSort(int sort) {
        Sort = sort;
    }

    public String getRoadName() {
        return RoadName;
    }

    public void setRoadName(String roadName) {
        RoadName = roadName;
    }

    public String getStartName() {
        return StartName;
    }

    public String getRoadNatrue() {
        return RoadNatrue;
    }

    public void setRoadNatrue(String roadNatrue) {
        RoadNatrue = roadNatrue;
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

    public void setStartName(String startName) {
        StartName = startName;
    }

    public String getEndName() {
        return EndName;
    }

    public void setEndName(String endName) {
        EndName = endName;
    }

    public String getBuildYear() {
        return BuildYear;
    }

    public void setBuildYear(String buildYear) {
        BuildYear = buildYear;
    }

    public String getOpenDate() {
        return OpenDate;
    }

    public void setOpenDate(String openDate) {
        OpenDate = openDate;
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

}
