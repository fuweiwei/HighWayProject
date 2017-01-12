package com.ty.highway.highwaysystem.support.bean.basedata;

import java.io.Serializable;

/**
 * Created by fuweiwei on 2015/9/18.
 */
public class CTDiseaseTypeByDescriptBean implements Serializable {
    private String NewId;
    private String PropertyName;
    private String CreateDate;
    private String DiseaseDescribeId;
    private String MeasuringUnit;
    private String PropertyValue;

    public String getNewId() {
        return NewId;
    }

    public void setNewId(String newId) {
        NewId = newId;
    }

    public String getPropertyName() {
        return PropertyName;
    }

    public void setPropertyName(String propertyName) {
        PropertyName = propertyName;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getDiseaseDescribeId() {
        return DiseaseDescribeId;
    }

    public void setDiseaseDescribeId(String diseaseDescribeId) {
        DiseaseDescribeId = diseaseDescribeId;
    }

    public String getMeasuringUnit() {
        return MeasuringUnit;
    }

    public void setMeasuringUnit(String measuringUnit) {
        MeasuringUnit = measuringUnit;
    }

    public String getPropertyValue() {
        return PropertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        PropertyValue = propertyValue;
    }
}
