package com.ty.highway.highwaysystem.support.bean.check;

import java.util.List;

/**
 * Created by fuweiwei on 2015/11/3.
 */
public class CTHistroyDiseaseInfoResultBean {
    private String r;
    private CTHistroyDiseaseS s;

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public CTHistroyDiseaseS getS() {
        return s;
    }

    public void setS(CTHistroyDiseaseS s) {
        this.s = s;
    }

    public class  CTHistroyDiseaseS{
        private String updateTime;
        private List<CTHistroyDiseaseInfoBean> data;

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public List<CTHistroyDiseaseInfoBean> getData() {
            return data;
        }

        public void setData(List<CTHistroyDiseaseInfoBean> data) {
            this.data = data;
        }
    }
}
