package com.ty.highway.highwaysystem.support.bean.check;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fuweiwei on 2015/9/11.
 */
public class TaskListBean implements Serializable {
    private String r;
    private TaskInfoBeanS s;

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public TaskInfoBeanS getS() {
        return s;
    }

    public void setS(TaskInfoBeanS s) {
        this.s = s;
    }

    public  class TaskInfoBeanS{
        private String updateTime;
        private List<TaskInfoBean> data;

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public List<TaskInfoBean> getData() {
            return data;
        }

        public void setData(List<TaskInfoBean> data) {
            this.data = data;
        }
    }
}
