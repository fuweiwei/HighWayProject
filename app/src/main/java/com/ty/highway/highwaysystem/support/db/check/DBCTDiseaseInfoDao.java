package com.ty.highway.highwaysystem.support.db.check;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.ty.highway.highwaysystem.base.Constants;
import com.ty.highway.highwaysystem.support.bean.basedata.CTTunnelVsItemBean;
import com.ty.highway.highwaysystem.support.bean.check.CTDiseaseInfoBean;
import com.ty.highway.highwaysystem.support.bean.check.CTDiseasePhotoBean;
import com.ty.highway.highwaysystem.support.bean.check.CheckRecordItem;
import com.ty.highway.highwaysystem.support.db.basedata.DBCTTunnelVsItemDao;
import com.ty.highway.highwaysystem.support.db.basic.DBDao;
import com.ty.highway.highwaysystem.support.db.basic.TableColumns;
import com.ty.highway.highwaysystem.support.utils.PreferencesUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * 病害信息表操作
 * Created by ${dzm} on 2015/9/11 0011.
 */
public class DBCTDiseaseInfoDao extends DBDao implements TableColumns.CTDiseaseInfo{
    private final static String TAG = "DBCTDiseaseInfoDao";
    private static DBCTDiseaseInfoDao instance;
    private Context mContext;

    private DBCTDiseaseInfoDao(Context context) {
        super(context);
        mContext = context;
    }

