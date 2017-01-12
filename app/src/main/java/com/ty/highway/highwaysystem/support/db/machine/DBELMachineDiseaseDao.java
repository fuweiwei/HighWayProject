package com.ty.highway.highwaysystem.support.db.machine;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ty.highway.highwaysystem.support.bean.machine.ELMachineDiseaseBean;
import com.ty.highway.highwaysystem.support.bean.machine.ELMachineDiseasePhotoBean;
import com.ty.highway.highwaysystem.support.bean.machine.MachineCheckRechodBean;
import com.ty.highway.highwaysystem.support.db.basic.DBDao;
import com.ty.highway.highwaysystem.support.db.basic.TableColumns;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by fuweiwei on 2016/1/5.
 */
public class DBELMachineDiseaseDao extends DBDao implements TableColumns.ELMachineDiseaseInfo {
    private final static String TAG = "DBELMachineDiseaseDao";
    private static DBELMachineDiseaseDao instance;
    private Context mContext;

    private DBELMachineDiseaseDao(Context context) {
        super(context);
        mContext = context;
    }

    public static DBELMachineDiseaseDao getInstance(Context context) {
        if (instance == null) {
            synchronized (DBELMachineDiseaseDao.class) {
                instance = new DBELMachineDiseaseDao(context.getApplicationContext());
            }
        }
        return instance;
    }
    /**
     * 清空表一条数据
     * @param taskId
     */
    public  void clearDataByTaskId(String taskId, String belongUserId){
        SQLiteDatabase database = getDataBase();
        database.execSQL("delete from " + TABLE_ELMACHINEDISEASEINFO +" where "+TASKID+"='"+taskId+"' and "+USERID+"='"+belongUserId+"'");
        closeDB();
    }
    /**
     * 清空表一条数据
     * @param newId
     */
    public  void clearDataByNewId(String newId,String belongUserId){
        SQLiteDatabase database = getDataBase();
        database.execSQL("delete from " + TABLE_ELMACHINEDISEASEINFO +" where "+NEWID+"='"+newId+"' and "+USERID+"='"+belongUserId+"'");
        closeDB();
    }

    /**
     * 添加数据
     * @param bean
     */
    public  void addData(ELMachineDiseaseBean bean){
        if(bean ==null){
            return ;
        }
        SQLiteDatabase database = getDataBase();
        ContentValues values = new ContentValues();
        values.put(NEWID,bean.getNewId());
        values.put(ISUSE,bean.getIsUse());
        values.put(REMARK,bean.getRemark());
        values.put(LEVELID,bean.getLevelId());
        values.put(MMACHINECONTENTID,bean.getMMachineContentId());
        values.put(MMACHINEITEMID,bean.getMMachineItemId());
        values.put(MMACHINEDEVICEID,bean.getMMachineDeviceId());
        values.put(LEVELSTRING,bean.getLevelString());
        values.put(CONTENTSTRING,bean.getContentString());
        values.put(ITEMSTRING,bean.getItemString());
        values.put(DEVICESTRING,bean.getDeviceString());
        values.put(TUNNELID,bean.getTunnelId());
        values.put(TUNNELNAME,bean.getTunnelName());
        values.put(USERID,bean.getUserId());
        values.put(MMACHINEDESCRIPTID,bean.getMMachineDescriptId());
        values.put(DESCRIPSTRING, bean.getDescripString());
        values.put(TASKID, bean.getTaskId());
        values.put(MMACHINEDEVICEDID, bean.getMMachineDeviceDId());
        values.put(CONSERVATIONMEASURESID, bean.getConservationMeasuresId());
       /* String sql = "insert into "+TABLE_ELMACHINEDISEASEINFO+" values('"+bean.getNewId()+"','"+bean.getIsUse()+"','"+bean.getRemark()+"','"+bean.getLevelId()+"','"+bean.getMMachineContentId()+"','"+bean.getMMachineItemId()+
                "','"+bean.getMMachineDeviceId()+"','"+bean.getLevelString()+"','"+bean.getContentString()+"','"+bean.getItemString()+"','"+bean.getDeviceString()+"','"+bean.getTunnelId()+"','"+bean.getTunnelName()+"','"+
                bean.getUserId()+"','"+bean.getMMachineDescriptId()+"','"+bean.getDescripString()+"','"+bean.getTaskId()+"')";
        database.execSQL(sql);*/
        database.insert(TABLE_ELMACHINEDISEASEINFO, NEWID, values);
        closeDB();
    }

