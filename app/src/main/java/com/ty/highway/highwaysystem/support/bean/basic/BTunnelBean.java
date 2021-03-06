package com.ty.highway.highwaysystem.support.bean.basic;

import java.io.Serializable;

/**
 * 隧道信息
 * Created by ${dzm} on 2015/9/7 0007.
 */
public class BTunnelBean implements Serializable {

    public BTunnelBean() {
    }

    private String NewId;//隧道ID
    private String SectionId;//路段ID
    private String TunnelName;//隧道名称
    private double TunnelLength;//隧道长度
    private String StartMileage;//起始里程
    private String EndMileage;//结束里程
    private String CheckUnit;//
    private String CuringUnit;//
    private String RoadId;//路线ID
    private String CreateDate;//创建日期
    private String EndDate;//结束日期
    private String TunnelCode;//隧道编号
    private String CompletionDate;//竣工日期
    private double TunnelHeight;//隧道高度
    private double TunnelWidth;//隧道宽度
    private boolean IsUnderwater;//是否水下通道
    private boolean IsInvert;//是否仰拱
    private boolean IsAlarm;//是否有报警设施
    private boolean IsOwnAlarm;//是否有自警设施
    private boolean SafePass;//安全通道
    private int CarHote;//车行横洞
    private int PeopleHote;//人行横洞
    private String Remark;//备注
    private int Sort;//排序
    private double StartMileageNum;//开始里程号
    private double EndMileageNum;//结束里程号
    private String CustodyUnit;//管养单位
    private String LightingFacility;//照明设施
    private String MonitoringFacility;//监控设施
    private String VentilatingFacility;//管养单位
    private String FireFacility;//消防设施
    private String PipelineFacility;//管道设施
    private String StartConstruction;//起始施工桩号
    private String EndConstruction;//终止施工桩号
    private String StartConstructionNum;//
    private String EndConstructionNum;//
    private String DrainageType;//排水类型
    private String InHoleType;//入口洞类型
    private String OutHoleType;//出口洞类型
    private String SurfaceType;//断面形式
    private String LiningType;//衬砌类型
    private String LiningMaterial;//衬砌材料
    private String RoadStructure;//路面结构
    private String BuildType;//建设性质
    private String TunnelType;//隧道类别
    private String IsComplete;//是否竣工
    private boolean IsInformation;//可变情报板
    private boolean TrafficSign;//交通标志
    private String ExplorationUnits;//勘察单位
    private String DesignUnits;//设计单位
    private String ConstructUnits;//施工单位
    private String SupervisingUnit;//监理单位
    private String RoadNum;//行车道数

    public String getNewId() {
        return NewId;
    }

    public void setNewId(String newId) {
        NewId = newId;
    }

    public String getSectionId() {
        return SectionId;
    }

    public void setSectionId(String sectionId) {
        SectionId = sectionId;
    }

    public String getTunnelName() {
        return TunnelName;
    }

    public void setTunnelName(String tunnelName) {
        TunnelName = tunnelName;
    }

    public double getTunnelLength() {
        return TunnelLength;
    }

