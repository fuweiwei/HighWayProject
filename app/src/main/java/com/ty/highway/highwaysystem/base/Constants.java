package com.ty.highway.highwaysystem.base;

public class Constants {

	//DBVersion 5-6增加CTDiseaseInfo表 ConservationMeasuresId字段
	//DBVersion 6-7增加机电检修相关表
	public static final int NEW_DB_VERSION = 7;
	//命名空间
	public static final String SOAP_URL = "http://tempuri.org/";
	public static final String KEY="FC240E11-D9D5-443F-BBE5-7CD49381B7A7" ;
	public static final String ENCODINGSTYLE="UTF-8" ;
	//public static final String WEBURL="220.197.219.59";//正式网络请求地址
	public static final String WEBURL="192.168.0.3:8000";//测试网络请求地址

	//服务地址类型名
	public static final String SERVICEURL_TYPE_BASEINFO="BaseInfo.asmx?";
	public static final String SERVICEURL_TYPE_CHECK="Check.asmx?" ;
	public static final String SERVICEURL_TYPE_BTUNNELINFO="BtunnelInfo.asmx?" ;
	public static final String SERVICEURL_TYPE_TUNNELCHECK="TunnelCheck.asmx" ;
	public static final String SERVICEURL_TYPE_CHECKCONTENTFORTEMPORARY="CheckContentForTemporary.asmx" ;
	public static final String SERVICEURL_TYPE_MACHINECHECK="MachineCheck.asmx" ;

	//方法名
	/* 检查版本更新 */
	public static final String METHOD_VERSIONYPDATE = "CheckVersions";
	/* 验证登录 */
	public static final String METHOD_LOGIN= "Login";
	/* 获取菜单 */
	public static final String METHOD_GETMENU= "GetMenu";
	/* 获取所有线路信息 */
	public static final String METHOD_GETALLBLOADINFO = "GetAllBRoadInfo";
	/* 获取所有路段信息 */
	public static final String METHOD_GETALLBSECTIONINFO = "GetAllBSectionInfo";
	/* 获取所有用户信息 */
	public static final String METHOD_GETALLUSERS = "GetAllUsers";
	/* 获取所有隧道信息 */
	public static final String METHOD_GETALLBTUNNELINFO = "GetAllBTunnelInfo";
	/* 获取所有单位信息 */
	public static final String METHOD_GETALLBYPORGANIZE = "GetAllByPOrganize";
	/* 获取所有任务 */
	public static final String METHOD_GETALLCHECKTASKINFO = "GetAllCheckTaskInfo";
	/* 基础数据更新 */
	public static final String METHOD_GETISDATAUPDATE = "GetIsDataUpdate";
	/* 获取所有用户组织关系 */
	public static final String METHOD_GETALLUSERVSORGANIZATION = "GetAllUserVsOrganization";
	/* 获取所有用户组织关系 */
	public static final String METHOD_GETUSERCHECKTUNNELPERMISSION = "GetUserCheckTunnelPermission";
	/* 获取当前用户检查权限(用户与隧道的关系) */
	public static final String METHOD_CTCHECKPOSITION = "GetCheckPosition";
	/* 获取当前隧道的配置项(隧道与检查项) */
	public static final String METHOD_GETCHECKTUNNELVSCHECKITEM = "GetCheckTunnelVsCheckItem";
	/* (检查项与病害类型) */
	public static final String METHOD_GETCHECKITEMVSDISEASETYPE = "GetCheckItemVsDiseaseType";
	/* 获取病害类型与病害等及描述的关系 */
	public static final String METHOD_GETCHECKTYPEVSDISEASEDESC = "GetCheckTypeVsDiseaseDesc";
	/* 获取定期详细描述 */
	public static final String METHOD_GETDISEASETYPEBYDESCRIPT = "GetDiseaseTypeByDescript";
	/* 获取字典数据(目前只有病害等级、检查方式) */
	public static final String METHOD_GETDICTIONARY = "GetDictionary";
	/* 上传检查数据 */
	public static final String METHOD_GETSAVECHECKDATA = "SaveCheckData";
	/** 登录验证 */
	public static final String METHOD_LOGINAUTHENTICATION = "LoginAuthentication";
	/** 任务检查数据是否可以上传 */
	public static final String METHOD_GETISREPORT = "GetIsReport";
	/** 获取App最新版本 */
	public static final String METHOD_LOADAPPVERSION = "LoadAppVersion";
	/** 上传旧版数据*/
	public static final String METHOD_CHECKCOTENT = "CheckCotent";
	/** 获取病害库信息*/
	public static final String METHOD_GETDIEASEBASE = "GetDieaseBase";
	/** 获取病害库照片信息*/
	public static final String METHOD_GETPHOTODATA = "GetPhotoData";

