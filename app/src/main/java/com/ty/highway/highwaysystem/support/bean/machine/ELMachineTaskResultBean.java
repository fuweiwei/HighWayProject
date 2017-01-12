package com.ty.highway.highwaysystem.support.bean.machine;

import java.util.List;

/**
 * Created by fuweiwei on 2016/1/5.
 */
public class ELMachineTaskResultBean {
    private String r;
    private ELMachineTaskBeanS s;

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public ELMachineTaskBeanS getS() {
        return s;
    }

    public void setS(ELMachineTaskBeanS s) {
        this.s = s;
    }

    public  class  ELMachineTaskBeanS{
        private String updateTime;
        private List<ELMachineTaskBean> data;

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public List<ELMachineTaskBean> getData() {
            return data;
        }

        public void setData(List<ELMachineTaskBean> data) {
            this.data = data;
        }
    }
}