    public void setTunnelLength(double tunnelLength) {
        TunnelLength = tunnelLength;
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

    public String getCheckUnit() {
        return CheckUnit;
    }

    public void setCheckUnit(String checkUnit) {
        CheckUnit = checkUnit;
    }

    public String getCuringUnit() {
        return CuringUnit;
    }

    public void setCuringUnit(String curingUnit) {
        CuringUnit = curingUnit;
    }

    public String getRoadId() {
        return RoadId;
    }

    public void setRoadId(String roadId) {
        RoadId = roadId;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getTunnelCode() {
        return TunnelCode;
    }

    public void setTunnelCode(String tunnelCode) {
        TunnelCode = tunnelCode;
    }

    public String getCompletionDate() {
        return CompletionDate;
    }

    public void setCompletionDate(String completionDate) {
        CompletionDate = completionDate;
    }

    public double getTunnelHeight() {
        return TunnelHeight;
    }

    public void setTunnelHeight(double tunnelHeight) {
        TunnelHeight = tunnelHeight;
    }

    public double getTunnelWidth() {
        return TunnelWidth;
    }

    public void setTunnelWidth(double tunnelWidth) {
        TunnelWidth = tunnelWidth;
    }

    public boolean isUnderwater() {
        return IsUnderwater;
    }

    public void setIsUnderwater(boolean isUnderwater) {
        IsUnderwater = isUnderwater;
    }

    public boolean isInvert() {
        return IsInvert;
    }

    public void setIsInvert(boolean isInvert) {
        IsInvert = isInvert;
    }

    public boolean isAlarm() {
        return IsAlarm;
    }

    public void setIsAlarm(boolean isAlarm) {
        IsAlarm = isAlarm;
    }

    public boolean isOwnAlarm() {
        return IsOwnAlarm;
    }

    public void setIsOwnAlarm(boolean isOwnAlarm) {
        IsOwnAlarm = isOwnAlarm;
    }

    public boolean isSafePass() {
        return SafePass;
    }

    public void setSafePass(boolean safePass) {
        SafePass = safePass;
    }

    public int getCarHote() {
        return CarHote;
    }

    public void setCarHote(int carHote) {
        CarHote = carHote;
    }

    public int getPeopleHote() {
        return PeopleHote;
    }

    public void setPeopleHote(int peopleHote) {
        PeopleHote = peopleHote;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public int getSort() {
        return Sort;
    }

    public void setSort(int sort) {
        Sort = sort;
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

    public String getCustodyUnit() {
        return CustodyUnit;
    }

    public void setCustodyUnit(String custodyUnit) {
        CustodyUnit = custodyUnit;
    }

    public String getLightingFacility() {
        return LightingFacility;
    }

    public void setLightingFacility(String lightingFacility) {
        LightingFacility = lightingFacility;
    }

    public String getMonitoringFacility() {
        return MonitoringFacility;
    }

    public void setMonitoringFacility(String monitoringFacility) {
        MonitoringFacility = monitoringFacility;
    }

    public String getVentilatingFacility() {
        return VentilatingFacility;
    }

    public void setVentilatingFacility(String ventilatingFacility) {
        VentilatingFacility = ventilatingFacility;
    }

    public String getFireFacility() {
        return FireFacility;
    }

    public void setFireFacility(String fireFacility) {
        FireFacility = fireFacility;
    }

    public String getPipelineFacility() {
        return PipelineFacility;
    }

    public void setPipelineFacility(String pipelineFacility) {
        PipelineFacility = pipelineFacility;
    }

    public String getStartConstruction() {
        return StartConstruction;
    }

    public void setStartConstruction(String startConstruction) {
        StartConstruction = startConstruction;
    }

    public String getEndConstruction() {
        return EndConstruction;
    }

    public void setEndConstruction(String endConstruction) {
        EndConstruction = endConstruction;
    }

    public String getStartConstructionNum() {
        return StartConstructionNum;
    }

    public void setStartConstructionNum(String startConstructionNum) {
        StartConstructionNum = startConstructionNum;
    }

    public String getEndConstructionNum() {
        return EndConstructionNum;
    }

    public void setEndConstructionNum(String endConstructionNum) {
        EndConstructionNum = endConstructionNum;
    }

    public String getDrainageType() {
        return DrainageType;
    }

    public void setDrainageType(String drainageType) {
        DrainageType = drainageType;
    }

    public String getInHoleType() {
        return InHoleType;
    }

    public void setInHoleType(String inHoleType) {
        InHoleType = inHoleType;
    }

    public String getOutHoleType() {
        return OutHoleType;
    }

    public void setOutHoleType(String outHoleType) {
        OutHoleType = outHoleType;
    }

    public String getSurfaceType() {
        return SurfaceType;
    }

    public void setSurfaceType(String surfaceType) {
        SurfaceType = surfaceType;
    }

    public String getLiningType() {
        return LiningType;
    }

    public void setLiningType(String liningType) {
        LiningType = liningType;
    }

    public String getLiningMaterial() {
        return LiningMaterial;
    }

    public void setLiningMaterial(String liningMaterial) {
        LiningMaterial = liningMaterial;
    }

    public String getRoadStructure() {
        return RoadStructure;
    }

    public void setRoadStructure(String roadStructure) {
        RoadStructure = roadStructure;
    }

    public String getBuildType() {
        return BuildType;
    }

    public void setBuildType(String buildType) {
        BuildType = buildType;
    }

    public String getTunnelType() {
        return TunnelType;
    }

    public void setTunnelType(String tunnelType) {
        TunnelType = tunnelType;
    }

    public String getIsComplete() {
        return IsComplete;
    }

    public void setIsComplete(String isComplete) {
        IsComplete = isComplete;
    }

    public boolean isInformation() {
        return IsInformation;
    }

    public void setIsInformation(boolean isInformation) {
        IsInformation = isInformation;
    }

    public boolean isTrafficSign() {
        return TrafficSign;
    }

    public void setTrafficSign(boolean trafficSign) {
        TrafficSign = trafficSign;
    }

    public String getExplorationUnits() {
        return ExplorationUnits;
    }

    public void setExplorationUnits(String explorationUnits) {
        ExplorationUnits = explorationUnits;
    }

    public String getDesignUnits() {
        return DesignUnits;
    }

    public void setDesignUnits(String designUnits) {
        DesignUnits = designUnits;
    }

    public String getConstructUnits() {
        return ConstructUnits;
    }

    public void setConstructUnits(String constructUnits) {
        ConstructUnits = constructUnits;
    }

    public String getSupervisingUnit() {
        return SupervisingUnit;
    }

    public void setSupervisingUnit(String supervisingUnit) {
        SupervisingUnit = supervisingUnit;
    }

    public String getRoadNum() {
        return RoadNum;
    }

    public void setRoadNum(String roadNum) {
        RoadNum = roadNum;
    }
}