	//机电检修
	/** 获取机电检修任务*/
	public static final String METHOD_GETMACHINETASK = "GetMachineTask";
	/** 获取机电库信息*/
	public static final String METHOD_GETALLMACHINE = "GetAllMachine";
	/** 获取机电类型信息*/
	public static final String METHOD_GETALLMACHINETYPE = "GetAllMachineType";
	/** 获取所有隧道机电配置*/
	public static final String METHOD_GETBTUNNELDEPLOY= "GetBtunnelDeploy";
	/** 获取机电类型关系信息*/
	public static final String METHOD_GETMACHINEBYTYPE = "GetMachineByType";
	/** 获取机电类型项目关系表*/
	public static final String METHOD_GETMACHINETYPEBYITEM = "GetMachineTypeByItem";
	/** 获取机电类型项目内容关系表*/
	public static final String METHOD_GETMACHINEITEMBYCONTENT = "GetMachineItemByContent";
	/** 获取机电描述关系表*/
	public static final String METHOD_GETMACHINECONTENTBYDESCRIPT = "GetMachineContentByDescript";
	/** 机电等级判定*/
	public static final String METHOD_GETMACHINEDECISIONLEVEL = "GetMachineDecisionLevel";
	/** 保存机电信息*/
	public static final String METHOD_SAVEMACHINELETTER = "SaveMachineLetter";
	/** 机电是否更新*/
	public static final String METHOD_GETMACHINEISUPDATE = "GetMachineIsUpdate";
	/** 机电任务是否上传*/
	public static final String METHOD_GETMACHINETASKISREPORT = "GetMachineTaskIsReport";


	//检查来源
	public static final String DISEASEFROM_HAND = "2";//手持端
	public static final String DISEASEFROM_CAR = "3";//采集车
	//任务状态
	public static final int TASK_STATE_NOSTART = 0;//未开始
	public static final int TASK_STATE_WORKING = 1;//进行中
	public static final int TASK_STATE_FINISH = 2;//已完成
	public static final int TASK_STATE_HASLOADING = 3;//已上传

	//SharedPreferences 存值
	public static final String SP_IS_SAVEPW = "isSavePw";//是否保存密码
	public static final String SP_LOGIN_KEY = "loginKey";//保存的key
	public static final String SP_USER_NAME = "userName";//保存的用户名
	public static final String SP_USER_ID = "userId";//保存的用户ID
	public static final String SP_DPT_ID = "dptId";//保存的用户组织ID
	public static final String SP_USER_PW = "userPw";//保存的用户密码
	public static final String SP_IS_VERIFICATION = "isVerification";//设备是否被认证
	public static final String SP_WEBURL = "webUrl";//网络请求地址
	public static final String SP_HOSTROY_TIME = "histroyTime";//历史病害更新时间

	//检查方式
	public static final int CHECK_TYPE_DAILY = 1;//日常检查
	public static final int CHECK_TYPE_OFTEN = 2;//经常检查
	public static final int CHECK_TYPE_REGULAR = 3;//定期检查
	public static final int CHECK_TYPE_URGENT = 4;//应急检查
	public static final int CHECK_TYPE_SPECIAL = 5;//专项检查

