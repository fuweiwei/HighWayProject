package com.ty.highway.highwaysystem.support.bean.machine;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fuweiwei on 2016/1/8.
 */
public class MachineCheckRechodBean implements Serializable {
    private String CheckNo ;
    private String TunnelId ;
    private String CheckEmp ;
    private String CheckDate ;
    private String CheckWeather ;
    private String CheckWayId ;
    private String Remark ;
    private String RecordEmp ;
    private String CreateDate ;
    private int AuditCount  ;
    private String MaintenanceOrgan  ;
    private List<MCheckMachineDevice> MCheckMachineDevice;
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

    public String getCheckDate() {
        return CheckDate;
    }

    public void setCheckDate(String checkDate) {
        CheckDate = checkDate;
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

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getRecordEmp() {
        return RecordEmp;
    }

    public void setRecordEmp(String recordEmp) {
        RecordEmp = recordEmp;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public int getAuditCount() {
        return AuditCount;
    }

    public void setAuditCount(int auditCount) {
        AuditCount = auditCount;
    }

    public String getMaintenanceOrgan() {
        return MaintenanceOrgan;
    }

    public void setMaintenanceOrgan(String maintenanceOrgan) {
        MaintenanceOrgan = maintenanceOrgan;
    }

    public List<MachineCheckRechodBean.MCheckMachineDevice> getMCheckMachineDevice() {
        return MCheckMachineDevice;
    }

    public void setMCheckMachineDevice(List<MachineCheckRechodBean.MCheckMachineDevice> MCheckMachineDevice) {
        this.MCheckMachineDevice = MCheckMachineDevice;
    }

    public  class  MCheckMachineDevice implements Serializable{
        private String MMachineDeviceId;
        private List<MCheckMachineDeviceItem> MCheckMachineDeviceItem;

        public String getMMachineDeviceId() {
            return MMachineDeviceId;
        }

        public void setMMachineDeviceId(String MMachineDeviceId) {
            this.MMachineDeviceId = MMachineDeviceId;
        }

        public List<MachineCheckRechodBean.MCheckMachineDevice.MCheckMachineDeviceItem> getMCheckMachineDeviceItem() {
            return MCheckMachineDeviceItem;
        }

        public void setMCheckMachineDeviceItem(List<MachineCheckRechodBean.MCheckMachineDevice.MCheckMachineDeviceItem> MCheckMachineDeviceItem) {
            this.MCheckMachineDeviceItem = MCheckMachineDeviceItem;
        }

        public  class MCheckMachineDeviceItem implements Serializable{
            private String MMachineDeviceItemId ;
            private  List<MCheckMachineContent> MCheckMachineContent;

            public String getMMachineDeviceItemId() {
                return MMachineDeviceItemId;
            }

            public void setMMachineDeviceItemId(String MMachineDeviceItemId) {
                this.MMachineDeviceItemId = MMachineDeviceItemId;
            }

            public List<MachineCheckRechodBean.MCheckMachineDevice.MCheckMachineDeviceItem.MCheckMachineContent> getMCheckMachineContent() {
                return MCheckMachineContent;
            }

            public void setMCheckMachineContent(List<MachineCheckRechodBean.MCheckMachineDevice.MCheckMachineDeviceItem.MCheckMachineContent> MCheckMachineContent) {
                this.MCheckMachineContent = MCheckMachineContent;
            }

            public class MCheckMachineContent implements Serializable{
                private int DiseaseFrom;
                private String NewId;
                private boolean IsUse ;
                private String Remark ;
                private String LevelId ;
                private String MMachineContentId ;
                private String MMachineItemId  ;
                private String MMachineDeviceId  ;
                private String MMachineDescriptId;
                private String MMachineDeviceRId  ;
                private String ConservationMeasuresId  ;
                private List<DDocumentsEnity> DDocumentsEnity;

                public String getConservationMeasuresId() {
                    return ConservationMeasuresId;
                }

                public int getDiseaseFrom() {
                    return DiseaseFrom;
                }

                public void setDiseaseFrom(int diseaseFrom) {
                    DiseaseFrom = diseaseFrom;
                }

                public void setConservationMeasuresId(String conservationMeasuresId) {
                    ConservationMeasuresId = conservationMeasuresId;
                }

                public String getMMachineDescriptId() {
                    return MMachineDescriptId;
                }

                public void setMMachineDescriptId(String MMachineDescriptId) {
                    this.MMachineDescriptId = MMachineDescriptId;
                }

                public String getMMachineDeviceRId() {
                    return MMachineDeviceRId;
                }

                public void setMMachineDeviceRId(String MMachineDeviceRId) {
                    this.MMachineDeviceRId = MMachineDeviceRId;
                }

                public String getNewId() {
                    return NewId;
                }

                public void setNewId(String newId) {
                    NewId = newId;
                }

                public boolean isUse() {
                    return IsUse;
                }

                public void setIsUse(boolean isUse) {
                    IsUse = isUse;
                }

                public String getRemark() {
                    return Remark;
                }

                public void setRemark(String remark) {
                    Remark = remark;
                }

                public String getLevelId() {
                    return LevelId;
                }

                public void setLevelId(String levelId) {
                    LevelId = levelId;
                }

                public String getMMachineContentId() {
                    return MMachineContentId;
                }

                public void setMMachineContentId(String MMachineContentId) {
                    this.MMachineContentId = MMachineContentId;
                }

                public String getMMachineItemId() {
                    return MMachineItemId;
                }

                public void setMMachineItemId(String MMachineItemId) {
                    this.MMachineItemId = MMachineItemId;
                }

                public String getMMachineDeviceId() {
                    return MMachineDeviceId;
                }

                public void setMMachineDeviceId(String MMachineDeviceId) {
                    this.MMachineDeviceId = MMachineDeviceId;
                }

                public List<MachineCheckRechodBean.MCheckMachineDevice.MCheckMachineDeviceItem.MCheckMachineContent.DDocumentsEnity> getDDocumentsEnity() {
                    return DDocumentsEnity;
                }

                public void setDDocumentsEnity(List<MachineCheckRechodBean.MCheckMachineDevice.MCheckMachineDeviceItem.MCheckMachineContent.DDocumentsEnity> DDocumentsEnity) {
                    this.DDocumentsEnity = DDocumentsEnity;
                }

                public  class DDocumentsEnity implements Serializable{
                    public  String DocumentPath;
                    public  String DisplayName;
                    public  String ExtensionName;

                    public String getDocumentPath() {
                        return DocumentPath;
                    }

                    public void setDocumentPath(String documentPath) {
                        DocumentPath = documentPath;
                    }

                    public String getDisplayName() {
                        return DisplayName;
                    }

                    public void setDisplayName(String displayName) {
                        DisplayName = displayName;
                    }

                    public String getExtensionName() {
                        return ExtensionName;
                    }

                    public void setExtensionName(String extensionName) {
                        ExtensionName = extensionName;
                    }
                }
            }
        }
    }
}
