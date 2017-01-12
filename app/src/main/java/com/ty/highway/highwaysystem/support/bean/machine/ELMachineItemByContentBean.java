package com.ty.highway.highwaysystem.support.bean.machine;

import java.io.Serializable;

/**
 * Created by fuweiwei on 2016/1/5.
 */
public class ELMachineItemByContentBean implements Serializable {
    private String NewId;
    private String MMachineRItemId;
    private String MMachineContentId;
    private String MContentName;

    public String getNewId() {
        return NewId;
    }

    public void setNewId(String newId) {
        NewId = newId;
    }

    public String getMMachineRItemId() {
        return MMachineRItemId;
    }

    public void setMMachineRItemId(String MMachineRItemId) {
        this.MMachineRItemId = MMachineRItemId;
    }

    public String getMMachineContentId() {
        return MMachineContentId;
    }

    public void setMMachineContentId(String MMachineContentId) {
        this.MMachineContentId = MMachineContentId;
    }

    public String getMContentName() {
        return MContentName;
    }

    public void setMContentName(String MContentName) {
        this.MContentName = MContentName;
    }
}
