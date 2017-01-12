package com.ty.highway.highwaysystem.support.bean.basic;

import java.util.List;

/**
 * Created by ${dzm} on 2015/9/14 0014.
 */
public class POrganizeResultBean {

    private String r;
    private POrganizeBeanS s;

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public POrganizeBeanS getS() {
        return s;
    }

    public void setS(POrganizeBeanS s) {
        this.s = s;
    }

    public class POrganizeBeanS {

        private String updateTime;
        private List<POrganizeBean> data;

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public List<POrganizeBean> getData() {
            return data;
        }

        public void setData(List<POrganizeBean> data) {
            this.data = data;
        }
    }
}
