package com.ty.highway.highwaysystem.support.bean.check;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fuweiwei on 2015/9/21.
 */
public class TaskUploadInfoResultBean implements Serializable{
        public  String CheckNo;
        public  String TunnelId;
        public  String CheckEmp;
        public  String CheckWeather;
        public  String CheckWayId;
        public  String RecordEmp;
        public List<CheckRecordItem> CCheckRecordInterfaceItem;

        public String getCheckNo() {
            return CheckNo;
        }

        public void setCheckNo(String checkNo) {
            CheckNo = checkNo;
        }

        public String getTunnelId() {
            return TunnelId;
        }

        public void setTunnelId(String tunnelId) {
            TunnelId = tunnelId;
        }

        public String getCheckEmp() {
            return CheckEmp;
        }

        public void setCheckEmp(String checkEmp) {
            CheckEmp = checkEmp;
        }

        public String getCheckWeather() {
            return CheckWeather;
        }

        public void setCheckWeather(String checkWeather) {
            CheckWeather = checkWeather;
        }

        public String getCheckWayId() {
            return CheckWayId;
        }

        public void setCheckWayId(String checkWayId) {
            CheckWayId = checkWayId;
        }

        public String getRecordEmp() {
            return RecordEmp;
        }

        public void setRecordEmp(String recordEmp) {
            RecordEmp = recordEmp;
        }

        public List<CheckRecordItem> getCCheckRecordInterfaceItem() {
            return CCheckRecordInterfaceItem;
        }

        public void setCCheckRecordInterfaceItem(List<CheckRecordItem> CCheckRecordInterfaceItem) {
            this.CCheckRecordInterfaceItem = CCheckRecordInterfaceItem;
        }

}