    public static DBCTDiseaseInfoDao getInstance(Context context) {
        if (instance == null) {
            synchronized (DBCTDiseaseInfoDao.class) {
                instance = new DBCTDiseaseInfoDao(context.getApplicationContext());
            }
        }
        return instance;
    }
    /**
     * 获取病害
     * @param taskId
     * @param belongPro
     * @return
     */
    public List<CTDiseaseInfoBean> getInfoById(String taskId,String belongPro,String belongUserId){
        List<CTDiseaseInfoBean> list = new ArrayList<CTDiseaseInfoBean>();
        if(taskId==null||belongPro==null){
            return  list;
        }
        SQLiteDatabase database = getDataBase();
        String sql = "select * from "+TABLE_CTDISEASEINFO+" where "+TASKID+"=? and "+BELONGPRO+"=? and "+BELONGUSERID+"=?";
        Cursor cursor = database.rawQuery(sql,new String[]{taskId,belongPro,belongUserId});
        while (cursor.moveToNext()){
            CTDiseaseInfoBean bean = new CTDiseaseInfoBean();
            bean.setNewId(cursor.getString(cursor.getColumnIndex(NEWID)));
            bean.setCheckData(cursor.getString(cursor.getColumnIndex(CHECKDATA)));
            bean.setCheckPostion(cursor.getString(cursor.getColumnIndex(CHECKPOSTION)));
            bean.setMileage(cursor.getString(cursor.getColumnIndex(MILEAGE)));
            bean.setJudgeLevel(cursor.getString(cursor.getColumnIndex(JUDGELEVEL)));
            bean.setLevelContent(cursor.getString(cursor.getColumnIndex(LEVELCONTENT)));
            bean.setCheckType(cursor.getString(cursor.getColumnIndex(CHECKTYPE)));
            bean.setTaskId(cursor.getString(cursor.getColumnIndex(TASKID)));
            bean.setRemarks(cursor.getString(cursor.getColumnIndex(REMARKS)));
            bean.setBelongPro(cursor.getString(cursor.getColumnIndex(BELONGPRO)));
            bean.setUploadState(cursor.getInt(cursor.getColumnIndex(UPLOADSTATE)));
            bean.setTunnelId(cursor.getString(cursor.getColumnIndex(TUNNELID)));
            bean.setItemId(cursor.getString(cursor.getColumnIndex(ITEMID)));
            bean.setDiseaseTypeId(cursor.getString(cursor.getColumnIndex(DISEASETYPEID)));
            bean.setGuid(cursor.getString(cursor.getColumnIndex(GUID)));
            bean.setIsNearDisease(cursor.getInt(cursor.getColumnIndex(ISNEARDISEASE)));
            bean.setLengths(cursor.getString(cursor.getColumnIndex(LENGTHS)));
            bean.setWidths(cursor.getString(cursor.getColumnIndex(WIDTHS)));
            bean.setDeeps(cursor.getString(cursor.getColumnIndex(DEEPS)));
            bean.setAreas(cursor.getString(cursor.getColumnIndex(AREAS)));
            bean.setAngles(cursor.getString(cursor.getColumnIndex(ANGLES)));
            bean.setCutCount(cursor.getString(cursor.getColumnIndex(CUTCOUNT)));
            bean.setTheDeformation(cursor.getString(cursor.getColumnIndex(THEDEFORMATION)));
            bean.setErrorDeformation(cursor.getString(cursor.getColumnIndex(ERRORDEFORMATION)));
            bean.setPercentage(cursor.getString(cursor.getColumnIndex(PERCENTAGE)));
            bean.setDirection(cursor.getString(cursor.getColumnIndex(DIRECTION)));
            bean.setDType(cursor.getString(cursor.getColumnIndex(DTYPE)));
            bean.setHeight(cursor.getString(cursor.getColumnIndex(HEIGHT)));
            bean.setTheRate(cursor.getString(cursor.getColumnIndex(THERATE)));
            bean.setDiseaseRemark(cursor.getString(cursor.getColumnIndex(DISEASEREMARK)));
            bean.setCheckItemId(cursor.getString(cursor.getColumnIndex(CHECKITEMID)));
            if(cursor.getInt(cursor.getColumnIndex(ISREPEAT))==1){
                bean.setIsRepeat(true);
            }else{
                bean.setIsRepeat(false);
            }
            bean.setIsRepeatId(cursor.getString(cursor.getColumnIndex(ISREPEATID)));
            bean.setDiseaseDescribe(cursor.getString(cursor.getColumnIndex(DISEASEDESCRIBE)));
            bean.setConservationMeasuresId(cursor.getString(cursor.getColumnIndex(CONSERVATIONMEASURESID)));
            list.add(bean);
        }
        if(cursor!=null){
            cursor.close();
        }
        closeDB();
        return list;
    }
    /**
     * 获取所有病害
     * @return
     */
    public List<CTDiseaseInfoBean> getAllInfo(String taskId,String belongUserId){
        List<CTDiseaseInfoBean> list = new ArrayList<CTDiseaseInfoBean>();
        SQLiteDatabase database = getDataBase();
        String sql = "select * from "+TABLE_CTDISEASEINFO+" where "+TASKID+"=? and "+BELONGUSERID+"=?";
        Cursor cursor = database.rawQuery(sql,new String[]{taskId,belongUserId});
        while (cursor.moveToNext()){
            CTDiseaseInfoBean bean = new CTDiseaseInfoBean();
            bean.setNewId(cursor.getString(cursor.getColumnIndex(NEWID)));
            bean.setCheckData(cursor.getString(cursor.getColumnIndex(CHECKDATA)));
            bean.setCheckPostion(cursor.getString(cursor.getColumnIndex(CHECKPOSTION)));
            bean.setMileage(cursor.getString(cursor.getColumnIndex(MILEAGE)));
            bean.setJudgeLevel(cursor.getString(cursor.getColumnIndex(JUDGELEVEL)));
            bean.setLevelContent(cursor.getString(cursor.getColumnIndex(LEVELCONTENT)));
            bean.setCheckType(cursor.getString(cursor.getColumnIndex(CHECKTYPE)));
            bean.setTaskId(cursor.getString(cursor.getColumnIndex(TASKID)));
            bean.setRemarks(cursor.getString(cursor.getColumnIndex(REMARKS)));
            bean.setBelongPro(cursor.getString(cursor.getColumnIndex(BELONGPRO)));
            bean.setUploadState(cursor.getInt(cursor.getColumnIndex(UPLOADSTATE)));
            bean.setTunnelId(cursor.getString(cursor.getColumnIndex(TUNNELID)));
            bean.setItemId(cursor.getString(cursor.getColumnIndex(ITEMID)));
            bean.setDiseaseTypeId(cursor.getString(cursor.getColumnIndex(DISEASETYPEID)));
            bean.setGuid(cursor.getString(cursor.getColumnIndex(GUID)));
            bean.setIsNearDisease(cursor.getInt(cursor.getColumnIndex(ISNEARDISEASE)));
            bean.setLengths(cursor.getString(cursor.getColumnIndex(LENGTHS)));
            bean.setWidths(cursor.getString(cursor.getColumnIndex(WIDTHS)));
            bean.setDeeps(cursor.getString(cursor.getColumnIndex(DEEPS)));
            bean.setAreas(cursor.getString(cursor.getColumnIndex(AREAS)));
            bean.setAngles(cursor.getString(cursor.getColumnIndex(ANGLES)));
            bean.setCutCount(cursor.getString(cursor.getColumnIndex(CUTCOUNT)));
            bean.setTheDeformation(cursor.getString(cursor.getColumnIndex(THEDEFORMATION)));
            bean.setErrorDeformation(cursor.getString(cursor.getColumnIndex(ERRORDEFORMATION)));
            bean.setPercentage(cursor.getString(cursor.getColumnIndex(PERCENTAGE)));
            bean.setDirection(cursor.getString(cursor.getColumnIndex(DIRECTION)));
            bean.setDType(cursor.getString(cursor.getColumnIndex(DTYPE)));
            bean.setHeight(cursor.getString(cursor.getColumnIndex(HEIGHT)));
            bean.setTheRate(cursor.getString(cursor.getColumnIndex(THERATE)));
            bean.setDiseaseRemark(cursor.getString(cursor.getColumnIndex(DISEASEREMARK)));
            bean.setCheckItemId(cursor.getString(cursor.getColumnIndex(CHECKITEMID)));
            if(cursor.getInt(cursor.getColumnIndex(ISREPEAT))==1){
                bean.setIsRepeat(true);
            }else{
                bean.setIsRepeat(false);
            }
            bean.setIsRepeatId(cursor.getString(cursor.getColumnIndex(ISREPEATID)));
            bean.setDiseaseDescribe(cursor.getString(cursor.getColumnIndex(DISEASEDESCRIBE)));
            bean.setConservationMeasuresId(cursor.getString(cursor.getColumnIndex(CONSERVATIONMEASURESID)));
            list.add(bean);
        }
        if(cursor!=null){
            cursor.close();
        }
        closeDB();
        return list;
    }

