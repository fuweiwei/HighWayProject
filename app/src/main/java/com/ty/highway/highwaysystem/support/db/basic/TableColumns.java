package com.ty.highway.highwaysystem.support.db.basic;

/**
 * Created by fuweiwei on 2015/9/11.
 * 数据库表名和字段，方便统一管理
 */
public interface TableColumns {

    public static interface CTTaskInfo {
        String TABLE_CTTASKINFO="CTTaskInfo";
        String AUDITCOUNT="AuditCount";
        String UPDATESTATE="UpdateState";
        String NEWID="NewId";
        String CHECKNO="CheckNo";
        String TUNNELID="TunnelId";
        String CHECKEMP="CheckEmp";
        String CHECKDATE="CheckDate";
        String CHECKWEATHER="CheckWeather";
        String CHECKWAYID="CheckWayId";
        String REMARK="Remark";
        String RECORDEMP="RecordEmp";
        String CREATEDATE="CreateDate";
        String MAINTENANCEORGAN="MaintenanceOrgan";
        String NEARTASKID="NearTaskId";
        String ISNEARTASK="IsNearTask";
        String BELONGUSERID="BelongUserId";
    }
    public static  interface CTUser {
        String TABLE_CTUSER ="CTUser";
        String USERID="UserID";
        String USERNAME="UserName";
        String DPTID="DptId";
        String DPTNAME="DptName";
        String USERACCOUNT="UserAccount";
        String EROLEID="ERoleId";
        String FTPPATH="FtpPath";
        String FTPIP="FtpIp";
        String FTPUSER="FtpUser";
        String FTPPWD="FtpPwd";
        String ISLOGIN="IsLogin";
        String LEGALIZEKEY ="legalizeKey";
        String FTPREADPATH ="FtpReadPath";
    }
    public static  interface  CTCheckItemVsDiseaseType{
        String TABLE_CTCHECKITEMVSDISEASETYPE="CTCheckItemVsDiseaseType";
        String NEWID="NewId";
        String TUNNELID="TunnelId";
        String CHECKWAYID="CheckWayId";
        String ITEMNAME="ItemName";
        String CONTENTNAME="ContentName";
        String DISEASENAME="DiseaseName";
        String ITEMID="ItemId";
        String CONTENTID="ContentId";
        String DISEASEID="DiseaseId";
        String AUDITCOUNT="AuditCount";
    }
    public static  interface  CTTunnelVsItem{
        String TABLE_CTTUNNELVSITEM="CTTunnelVsItem";
        String NEWID="NewId";
        String TUNNELID="TunnelId";
        String CREATEDATE="CreateDate";
        String CHECKWAYID="CheckWayId";
        String ITEMNAME="ItemName";
        String CONTENTNAME="ContentName";
        String DISEASENAME="DiseaseName";
        String ITEMID="ItemId";
        String CONTENTID="ContentId";
        String DISEASEID="DiseaseId";
        String AUDITCOUNT="AuditCount";
    }
    public static  interface  CTDiseaseTypeVsDamageDesc{
        String TABLE_CTDISEASETYPEVSDAMAGEDESC="CTDiseaseTypeVsDamageDesc";
        String NEWID="NewId";
        String TUNNELID="TunnelId";
        String CHECKWAYID="CheckWayId";
        String ITEMNAME="ItemName";
        String CONTENTNAME="ContentName";
        String DISEASENAME="DiseaseName";
        String ITEMID="ItemId";
        String CONTENTID="ContentId";
        String DISEASEID="DiseaseId";
        String AUDITCOUNT="AuditCount";
    }
    public static  interface  POrgVsUser{
        String TABLE_PORGVSUSER="POrgVsUser";
        String NEWID="NewId";
        String ORGANIZEID="OrganizeId";
        String USERID="UserId";
        String ROLEID="RoleId";
        String STUTAS="Stutas";
        String WORKDATE="WorkDate";
        String ISDEFAULT="IsDefault";
        String CREATEDATE="CreateDate";
        String UPDATEDATE="UpdateDate";
    }
    public static  interface  PUserCheckPermission{
        String TABLE_PUSERCHECKPERMISSION="PUserCheckPermission";
        String NEWID="NewId";
        String SECTIONID="SectionId";
        String TUNNELNAME="TunnelName";
        String TUNNELCODE="TunnelCode";
    }
    public static  interface  CTCheckPosition{
        String TABLE_CTCHECKPOSITION="CTCheckPosition";
        String NEWID="NewId";
        String ITEMID="ItemId";
        String RANGENAME="RangeName";
        String SORT="Sort";
        String CREATEDATE="CreateDate";
        String CHECKWAYID="CheckwayId";
    }

