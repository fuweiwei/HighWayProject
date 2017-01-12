package com.ty.highway.highwaysystem.support.db.check;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.ty.highway.highwaysystem.support.bean.check.CTHistroyDiseaseInfoBean;
import com.ty.highway.highwaysystem.support.db.basic.DBDao;
import com.ty.highway.highwaysystem.support.db.basic.TableColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuweiwei on 2015/11/3.
 * 历史病害信息操作表
 */
public class DBCTHistroyDiseaseInfoDao extends DBDao implements TableColumns.CTHistroyDisease{
    private final static String TAG = "DBCTHistroyDiseaseInfoDao";
    private static DBCTHistroyDiseaseInfoDao instance;
    private Context mContext;

    private DBCTHistroyDiseaseInfoDao(Context context) {
        super(context);
        mContext = context;
    }

    public static DBCTHistroyDiseaseInfoDao getInstance(Context context) {
        if (instance == null) {
            synchronized (DBCTHistroyDiseaseInfoDao.class) {
                instance = new DBCTHistroyDiseaseInfoDao(context.getApplicationContext());
            }
        }
        return instance;
    }

    /**
     * 是否有数据
     * @return
     */
    public boolean isHasData(){
        SQLiteDatabase database = getDataBase();
        String sql = "select count(*)  from "+TABLE_CTHISTROYDISEASE;
        Cursor cursor = database.rawQuery(sql, null);
        int count = 0;
        if (cursor!=null && cursor.moveToFirst()){
            count =cursor.getInt(0);
        }
        if(cursor != null) {
            cursor.close();
            cursor = null;
        }
        closeDB();
        return count!=0;
    }
    /**
     * 根据检查方式是否有数据
     * @return
     */
    public boolean isHasDataByCheckWay(String checkWayId){
        SQLiteDatabase database = getDataBase();
        String sql = "select count(*)  from "+TABLE_CTHISTROYDISEASE+" where "+CHECKWAYID+"=?";
        Cursor cursor = database.rawQuery(sql, new String[]{checkWayId});
        int count = 0;
        if (cursor!=null && cursor.moveToFirst()){
            count =cursor.getInt(0);
        }
        if(cursor != null) {
            cursor.close();
            cursor = null;
        }
        closeDB();
        return count!=0;
    }
    /**
     * 添加数据
     */
    public  void addData(List<CTHistroyDiseaseInfoBean> list){
        if(list ==null){
            return ;
        }
        SQLiteDatabase database = getDataBase();
        ContentValues values = new ContentValues();
        for(CTHistroyDiseaseInfoBean bean:list){
            values.put(NEWID,bean.getNewId());
            values.put(CHECKCONTENTRECORDID,bean.getCheckContentRecordId());
            values.put(DISEASEID,bean.getDiseaseId());
            values.put(SEATDESCRIBE,bean.getSeatDescribe());
            values.put(DISEASEDESCRIBE,bean.getDiseaseDescribe());
            values.put(REMARK,bean.getRemark());
            values.put(STARTMILEAGENUM,bean.getStartMileageNum());
            values.put(ENDMILEAGENUM,bean.getEndMileageNum());
            values.put(LENGTHS,bean.getLengths());
            values.put(WIDTHS,bean.getWidths());
            values.put(DEEPS,bean.getDeeps());
            values.put(AREAS,bean.getAreas());
            values.put(ANGLES,bean.getAngles());
            values.put(SPACEID,bean.getSpaceId());
            values.put(LEVELID,bean.getLevelId());
            values.put(CUTCOUNT,bean.getCutCount());
            values.put(THEDEFORMATION,bean.getTheDeformation());
            values.put(ERRORDEFORMATION,bean.getErrorDeformation());
            values.put(PERCENTAGE,bean.getPercentage());
            values.put(DIRECTION,bean.getDirection());
            values.put(DTYPE,bean.getDType());
            values.put(HEIGHT,bean.getHeight());
            values.put(THERATE,bean.getTheRate());
            values.put(CENTERPOINT,bean.getCenterPoint());
            values.put(EDGEDISTANCE,bean.getEdgeDistance());
            values.put(DISEASEFROM,bean.getDiseaseFrom());
            values.put(CREATEDATE,bean.getCreateDate());
            values.put(UPDATEDATE,bean.getUpdateDate());
            values.put(ISREPEAT,bean.getIsRepeat());
            values.put(DISEASENAME,bean.getDiseaseName());
            values.put(CONTENTID,bean.getContentId());
            values.put(CONTENTNAME,bean.getContentName());
            values.put(ITEMID,bean.getItemId());
            values.put(ISREPEATID,bean.getIsRepeatId());
            values.put(ITEMNAME,bean.getItemName());
            values.put(CHECKNO,bean.getCheckNo());
            values.put(SPACENAME,bean.getSpaceName());
            values.put(LEVELNAME,bean.getLevelName());
            values.put(ROADID,bean.getRoadId());
            values.put(ROADNAME,bean.getRoadName());
            values.put(SECTIONID,bean.getSectionId());
            values.put(SECTIONNAME,bean.getSectionName());
            values.put(TUNNELID,bean.getTunnelId());
            values.put(TUNNELNAME,bean.getTunnelName());
            values.put(RECORDNAME,bean.getRecordName());
            values.put(CHECKNAME,bean.getCheckName());
            values.put(CHECKDATE,bean.getCheckDate());
            values.put(CHECKWAYID,bean.getCheckWayId());
            values.put(STATE,bean.getState());
            values.put(DISEASENUM,bean.getDiseaseNum());
            database.insert(TABLE_CTHISTROYDISEASE, NEWID, values);
        }
        closeDB();
    }
    /**
     * 获取病害的数目
     * @param tunnelId
     * @param itemId
     * @return
     */
    public  int getDiseaseNum(String tunnelId,String itemId){
        if(TextUtils.isEmpty(tunnelId)||TextUtils.isEmpty(itemId)){
            return 0;
        }
        int num = 0;
        SQLiteDatabase database = getDataBase();
        String sql = "select count(*) from "+TABLE_CTHISTROYDISEASE+" where "+TUNNELID+"=? and "+ITEMID+"=?";
        Cursor cursor = database.rawQuery(sql,new String[]{tunnelId,itemId});
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
     * 获取历史病害信息
     * @param tunnelId 隧道id
     * @param itemId  检查项id
     * @return
     */
    public  List<CTHistroyDiseaseInfoBean> getInfosById(String tunnelId,String itemId){
        List<CTHistroyDiseaseInfoBean> list = new ArrayList<>();
        if(TextUtils.isEmpty(tunnelId)||TextUtils.isEmpty(itemId)){
            return list;
        }
        SQLiteDatabase database = getDataBase();
        String sql = "select *from "+TABLE_CTHISTROYDISEASE+" where "+TUNNELID+"=? and "+ITEMID+"=?";
        Cursor cursor = database.rawQuery(sql,new String[]{tunnelId,itemId});
        while (cursor.moveToNext()){
            CTHistroyDiseaseInfoBean bean = new CTHistroyDiseaseInfoBean();
            bean.setNewId(cursor.getString(cursor.getColumnIndex(NEWID)));
            bean.setCheckContentRecordId(cursor.getString(cursor.getColumnIndex(CHECKCONTENTRECORDID)));
            bean.setDiseaseId(cursor.getString(cursor.getColumnIndex(DISEASEID)));
            bean.setSeatDescribe(cursor.getString(cursor.getColumnIndex(SEATDESCRIBE)));
            bean.setDiseaseDescribe(cursor.getString(cursor.getColumnIndex(DISEASEDESCRIBE)));
            bean.setRemark(cursor.getString(cursor.getColumnIndex(REMARK)));
            bean.setStartMileageNum(cursor.getString(cursor.getColumnIndex(STARTMILEAGENUM)));
            bean.setEndMileageNum(cursor.getString(cursor.getColumnIndex(ENDMILEAGENUM)));
            bean.setLengths(cursor.getDouble(cursor.getColumnIndex(LENGTHS)));
            bean.setWidths(cursor.getDouble(cursor.getColumnIndex(WIDTHS)));
            bean.setDeeps(cursor.getDouble(cursor.getColumnIndex(DEEPS)));
            bean.setAreas(cursor.getDouble(cursor.getColumnIndex(AREAS)));
            bean.setAngles(cursor.getDouble(cursor.getColumnIndex(ANGLES)));
            bean.setSpaceId(cursor.getString(cursor.getColumnIndex(SPACEID)));
            bean.setLevelId(cursor.getString(cursor.getColumnIndex(LEVELID)));
            bean.setCutCount(cursor.getDouble(cursor.getColumnIndex(CUTCOUNT)));
            bean.setTheDeformation(cursor.getDouble(cursor.getColumnIndex(THEDEFORMATION)));
            bean.setErrorDeformation(cursor.getDouble(cursor.getColumnIndex(ERRORDEFORMATION)));
            bean.setPercentage(cursor.getDouble(cursor.getColumnIndex(PERCENTAGE)));
            bean.setDirection(cursor.getString(cursor.getColumnIndex(DIRECTION)));
            bean.setDType(cursor.getString(cursor.getColumnIndex(DTYPE)));
            bean.setHeight(cursor.getDouble(cursor.getColumnIndex(HEIGHT)));
            bean.setTheRate(cursor.getDouble(cursor.getColumnIndex(THERATE)));
            bean.setCenterPoint(cursor.getString(cursor.getColumnIndex(CENTERPOINT)));
            bean.setEdgeDistance(cursor.getString(cursor.getColumnIndex(EDGEDISTANCE)));
            bean.setDiseaseFrom(cursor.getInt(cursor.getColumnIndex(DISEASEFROM)));
            bean.setCreateDate(cursor.getString(cursor.getColumnIndex(CREATEDATE)));
            bean.setUpdateDate(cursor.getString(cursor.getColumnIndex(UPDATEDATE)));
            if(1==cursor.getInt(cursor.getColumnIndex(ISREPEAT))){
                bean.setIsRepeat(true);
            }else{
                bean.setIsRepeat(false);
            }
            bean.setDiseaseName(cursor.getString(cursor.getColumnIndex(DISEASENAME)));
            bean.setContentId(cursor.getString(cursor.getColumnIndex(CONTENTID)));
            bean.setContentName(cursor.getString(cursor.getColumnIndex(CONTENTNAME)));
            bean.setItemId(cursor.getString(cursor.getColumnIndex(ITEMID)));
            bean.setIsRepeatId(cursor.getString(cursor.getColumnIndex(ISREPEATID)));
            bean.setItemName(cursor.getString(cursor.getColumnIndex(ITEMNAME)));
            bean.setCheckNo(cursor.getString(cursor.getColumnIndex(CHECKNO)));
            bean.setSpaceName(cursor.getString(cursor.getColumnIndex(SPACENAME)));
            bean.setLevelName(cursor.getString(cursor.getColumnIndex(LEVELNAME)));
            bean.setRoadId(cursor.getString(cursor.getColumnIndex(ROADID)));
            bean.setRoadName(cursor.getString(cursor.getColumnIndex(ROADNAME)));
            bean.setSectionId(cursor.getString(cursor.getColumnIndex(SECTIONID)));
            bean.setSectionName(cursor.getString(cursor.getColumnIndex(SECTIONNAME)));
            bean.setTunnelId(cursor.getString(cursor.getColumnIndex(TUNNELID)));
            bean.setTunnelName(cursor.getString(cursor.getColumnIndex(TUNNELNAME)));
            bean.setRecordName(cursor.getString(cursor.getColumnIndex(RECORDNAME)));
            bean.setCheckName(cursor.getString(cursor.getColumnIndex(CHECKNAME)));
            bean.setCheckDate(cursor.getString(cursor.getColumnIndex(CHECKDATE)));
            bean.setCheckWayId(cursor.getString(cursor.getColumnIndex(CHECKWAYID)));
            bean.setState(cursor.getInt(cursor.getColumnIndex(STATE)));
            bean.setDiseaseNum(cursor.getInt(cursor.getColumnIndex(DISEASENUM)));
            list.add(bean);
        }
        if (cursor!=null){
            cursor.close();
        }
        closeDB();
        return list;
    }
    /**
     * 清空表数据
     */
    public  void clearData(){
        SQLiteDatabase database = getDataBase();
        database.execSQL("delete from " + TABLE_CTHISTROYDISEASE);
        closeDB();
    }
    /**
     * 清空表数据根据检查方式
     */
    public  void clearDataByCheckWay(String checkWayId ){
        SQLiteDatabase database = getDataBase();
        database.execSQL("delete from " + TABLE_CTHISTROYDISEASE+ " where "+CHECKWAYID+"='"+checkWayId+"'");
        closeDB();
    }
}
