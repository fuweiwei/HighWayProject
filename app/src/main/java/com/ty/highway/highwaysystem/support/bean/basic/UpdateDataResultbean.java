package com.ty.highway.highwaysystem.support.bean.basic;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fuweiwei on 2015/9/14.
 */
public class UpdateDataResultbean implements Serializable{
    private String  r;
    private UpdateDatabeanS s;

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public UpdateDatabeanS getS() {
        return s;
    }

    public void setS(UpdateDatabeanS s) {
        this.s = s;
    }

    public class UpdateDatabeanS{
        private List<UpdateDatabean> data;

        public List<UpdateDatabean> getData() {
            return data;
        }

        public void setData(List<UpdateDatabean> data) {
            this.data = data;
        }
    }
    public  class UpdateDatabean{
        private  String LineTime;
        private  String RoadTime;
        private  String TunnelTime;
        private  String OrganizationTime;
        private  String DictionTime;
        private  String CheckPositionTime;
        private  String OrgTUserTime;
        private  String TunnelTCheckItemTime;
        private  String DiseaseTypeByDescriptTime;
        private  String OrgnizationRangeTime;

        public String getLineTime() {
            return LineTime;
        }

        public void setLineTime(String lineTime) {
            LineTime = lineTime;
        }

        public String getRoadTime() {
            return RoadTime;
        }

        public void setRoadTime(String roadTime) {
            RoadTime = roadTime;
        }

        public String getTunnelTime() {
            return TunnelTime;
        }

        public void setTunnelTime(String tunnelTime) {
            TunnelTime = tunnelTime;
        }

        public String getOrganizationTime() {
            return OrganizationTime;
        }

        public void setOrganizationTime(String organizationTime) {
            OrganizationTime = organizationTime;
        }

        public String getDictionTime() {
            return DictionTime;
        }

        public void setDictionTime(String dictionTime) {
            DictionTime = dictionTime;
        }

        public String getCheckPositionTime() {
            return CheckPositionTime;
        }

        public void setCheckPositionTime(String checkPositionTime) {
            CheckPositionTime = checkPositionTime;
        }

        public String getOrgTUserTime() {
            return OrgTUserTime;
        }

        public void setOrgTUserTime(String orgTUserTime) {
            OrgTUserTime = orgTUserTime;
        }

        public String getTunnelTCheckItemTime() {
            return TunnelTCheckItemTime;
        }

        public void setTunnelTCheckItemTime(String tunnelTCheckItemTime) {
            TunnelTCheckItemTime = tunnelTCheckItemTime;
        }

        public String getDiseaseTypeByDescriptTime() {
            return DiseaseTypeByDescriptTime;
        }

        public void setDiseaseTypeByDescriptTime(String diseaseTypeByDescriptTime) {
            DiseaseTypeByDescriptTime = diseaseTypeByDescriptTime;
        }

        public String getOrgnizationRangeTime() {
            return OrgnizationRangeTime;
        }

        public void setOrgnizationRangeTime(String orgnizationRangeTime) {
            OrgnizationRangeTime = orgnizationRangeTime;
        }
    }

}