    //线路表字段
    public static interface BRoad{
        String TABLE_BROAD = "BRoad";
        String NEWID = "NewId";//路线ID
        String ROADNAME = "RoadName";//路线名称
        String ROLADLENGT = "RoladLength";//路线长度
        String PROVINCESID = "ProvincesID";//省份ID
        String REMARK = "Remark";//备注
        String ROADNATRUE = "RoadNatrue";
        String SORT = "Sort";//排序
        String ROADCODE = "RoadCode";//路线编号
        String STARTMILEAGE = "StartMileage";//起始桩号
        String STARTMILEAGENUM = "StartMileageNum";//起始桩号里程
        String ENDMILEAGE = "EndMileage";//结束桩号
        String ENDMILEAGENUM = "EndMileageNum";//终止桩号里程
        String STARTNAME = "StartName";//起始名字
        String ENDNAME = "EndName";//终止名字
        String BUILDYEAR = "BuildYear";//建设年限
        String OPENDATE = "OpenDate";//开通日期
    }

    //路段表字段
    public static interface BSection{
        String TABLE_BSECTION = "BSection";
        String NEWID = "NewId";
        String SECTIONNAME = "SectionName";
        String ROADID = "RoadId";
        String ROADTYPEID = "RoadTypeId";
        String STARTMILEAGE = "StartMileage";
        String ENDMILEAGE = "EndMileage";
        String SORT = "Sort";
        String REMARK = "Remark";
        String STARTMILEAGENUM = "StartMileageNum";
        String ENDMILEAGENUM = "EndMileageNum";
        String SKILLLEVEL = "SkillLevel";
    }

