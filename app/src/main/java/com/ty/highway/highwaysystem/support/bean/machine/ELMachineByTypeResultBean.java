package com.ty.highway.highwaysystem.support.bean.machine;

import java.util.List;

/**
 * Created by fuweiwei on 2016/1/5.
 */
public class ELMachineByTypeResultBean {
    private String r;
    private ELMachineByTypeBeanS s;

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public ELMachineByTypeBeanS getS() {
        return s;
    }

    public void setS(ELMachineByTypeBeanS s) {
        this.s = s;
    }

    public  class  ELMachineByTypeBeanS{
        private String updateTime;
        private List<ELMachineByTypeBean> data;

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public List<ELMachineByTypeBean> getData() {
            return data;
        }

        public void setData(List<ELMachineByTypeBean> data) {
            this.data = data;
        }
    }
}
