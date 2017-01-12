package com.ty.highway.highwaysystem.support.bean.basic;

import java.util.List;

/**
 * Created by ${dzm} on 2015/9/14 0014.
 */
public class BRoadResutBean {

    private String r;
    private BRoadBeanS s;

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public BRoadBeanS getS() {
        return s;
    }

    public void setS(BRoadBeanS s) {
        this.s = s;
    }

    public class BRoadBeanS {

        private String updateTime;
        private List<BRoadBean> data;

        public List<BRoadBean> getData() {
            return data;
        }

        public void setData(List<BRoadBean> data) {
            this.data = data;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }

}
