package com.ty.highway.highwaysystem.support.bean.machine;

import java.io.Serializable;

/**
 * Created by fuweiwei on 2016/1/8.
 */
public class ELMachineContentByDescriptBean implements Serializable {
    private String NewId;
    private String MMachineItemRContentId;
    private String MMachineDescriptId;
    private String MDescriptName;

    public String getNewId() {
        return NewId;
    }

    public void setNewId(String newId) {
        NewId = newId;
    }

    public String getMMachineItemRContentId() {
        return MMachineItemRContentId;
    }

    public void setMMachineItemRContentId(String MMachineItemRContentId) {
        this.MMachineItemRContentId = MMachineItemRContentId;
    }

    public String getMMachineDescriptId() {
        return MMachineDescriptId;
    }

    public void setMMachineDescriptId(String MMachineDescriptId) {
        this.MMachineDescriptId = MMachineDescriptId;
    }

    public String getMDescriptName() {
        return MDescriptName;
    }

    public void setMDescriptName(String MDescriptName) {
        this.MDescriptName = MDescriptName;
    }
}