    /**
     * 更新病害的上传状态
     * @param taskId
     */
    public void updateDiseaseState(String taskId,String belongUserId){
        SQLiteDatabase database = getDataBase();
        String sql = "update "+TABLE_CTDISEASEINFO+" set "+UPLOADSTATE+"='1' where "+TASKID+" ='"+taskId+"' and "+BELONGUSERID+"='"+belongUserId+"'";
        database.execSQL(sql);
        closeDB();
    }
    /**
     * 获取病害的数目
     * @param taskId
     * @param checkItem
     * @return
     */
    public  int getDiseaseNum(String taskId,String checkItem,String belongUserId){
        int num = 0;
        SQLiteDatabase database = getDataBase();
        String sql = "select count(*) from "+TABLE_CTDISEASEINFO+" where "+TASKID+"=? and "+BELONGPRO+"=? and "+BELONGUSERID+"=?";
        Cursor cursor = database.rawQuery(sql,new String[]{taskId,checkItem,belongUserId});
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
     * 添加一条数据
     */
    public  void addData(CTDiseaseInfoBean bean,String belongUserId){
        if(bean ==null){
            return ;
        }
        SQLiteDatabase database = getDataBase();
        ContentValues values = new ContentValues();
        values.put(NEWID,bean.getNewId());
        values.put(CHECKDATA, bean.getCheckData());
        values.put(CHECKPOSTION, bean.getCheckPostion());
        values.put(MILEAGE, bean.getMileage());
        values.put(JUDGELEVEL, bean.getJudgeLevel());
        values.put(LEVELCONTENT, bean.getLevelContent());
        values.put(CHECKTYPE, bean.getCheckType());
        values.put(TASKID, bean.getTaskId());
        values.put(REMARKS, bean.getRemarks());
        values.put(BELONGPRO, bean.getBelongPro());
        values.put(UPLOADSTATE, bean.getUploadState());
        values.put(TUNNELID, bean.getTunnelId());
        values.put(ITEMID, bean.getItemId());
        values.put(DISEASETYPEID, bean.getDiseaseTypeId());
        values.put(GUID, bean.getGuid());
        values.put(ISNEARDISEASE, bean.getIsNearDisease());
        values.put(LENGTHS, bean.getLengths());
        values.put(WIDTHS, bean.getWidths());
        values.put(DEEPS, bean.getDeeps());
        values.put(AREAS, bean.getAreas());
        values.put(ANGLES, bean.getAngles());
        values.put(CUTCOUNT, bean.getCutCount());
        values.put(THEDEFORMATION, bean.getTheDeformation());
        values.put(ERRORDEFORMATION, bean.getErrorDeformation());
        values.put(PERCENTAGE, bean.getPercentage());
        values.put(DIRECTION, bean.getDirection());
        values.put(DTYPE, bean.getDType());
        values.put(HEIGHT, bean.getHeight());
        values.put(THERATE, bean.getTheRate());
        values.put(DISEASEREMARK, bean.getDiseaseRemark());
        values.put(CHECKITEMID, bean.getCheckItemId());
        values.put(ISREPEAT, bean.isRepeat());
        values.put(ISREPEATID, bean.getIsRepeatId());
        values.put(DISEASEDESCRIBE, bean.getDiseaseDescribe());
        values.put(CONSERVATIONMEASURESID, bean.getConservationMeasuresId());
        values.put(BELONGUSERID, belongUserId);
        database.insert(TABLE_CTDISEASEINFO, NEWID, values);
        closeDB();
    }

    /**
     * 临时任务关联任务是过滤没有的检查项
     * @param taskId
     * @param set
     * @return
     */
    public HashSet<String> filterItem(String taskId,HashSet<String> set){
        String tunnelId = DBTaskDao.getInstance(mContext).getTaskById(taskId, PreferencesUtils.getString(mContext, Constants.SP_USER_ID)).getTunnelId();
        String checkWayId = DBTaskDao.getInstance(mContext).getTaskById(taskId, PreferencesUtils.getString(mContext, Constants.SP_USER_ID)).getCheckWayId();
        List<CTTunnelVsItemBean> list = DBCTTunnelVsItemDao.getInstance(mContext).getAllInfoById(tunnelId,checkWayId);
        if(list!=null&&list.size()>0){
            List<String> listString = new ArrayList<>();
            for(CTTunnelVsItemBean info :list){
                listString.add(info.getItemId());
            }
            Iterator<String> iterator = set.iterator();
            while (iterator.hasNext()) {
                String s = iterator.next();
                if (!listString.contains(s)){
                    set.remove(iterator.next());
                }
            }
        }else{

        }
        return set;
    }
    public  List<CheckRecordItem> getCheckItem(String taskId,String belongUserId){
        List<CheckRecordItem> checkRecordItemList = new ArrayList<>();
        HashSet<String> itemList = new HashSet<>();

        SQLiteDatabase database = getDataBase();
        Cursor cursor1=null,cursor2 = null,cursor3=null;
        String sql1 = "select * from "+TABLE_CTDISEASEINFO+" where "+TASKID+"=? and "+BELONGUSERID+"=?";
        cursor1 = database.rawQuery(sql1,new String[]{taskId,belongUserId});
        while (cursor1.moveToNext()){
            itemList.add(cursor1.getString(cursor1.getColumnIndex(ITEMID)));
        }
        itemList = filterItem(taskId,itemList);
        for(String itemId:itemList){
            HashSet<String> typeList = new HashSet<>();
            CheckRecordItem checkRecordItem  = new CheckRecordItem();
            List< CheckRecordItem.CheckRecordType> checkRecordTypeList = new ArrayList<>();
            checkRecordItem.setItemId(itemId);
            String sql2 = "select * from "+TABLE_CTDISEASEINFO +" where "+TASKID+"=? and "+ITEMID+"=? and "+BELONGUSERID+"=?";
            cursor2 = database.rawQuery(sql2,new String[]{taskId,itemId,belongUserId});
            while (cursor2.moveToNext()){
                typeList.add(cursor2.getString(cursor2.getColumnIndex(DISEASETYPEID)));
            }
            for(String typeId:typeList){
                CheckRecordItem.CheckRecordType checkRecordType = checkRecordItem.new CheckRecordType();
                checkRecordType.setDiseaseTypeId(typeId);
                String sql3 = "select * from "+TABLE_CTDISEASEINFO +" where "+TASKID+"=? and "+ITEMID+"=? and "+DISEASETYPEID+"=? and "+BELONGUSERID+"=?";
                cursor3 = database.rawQuery(sql3,new String[]{taskId,itemId,typeId,belongUserId});
                List<CheckRecordItem.CheckRecordType.CheckRecordData> checkRecordDataList = new ArrayList<>();
                while (cursor3.moveToNext()){
                    CheckRecordItem.CheckRecordType.CheckRecordData checkRecordData = checkRecordType.new CheckRecordData();
                    checkRecordData.setDiseaseId(cursor3.getString(cursor3.getColumnIndex(NEWID)));
                    checkRecordData.setRemark(cursor3.getString(cursor3.getColumnIndex(REMARKS)));
                    String mileage = cursor3.getString(cursor3.getColumnIndex(MILEAGE));
                    if(!TextUtils.isEmpty(mileage)){
                        double m  = Double.parseDouble(mileage);
                        int start =(int) m/1000;
                        DecimalFormat fnum = new DecimalFormat("##0.00");
                        String sizeTemp = fnum.format(m%1000);
                        checkRecordData.setStartMileageNum(start+"");
                        checkRecordData.setEndMileageNum(sizeTemp);
                    }
                    checkRecordData.setDiseaseFrom(Constants.DISEASEFROM_HAND);
                    checkRecordData.setLevelId(cursor3.getString(cursor3.getColumnIndex(JUDGELEVEL)));
                    checkRecordData.setSpaceId(cursor3.getString(cursor3.getColumnIndex(CHECKPOSTION)));
                    checkRecordData.setConservationMeasuresId(cursor3.getString(cursor3.getColumnIndex(CONSERVATIONMEASURESID)));
                    if(cursor3.getInt(cursor3.getColumnIndex(ISREPEAT))==1){
                        checkRecordData.setIsRepeat(true);
                        checkRecordData.setIsRepeatId(cursor3.getString(cursor3.getColumnIndex(ISREPEATID)));
                    }else{
                        checkRecordData.setIsRepeat(false);
                    }
                    if(!TextUtils.isEmpty(cursor3.getString(cursor3.getColumnIndex(LENGTHS)))){
                        checkRecordData.setLengths(cursor3.getString(cursor3.getColumnIndex(LENGTHS)));
                    }
                    if(!TextUtils.isEmpty(cursor3.getString(cursor3.getColumnIndex(WIDTHS)))){
                        checkRecordData.setWidths(cursor3.getString(cursor3.getColumnIndex(WIDTHS)));
                    }
                    if(!TextUtils.isEmpty(cursor3.getString(cursor3.getColumnIndex(DEEPS)))){
                        checkRecordData.setDeeps(cursor3.getString(cursor3.getColumnIndex(DEEPS)));
                    }
                    if(!TextUtils.isEmpty(cursor3.getString(cursor3.getColumnIndex(AREAS)))){
                        checkRecordData.setAreas(cursor3.getString(cursor3.getColumnIndex(AREAS)));
                    }
                    if(!TextUtils.isEmpty(cursor3.getString(cursor3.getColumnIndex(ANGLES)))){
                        checkRecordData.setAngles(cursor3.getString(cursor3.getColumnIndex(ANGLES)));
                    }
                    if(!TextUtils.isEmpty(cursor3.getString(cursor3.getColumnIndex(CUTCOUNT)))){
                        checkRecordData.setCutCount(cursor3.getString(cursor3.getColumnIndex(CUTCOUNT)));
                    }
                    if(!TextUtils.isEmpty(cursor3.getString(cursor3.getColumnIndex(THEDEFORMATION)))){
                        checkRecordData.setTheDeformation(cursor3.getString(cursor3.getColumnIndex(THEDEFORMATION)));
                    }
                    if(!TextUtils.isEmpty(cursor3.getString(cursor3.getColumnIndex(ERRORDEFORMATION)))){
                        checkRecordData.setErrorDeformation(cursor3.getString(cursor3.getColumnIndex(ERRORDEFORMATION)));
                    }
                    if(!TextUtils.isEmpty(cursor3.getString(cursor3.getColumnIndex(PERCENTAGE)))){
                        checkRecordData.setPercentage(cursor3.getString(cursor3.getColumnIndex(PERCENTAGE)));
                    }
                    if(!TextUtils.isEmpty(cursor3.getString(cursor3.getColumnIndex(DIRECTION)))){
                        checkRecordData.setDirection(cursor3.getString(cursor3.getColumnIndex(DIRECTION)));
                    }
                    if(!TextUtils.isEmpty(cursor3.getString(cursor3.getColumnIndex(DTYPE)))){
                        checkRecordData.setDType(cursor3.getString(cursor3.getColumnIndex(DTYPE)));
                    }
                    if(!TextUtils.isEmpty(cursor3.getString(cursor3.getColumnIndex(HEIGHT)))){
                        checkRecordData.setHeight(cursor3.getString(cursor3.getColumnIndex(HEIGHT)));
                    }
                    if(!TextUtils.isEmpty(cursor3.getString(cursor3.getColumnIndex(THERATE)))){
                        checkRecordData.setTheRate(cursor3.getString(cursor3.getColumnIndex(THERATE)));
                    }
                    if(!TextUtils.isEmpty(cursor3.getString(cursor3.getColumnIndex(DISEASEREMARK)))){
                        checkRecordData.setDiseaseRemark(cursor3.getString(cursor3.getColumnIndex(DISEASEREMARK)));
                    }
                    String guid = cursor3.getString(cursor3.getColumnIndex(GUID));
                    List<CheckRecordItem.CheckRecordType.CheckRecordData.DDocumentsEnityInfo>dDocumentsEnityInfoList = new ArrayList<>();
                    List<CTDiseasePhotoBean> ctDiseasePhotoBeanList = DBCTDiseasePhotoDao.getInstance(mContext).getAllByDisId(guid);
                    if(ctDiseasePhotoBeanList!=null&&ctDiseasePhotoBeanList.size()>0){
                        for(CTDiseasePhotoBean bean :ctDiseasePhotoBeanList){
                            CheckRecordItem.CheckRecordType.CheckRecordData.DDocumentsEnityInfo dDocumentsEnityInfo = checkRecordData.new DDocumentsEnityInfo();
                            dDocumentsEnityInfo.setDisplayName(bean.getName());
                            dDocumentsEnityInfo.setDocumentPath(bean.getWebDocument());
                            dDocumentsEnityInfo.setExtensionName(bean.getLatterName());
                            dDocumentsEnityInfoList.add(dDocumentsEnityInfo);
                        }
                        checkRecordData.setDDocumentsEnity(dDocumentsEnityInfoList);
                    }
                    checkRecordDataList.add(checkRecordData);
                }
                checkRecordType.setCCheckRecordInterfaceData(checkRecordDataList);
                checkRecordTypeList.add(checkRecordType);
            }
            checkRecordItem.setCCheckRecordInterfaceType(checkRecordTypeList);
            checkRecordItemList.add(checkRecordItem);
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
        return  checkRecordItemList;
    }

    /**
     * 绑定任务更新数据
     * @param relateId 临时任务id
     * @param taskId  要绑定任务的id
     * @param tunnelId 要绑定任务的隧道id
     */
    public void relatedTask(String relateId,String taskId,String tunnelId,String belongUserId){
        SQLiteDatabase database = getDataBase();
        String sql = "update "+ TABLE_CTDISEASEINFO +" set "+ TASKID +"='"+taskId+"' , "+TUNNELID+" ='"+tunnelId
                +"' where "+TASKID+" ='"+relateId+"' and "+BELONGUSERID+"='"+belongUserId+"'" ;
        database.execSQL(sql);
        closeDB();
    }

    /**
     * 解除关联任务
     * @param relateId
     * @param taskId
     */
    public void cancelRelatedTask(String taskId,String relateId,String belongUserId){
        SQLiteDatabase database = getDataBase();
        String sql = "update "+ TABLE_CTDISEASEINFO +" set "+ TASKID +"='"+relateId+"' , "+TUNNELID+" =null " +
                " where "+TASKID+" ='"+taskId+"' and "+ISNEARDISEASE +"='1' and "+BELONGUSERID+"='"+belongUserId+"'";
        database.execSQL(sql);
        closeDB();
    }
    /**
     * 清空表数据
     */
    public  void clearData(){
        SQLiteDatabase database = getDataBase();
        database.execSQL("delete from " + TABLE_CTDISEASEINFO);
        closeDB();
    }
    /**
     * 清空表一条数据
     * @param guid
     */
    public  void clearDataById(String guid,String belongUserId){
        SQLiteDatabase database = getDataBase();
        database.execSQL("delete from " + TABLE_CTDISEASEINFO +" where "+GUID+"='"+guid+"' and "+BELONGUSERID+"='"+belongUserId+"'");
        closeDB();
    }
    /**
     * 清空某任务数据
     * @param taskId
     */
    public  void clearDataByTaskId(String taskId,String belongUserId){
        SQLiteDatabase database = getDataBase();
        database.execSQL("delete from " + TABLE_CTDISEASEINFO +" where "+TASKID+"='"+taskId+"' and "+BELONGUSERID+"='"+belongUserId+"'");
        closeDB();
    }
}
