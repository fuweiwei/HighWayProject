package com.ty.highway.highwaysystem.support.bean.check;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fuweiwei on 2015/11/4.
 */
public class CTHistroyDiseasePhotoResultBean implements Serializable{
    private String r;
    private List<CTHistroyDiseasePhotoBean> s;

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public List<CTHistroyDiseasePhotoBean> getS() {
        return s;
    }

    public void setS(List<CTHistroyDiseasePhotoBean> s) {
        this.s = s;
    }
}
