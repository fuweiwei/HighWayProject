package com.ty.highway.highwaysystem.support.bean.basedata;

import java.util.List;

/**
 * Created by ${dzm} on 2015/9/14 0014.
 */
public class POrgVsUserResultBean {

    private String r;
    private POrgVsUserBeanS s;

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public POrgVsUserBeanS getS() {
        return s;
    }

    public void setS(POrgVsUserBeanS s) {
        this.s = s;
    }

    public class POrgVsUserBeanS{

        private String updateTime;
        private List<POrgVsUserBean> data;

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public List<POrgVsUserBean> getData() {
            return data;
        }

        public void setData(List<POrgVsUserBean> data) {
            this.data = data;
        }
    }
}