    //隧道表字段
    public static interface BTunnel{
        String TABLE_BTUNNEL= "BTunnel";
        String NEWID = "NewId";//隧道ID
        String SECTIONID = "SectionId";//路段ID
        String TUNNELNAME = "TunnelName";//隧道名称
        String TUNNELLENGTH = "TunnelLength";//隧道长度
        String STARTMILEAGE = "StartMileage";//起始里程
        String ENDMILEAGE = "EndMileage";//结束里程
        String CHECKUNIT = "CheckUnit";//
        String CURINGUNIT = "CuringUnit";//
        String ROADID = "RoadId";//路线ID
        String CREATEDATE = "CreateDate";//创建日期
        String ENDDATE = "EndDate";//结束日期
        String TUNNELCODE = "TunnelCode";//隧道编号
        String COMPLETIONDATE = "CompletionDate";//竣工日期
        String TUNNELHEIGHT = "TunnelHeight";//隧道高度
        String TUNNELWIDTH = "TunnelWidth";//隧道宽度
        String ISUNDERWATER = "IsUnderwater";//是否水下通道
        String ISINVERT = "IsInvert";//是否仰拱
        String ISALARM = "IsAlarm";//是否有报警设施
        String ISOWNALARM = "IsOwnAlarm";//是否有自警设施
        String SAFEPASS = "SafePass";//安全通道
        String CARHOTE = "CarHote";//车行横洞
        String PEOPLEHOTE = "PeopleHote";//人行横洞
        String REMARK = "Remark";//备注
        String SORT = "Sort";//排序
        String STARTMILEAGENUM = "StartMileageNum";//开始里程号
        String ENDMILEAGENUM = "EndMileageNum";//结束里程号
        String CUSTODYUNIT = "CustodyUnit";//管养单位
        String LIGHTINGFACILITY = "LightingFacility";//照明设施
        String MONITORINGFACILITY = "MonitoringFacility";//监控设施
        String VENTILATINGFACILITY = "VentilatingFacility";//管养单位
        String FIREFACILITY = "FireFacility";//消防设施
        String PIPELINEFACILITY = "PipelineFacility";//管道设施
        String STARTCONSTRUCTION = "StartConstruction";//起始施工桩号
        String ENDCONSTRUCTION = "EndConstruction";//终止施工桩号
        String STARTCONSTRUCTIONNUM = "StartConstructionNum";//
        String ENDCONSTRUCTIONNUM = "EndConstructionNum";//
        String DRAINAGETYPE = "DrainageType";//排水类型
        String INHOLETYPE = "InHoleType";//入口洞类型
        String OUTHOLETYPE = "OutHoleType";//出口洞类型
        String SURFACETYPE = "SurfaceType";//断面形式
        String LININGTYPE = "LiningType";//衬砌类型
        String LININGMATERIAL = "LiningMaterial";//衬砌材料
        String ROADSTRUCTURE = "RoadStructure";//路面结构
        String BUILDTYPE = "BuildType";//建设性质
        String TUNNELTYPE = "TunnelType";//隧道类别
        String ISCOMPLETE = "IsComplete";//是否竣工
        String ISINFORMATION = "IsInformation";//可变情报板
        String TRAFFICSIGN = "TrafficSign";//交通标志
        String EXPLORATIONUNITS = "ExplorationUnits";//勘察单位
        String DESIGNUNITS = "DesignUnits";//设计单位
        String CONSTRUCTUNITS = "ConstructUnits";//施工单位
        String SUPERVISINGUNIT = "SupervisingUnit";//监理单位
        String ROADNUM = "RoadNum";//行车道数
        String BELONGUSERID = "BelongUserId";//属于用户ID
    }

