package com.ty.highway.highwaysystem.support.bean.basedata;

import java.util.List;

/**
 * Created by ${dzm} on 2015/9/14 0014.
 */
public class PUserCheckPermissionResultBean {

    private String r;
    private PUserCheckPermissionBeanS s;

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public PUserCheckPermissionBeanS getS() {
        return s;
    }

    public void setS(PUserCheckPermissionBeanS s) {
        this.s = s;
    }

    public class PUserCheckPermissionBeanS{

        private String updateTime;
        private List<PUserCheckPermissionBean> data;

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public List<PUserCheckPermissionBean> getData() {
            return data;
        }

        public void setData(List<PUserCheckPermissionBean> data) {
            this.data = data;
        }
    }
}
