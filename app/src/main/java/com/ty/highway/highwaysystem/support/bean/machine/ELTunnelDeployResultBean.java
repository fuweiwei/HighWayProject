package com.ty.highway.highwaysystem.support.bean.machine;

import java.util.List;

/**
 * Created by fuweiwei on 2016/1/5.
 */
public class ELTunnelDeployResultBean {
    private String r;
    private ELTunnelDeployBeanS s;

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public ELTunnelDeployBeanS getS() {
        return s;
    }

    public void setS(ELTunnelDeployBeanS s) {
        this.s = s;
    }

    public  class  ELTunnelDeployBeanS{
        private String updateTime;
        private List<ELTunnelDeployBean> data;

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public List<ELTunnelDeployBean> getData() {
            return data;
        }

        public void setData(List<ELTunnelDeployBean> data) {
            this.data = data;
        }
    }
}