    //组织表字段
    public static interface POrganize{
        String TABLE_PORGANIZE = "POrganize";
        String NEWID = "NewId";//单位ID
        String ORGANIZENAME = "OrganizeName";//单位名称
        String ORGANIZEADDRESS = "OrganizeAddress";
        String ORGANIZENATURE = "OrganizeNature";
        String ORGANIZEPHONE = "OrganizePhone";
        String PID = "Pid";
        String CREATEDATE = "CreateDate";
        String UPDATEDATE = "UpdateDate";
        String ORGANIZENUMBER = "OrganizeNumber";
        String SIMPLENAME = "SimpleName";
        String ORGANIZETYPE = "OrganizeType";
    }
    //检查方式
    public static interface CTDictionary{
        String TABLE_CTDICTIONARY = "CTDictionary";
        String NEWID = "NewId";//单位ID
        String NAME = "Name";//单位名称
        String TYPE = "Type";
        String SORT = "Sort";
    }
    //病害等级
    public static interface CTDiseaseLevel{
        String TABLE_CTDISEASELEVEL = "CTDiseaseLevel";
        String NEWID = "NewId";//单位ID
        String NAME = "Name";//单位名称
        String TYPE = "Type";
        String SORT = "Sort";
    }
    //更新时间
    public static interface BTableUpdate{
        String TABLE_BTABLEUPDATE = "BTableUpdate";
        String UPDATETYPE = "UpdateType";
        String UPDATETIME = "UpdateTime";
    }
    //病害信息
    public static interface CTDiseaseInfo{
        String TABLE_CTDISEASEINFO = "CTDiseaseInfo";
        String NEWID = "NewId";
        String CHECKDATA = "CheckData";
        String CHECKPOSTION = "CheckPostion";
        String MILEAGE = "Mileage";
        String JUDGELEVEL = "JudgeLevel";
        String LEVELCONTENT = "LevelContent";
        String CHECKTYPE = "CheckType";
        String TASKID = "TaskId";
        String REMARKS = "Remarks";
        String BELONGPRO = "BelongPro";
        String UPLOADSTATE = "UploadState";
        String TUNNELID = "TunnelId";
        String ITEMID = "ItemId";
        String DISEASETYPEID = "DiseaseTypeId";
        String GUID = "Guid";
        String ISNEARDISEASE = "IsNearDisease";
        String LENGTHS = "Lengths";
        String WIDTHS = "Widths";
        String DEEPS = "Deeps";
        String AREAS = "Areas";
        String ANGLES = "Angles";
        String CUTCOUNT = "CutCount";
        String THEDEFORMATION = "TheDeformation";
        String ERRORDEFORMATION = "ErrorDeformation";
        String PERCENTAGE = "Percentage";
        String DIRECTION = "Direction";
        String DTYPE = "DType";
        String HEIGHT = "Height";
        String THERATE = "TheRate";
        String DISEASEREMARK = "DiseaseRemark";//析出物
        String CHECKITEMID ="CheckItemId";
        String ISREPEAT ="IsRepeat";
        String ISREPEATID ="IsRepeatId";
        String DISEASEDESCRIBE ="DiseaseDescribe";
        String CONSERVATIONMEASURESID ="ConservationMeasuresId";
        String BELONGUSERID ="BelongUserId";


    }
    //病害信息
    public static interface CTNearTaskInfo{
        String TABLE_CTNEARTASKINFO = "CTNearTaskInfo";
        String NEWID = "NewId";
        String CHECKTYPE = "CheckType";
        String CHECKWAYID = "CheckWayId";
        String CHECKRANGE = "CheckRange";
        String NAME = "Name";
        String RELATIONTASKID = "RelationTaskId";
        String UPDATESTATE = "UpdateState";
    }
    //定期详细描述
    public static interface CTDiseaseTypeByDescript{
        String TABLE_CTDISEASETYPEBYDESCRIPT = "CTDiseaseTypeByDescript";
        String NEWID = "NewId";
        String PROPERTYNAME  = "PropertyName";
        String CREATEDATE = "CreateDate";
        String DISEASEDESCRIBEID = "DiseaseDescribeId";
        String MEASURINGUNIT = "MeasuringUnit";
        String PROPERTYVALUE = "PropertyValue";
    }
    //病害图片
    public static interface CTDiseasePhoto{
        String TABLE_CTDISEASEPHOTO= "CTDiseasePhoto";
        String GUID = "Guid";
        String DISEASEGUID = "DiseaseGuid";
        String POSITION = "Position";
        String NAME = "Name";
        String TASKID = "TaskId";
        String UPDATESTATE = "UpdateState";
        String LATTERNAME = "LatterName";
        String WEBDOCUMENT = "WebDocument";

    }
    //历史病害信息
    public static interface CTHistroyDisease{
        String TABLE_CTHISTROYDISEASE= "CTHistroyDiseaseInfo";
        String NEWID ="NewId";
        String CHECKCONTENTRECORDID ="CheckContentRecordId";
        String DISEASEID ="DiseaseId";
        String SEATDESCRIBE ="SeatDescribe";
        String DISEASEDESCRIBE ="DiseaseDescribe";
        String REMARK ="Remark";
        String STARTMILEAGENUM ="StartMileageNum";
        String ENDMILEAGENUM ="EndMileageNum";
        String LENGTHS ="Lengths";
        String WIDTHS ="Widths";
        String DEEPS ="Deeps";
        String AREAS ="Areas";
        String ANGLES ="Angles";
        String SPACEID ="SpaceId";
        String LEVELID ="LevelId";
        String CUTCOUNT ="CutCount";
        String THEDEFORMATION ="TheDeformation";
        String ERRORDEFORMATION ="ErrorDeformation";
        String PERCENTAGE ="Percentage";
        String DIRECTION ="Direction";
        String DTYPE ="DType";
        String HEIGHT ="Height";
        String THERATE ="TheRate";
        String CENTERPOINT ="CenterPoint";
        String EDGEDISTANCE ="EdgeDistance";
        String DISEASEFROM ="DiseaseFrom";
        String CREATEDATE ="CreateDate";
        String UPDATEDATE ="UpdateDate";
        String ISREPEAT ="IsRepeat";
        String DISEASENAME ="DiseaseName";
        String CONTENTID ="ContentId";
        String CONTENTNAME ="ContentName";
        String ITEMID ="ItemId";
        String ISREPEATID ="IsRepeatId";
        String ITEMNAME ="ItemName";
        String CHECKNO ="CheckNo";
        String SPACENAME ="SpaceName";
        String LEVELNAME ="LevelName";
        String ROADID ="RoadId";
        String ROADNAME ="RoadName";
        String SECTIONID ="SectionId";
        String SECTIONNAME ="SectionName";
        String TUNNELID ="TunnelId";
        String TUNNELNAME ="TunnelName";
        String RECORDNAME ="RecordName";
        String CHECKNAME ="CheckName";
        String CHECKDATE ="CheckDate";
        String CHECKWAYID ="CheckWayId";
        String STATE ="State";
        String DISEASENUM ="DiseaseNum";


    }
    //账号信息表
    public static interface PAccount{
        String TABLE_PACCOUNT= "PAccount";
        String NAME="name";
        String PASSWORD="password";
        String ISSAVE="issave";  //0 没有保存 1保存
    }
    //机电信息
    public static interface ELMachine{
        String TABLE_ELMACHINE= "ELMachine";
        String NEWID= "NewId";
        String DEVICENAME= "DeviceName";
        String DEVICENO= "DeviceNo";
        String MMACHINEID= "MMachineId";
        String MANUFACTURER= "ManuFacturer";
        String USEAGE= "UseAge";
        String BUYDATE= "BuyDate";
        String USEDATE= "UseDate";
        String CREATEDATE= "CreateDate";
        String UPDATEDATE= "UpdateDate";
        String REMARK= "Remark";
        String PRICE= "Price";
        String ORGID= "OrgId";
        String COUNT= "Count";
        String LEAVEDATE= "LeaveDate";
        String ISUSE= "IsUse";
        String TWOCODE= "TwoCode";
        String MMACHINETYPEID= "MMachineTypeId";
        String MMACHINEDEVICEDID= "MMachineDeviceDId";
        String TYPE= "Type";
        ;
    }
    //机电类型信息
    public static interface ELMachineType{
        String TABLE_ELMACHINETYPE= "ELMachineType";
        String NEWID= "NewId";
        String MACHINETYPENAME= "MachineTypeName";
        String REMARK= "Remark";
        String SORT= "Sort";
        String PID= "Pid";
        String UPDATEDATE= "UpdateDate";
        String CREATEDATE= "CreateDate";
        String TYPE= "Type";
        ;
    }
    //隧道机电配置
    public static interface ELTunnelDeploy{
        String TABLE_ELTUNNELDEPLOY= "ELTunnelDeploy";
        String NEWID= "NewId";
        String TUNNELD= "Tunneld";
        String ORGID= "OrgId";
        String STARTCONSTRUCTION= "StartConstruction";
        String ENDCONSTRUCTION= "EndConstruction";
        String UPDATEDATE= "UpdateDate";
        String CREATEDATE= "CreateDate";
        String USERDATE= "UserDate";
        String ISUSER= "IsUser";
        String SORT= "Sort";
        String REMARK= "Remark";
        String MAXDEVICEID= "MaxDeviceId";
        String MINDEVICEID= "MinDeviceId";
        String DEVICETYPE= "DeviceType";
        ;
    }
    //机电类型关系信息
    public static interface ELMachineByType{
        String TABLE_ELMACHINEBYTYPE = "ELMachineByType";
        String NEWID= "NewId";
        String WAYID= "WayId";
        String MMACHINETYPEID= "MMachineTypeId";
        String DEPLOYTYPE= "DeployType";
        String TUNNELID= "TunnelId";
        String CREATEDATE= "CreateDate";
        String UPDATEDATE= "UpdateDate";
        ;
    }
    //机电类型项目关系表
    public static interface ELMachineTypeByItem{
        String TABLE_ELMACHINETYPEBYITEM = "ELMachineTypeByItem";
        String NEWID= "NewId";
        String MMACHINERWAYID= "MMachineRWayId";
        String MMACHINEITEMID= "MMachineItemId";
        String MMACHINEITEMNAME= "MMachineItemName";
        ;
    }
    //机电类型项目内容关系表
    public static interface ELMachineItemByContent{
        String TABLE_ELMACHINEITEMBYCONTENT = "ELMachineItemByContent";
        String NEWID= "NewId";
        String MMACHINERITEMID= "MMachineRItemId";
        String MMACHINECONTENTID= "MMachineContentId";
        String MCONTENTNAME= "MContentName";
        ;
    }
    //机电任务表
    public static interface ELMachineTask{
        String TABLE_ELMACHINETASK = "ELMachineTask";
        String NEWID= "NewId";
        String CHECKNO= "CheckNo";
        String TUNNELID= "TunnelId";
        String CHECKEMP= "CheckEmp";
        String CHECKDATE= "CheckDate";
        String CHECKWEATHER= "CheckWeather";
        String CHECKWAYID= "CheckWayId";
        String REMARK= "Remark";
        String RECORDEMP= "RecordEmp";
        String CREATEDATE= "CreateDate";
        String AUDITCOUNT= "AuditCount";
        String UPDATEDATE= "UpdateDate";
        String MAINTENANCEORGAN= "MaintenanceOrgan";
        String USERID= "UserId";
        String UPDATESTATE= "UpdateState";
        ;
    }
    //机电描述关系表
    public static interface ELMachineContentByDescript{
        String TABLE_ELMACHINECONTENTBYDESCRIPT = "ELMachineContentByDescript";
        String NEWID= "NewId";
        String MMACHINEITEMRCONTENTID= "MMachineItemRContentId";
        String MMACHINEDESCRIPTID= "MMachineDescriptId";
        String MDESCRIPTNAME= "MDescriptName";
        ;
    }
    //机电检修等级
    public static interface ELMachineDecisionLevel{
        String TABLE_ELMACHINEDECISIONLEVEL = "ELMachineDecisionLevel";
        String NEWID = "NewId";//单位ID
        String NAME = "Name";//单位名称
        String TYPE = "Type";
        String SORT = "Sort";
    }
    //机电检修病害
    public static interface ELMachineDiseaseInfo{
        String TABLE_ELMACHINEDISEASEINFO = "ELMachineDiseaseInfo";
        String NEWID = "NewId";
        String ISUSE = "IsUse";
        String REMARK = "Remark";
        String LEVELID = "LevelId";
        String MMACHINECONTENTID = "MMachineContentId";
        String MMACHINEITEMID = "MMachineItemId";
        String MMACHINEDEVICEID = "MMachineDeviceId";
        String LEVELSTRING = "LevelString";
        String CONTENTSTRING = "ContentString";
        String ITEMSTRING = "ItemString";
        String DEVICESTRING = "DeviceString";
        String TUNNELID = "TunnelId";
        String TUNNELNAME = "TunnelName";
        String USERID = "UserId";
        String MMACHINEDESCRIPTID = "MMachineDescriptId";
        String DESCRIPSTRING = "DescripString";
        String TASKID = "TaskId";
        String MMACHINEDEVICEDID = "MMachineDeviceDId";
        String CONSERVATIONMEASURESID = "ConservationMeasuresId";
    }
    //机电检修病害图片
    public static interface ELMachineDiseasePhoto {
        String TABLE_ELMACHINEDISEASEPHOTO = "ELMachineDiseasePhoto";
        String GUID = "Guid";
        String DISEASEGUID = "DiseaseGuid";
        String POSITION = "Position";
        String NAME = "Name";
        String TASKID = "TaskId";
        String UPDATESTATE = "UpdateState";
        String LATTERNAME = "LatterName";
        String WEBDOCUMENT = "WebDocument";
    }
        //菜单
        public static interface ELMenu {
            String TABLE_ELMENU = "ELMenu";
            String NEWID = "NewId";
            String MENUNAME = "MenuName";
            String PID = "Pid";
            String MENUURL = "MenuUrl";
            String SORT = "Sort";
            String MENUNO = "MenuNo";
        }
    }
