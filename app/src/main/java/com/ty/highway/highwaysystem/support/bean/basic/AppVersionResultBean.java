package com.ty.highway.highwaysystem.support.bean.basic;

/**
 * Created by fuweiwei on 2015/10/9.
 */
public class AppVersionResultBean {
    private String r;
    private AppVersionBean s;

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public AppVersionBean getS() {
        return s;
    }

    public void setS(AppVersionBean s) {
        this.s = s;
    }

    public  class  AppVersionBean{
        private String NewId;
        private String AppVersion;
        private String AppPath;
        private String AppSize;
        private String CreateDate;
        private String UpdateDate;
        private String Remark;
        private String Sort;

        public String getNewId() {
            return NewId;
        }

        public void setNewId(String newId) {
            NewId = newId;
        }

        public String getAppVersion() {
            return AppVersion;
        }

        public void setAppVersion(String appVersion) {
            AppVersion = appVersion;
        }

        public String getAppPath() {
            return AppPath;
        }

        public void setAppPath(String appPath) {
            AppPath = appPath;
        }

        public String getAppSize() {
            return AppSize;
        }

        public void setAppSize(String appSize) {
            AppSize = appSize;
        }

        public String getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(String createDate) {
            CreateDate = createDate;
        }

        public String getUpdateDate() {
            return UpdateDate;
        }

        public void setUpdateDate(String updateDate) {
            UpdateDate = updateDate;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String remark) {
            Remark = remark;
        }

        public String getSort() {
            return Sort;
        }

        public void setSort(String sort) {
            Sort = sort;
        }
    }
}
