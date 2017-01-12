package com.ty.highway.highwaysystem.support.bean.machine;

import java.util.List;

/**
 * Created by fuweiwei on 2016/1/5.
 */
public class ELMachineTypeResultBean {
    private String r;
    private ELMachineBeanTypeS s;

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public ELMachineBeanTypeS getS() {
        return s;
    }

    public void setS(ELMachineBeanTypeS s) {
        this.s = s;
    }

    public  class  ELMachineBeanTypeS{
        private String updateTime;
        private List<ELMachineTypeBean> data;

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public List<ELMachineTypeBean> getData() {
            return data;
        }

        public void setData(List<ELMachineTypeBean> data) {
            this.data = data;
        }
    }
}