    /**
     * 获取机电检修记录根据检修项目
     * @param taskId
     * @param deviceId
     * @param itemId
     * @param userId
     * @return
     */
    public List<ELMachineDiseaseBean> getDataByItemId(String taskId,String deviceId,String itemId,String userId){
        List <ELMachineDiseaseBean> list = new ArrayList<>();
        if(taskId==null||itemId==null||userId==null||deviceId==null){
            return  list;

        }
        SQLiteDatabase database = getDataBase();
        String sql = " select *from "+TABLE_ELMACHINEDISEASEINFO+" where "+TASKID+"=? and "+MMACHINEITEMID+"=? and "+USERID+"=? and "+MMACHINEDEVICEID+"=?";
        Cursor cursor = database.rawQuery(sql,new String[]{taskId,itemId,userId,deviceId});
        while (cursor!=null&&cursor.moveToNext()){
            ELMachineDiseaseBean bean = new ELMachineDiseaseBean();
            bean.setNewId(cursor.getString(cursor.getColumnIndex(NEWID)));
            bean.setIsUse(cursor.getString(cursor.getColumnIndex(ISUSE)));
            bean.setRemark(cursor.getString(cursor.getColumnIndex(REMARK)));
            bean.setLevelId(cursor.getString(cursor.getColumnIndex(LEVELID)));
            bean.setMMachineContentId(cursor.getString(cursor.getColumnIndex(MMACHINECONTENTID)));
            bean.setMMachineItemId(cursor.getString(cursor.getColumnIndex(MMACHINEITEMID)));
            bean.setMMachineDeviceId(cursor.getString(cursor.getColumnIndex(MMACHINEDEVICEID)));
            bean.setLevelString(cursor.getString(cursor.getColumnIndex(LEVELSTRING)));
            bean.setContentString(cursor.getString(cursor.getColumnIndex(CONTENTSTRING)));
            bean.setItemString(cursor.getString(cursor.getColumnIndex(ITEMSTRING)));
            bean.setDeviceString(cursor.getString(cursor.getColumnIndex(DEVICESTRING)));
            bean.setTunnelId(cursor.getString(cursor.getColumnIndex(TUNNELID)));
            bean.setTunnelName(cursor.getString(cursor.getColumnIndex(TUNNELNAME)));
            bean.setUserId(cursor.getString(cursor.getColumnIndex(USERID)));
            bean.setMMachineDescriptId(cursor.getString(cursor.getColumnIndex(MMACHINEDESCRIPTID)));
            bean.setDescripString(cursor.getString(cursor.getColumnIndex(DESCRIPSTRING)));
            bean.setTaskId(cursor.getString(cursor.getColumnIndex(TASKID)));
            bean.setMMachineDeviceDId(cursor.getString(cursor.getColumnIndex(MMACHINEDEVICEDID)));
            bean.setConservationMeasuresId(cursor.getString(cursor.getColumnIndex(CONSERVATIONMEASURESID)));

            list.add(bean);
        }
        if (cursor!=null){
            cursor.close();
        }
        closeDB();
        return  list;
    }
    /**
     * 获取病害的数目
     * @param taskId
     * @param deviceId
     * @return
     */
    public  int getDiseaseNum(String taskId,String deviceId,String belongUserId){
        int num = 0;
        SQLiteDatabase database = getDataBase();
        String sql = "select count(*) from "+TABLE_ELMACHINEDISEASEINFO+" where "+TASKID+"=? and "+MMACHINEDEVICEID+"=? and "+USERID+"=?";
        Cursor cursor = database.rawQuery(sql,new String[]{taskId,deviceId,belongUserId});
        while (cursor.moveToNext()){
            num=cursor.getInt(0);
        }
        if(cursor!=null){
            cursor.close();
        }
        closeDB();
        return num;
    }
    /**
     * 该检查内容是否已经存在
     * @param taskId
     * @param deviceId
     * @return
     */
    public  boolean isHasDiseaseByContent(String taskId,String deviceId,String itemId,String contentId,String useId){
        int num = 0;
        SQLiteDatabase database = getDataBase();
        String sql = "select count(*) from "+TABLE_ELMACHINEDISEASEINFO+" where "+TASKID+"=? and "+MMACHINEDEVICEID+"=? and "
                +MMACHINEITEMID+"=? and "+MMACHINECONTENTID+"=? and "+USERID+"=?";
        Cursor cursor = database.rawQuery(sql,new String[]{taskId,deviceId,itemId,contentId,useId});
        while (cursor.moveToNext()){
            num=cursor.getInt(0);
        }
        if(cursor!=null){
            cursor.close();
        }
        closeDB();
        return num!=0;
    }
    /**
     * 获取机电检修记录根据任务Id
     * @param taskId
     * @param userId
     * @return
     */
    public List<ELMachineDiseaseBean> getAllDataByTaskId(String taskId,String userId){
        List <ELMachineDiseaseBean> list = new ArrayList<>();
        if(taskId==null||userId==null){
            return  list;

        }
        SQLiteDatabase database = getDataBase();
        String sql = " select *from "+TABLE_ELMACHINEDISEASEINFO+" where "+TASKID+"=? and "+USERID+"=?";
        Cursor cursor = database.rawQuery(sql,new String[]{taskId,userId});
        while (cursor!=null&&cursor.moveToNext()){
            ELMachineDiseaseBean bean = new ELMachineDiseaseBean();
            bean.setNewId(cursor.getString(cursor.getColumnIndex(NEWID)));
            bean.setIsUse(cursor.getString(cursor.getColumnIndex(ISUSE)));
            bean.setRemark(cursor.getString(cursor.getColumnIndex(REMARK)));
            bean.setLevelId(cursor.getString(cursor.getColumnIndex(LEVELID)));
            bean.setMMachineContentId(cursor.getString(cursor.getColumnIndex(MMACHINECONTENTID)));
            bean.setMMachineItemId(cursor.getString(cursor.getColumnIndex(MMACHINEITEMID)));
            bean.setMMachineDeviceId(cursor.getString(cursor.getColumnIndex(MMACHINEDEVICEID)));
            bean.setLevelString(cursor.getString(cursor.getColumnIndex(LEVELSTRING)));
            bean.setContentString(cursor.getString(cursor.getColumnIndex(CONTENTSTRING)));
            bean.setItemString(cursor.getString(cursor.getColumnIndex(ITEMSTRING)));
            bean.setDeviceString(cursor.getString(cursor.getColumnIndex(DEVICESTRING)));
            bean.setTunnelId(cursor.getString(cursor.getColumnIndex(TUNNELID)));
            bean.setTunnelName(cursor.getString(cursor.getColumnIndex(TUNNELNAME)));
            bean.setUserId(cursor.getString(cursor.getColumnIndex(USERID)));
            bean.setMMachineDescriptId(cursor.getString(cursor.getColumnIndex(MMACHINEDESCRIPTID)));
            bean.setDescripString(cursor.getString(cursor.getColumnIndex(DESCRIPSTRING)));
            bean.setTaskId(cursor.getString(cursor.getColumnIndex(TASKID)));
            bean.setMMachineDeviceDId(cursor.getString(cursor.getColumnIndex(MMACHINEDEVICEDID)));
            bean.setConservationMeasuresId(cursor.getString(cursor.getColumnIndex(CONSERVATIONMEASURESID)));
            list.add(bean);
        }
        if (cursor!=null){
            cursor.close();
        }
        closeDB();
        return  list;
    }

