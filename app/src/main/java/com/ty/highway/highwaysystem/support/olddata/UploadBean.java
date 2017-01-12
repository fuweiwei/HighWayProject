package com.ty.highway.highwaysystem.support.olddata;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fuweiwei on 2015/10/16.
 */
public class UploadBean implements Serializable {
    private String TunnelName;                           //所属隧道
    private String StructName;                                       //结构名称
    private String CheckContent;                               //检查内容
    private String DefectLocation;                               //缺损位置
    private String MileagePile;                              //里程桩号
    private String ExceptionDescription;        //异常描述
    private String DescDetails;                        //详细描述
    private String JudgeLevel;                           //判定
    private String InputPerson;                        //录入人
    private String CheckPerson;                        //检查人
    private String Weather;                            //天气
    private String CheckDate;                  //检查日期
    private List<Image> Images;

    public String getTunnelName() {
        return TunnelName;
    }

    public void setTunnelName(String tunnelName) {
        TunnelName = tunnelName;
    }

    public String getStructName() {
        return StructName;
    }

    public void setStructName(String structName) {
        StructName = structName;
    }

    public String getCheckContent() {
        return CheckContent;
    }

    public void setCheckContent(String checkContent) {
        CheckContent = checkContent;
    }

    public String getDefectLocation() {
        return DefectLocation;
    }

    public void setDefectLocation(String defectLocation) {
        DefectLocation = defectLocation;
    }

    public String getMileagePile() {
        return MileagePile;
    }

    public void setMileagePile(String mileagePile) {
        MileagePile = mileagePile;
    }

    public String getExceptionDescription() {
        return ExceptionDescription;
    }

    public void setExceptionDescription(String exceptionDescription) {
        ExceptionDescription = exceptionDescription;
    }

    public String getDescDetails() {
        return DescDetails;
    }

    public void setDescDetails(String descDetails) {
        DescDetails = descDetails;
    }

    public String getJudgeLevel() {
        return JudgeLevel;
    }

    public void setJudgeLevel(String judgeLevel) {
        JudgeLevel = judgeLevel;
    }

    public String getInputPerson() {
        return InputPerson;
    }

    public void setInputPerson(String inputPerson) {
        InputPerson = inputPerson;
    }

    public String getCheckPerson() {
        return CheckPerson;
    }

    public void setCheckPerson(String checkPerson) {
        CheckPerson = checkPerson;
    }

    public String getWeather() {
        return Weather;
    }

    public void setWeather(String weather) {
        Weather = weather;
    }

    public String getCheckDate() {
        return CheckDate;
    }

    public void setCheckDate(String checkDate) {
        CheckDate = checkDate;
    }

    public List<Image> getImages() {
        return Images;
    }

    public void setImages(List<Image> images) {
        Images = images;
    }

    public class Image {
        private String Url;
        private String DisplayName;

        public String getUrl() {
            return Url;
        }

        public void setUrl(String url) {
            Url = url;
        }

        public String getDisplayName() {
            return DisplayName;
        }

        public void setDisplayName(String displayName) {
            DisplayName = displayName;
        }
    }
}
