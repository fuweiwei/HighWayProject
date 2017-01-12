package com.ty.highway.highwaysystem.support.bean.machine;

import java.io.Serializable;

/**
 * Created by fuweiwei on 2016/1/5.
 */
public class ELMachineTypeByItemBean implements Serializable {
    private String NewId;
    private String MMachineRWayId;
    private String MMachineItemId;
    private String MMachineItemName;

    public String getNewId() {
        return NewId;
    }

    public void setNewId(String newId) {
        NewId = newId;
    }

    public String getMMachineRWayId() {
        return MMachineRWayId;
    }

    public void setMMachineRWayId(String MMachineRWayId) {
        this.MMachineRWayId = MMachineRWayId;
    }

    public String getMMachineItemId() {
        return MMachineItemId;
    }

    public void setMMachineItemId(String MMachineItemId) {
        this.MMachineItemId = MMachineItemId;
    }

    public String getMMachineItemName() {
        return MMachineItemName;
    }

    public void setMMachineItemName(String MMachineItemName) {
        this.MMachineItemName = MMachineItemName;
    }
}