    public List<MachineCheckRechodBean.MCheckMachineDevice> getMachineDeviceByTaskId(MachineCheckRechodBean checkRechodBean,String taskId,String tunnelId,String userId){
        List<MachineCheckRechodBean.MCheckMachineDevice> deviceList =  new ArrayList<>();
        HashSet<String> deviceListId = new HashSet<>();
        SQLiteDatabase database = getDataBase();
        Cursor cursor1=null,cursor2 = null,cursor3=null;
        String sql1 = "select *from "+TABLE_ELMACHINEDISEASEINFO+" where "+TASKID+"=? and "+USERID+"=?";
        cursor1 = database.rawQuery(sql1,new String[]{taskId,userId});
        while(cursor1!=null&&cursor1.moveToNext()){
            deviceListId.add(cursor1.getString(cursor1.getColumnIndex(MMACHINEDEVICEID)));
        }
        for(String deviceId: deviceListId) {
            HashSet<String> itemListId = new HashSet<>();
            List<MachineCheckRechodBean.MCheckMachineDevice.MCheckMachineDeviceItem> itemList = new ArrayList<>();
            MachineCheckRechodBean.MCheckMachineDevice mCheckMachineDeviceBean = checkRechodBean.new MCheckMachineDevice();
            mCheckMachineDeviceBean.setMMachineDeviceId(deviceId);
            String sql2 = "select *from " + TABLE_ELMACHINEDISEASEINFO + " where " + TASKID + "=? and " + USERID + "=? and " + MMACHINEDEVICEID + "=?";
            cursor2 = database.rawQuery(sql2, new String[]{taskId, userId, deviceId});
            while (cursor2 != null && cursor2.moveToNext()) {
                itemListId.add(cursor2.getString(cursor2.getColumnIndex(MMACHINEITEMID)));
            }
            for (String itemId : itemListId) {
                HashSet<String> contentListId = new HashSet<>();
                MachineCheckRechodBean.MCheckMachineDevice.MCheckMachineDeviceItem mCheckMachineDeviceItem = mCheckMachineDeviceBean.new MCheckMachineDeviceItem();
                List<MachineCheckRechodBean.MCheckMachineDevice.MCheckMachineDeviceItem.MCheckMachineContent> contentList = new ArrayList<>();
                mCheckMachineDeviceItem.setMMachineDeviceItemId(itemId);
                String sql3 = "select *from " + TABLE_ELMACHINEDISEASEINFO + " where " + TASKID + "=? and " +
                        USERID + "=? and " + MMACHINEDEVICEID + "=? and " + MMACHINEITEMID + "=?";
                cursor3 = database.rawQuery(sql3, new String[]{taskId, userId, deviceId, itemId});
                while (cursor3 != null && cursor3.moveToNext()) {
                    MachineCheckRechodBean.MCheckMachineDevice.MCheckMachineDeviceItem.MCheckMachineContent mCheckMachineContentBean = mCheckMachineDeviceItem.new MCheckMachineContent();
                    mCheckMachineContentBean.setMMachineDeviceId(cursor3.getString(cursor3.getColumnIndex(MMACHINEDEVICEID)));
                    mCheckMachineContentBean.setNewId(cursor3.getString(cursor3.getColumnIndex(NEWID)));
                    String isuse = cursor3.getString(cursor3.getColumnIndex(ISUSE));
                    if ("1".equals(isuse)) {
                        mCheckMachineContentBean.setIsUse(true);
                    } else {
                        mCheckMachineContentBean.setIsUse(false);
                    }
                    mCheckMachineContentBean.setDiseaseFrom(2);
                    mCheckMachineContentBean.setRemark(cursor3.getString(cursor3.getColumnIndex(REMARK)));
                    mCheckMachineContentBean.setLevelId(cursor3.getString(cursor3.getColumnIndex(LEVELID)));
                    mCheckMachineContentBean.setMMachineDescriptId(cursor3.getString(cursor3.getColumnIndex(MMACHINEDESCRIPTID)));
                    mCheckMachineContentBean.setMMachineDeviceRId(cursor3.getString(cursor3.getColumnIndex(MMACHINEDEVICEDID)));
                    mCheckMachineContentBean.setMMachineContentId(cursor3.getString(cursor3.getColumnIndex(MMACHINECONTENTID)));
                    mCheckMachineContentBean.setMMachineItemId(cursor3.getString(cursor3.getColumnIndex(MMACHINEITEMID)));
                    mCheckMachineContentBean.setConservationMeasuresId(cursor3.getString(cursor3.getColumnIndex(CONSERVATIONMEASURESID)));
                    String guid = cursor3.getString(cursor3.getColumnIndex(NEWID));
                    List<MachineCheckRechodBean.MCheckMachineDevice.MCheckMachineDeviceItem.MCheckMachineContent.DDocumentsEnity> documentsEnityList = new ArrayList<>();
                    List<ELMachineDiseasePhotoBean> ctDiseasePhotoBeanList = DBELMachineDiseasePhotoDao.getInstance(mContext).getAllByDisId(guid);
                    if (ctDiseasePhotoBeanList != null && ctDiseasePhotoBeanList.size() > 0) {
                        for (ELMachineDiseasePhotoBean bean : ctDiseasePhotoBeanList) {
                            MachineCheckRechodBean.MCheckMachineDevice.MCheckMachineDeviceItem.MCheckMachineContent.DDocumentsEnity dDocumentsEnity = mCheckMachineContentBean.new DDocumentsEnity();
                            dDocumentsEnity.setDisplayName(bean.getName());
                            dDocumentsEnity.setDocumentPath(bean.getWebDocument());
                            dDocumentsEnity.setExtensionName(bean.getLatterName());
                            documentsEnityList.add(dDocumentsEnity);
                        }
                        mCheckMachineContentBean.setDDocumentsEnity(documentsEnityList);

                    }
                    contentList.add(mCheckMachineContentBean);
                }
                mCheckMachineDeviceItem.setMCheckMachineContent(contentList);
                itemList.add(mCheckMachineDeviceItem);
            }
            mCheckMachineDeviceBean.setMCheckMachineDeviceItem(itemList);
            deviceList.add(mCheckMachineDeviceBean);
        }
        if(cursor1!=null){
            cursor1.close();
        }
        if(cursor2!=null){
            cursor2.close();
        }
        if(cursor3!=null){
            cursor3.close();
        }
        closeDB();
        return deviceList;
    }

    /**
     * 清空表数据
     */
    public  void clearData(){
        SQLiteDatabase database = getDataBase();
        database.execSQL("delete from " + TABLE_ELMACHINEDISEASEINFO);
        closeDB();
    }
}
