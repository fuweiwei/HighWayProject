package com.ty.highway.highwaysystem.support.bean.basedata;

/**
 * Created by fuweiwei on 2015/9/14.
 * 检查位置信息bean
 */
public class CTCheckPositionBean {
    private  String NewId;
    private  String ItemId;//项目Id
    private  String RangeName;// "进口"
    private  int Sort;
    private  String CreateDate;
    private  String CheckwayId;//检查方式Id

    public String getNewId() {
        return NewId;
    }

    public void setNewId(String newId) {
        NewId = newId;
    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public String getRangeName() {
        return RangeName;
    }

    public void setRangeName(String rangeName) {
        RangeName = rangeName;
    }

    public int getSort() {
        return Sort;
    }

    public void setSort(int sort) {
        Sort = sort;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getCheckwayId() {
        return CheckwayId;
    }

    public void setCheckwayId(String checkwayId) {
        CheckwayId = checkwayId;
    }
}
