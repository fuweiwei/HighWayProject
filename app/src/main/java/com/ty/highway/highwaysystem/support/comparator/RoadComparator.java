package com.ty.highway.highwaysystem.support.comparator;

import com.ty.highway.highwaysystem.support.bean.basic.BaseTreeBean;

import java.text.Collator;
import java.util.Comparator;

/**
 * Created by fuweiwei on 2015/12/2.
 * 路线比较器
 */
public class RoadComparator implements Comparator<BaseTreeBean> {
    private Collator collator = Collator.getInstance();
    @Override
    public int compare(BaseTreeBean lhs, BaseTreeBean rhs) {
        int count1= lhs.getNum();
        int count2 = rhs.getNum();
        /*//把字符串转换为一系列比特，它们可以以比特形式与 CollationKeys 相比较
        CollationKey key1=collator.getCollationKey(s1);//要想不区分大小写进行比较用o1.toString().toLowerCase()
        CollationKey key2=collator.getCollationKey(s2);*/
        return count1 == count2 ? 0 : (count1 > count2 ? -1 : 1);
    }
}
