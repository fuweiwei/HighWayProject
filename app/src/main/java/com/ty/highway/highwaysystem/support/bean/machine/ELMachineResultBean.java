package com.ty.highway.highwaysystem.support.bean.machine;

import java.util.List;

/**
 * Created by fuweiwei on 2016/1/5.
 */
public class ELMachineResultBean {
    private String r;
    private ELMachineBeanS s;

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public ELMachineBeanS getS() {
        return s;
    }

    public void setS(ELMachineBeanS s) {
        this.s = s;
    }

    public  class  ELMachineBeanS{
        private String updateTime;
        private List<ELMachineBean> data;

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public List<ELMachineBean> getData() {
            return data;
        }

        public void setData(List<ELMachineBean> data) {
            this.data = data;
        }
    }
}
