package com.ty.highway.highwaysystem.support.db.machine;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.ty.highway.highwaysystem.support.bean.machine.ELMachineTaskBean;
import com.ty.highway.highwaysystem.support.db.basedata.DBBSectionDao;
import com.ty.highway.highwaysystem.support.db.basedata.DBBTunnelDao;
import com.ty.highway.highwaysystem.support.db.basic.DBDao;
import com.ty.highway.highwaysystem.support.db.basic.TableColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuweiwei on 2016/1/5.
 */
public class DBELMachineTaskDao extends DBDao implements TableColumns.ELMachineTask {
    private final static String TAG = "DBELMachineTaskDao";
    private static DBELMachineTaskDao instance;
    private Context mContext;

    private DBELMachineTaskDao(Context context) {
        super(context);
        mContext = context;
    }

    public static DBELMachineTaskDao getInstance(Context context) {
        if (instance == null) {
            synchronized (DBELMachineTaskDao.class) {
                instance = new DBELMachineTaskDao(context.getApplicationContext());
            }
        }
        return instance;
    }

    /**
     * 添加一条数据
     * @param bean
     */
    public  void addData(ELMachineTaskBean bean,String userId){
        if(bean ==null){
            return ;
        }
        SQLiteDatabase database = getDataBase();
        ContentValues values = new ContentValues();
        values.put(NEWID,bean.getNewId());
        values.put(CHECKNO,bean.getCheckNo());
        values.put(TUNNELID,bean.getTunnelId());
        values.put(TUNNELID,bean.getTunnelId());
        values.put(CHECKDATE,bean.getCheckDate());
        values.put(CHECKWEATHER,bean.getCheckWeather());
        values.put(CHECKWAYID,bean.getCheckWayId());
        values.put(REMARK,bean.getRemark());
        values.put(RECORDEMP,bean.getRecordEmp());
        values.put(CREATEDATE,bean.getCreateDate());
        values.put(AUDITCOUNT,bean.getAuditCount());
        values.put(UPDATEDATE,bean.getUpdateDate());
        values.put(MAINTENANCEORGAN,bean.getMaintenanceOrgan());
        values.put(CHECKEMP,bean.getCheckEmp());
        values.put(USERID,userId);
        values.put(UPDATESTATE,0);
        database.insert(TABLE_ELMACHINETASK, NEWID, values);
        closeDB();
    }
    /**
     * 判断是否有该任务
     * @param checkNo
     * @return
     */
    public Boolean isHasTaskById(String checkNo,String userId){
        if(TextUtils.isEmpty(checkNo)){
            return  false;
        }
        SQLiteDatabase database = getDataBase();
        String sql = "select count(*) from "+TABLE_ELMACHINETASK+" where "+CHECKNO+"=? and "+USERID+"=?";
        Cursor cursor = database.rawQuery(sql,new String[]{checkNo,userId});
        int count =0;
        if(cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        if(cursor != null) {
            cursor.close();
            cursor = null;
        }
        closeDB();
        return count != 0;
    }
    /**
     * 删除一个任务
     * @param checkNo
     */
    public  void deleteTaskInfoByNo(String checkNo,String userId){
        SQLiteDatabase database = getDataBase();
        database.delete(TABLE_ELMACHINETASK, CHECKNO +"=? and "+USERID+"=?", new String[] { checkNo,userId });
        closeDB();
    }
    /**
     * 获取机电检修的任务
     * @param userId
     * @return
     */
    public List<ELMachineTaskBean> getAllTask(String userId){
        List<ELMachineTaskBean> list = new ArrayList<>();
        if(userId==null){
            return list;
        }
        SQLiteDatabase database = getDataBase();
        String sql = "select *from "+TABLE_ELMACHINETASK+" where "+USERID+"=?";
        Cursor c = database.rawQuery(sql, new String[]{userId});
        while (c!=null&&c.moveToNext()){
            ELMachineTaskBean  bean = new ELMachineTaskBean();
            bean.setNewId(c.getString(c.getColumnIndex(NEWID)));
            bean.setCheckNo(c.getString(c.getColumnIndex(CHECKNO)));
            bean.setTunnelId(c.getString(c.getColumnIndex(TUNNELID)));
            bean.setCheckEmp(c.getString(c.getColumnIndex(CHECKEMP)));
            bean.setCheckDate(c.getString(c.getColumnIndex(CHECKDATE)));
            bean.setCheckWeather(c.getString(c.getColumnIndex(CHECKWEATHER)));
            bean.setCheckWayId(c.getString(c.getColumnIndex(CHECKWAYID)));
            bean.setRemark(c.getString(c.getColumnIndex(REMARK)));
            bean.setRecordEmp(c.getString(c.getColumnIndex(RECORDEMP)));
            bean.setCreateDate(c.getString(c.getColumnIndex(CREATEDATE)));
            bean.setAuditCount(c.getInt(c.getColumnIndex(AUDITCOUNT)));
            bean.setUpdateDate(c.getString(c.getColumnIndex(UPDATEDATE)));
            bean.setMaintenanceOrgan(c.getString(c.getColumnIndex(MAINTENANCEORGAN)));
            bean.setUserId(c.getString(c.getColumnIndex(USERID)));
            bean.setUpdateState(c.getInt(c.getColumnIndex(UPDATESTATE)));
            list.add(bean);
        }
        if(c != null) {
            c.close();
            c = null;
        }
        closeDB();
        return list;

    }
    /**
     * 获取机电检修的任务
     * @param userId
     * @return
     */
    public ELMachineTaskBean getTaskById(String taskId,String userId){
        ELMachineTaskBean  bean = new ELMachineTaskBean();
        if(userId==null){
            return null;
        }
        SQLiteDatabase database = getDataBase();
        String sql = "select *from "+TABLE_ELMACHINETASK+" where "+USERID+"=? and "+NEWID+"=?";
        Cursor c = database.rawQuery(sql, new String[]{userId,taskId});
        while (c!=null&&c.moveToNext()){
            bean.setNewId(c.getString(c.getColumnIndex(NEWID)));
            bean.setCheckNo(c.getString(c.getColumnIndex(CHECKNO)));
            bean.setTunnelId(c.getString(c.getColumnIndex(TUNNELID)));
            bean.setCheckEmp(c.getString(c.getColumnIndex(CHECKEMP)));
            bean.setCheckDate(c.getString(c.getColumnIndex(CHECKDATE)));
            bean.setCheckWeather(c.getString(c.getColumnIndex(CHECKWEATHER)));
            bean.setCheckWayId(c.getString(c.getColumnIndex(CHECKWAYID)));
            bean.setRemark(c.getString(c.getColumnIndex(REMARK)));
            bean.setRecordEmp(c.getString(c.getColumnIndex(RECORDEMP)));
            bean.setCreateDate(c.getString(c.getColumnIndex(CREATEDATE)));
            bean.setAuditCount(c.getInt(c.getColumnIndex(AUDITCOUNT)));
            bean.setUpdateDate(c.getString(c.getColumnIndex(UPDATEDATE)));
            bean.setMaintenanceOrgan(c.getString(c.getColumnIndex(MAINTENANCEORGAN)));
            bean.setUserId(c.getString(c.getColumnIndex(USERID)));
            bean.setUpdateState(c.getInt(c.getColumnIndex(UPDATESTATE)));
        }
        if(c != null) {
            c.close();
            c = null;
        }
        closeDB();
        return bean;

    }
    /**
     * 根据检修方式获取机电检修的任务
     * @param userId
     * @return
     */
    public List<ELMachineTaskBean> getTaskByCheckWay(String userId ,String checkWayId){
        List<ELMachineTaskBean> list = new ArrayList<>();
        if(userId==null||checkWayId==null){
            return list;
        }
        SQLiteDatabase database = getDataBase();
        String sql = "select *from "+TABLE_ELMACHINETASK+" where "+USERID+"=? and "+CHECKWAYID+"=?";
        Cursor c = database.rawQuery(sql, new String[]{userId,checkWayId});
        while (c!=null&&c.moveToNext()){
            ELMachineTaskBean  bean = new ELMachineTaskBean();
            bean.setNewId(c.getString(c.getColumnIndex(NEWID)));
            bean.setCheckNo(c.getString(c.getColumnIndex(CHECKNO)));
            bean.setTunnelId(c.getString(c.getColumnIndex(TUNNELID)));
            bean.setCheckEmp(c.getString(c.getColumnIndex(CHECKEMP)));
            bean.setCheckDate(c.getString(c.getColumnIndex(CHECKDATE)));
            bean.setCheckWeather(c.getString(c.getColumnIndex(CHECKWEATHER)));
            bean.setCheckWayId(c.getString(c.getColumnIndex(CHECKWAYID)));
            bean.setRemark(c.getString(c.getColumnIndex(REMARK)));
            bean.setRecordEmp(c.getString(c.getColumnIndex(RECORDEMP)));
            bean.setCreateDate(c.getString(c.getColumnIndex(CREATEDATE)));
            bean.setAuditCount(c.getInt(c.getColumnIndex(AUDITCOUNT)));
            bean.setUpdateDate(c.getString(c.getColumnIndex(UPDATEDATE)));
            bean.setMaintenanceOrgan(c.getString(c.getColumnIndex(MAINTENANCEORGAN)));
            bean.setUserId(c.getString(c.getColumnIndex(USERID)));
            bean.setUpdateState(c.getInt(c.getColumnIndex(UPDATESTATE)));
            list.add(bean);
        }
        if(c != null) {
            c.close();
            c = null;
        }
        closeDB();
        return list;

    }
    /**
     * 根据任务状态获取机电检修的任务
     * @param userId
     * @return
     */
    public List<ELMachineTaskBean> getTaskByState(String userId ,String checkWayId,int updateState){
        List<ELMachineTaskBean> list = new ArrayList<>();
        if(userId==null||checkWayId==null){
            return list;
        }
        SQLiteDatabase database = getDataBase();
        Cursor c = null;
        if(updateState<0){
            String sql = "select *from "+TABLE_ELMACHINETASK+" where "+USERID+"=? and "+CHECKWAYID+"=?";
            c= database.rawQuery(sql, new String[]{userId,checkWayId,});
        }else{
            String sql = "select *from "+TABLE_ELMACHINETASK+" where "+USERID+"=? and "+CHECKWAYID+"=? and "+UPDATESTATE+"=?";
            c= database.rawQuery(sql, new String[]{userId,checkWayId,updateState+""});
        }
        while (c!=null&&c.moveToNext()){
            ELMachineTaskBean  bean = new ELMachineTaskBean();
            bean.setNewId(c.getString(c.getColumnIndex(NEWID)));
            bean.setCheckNo(c.getString(c.getColumnIndex(CHECKNO)));
            bean.setTunnelId(c.getString(c.getColumnIndex(TUNNELID)));
            bean.setCheckEmp(c.getString(c.getColumnIndex(CHECKEMP)));
            bean.setCheckDate(c.getString(c.getColumnIndex(CHECKDATE)));
            bean.setCheckWeather(c.getString(c.getColumnIndex(CHECKWEATHER)));
            bean.setCheckWayId(c.getString(c.getColumnIndex(CHECKWAYID)));
            bean.setRemark(c.getString(c.getColumnIndex(REMARK)));
            bean.setRecordEmp(c.getString(c.getColumnIndex(RECORDEMP)));
            bean.setCreateDate(c.getString(c.getColumnIndex(CREATEDATE)));
            bean.setAuditCount(c.getInt(c.getColumnIndex(AUDITCOUNT)));
            bean.setUpdateDate(c.getString(c.getColumnIndex(UPDATEDATE)));
            bean.setMaintenanceOrgan(c.getString(c.getColumnIndex(MAINTENANCEORGAN)));
            bean.setUserId(c.getString(c.getColumnIndex(USERID)));
            bean.setUpdateState(c.getInt(c.getColumnIndex(UPDATESTATE)));
            list.add(bean);
        }
        if(c != null) {
            c.close();
            c = null;
        }
        closeDB();
        return list;

    }
    /**
     * 根据任务状态获取机电检修的任务
     * @param userId
     * @return
     */
    public List<ELMachineTaskBean> getTaskByState(String userId ,String checkWayId,int updateState,String tunnelId){
        List<ELMachineTaskBean> list = new ArrayList<>();
        if(userId==null||checkWayId==null){
            return list;
        }
        SQLiteDatabase database = getDataBase();
        Cursor c = null;
        if(updateState<0){
            String sql = "select *from "+TABLE_ELMACHINETASK+" where "+USERID+"=? and "+CHECKWAYID+"=? and "+TUNNELID+"=?";
            c= database.rawQuery(sql, new String[]{userId,checkWayId,tunnelId});
        }else{
            String sql = "select *from "+TABLE_ELMACHINETASK+" where "+USERID+"=? and "+CHECKWAYID+"=? and "+UPDATESTATE+"=? and "+TUNNELID+"=?";
            c= database.rawQuery(sql, new String[]{userId,checkWayId,updateState+"",tunnelId});
        }
        while (c!=null&&c.moveToNext()){
            ELMachineTaskBean  bean = new ELMachineTaskBean();
            bean.setNewId(c.getString(c.getColumnIndex(NEWID)));
            bean.setCheckNo(c.getString(c.getColumnIndex(CHECKNO)));
            bean.setTunnelId(c.getString(c.getColumnIndex(TUNNELID)));
            bean.setCheckEmp(c.getString(c.getColumnIndex(CHECKEMP)));
            bean.setCheckDate(c.getString(c.getColumnIndex(CHECKDATE)));
            bean.setCheckWeather(c.getString(c.getColumnIndex(CHECKWEATHER)));
            bean.setCheckWayId(c.getString(c.getColumnIndex(CHECKWAYID)));
            bean.setRemark(c.getString(c.getColumnIndex(REMARK)));
            bean.setRecordEmp(c.getString(c.getColumnIndex(RECORDEMP)));
            bean.setCreateDate(c.getString(c.getColumnIndex(CREATEDATE)));
            bean.setAuditCount(c.getInt(c.getColumnIndex(AUDITCOUNT)));
            bean.setUpdateDate(c.getString(c.getColumnIndex(UPDATEDATE)));
            bean.setMaintenanceOrgan(c.getString(c.getColumnIndex(MAINTENANCEORGAN)));
            bean.setUserId(c.getString(c.getColumnIndex(USERID)));
            bean.setUpdateState(c.getInt(c.getColumnIndex(UPDATESTATE)));
            list.add(bean);
        }
        if(c != null) {
            c.close();
            c = null;
        }
        closeDB();
        return list;

    }

    /**
     * 更新任务的信息
     * @param taskInfo
     * @param userId
     */
    public  void updateTaskInfo(ELMachineTaskBean taskInfo,String userId){
        SQLiteDatabase db =getDataBase();
        String sql = "update "+TABLE_ELMACHINETASK+" set "+CHECKDATE+"= '"+taskInfo.getCheckDate()+"' , " + CHECKWEATHER+"= '"+taskInfo.getCheckWeather()+
                "' , "+MAINTENANCEORGAN+"= '"+taskInfo.getMaintenanceOrgan()+"' , "+CHECKEMP+"= '"+taskInfo.getCheckEmp()+"' where "+CHECKNO+"= '"+
                taskInfo.getCheckNo()+"' and "+USERID+"='"+userId+"'";
        db.execSQL(sql);
        closeDB();
    }
    /**
     * 更新任务信息
     * @param taskInfo
     */
    public void updateTask(ELMachineTaskBean taskInfo,String userId){
        SQLiteDatabase db =getDataBase();
        String sql = "update "+TABLE_ELMACHINETASK+" set "+CHECKDATE+"= '"+taskInfo.getCheckDate()+"' , " + CHECKWEATHER+"= '"+taskInfo.getCheckWeather()+"' , "+UPDATESTATE+"= '"+taskInfo.getUpdateState()+"' where "+NEWID+"= '"+taskInfo.getNewId()+"' and "+USERID+"='"+userId+"'";
        db.execSQL(sql);
        closeDB();
    }

    /**
     * 更新任务状态
     * @param taskId
     * @param state
     */
    public void setState(String taskId,int state,String belongUserId) {
        if (taskId == null) {
            return;
        }
        SQLiteDatabase database = getDataBase();
        String id = null;
        String sql = "update  " + TABLE_ELMACHINETASK + " set " + UPDATESTATE + "='" + state + "' where " + NEWID + "='" + taskId + "' and " + USERID + "='" + belongUserId + "'";
        database.execSQL(sql);
        closeDB();
    }
    /**
     * 根据检隧道ID获取机电检修的任务
     * @param userId
     * @return
     */
    public List<ELMachineTaskBean> getTaskByTunenlId(String userId ,String checkWayId,String tunnelId){
        List<ELMachineTaskBean> list = new ArrayList<>();
        if(userId==null||checkWayId==null||tunnelId==null){
            return list;
        }
        SQLiteDatabase database = getDataBase();
        String sql = "select *from "+TABLE_ELMACHINETASK+" where "+USERID+"=? and "+CHECKWAYID+"=? and " +TUNNELID+"=?";
        Cursor c = database.rawQuery(sql, new String[]{userId,checkWayId,tunnelId});
        while (c!=null&&c.moveToNext()){
            ELMachineTaskBean  bean = new ELMachineTaskBean();
            bean.setNewId(c.getString(c.getColumnIndex(NEWID)));
            bean.setCheckNo(c.getString(c.getColumnIndex(CHECKNO)));
            bean.setTunnelId(c.getString(c.getColumnIndex(TUNNELID)));
            bean.setCheckEmp(c.getString(c.getColumnIndex(CHECKEMP)));
            bean.setCheckDate(c.getString(c.getColumnIndex(CHECKDATE)));
            bean.setCheckWeather(c.getString(c.getColumnIndex(CHECKWEATHER)));
            bean.setCheckWayId(c.getString(c.getColumnIndex(CHECKWAYID)));
            bean.setRemark(c.getString(c.getColumnIndex(REMARK)));
            bean.setRecordEmp(c.getString(c.getColumnIndex(RECORDEMP)));
            bean.setCreateDate(c.getString(c.getColumnIndex(CREATEDATE)));
            bean.setAuditCount(c.getInt(c.getColumnIndex(AUDITCOUNT)));
            bean.setUpdateDate(c.getString(c.getColumnIndex(UPDATEDATE)));
            bean.setMaintenanceOrgan(c.getString(c.getColumnIndex(MAINTENANCEORGAN)));
            bean.setUserId(c.getString(c.getColumnIndex(USERID)));
            bean.setUpdateState(c.getInt(c.getColumnIndex(UPDATESTATE)));
            list.add(bean);
        }
        if(c != null) {
            c.close();
            c = null;
        }
        closeDB();
        return list;

    }

    /**
     * 通过区段获取检查类型的任务数
     */
    public  int  getTaskCountBySection(String checkWayId,String belongUserId,String sectionId){
        int count = 0;
        List<String> list = DBBTunnelDao.getInstance(mContext).getAllTunnelIdBySectionId(belongUserId, sectionId);
        for(String tunenlId:list){
            //int num = getTaskCountByTunnel(checkWayId,belongUserId,tunenlId);
            int num = getTaskByTunenlId(belongUserId, checkWayId,tunenlId).size();
            count = count+num;
        }
        return count;
    }
    /**
     * 通过路线获取检查类型的任务数
     */
    public  int  getTaskCountByRoad(String checkWayId,String belongUserId,String roadId){
        int count = 0;
        List<String> sectionIdList = DBBSectionDao.getInstance(mContext).getAllNewIdByRoadId(roadId);
        for(String sectionId:sectionIdList){
            int num = getTaskCountBySection(checkWayId, belongUserId, sectionId);
            count = count+num;
        }
        return count;
    }
    /**
     * 清空表数据
     */
    public  void clearData(){
        SQLiteDatabase database = getDataBase();
        database.execSQL("delete from " + TABLE_ELMACHINETASK);
        closeDB();
    }
}
