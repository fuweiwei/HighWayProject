package com.ty.highway.highwaysystem.support.comparator;

import com.ty.highway.highwaysystem.support.bean.check.TaskInfoBean;

import java.util.Comparator;

/**
 * Created by fuweiwei on 2015/10/13.
 * 任务比较器
 */
public class TaskComparator implements Comparator<TaskInfoBean> {
    @Override
    public int compare(TaskInfoBean infoBean1, TaskInfoBean infoBean2) {
        int key1 = 0;
        int key2 = 0;
        String checkNo1 = infoBean1.getCheckNo();
        String checkNo2 = infoBean2.getCheckNo();
        if(infoBean1.getIsNearTask()==1){
            key1=100000000;
        }else{
            key1=Integer.parseInt(checkNo1.substring(2));
        }
        if(infoBean2.getIsNearTask()==1){
            key2=100000000;
        }else{
            key2=Integer.parseInt(checkNo2.substring(2));
        }

         return key2 == key1 ? 0 : (key1 > key2 ? 1 : -1);
    }
}
