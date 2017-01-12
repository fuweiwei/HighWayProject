package com.ty.highway.highwaysystem.support.bean.basic;

import com.ty.highway.highwaysystem.support.utils.tree.TreeNodeId;
import com.ty.highway.highwaysystem.support.utils.tree.TreeNodeLabel;
import com.ty.highway.highwaysystem.support.utils.tree.TreeNodeNum;
import com.ty.highway.highwaysystem.support.utils.tree.TreeNodePid;

/**
 * Created by fuweiwei on 2015/12/24.
 */
public class BaseTreeBean {
    @TreeNodeId
    private String id;
    @TreeNodePid
    private  String parentId;
    @TreeNodeLabel
    private String name;
    @TreeNodeNum
    private int num; //病害数

    public BaseTreeBean(){

    }
    public BaseTreeBean(String id,String parentId,String name,int num){
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.num = num;

    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
