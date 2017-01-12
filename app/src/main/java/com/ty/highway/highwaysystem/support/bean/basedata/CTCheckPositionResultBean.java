package com.ty.highway.highwaysystem.support.bean.basedata;

import java.util.List;

/**
 * Created by ${dzm} on 2015/9/14 0014.
 */
public class CTCheckPositionResultBean {

    private String r;
    private CTCheckPositionBeanS s;

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public CTCheckPositionBeanS getS() {
        return s;
    }

    public void setS(CTCheckPositionBeanS s) {
        this.s = s;
    }

    public class CTCheckPositionBeanS{

        private String updateTime;
        private List<CTCheckPositionBean> data;

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public List<CTCheckPositionBean> getData() {
            return data;
        }

        public void setData(List<CTCheckPositionBean> data) {
            this.data = data;
        }
    }
}
