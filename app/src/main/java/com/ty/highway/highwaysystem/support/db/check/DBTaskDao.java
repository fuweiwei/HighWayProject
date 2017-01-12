package com.ty.highway.highwaysystem.support.db.check;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.ty.highway.highwaysystem.support.bean.check.TaskInfoBean;
import com.ty.highway.highwaysystem.support.db.basedata.DBBSectionDao;
import com.ty.highway.highwaysystem.support.db.basedata.DBBTunnelDao;
import com.ty.highway.highwaysystem.support.db.basic.DBDao;
import com.ty.highway.highwaysystem.support.db.basic.TableColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuweiwei on 2015/9/2.
 */
public class DBTaskDao extends DBDao implements TableColumns.CTTaskInfo {
    private static DBTaskDao INSTANCE;
    private Context mContext;
    protected DBTaskDao(Context context) {
        super(context);
        mContext = context;
    }
    public static DBTaskDao getInstance(Context context){
        if(INSTANCE==null){
            synchronized (DBTaskDao.class) {
                if(INSTANCE==null){
                    INSTANCE = new DBTaskDao(context.getApplicationContext());
                }
            }
        }
        return INSTANCE;

    }

    /**
     * 获取检查类型的任务数
     * @param checkWayId
     * @return
     */
    public  int  getTaskCount(String checkWayId,String belongUserId){
        int count = 0;
        SQLiteDatabase db = getDataBase();
        Cursor c = null;
        String sql = "select count(*) from " +TABLE_CTTASKINFO+ " where "+CHECKWAYID+"=? and "+BELONGUSERID +"=?";
        c = db.rawQuery(sql,new String[]{checkWayId,belongUserId});
        while(c!=null&&c.moveToNext()){
            count = c.getInt(0);
        }
        if(c!=null){
            c.close();
        }
        closeDB();
        return  count;
    }
    /**
     * 通过隧道获取检查类型的任务数
     */
    public  int  getTaskCountByTunnel(String checkWayId,String belongUserId,String tunnelId){
     /*   int count = 0;
        SQLiteDatabase db = getDataBase();
        Cursor c = null;
        String sql = "select count(*) from " +TABLE_CTTASKINFO+ " where "+CHECKWAYID+"=? and "+BELONGUSERID +"=? and "+TUNNELID+"=?" ;
        c = db.rawQuery(sql,new String[]{checkWayId,belongUserId,tunnelId});
        while(c!=null&&c.moveToNext()){
            count = c.getInt(0);
        }
        if(c!=null){
            c.close();
        }
        closeDB();*/
        int num = getTaskListByState(-1,checkWayId,belongUserId,tunnelId).size();
        return  num;
    }
    /**
     * 通过区段获取检查类型的任务数
     */
    public  int  getTaskCountBySection(String checkWayId,String belongUserId,String sectionId){
        int count = 0;
        List<String> list = DBBTunnelDao.getInstance(mContext).getAllTunnelIdBySectionId(belongUserId, sectionId);
        for(String tunenlId:list){
            //int num = getTaskCountByTunnel(checkWayId,belongUserId,tunenlId);
            int num = getTaskListByState(-1,checkWayId,belongUserId,tunenlId).size();
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
     * 获取任务列表根据隧道Id
     * @param state
     * @return
     */
    public List<TaskInfoBean> getTaskListByState(int state,String checkWayId,String belongUserId,String tunnelId){
        List<TaskInfoBean> list = new ArrayList<TaskInfoBean>();
        if(checkWayId==null||belongUserId==null||tunnelId==null){
            return  list;
        }
        String sql;
        Cursor c = null;
        SQLiteDatabase db = getDataBase();
        if(state<0){
            sql = "select *from "+TABLE_CTTASKINFO+" where "+CHECKWAYID+"=? and "+BELONGUSERID+"=? and "+TUNNELID+"=?";
            c = db.rawQuery(sql,new String[]{checkWayId,belongUserId,tunnelId});
        }else{
            sql = "select *from "+TABLE_CTTASKINFO+" where "+UPDATESTATE+"=? and "+CHECKWAYID+"=? and "+BELONGUSERID+"=? and "+TUNNELID+"=?";
            c = db.rawQuery(sql,new String[]{state+"",checkWayId,belongUserId,tunnelId});
        }
        while (c.moveToNext()){
            TaskInfoBean bean = new TaskInfoBean();
            bean.setAuditCount(c.getInt(c.getColumnIndex(AUDITCOUNT)));
            bean.setUpdateState(c.getInt(c.getColumnIndex(UPDATESTATE)));
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
            bean.setMaintenanceOrgan(c.getString(c.getColumnIndex(MAINTENANCEORGAN)));
            bean.setNearTaskId(c.getString(c.getColumnIndex(NEARTASKID)));
            bean.setIsNearTask(c.getInt(c.getColumnIndex(ISNEARTASK)));
            if(bean.getIsNearTask()==1&&!TextUtils.isEmpty(bean.getNearTaskId())){
            }else{
                list.add(bean);
            }
        }
        if(c != null) {
            c.close();
            c = null;
        }
        closeDB();
        return  list;
    }
    /**
     * 获取任务列表不包括已绑定的临时任务
     */
    public List<TaskInfoBean> getTaskListByState(int state,String checkWayId,String belongUserId){

        List<TaskInfoBean> list = new ArrayList<TaskInfoBean>();
        if(checkWayId==null||belongUserId==null){
            return  list;
        }
        String sql;
        Cursor c = null;
        SQLiteDatabase db = getDataBase();
        if(state<0){
            sql = "select *from "+TABLE_CTTASKINFO+" where "+CHECKWAYID+"=? and "+BELONGUSERID+"=?";
            c = db.rawQuery(sql,new String[]{checkWayId,belongUserId});
        }else{
            sql = "select *from "+TABLE_CTTASKINFO+" where "+UPDATESTATE+"=? and "+CHECKWAYID+"=? and "+BELONGUSERID+"=?";
            c = db.rawQuery(sql,new String[]{state+"",checkWayId,belongUserId});
        }
        while (c.moveToNext()){
            TaskInfoBean bean = new TaskInfoBean();
            bean.setAuditCount(c.getInt(c.getColumnIndex(AUDITCOUNT)));
            bean.setUpdateState(c.getInt(c.getColumnIndex(UPDATESTATE)));
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
            bean.setMaintenanceOrgan(c.getString(c.getColumnIndex(MAINTENANCEORGAN)));
            bean.setNearTaskId(c.getString(c.getColumnIndex(NEARTASKID)));
            bean.setIsNearTask(c.getInt(c.getColumnIndex(ISNEARTASK)));
            if(bean.getIsNearTask()==1&&!TextUtils.isEmpty(bean.getNearTaskId())){
            }else{
                list.add(bean);
            }
        }
        if(c != null) {
            c.close();
            c = null;
        }
        closeDB();
        return  list;
    }


    /**
     * 获取关联任务列表
     */
    public List<TaskInfoBean> getTasrelatekList(int state,String checkWayId,int isRelate,String belongUserId,String tunnelId){

        List<TaskInfoBean> list = new ArrayList<TaskInfoBean>();
        String sql;
        Cursor c = null;
        SQLiteDatabase db = getDataBase();
        if(TextUtils.isEmpty(tunnelId)){
            sql = "select *from "+TABLE_CTTASKINFO+" where "+UPDATESTATE+"=? and "+CHECKWAYID+"=? and "+ISNEARTASK+"=? and "+NEARTASKID+" is null and "+BELONGUSERID+"=?";
            c = db.rawQuery(sql,new String[]{state+"",checkWayId,isRelate+"",belongUserId});
        }else{
            sql = "select *from "+TABLE_CTTASKINFO+" where "+UPDATESTATE+"=? and "+CHECKWAYID+"=? and "+ISNEARTASK+"=? and "+NEARTASKID+" is null and "+BELONGUSERID+"=? and "+TUNNELID+"=?";
            c = db.rawQuery(sql,new String[]{state+"",checkWayId,isRelate+"",belongUserId,tunnelId});
        }
        while (c.moveToNext()){
            TaskInfoBean bean = new TaskInfoBean();
            bean.setAuditCount(c.getInt(c.getColumnIndex(AUDITCOUNT)));
            bean.setUpdateState(c.getInt(c.getColumnIndex(UPDATESTATE)));
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
            bean.setMaintenanceOrgan(c.getString(c.getColumnIndex(MAINTENANCEORGAN)));
            bean.setNearTaskId(c.getString(c.getColumnIndex(NEARTASKID)));
            bean.setIsNearTask(c.getInt(c.getColumnIndex(ISNEARTASK)));
            list.add(bean);
        }
        if(c != null) {
            c.close();
            c = null;
        }
        closeDB();
        return  list;
    }

    public List<TaskInfoBean> getTaskList(String belongUserId){
        List<TaskInfoBean> list = new ArrayList<TaskInfoBean>();
        String sql;
        Cursor c = null;
        SQLiteDatabase db = getDataBase();
        sql = "select *from "+TABLE_CTTASKINFO +" where "+BELONGUSERID+"=?";
        c = db.rawQuery(sql,new String[]{belongUserId});
        while (c.moveToNext()){
            TaskInfoBean bean = new TaskInfoBean();
            bean.setAuditCount(c.getInt(c.getColumnIndex(AUDITCOUNT)));
            bean.setUpdateState(c.getInt(c.getColumnIndex(UPDATESTATE)));
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
            bean.setMaintenanceOrgan(c.getString(c.getColumnIndex(MAINTENANCEORGAN)));
            bean.setNearTaskId(c.getString(c.getColumnIndex(NEARTASKID)));
            bean.setIsNearTask(c.getInt(c.getColumnIndex(ISNEARTASK)));
            list.add(bean);
        }
        if(c != null) {
            c.close();
            c = null;
        }
        closeDB();
        return  list;
    }
    /**
     * @param id
     * @return bean
     */
    public TaskInfoBean getTaskById(String id,String belongUserId){
        TaskInfoBean bean = new TaskInfoBean();
        String sql = "select * from "+TABLE_CTTASKINFO+" where "+NEWID+"=? and "+BELONGUSERID+"=?";
        SQLiteDatabase db = getDataBase();
        Cursor c = db.rawQuery(sql, new String[]{id, belongUserId});
        if (c!=null){
            c.moveToFirst();
            bean.setAuditCount(c.getInt(c.getColumnIndex(AUDITCOUNT)));
            bean.setUpdateState(c.getInt(c.getColumnIndex(UPDATESTATE)));
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
            bean.setMaintenanceOrgan(c.getString(c.getColumnIndex(MAINTENANCEORGAN)));
            bean.setNearTaskId(c.getString(c.getColumnIndex(NEARTASKID)));
            bean.setIsNearTask(c.getInt(c.getColumnIndex(ISNEARTASK)));
            c.close();
        }
        closeDB();
        return bean;
    }

    public void cancelRelatedTask(String id,String belongUserId){
        SQLiteDatabase db =getDataBase();
        String sql = "update "+TABLE_CTTASKINFO+" set "+NEARTASKID+"=null  where "
                +NEWID+"='"+id+"' and "+BELONGUSERID+"='"+belongUserId+"'";
        db.execSQL(sql);
    }
    /**
     * 更新关联任务
     * @param relatedId
     * @param id
     */
    public  void updateRelatedTask(String relatedId,String id,String belongUserId){
        SQLiteDatabase db =getDataBase();
        String sql = "update "+TABLE_CTTASKINFO+" set "+NEARTASKID+"='"+relatedId+"' where "
                +NEWID+"='"+id+"' and "+BELONGUSERID+"='"+belongUserId+"'";
        db.execSQL(sql);
    }

    /**
     * 更新任务信息
     * @param taskInfo
     */
    public void updateTask(TaskInfoBean taskInfo,String belongUserId){
        SQLiteDatabase db =getDataBase();
        String sql = "update "+TABLE_CTTASKINFO+" set "+CHECKDATE+"= '"+taskInfo.getCheckDate()+"' , " + CHECKWEATHER+"= '"+taskInfo.getCheckWeather()+"' , "+UPDATESTATE+"= '"+taskInfo.getUpdateState()+"' where "+NEWID+"= '"+taskInfo.getNewId()+"' and "+BELONGUSERID+"='"+belongUserId+"'";
        db.execSQL(sql);
        closeDB();
    }
    /**
     * 更新任务状态
     * @param taskId
     * @param state
     */
    public void setState(String taskId,int state,String belongUserId){
        if(taskId==null){
            return;
        }
        SQLiteDatabase database = getDataBase();
        String id = null;
        String sql = "update  "+TABLE_CTTASKINFO+" set "+UPDATESTATE+"='"+state+"' where "+NEWID+"='"+taskId+"' and "+BELONGUSERID+"='"+belongUserId+"'";
        database.execSQL(sql);
        closeDB();

        /*SQLiteDatabase database1 = getDataBase();
        String sql1 = "select *from "+TABLE_CTTASKINFO+" where "+NEWID +"=?";
        Cursor cursor = database1.rawQuery(sql1, new String[]{taskId});
        while (cursor.moveToNext()){
             id = cursor.getString(cursor.getColumnIndex(NEARTASKID));
        }
        if(cursor!=null){
            cursor.close();
        }
        closeDB();

        SQLiteDatabase database2 = getDataBase();
        if(!TextUtils.isEmpty(id)){
            String sql2 = "update  "+TABLE_CTTASKINFO+" set "+UPDATESTATE+"='"+state+"' where "+NEWID+"='"+id+"'";
            database2.execSQL(sql2);
        }
        closeDB();*/
    }
    /**
     * 判断是否有该任务
     * @param id
     * @return
     */
    public Boolean isHasTaskById(String id,String belongUserId){
        if(TextUtils.isEmpty(id)){
            return  false;
        }
        SQLiteDatabase database = getDataBase();
        String sql = "select count(*) from "+TABLE_CTTASKINFO+" where "+CHECKNO+"=? and "+BELONGUSERID+"=?";
        Cursor cursor = database.rawQuery(sql,new String[]{id,belongUserId});
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
     * 判断临时任务是否重名
     * @param name
     * @return
     */
    public boolean isHasTaskName(String name){
        if(TextUtils.isEmpty(name)){
            return  false;
        }
        SQLiteDatabase database = getDataBase();
        String sql = "select count(*) from "+TABLE_CTTASKINFO+" where "+TUNNELID+"=?";
        Cursor cursor = database.rawQuery(sql,new String[]{name});
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
     * 添加一个任务
     */
    public  void addTaskInfo(TaskInfoBean bean,String belongUserId){
        if(bean ==null){
            return ;
        }
        SQLiteDatabase database = getDataBase();
        ContentValues values = new ContentValues();
        values.put(AUDITCOUNT,bean.getAuditCount());
        values.put(UPDATESTATE,bean.getUpdateState());
        values.put(NEWID,bean.getNewId());
        values.put(CHECKNO,bean.getCheckNo());
        values.put(TUNNELID,bean.getTunnelId());
        values.put(CHECKEMP,bean.getCheckEmp());
        values.put(CHECKDATE,bean.getCheckDate());
        values.put(CHECKWEATHER,bean.getCheckWeather());
        values.put(CHECKWAYID,bean.getCheckWayId());
        values.put(REMARK,bean.getRemark());
        values.put(RECORDEMP,bean.getRecordEmp());
        values.put(CREATEDATE,bean.getCreateDate());
        values.put(MAINTENANCEORGAN,bean.getMaintenanceOrgan());
        values.put(NEARTASKID,bean.getNearTaskId());
        values.put(ISNEARTASK,bean.getIsNearTask());
        values.put(BELONGUSERID,belongUserId);
        database.insert(TABLE_CTTASKINFO, NEWID, values);
        closeDB();
    }

    /**
     * 删除一个任务
     * @param checkNo
     */
    public  void deleteTaskInfoByNo(String checkNo,String belongUserId){
        SQLiteDatabase database = getDataBase();
        database.delete(TABLE_CTTASKINFO, CHECKNO +"=? and "+BELONGUSERID+"=?", new String[] { checkNo,belongUserId });
        closeDB();
    }

    /**
     * 删除一个任务
     * @param id
     */
    public  void deleteTaskInfoById(String id,String belongUserId){
        SQLiteDatabase database = getDataBase();
        database.delete(TABLE_CTTASKINFO, NEWID +"=? and "+BELONGUSERID+"=?", new String[] { id,belongUserId });
        closeDB();
    }
    /**
     * 清空表数据
     */
    public  void clearData(){
        SQLiteDatabase database = getDataBase();
        database.execSQL("delete from " + TABLE_CTTASKINFO);
        closeDB();
    }
}
