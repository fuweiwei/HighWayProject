package com.ty.highway.highwaysystem.support.db.basic;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by fuweiwei on 2016/1/13.
 * 数据库版本更新辅助类
 */
public class DBVersionHelper {
    private SQLiteDatabase mSqliteDb;
    private int mOldVersion;
    private int mNewVersion;
    public DBVersionHelper(SQLiteDatabase db,int oldVer,int newVer ){
        mSqliteDb = db;
        mOldVersion = oldVer;
        mNewVersion = newVer;
    }
    //---------------------------------------- 数据库版本5-6：增加CTDiseaseInfo表 ConservationMeasuresId字段------------------------------------------------------

    /**
     * 增加CTDiseaseInfo表 ConservationMeasuresId字段
     */
    String ALTER_CTDISEASEINFO="ALTER TABLE "+ TableColumns.CTDiseaseInfo.TABLE_CTDISEASEINFO+" ADD COLUMN "+ TableColumns.CTDiseaseInfo.CONSERVATIONMEASURESID+" VARCHAR ";

    //------------------------------------------------------------------------------------------------------------------------------------------------------------



    //---------------------------------------- 数据库版本6-7：增加机电检修若干表----------------------------------------------------------------------------------

    /**
     * 创建机电设备信息表
     */
    private static final String CREATE_ELMACHINE ="CREATE TABLE IF NOT EXISTS "+TableColumns.ELMachine.TABLE_ELMACHINE
            +"("
            + "_id integer primary key autoincrement,"
            +TableColumns.ELMachine.NEWID+" VARCHAR,"
            +TableColumns.ELMachine.DEVICENAME+" VARCHAR,"
            +TableColumns.ELMachine.DEVICENO+" VARCHAR,"
            +TableColumns.ELMachine.MMACHINEID+" VARCHAR,"
            +TableColumns.ELMachine.MANUFACTURER+" VARCHAR,"
            +TableColumns.ELMachine.USEAGE+" INTEGER,"
            +TableColumns.ELMachine.BUYDATE+" VARCHAR,"
            +TableColumns.ELMachine.USEDATE+" VARCHAR,"
            +TableColumns.ELMachine.CREATEDATE+" VARCHAR,"
            +TableColumns.ELMachine.UPDATEDATE+" VARCHAR,"
            +TableColumns.ELMachine.REMARK+" VARCHAR,"
            +TableColumns.ELMachine.PRICE+" VARCHAR,"
            +TableColumns.ELMachine.ORGID+" VARCHAR,"
            +TableColumns.ELMachine.COUNT+" INTEGER,"
            +TableColumns.ELMachine.LEAVEDATE+" VARCHAR,"
            +TableColumns.ELMachine.ISUSE+" BOOLEAN,"
            +TableColumns.ELMachine.TWOCODE+" VARCHAR,"
            +TableColumns.ELMachine.MMACHINETYPEID+" VARCHAR,"
            +TableColumns.ELMachine.TYPE+" VARCHAR"
            +")";
    /**
     * 创建机电类型信息表
     */
    private static final String CREATE_ELMACHINEBYTYPE ="CREATE TABLE IF NOT EXISTS "+TableColumns.ELMachineByType.TABLE_ELMACHINEBYTYPE
            +"("
            + "_id integer primary key autoincrement,"
            +TableColumns.ELMachineByType.NEWID+" VARCHAR,"
            +TableColumns.ELMachineByType.WAYID+" VARCHAR,"
            +TableColumns.ELMachineByType.MMACHINETYPEID+" VARCHAR,"
            +TableColumns.ELMachineByType.DEPLOYTYPE+" VARCHAR,"
            +TableColumns.ELMachineByType.TUNNELID+" VARCHAR,"
            +TableColumns.ELMachineByType.CREATEDATE+" INTEGER,"
            +TableColumns.ELMachineByType.UPDATEDATE+" VARCHAR"
            +")";

