package com.ty.highway.highwaysystem.support.bean.basic;

import java.util.List;

/**
 * Created by ${dzm} on 2015/9/14 0014.
 */
public class BTunnelResultBean {

    private String r;
    private BTunnelBeanS s;

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public BTunnelBeanS getS() {
        return s;
    }

    public void setS(BTunnelBeanS s) {
        this.s = s;
    }

    public class BTunnelBeanS{

        private String updateTime;
        private List<BTunnelBean> data;

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public List<BTunnelBean> getData() {
            return data;
        }

        public void setData(List<BTunnelBean> data) {
            this.data = data;
        }
    }
}
