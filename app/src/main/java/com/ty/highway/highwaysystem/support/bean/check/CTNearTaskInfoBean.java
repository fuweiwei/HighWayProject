package com.ty.highway.highwaysystem.support.bean.check;

import java.io.Serializable;

/**
 * Created by fuweiwei on 2015/9/18.
 */
public class CTNearTaskInfoBean implements Serializable{
    private  String NewId;
    private  String CheckType;
    private  String CheckWayId;
    private  String CheckRange;
    private  String Name;
    private  String RelationTaskId;
    private int UpdateState;

    public int getUpdateState() {
        return UpdateState;
    }

    public void setUpdateState(int updateState) {
        UpdateState = updateState;
    }

    public String getNewId() {
        return NewId;
    }

    public void setNewId(String newId) {
        NewId = newId;
    }

    public String getCheckType() {
        return CheckType;
    }

    public void setCheckType(String checkType) {
        CheckType = checkType;
    }

    public String getCheckWayId() {
        return CheckWayId;
    }

    public void setCheckWayId(String checkWayId) {
        CheckWayId = checkWayId;
    }

    public String getCheckRange() {
        return CheckRange;
    }

    public void setCheckRange(String checkRange) {
        CheckRange = checkRange;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRelationTaskId() {
        return RelationTaskId;
    }

    public void setRelationTaskId(String relationTaskId) {
        RelationTaskId = relationTaskId;
    }
}