    /**
     * 创建机电描述关系信息表
     */
    private static final String CREATE_ELMACHINECONTENTBYDESCRIPT ="CREATE TABLE IF NOT EXISTS "+TableColumns.ELMachineContentByDescript.TABLE_ELMACHINECONTENTBYDESCRIPT
            +"("
            + "_id integer primary key autoincrement,"
            +TableColumns.ELMachineContentByDescript.NEWID+" VARCHAR,"
            +TableColumns.ELMachineContentByDescript.MMACHINEITEMRCONTENTID+" VARCHAR,"
            +TableColumns.ELMachineContentByDescript.MMACHINEDESCRIPTID+" VARCHAR,"
            +TableColumns.ELMachineContentByDescript.MDESCRIPTNAME+" VARCHAR"
            +")";
    /**
     * 创建机电等级判定信息表
     */
    private static final String CREATE_ELMACHINEDECISIONLEVEL ="CREATE TABLE IF NOT EXISTS "+TableColumns.ELMachineDecisionLevel.TABLE_ELMACHINEDECISIONLEVEL
            +"("
            + "_id integer primary key autoincrement,"
            +TableColumns.ELMachineDecisionLevel.NEWID+" VARCHAR,"
            +TableColumns.ELMachineDecisionLevel.NAME+" VARCHAR,"
            +TableColumns.ELMachineDecisionLevel.TYPE+" VARCHAR,"
            +TableColumns.ELMachineDecisionLevel.SORT+" VARCHAR"
            +")";
    /**
     * 创建机电检修记录信息表
     */
    private static final String CREATE_ELMACHINEDISEASEINFO ="CREATE TABLE IF NOT EXISTS "+TableColumns.ELMachineDiseaseInfo.TABLE_ELMACHINEDISEASEINFO
            +"("
            + "_id integer primary key autoincrement,"
            +TableColumns.ELMachineDiseaseInfo.NEWID+" VARCHAR,"
            +TableColumns.ELMachineDiseaseInfo.ISUSE+" VARCHAR,"
            +TableColumns.ELMachineDiseaseInfo.REMARK+" VARCHAR,"
            +TableColumns.ELMachineDiseaseInfo.LEVELID+" VARCHAR,"
            +TableColumns.ELMachineDiseaseInfo.MMACHINECONTENTID+" VARCHAR,"
            +TableColumns.ELMachineDiseaseInfo.MMACHINEITEMID+" VARCHAR,"
            +TableColumns.ELMachineDiseaseInfo.MMACHINEDEVICEID+" VARCHAR,"
            +TableColumns.ELMachineDiseaseInfo.LEVELSTRING+" VARCHAR,"
            +TableColumns.ELMachineDiseaseInfo.CONTENTSTRING+" VARCHAR,"
            +TableColumns.ELMachineDiseaseInfo.ITEMSTRING+" VARCHAR,"
            +TableColumns.ELMachineDiseaseInfo.DEVICESTRING+" VARCHAR,"
            +TableColumns.ELMachineDiseaseInfo.TUNNELID+" VARCHAR,"
            +TableColumns.ELMachineDiseaseInfo.TUNNELNAME+" VARCHAR,"
            +TableColumns.ELMachineDiseaseInfo.USERID+" VARCHAR,"
            +TableColumns.ELMachineDiseaseInfo.MMACHINEDESCRIPTID+" VARCHAR,"
            +TableColumns.ELMachineDiseaseInfo.DESCRIPSTRING+" VARCHAR,"
            +TableColumns.ELMachineDiseaseInfo.TASKID+" VARCHAR,"
            +TableColumns.ELMachineDiseaseInfo.CONSERVATIONMEASURESID+" VARCHAR,"
            +TableColumns.ELMachineDiseaseInfo.MMACHINEDEVICEDID+" VARCHAR"
            +")";
    /**
     * 创建机电检修记录图片信息表
     */
    private static final String CREATE_ELMACHINEDISEASEPHOTO ="CREATE TABLE IF NOT EXISTS "+TableColumns.ELMachineDiseasePhoto.TABLE_ELMACHINEDISEASEPHOTO
            +"("
            + "_id integer primary key autoincrement,"
            +TableColumns.ELMachineDiseasePhoto.GUID+" VARCHAR,"
            +TableColumns.ELMachineDiseasePhoto.DISEASEGUID+" VARCHAR,"
            +TableColumns.ELMachineDiseasePhoto.POSITION+" VARCHAR,"
            +TableColumns.ELMachineDiseasePhoto.NAME+" VARCHAR,"
            +TableColumns.ELMachineDiseasePhoto.TASKID+" VARCHAR,"
            +TableColumns.ELMachineDiseasePhoto.UPDATESTATE+" VARCHAR,"
            +TableColumns.ELMachineDiseasePhoto.LATTERNAME+" VARCHAR,"
            +TableColumns.ELMachineDiseasePhoto.WEBDOCUMENT+" VARCHAR"
            +")";
    /**
     * 创建机电类型项目内容关系信息表
     */
    private static final String CREATE_ELMACHINEITEMBYCONTENT ="CREATE TABLE IF NOT EXISTS "+TableColumns.ELMachineItemByContent.TABLE_ELMACHINEITEMBYCONTENT
            +"("
            + "_id integer primary key autoincrement,"
            +TableColumns.ELMachineItemByContent.NEWID+" VARCHAR,"
            +TableColumns.ELMachineItemByContent.MMACHINERITEMID+" VARCHAR,"
            +TableColumns.ELMachineItemByContent.MMACHINECONTENTID+" VARCHAR,"
            +TableColumns.ELMachineItemByContent.MCONTENTNAME+" VARCHAR"
            +")";
    /**
     * 创建机电检修任务信息表
     */
    private static final String CREATE_ELMACHINETASK ="CREATE TABLE IF NOT EXISTS "+TableColumns.ELMachineTask.TABLE_ELMACHINETASK
            +"("
            + "_id integer primary key autoincrement,"
            +TableColumns.ELMachineTask.NEWID+" VARCHAR,"
            +TableColumns.ELMachineTask.CHECKNO+" VARCHAR,"
            +TableColumns.ELMachineTask.TUNNELID+" VARCHAR,"
            +TableColumns.ELMachineTask.CHECKEMP+" VARCHAR,"
            +TableColumns.ELMachineTask.CHECKDATE+" VARCHAR,"
            +TableColumns.ELMachineTask.CHECKWEATHER+" VARCHAR,"
            +TableColumns.ELMachineTask.CHECKWAYID+" VARCHAR,"
            +TableColumns.ELMachineTask.REMARK+" VARCHAR,"
            +TableColumns.ELMachineTask.RECORDEMP+" VARCHAR,"
            +TableColumns.ELMachineTask.CREATEDATE+" VARCHAR,"
            +TableColumns.ELMachineTask.AUDITCOUNT+" VARCHAR,"
            +TableColumns.ELMachineTask.UPDATEDATE+" VARCHAR,"
            +TableColumns.ELMachineTask.MAINTENANCEORGAN+" VARCHAR,"
            +TableColumns.ELMachineTask.USERID+" VARCHAR,"
            +TableColumns.ELMachineTask.UPDATESTATE+" INTEGER"
            +")";
    /**
     * 创建机电类型信息表
     */
    private static final String CREATE_ELMACHINETYPE ="CREATE TABLE IF NOT EXISTS "+TableColumns.ELMachineType.TABLE_ELMACHINETYPE
            +"("
            + "_id integer primary key autoincrement,"
            +TableColumns.ELMachineType.NEWID+" VARCHAR,"
            +TableColumns.ELMachineType.MACHINETYPENAME+" VARCHAR,"
            +TableColumns.ELMachineType.REMARK+" VARCHAR,"
            +TableColumns.ELMachineType.SORT+" INTEGER,"
            +TableColumns.ELMachineType.PID+" VARCHAR,"
            +TableColumns.ELMachineType.UPDATEDATE+" VARCHAR,"
            +TableColumns.ELMachineType.CREATEDATE+" VARCHAR,"
            +TableColumns.ELMachineType.TYPE+" INTEGER"
            +")";
    /**
     * 创建机电类型项目关系信息表
     */
    private static final  String CREATE_ELMACHINETYPEBYITEM ="CREATE TABLE IF NOT EXISTS "+TableColumns.ELMachineTypeByItem.TABLE_ELMACHINETYPEBYITEM
            +"("
            + "_id integer primary key autoincrement,"
            +TableColumns.ELMachineTypeByItem.NEWID+" VARCHAR,"
            +TableColumns.ELMachineTypeByItem.MMACHINERWAYID+" VARCHAR,"
            +TableColumns.ELMachineTypeByItem.MMACHINEITEMID+" VARCHAR,"
            +TableColumns.ELMachineTypeByItem.MMACHINEITEMNAME+" VARCHAR"
            +")";
    /**
     * 创建隧道机电配置信息表
     */
    private static final  String CREATE_ELTUNNELDEPLOY ="CREATE TABLE IF NOT EXISTS "+TableColumns.ELTunnelDeploy.TABLE_ELTUNNELDEPLOY
            +"("
            + "_id integer primary key autoincrement,"
            +TableColumns.ELTunnelDeploy.NEWID+" VARCHAR,"
            +TableColumns.ELTunnelDeploy.TUNNELD+" VARCHAR,"
            +TableColumns.ELTunnelDeploy.ORGID+" VARCHAR,"
            +TableColumns.ELTunnelDeploy.STARTCONSTRUCTION+" VARCHAR,"
            +TableColumns.ELTunnelDeploy.ENDCONSTRUCTION+" VARCHAR,"
            +TableColumns.ELTunnelDeploy.UPDATEDATE+" VARCHAR,"
            +TableColumns.ELTunnelDeploy.CREATEDATE+" VARCHAR,"
            +TableColumns.ELTunnelDeploy.USERDATE+" VARCHAR,"
            +TableColumns.ELTunnelDeploy.ISUSER+" VARCHAR,"
            +TableColumns.ELTunnelDeploy.SORT+" INTEGER,"
            +TableColumns.ELTunnelDeploy.REMARK+" VARCHAR,"
            +TableColumns.ELTunnelDeploy.MAXDEVICEID+" VARCHAR,"
            +TableColumns.ELTunnelDeploy.MINDEVICEID+" VARCHAR,"
            +TableColumns.ELTunnelDeploy.DEVICETYPE+" INTEGER"
            +")";
    /**
     * 创建菜单配置信息表
     */
    private static final  String CREATE_ELMENU ="CREATE TABLE IF NOT EXISTS "+TableColumns.ELMenu.TABLE_ELMENU
            +"("
            + "_id integer primary key autoincrement,"
            +TableColumns.ELMenu.NEWID+" VARCHAR,"
            +TableColumns.ELMenu.MENUNAME+" VARCHAR,"
            +TableColumns.ELMenu.PID+" VARCHAR,"
            +TableColumns.ELMenu.MENUURL+" VARCHAR,"
            +TableColumns.ELMenu.MENUNO+" VARCHAR,"
            +TableColumns.ELMenu.SORT+" VARCHAR"
            +")";
    //------------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 更新数据库表
     */
    public void update(){
        if (mNewVersion > mOldVersion) {
            if(mOldVersion<mNewVersion-1){
                mSqliteDb.execSQL(ALTER_CTDISEASEINFO);
            }
            mSqliteDb.execSQL(CREATE_ELMACHINE);
            mSqliteDb.execSQL(CREATE_ELMACHINEBYTYPE);
            mSqliteDb.execSQL(CREATE_ELMACHINECONTENTBYDESCRIPT);
            mSqliteDb.execSQL(CREATE_ELMACHINEDECISIONLEVEL);
            mSqliteDb.execSQL(CREATE_ELMACHINEDISEASEINFO);
            mSqliteDb.execSQL(CREATE_ELMACHINEDISEASEPHOTO);
            mSqliteDb.execSQL(CREATE_ELMACHINEITEMBYCONTENT);
            mSqliteDb.execSQL(CREATE_ELMACHINETASK);
            mSqliteDb.execSQL(CREATE_ELMACHINETYPE);
            mSqliteDb.execSQL(CREATE_ELMACHINETYPEBYITEM);
            mSqliteDb.execSQL(CREATE_ELTUNNELDEPLOY);
            mSqliteDb.execSQL(CREATE_ELMENU);
        }
    }
}
