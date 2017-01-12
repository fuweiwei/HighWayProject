package com.ty.highway.highwaysystem.support.comparator;

import com.ty.highway.highwaysystem.support.bean.basedata.CTDiseaseLevelBean;

import java.util.Comparator;

/**
 * Created by fuweiwei on 2015/10/13.
 * 病害等级比较器
 */
public class DamageLevelComparator implements Comparator<CTDiseaseLevelBean> {
    @Override
    public int compare(CTDiseaseLevelBean info1, CTDiseaseLevelBean info2) {
        int key1 = Integer.parseInt(info1.getSort());
        int key2 = Integer.parseInt(info2.getSort());
         return key2 == key1 ? 0 : (key1 > key2 ? 1 : -1);
    }
}
