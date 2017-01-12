package com.ty.highway.highwaysystem.support.bean.basedata;

import java.util.List;

/**
 * Created by ${dzm} on 2015/9/14 0014.
 */
public class CTTunnelVsItemResultBean {

    private String r;
    private CTTunnelVsItemBeanS s;

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public CTTunnelVsItemBeanS getS() {
        return s;
    }

    public void setS(CTTunnelVsItemBeanS s) {
        this.s = s;
    }

    public class CTTunnelVsItemBeanS{

        private String updateTime;
        private List<CTTunnelVsItemBean> data;

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public List<CTTunnelVsItemBean> getData() {
            return data;
        }

        public void setData(List<CTTunnelVsItemBean> data) {
            this.data = data;
        }
    }
}
