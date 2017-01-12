package com.ty.highway.highwaysystem.support.comparator;

import com.ty.highway.highwaysystem.support.bean.check.CTHistroyDiseaseInfoBean;

import java.text.CollationKey;
import java.text.Collator;
import java.util.Comparator;

/**
 * Created by fuweiwei on 2015/12/2.
 * 历史病害比较器
 */
public class HistroyDamageComparator implements Comparator<CTHistroyDiseaseInfoBean> {
    private Collator collator = Collator.getInstance();
    @Override
    public int compare(CTHistroyDiseaseInfoBean lhs, CTHistroyDiseaseInfoBean rhs) {
        String s1 = lhs.getContentName();
        String s2 = rhs.getContentName();
        //把字符串转换为一系列比特，它们可以以比特形式与 CollationKeys 相比较
        CollationKey key1=collator.getCollationKey(s1);//要想不区分大小写进行比较用o1.toString().toLowerCase()
        CollationKey key2=collator.getCollationKey(s2);
        return key1.compareTo(key2);
    }
}
