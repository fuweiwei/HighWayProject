package com.ty.highway.highwaysystem.support.bean.basedata;

import java.util.List;

/**
 * Created by fuweiwei on 2015/9/18.
 */
public class CTDiseaseTypeByDescriptResultBean {
    private String r;
    private CTDiseaseTypeByDescriptS s;

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public CTDiseaseTypeByDescriptS getS() {
        return s;
    }

    public void setS(CTDiseaseTypeByDescriptS s) {
        this.s = s;
    }

    public class  CTDiseaseTypeByDescriptS{
        private String updateTime;
        private List<CTDiseaseTypeByDescriptBean> data;

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public List<CTDiseaseTypeByDescriptBean> getData() {
            return data;
        }

        public void setData(List<CTDiseaseTypeByDescriptBean> data) {
            this.data = data;
        }
    }
}
