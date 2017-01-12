package com.ty.highway.highwaysystem.support.bean.basic;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fuweiwei on 2016/1/15.
 */
public class MenuChildrenBean implements Serializable {
    private String NewId;
    private String MenuName;
    private String Pid;
    private String MenuUrl;
    private String MenuNo;
    private int Sort;
    private List<MenuChildrenBean> children;

    public String getMenuNo() {
        return MenuNo;
    }

    public void setMenuNo(String menuNo) {
        MenuNo = menuNo;
    }

    public String getNewId() {
        return NewId;
    }

    public void setNewId(String newId) {
        NewId = newId;
    }

    public String getMenuName() {
        return MenuName;
    }

    public void setMenuName(String menuName) {
        MenuName = menuName;
    }

    public String getPid() {
        return Pid;
    }

    public void setPid(String pid) {
        Pid = pid;
    }

    public String getMenuUrl() {
        return MenuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        MenuUrl = menuUrl;
    }

    public int getSort() {
        return Sort;
    }

    public void setSort(int sort) {
        Sort = sort;
    }

    public List<MenuChildrenBean> getChildren() {
        return children;
    }

    public void setChildren(List<MenuChildrenBean> children) {
        this.children = children;
    }
}