	//菜单种类Id
	public static final String MENU_TYPE_CHECK = "TJ";//土建检查
	public static final String MENU_TYPE_CHECK_OFEN = "TJC";//土建经常检查
	public static final String MENU_TYPE_CHECK_FIX = "TJD";//土建定期检查
	public static final String MENU_TYPE_MACHINE= "JD";//机电检修
	public static final String MENU_TYPE_MACHINE_OFEN= "JDC";//机电经常检修
	public static final String MENU_TYPE_MACHINE_FIX= "JDD";//机电定期检修

	//历史病害
	public static final String HISTROY_FIX_TIME = "histroyTimeFix";//定期检查病害时间
	public static final String HISTROY_OFTEN_TIME = "histroyTimeOften";//检查检查病害时间

	//消息通知常量
	public static final int MSG_0x2001 = 0x2001;
	public static final int MSG_0x2002 = 0x2002;
	public static final int MSG_0x2003 = 0x2003;
	public static final int MSG_0x2004 = 0x2004;
	public static final int MSG_0x2005 = 0x2005;
	public static final int MSG_0x2006 = 0x2006;
	public static final int MSG_0x2007 = 0x2007;
	public static final int MSG_0x2008 = 0x2008;
	public static final int MSG_0x2001E = 0x20011;
	public static final int MSG_0x2002E = 0x20021;
	public static final int MSG_0x2003E = 0x20031;
	public static final int MSG_0x2004E = 0x20041;
	public static final int MSG_0x2005E = 0x20051;
	public static final int MSG_0x2006E = 0x20061;
	public static final int MSG_0x2007E = 0x20071;
	public static final int MSG_0x2008E = 0x20081;
	public static final int MSG_0x2009E = 0x20091;
	public static final int MSG_0x2009 = 0x2009;
	public static final int MSG_0x20010 = 0x20010;
	public static final int SUCCESS = 0x1001;
	public static final int ERRER = 0x1002;

	//病害图片和视屏存放路径
	public static final String SD_DAMAGE_PATH = "/.HighWay/damage/";
	//历史病害图片地址
	public static final String SD_HISTROYDAMAGE_PATH = "/.HighWay/histroyImg/";
	//菜单图片地址
	public static final String SD_MENUIMG_PATH = "/.HighWay/menu/";
	//机电检修图片和视频地址
	public static final String SD_MACHINE_DAMAGE_PATH = "/.HighWay/machine/";
	//异常存放路径
	public static final String SD_EXCEPTION_PATH = "/.HighWay/exception/";
	/* 下载包安装路径 */
	public static final String SD_APK_PATH = "/.HighWay/apk/";
	//视屏最大大小100M
	public static final int VIDEO_MAX_SIZE = 1024*100;
	//视屏最大录制时间1分钟
	public static final int VIDEO_MAX_TIME = 1*60;
	//时间格式
	public static final String FULL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATE_FORMAT_NO_ = "yyyyMMdd";
	public static final String TIME_FORMAT = "HH:mm:ss";
	public static final String DATE_FORMAT_TO_SECONDS_NO_LINE = "yyyyMMddHHmmss";

	//异常处理
	public static final String EXCEPTION_CUSTOMERCODE = "HighWay";
	//用于上传异常的接口
	public static final String SERVER_COMMON_PUBLIC_KEY = "681515F7-B54C-4E17-9322-9A024A258DB3";
	public static final String SERVER_COMMON_WEBSERVICE = "http://www.geodigital.cn/PadCommonService/PadService.asmx";
	public static final String METHOD_OF_COMMON_EXCEPTIONUP = "ExceptionUp";//异常
	public static final String METHOD_OF_COMMON_GetDatetime = "GetDatetime";//时间
}
