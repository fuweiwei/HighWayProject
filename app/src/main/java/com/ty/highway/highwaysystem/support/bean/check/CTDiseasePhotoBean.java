package com.ty.highway.highwaysystem.support.bean.check;

import java.io.Serializable;

/**
 * Created by fuweiwei on 2015/9/22.
 */
public class CTDiseasePhotoBean implements Serializable {
    private String Guid;
    private String DiseaseGuid;
    private String Position;
    private String Name;
    private String TaskId;
    private int UpdateState;
    private String LatterName;
    private String WebDocument;

    public String getWebDocument() {
        return WebDocument;
    }

    public void setWebDocument(String webDocument) {
        WebDocument = webDocument;
    }

    public String getLatterName() {
        return LatterName;
    }

    public void setLatterName(String latterName) {
        LatterName = latterName;
    }

    public String getGuid() {
        return Guid;
    }

    public void setGuid(String guid) {
        Guid = guid;
    }

    public String getDiseaseGuid() {
        return DiseaseGuid;
    }

    public void setDiseaseGuid(String diseaseGuid) {
        DiseaseGuid = diseaseGuid;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTaskId() {
        return TaskId;
    }

    public void setTaskId(String taskId) {
        TaskId = taskId;
    }

    public int getUpdateState() {
        return UpdateState;
    }

    public void setUpdateState(int updateState) {
        UpdateState = updateState;
    }
}
