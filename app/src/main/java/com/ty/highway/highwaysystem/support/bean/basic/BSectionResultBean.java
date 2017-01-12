package com.ty.highway.highwaysystem.support.bean.basic;

import java.util.List;

/**
 * Created by ${dzm} on 2015/9/14 0014.
 */
public class BSectionResultBean {

    private String r;
    private BSectionBeanS s;

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public BSectionBeanS getS() {
        return s;
    }

    public void setS(BSectionBeanS s) {
        this.s = s;
    }

    public class BSectionBeanS{

        private String updateTime;
        private List<BSectionBean> data;

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public List<BSectionBean> getData() {
            return data;
        }

        public void setData(List<BSectionBean> data) {
            this.data = data;
        }
    }
}
