package com.ty.highway.highwaysystem.support.bean.check;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fuweiwei on 2015/9/21.
 */
public class CheckRecordItem implements Serializable{
    public String ItemId;
    public List<CheckRecordType> CCheckRecordInterfaceType;

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public List<CheckRecordType> getCCheckRecordInterfaceType() {
        return CCheckRecordInterfaceType;
    }

    public void setCCheckRecordInterfaceType(List<CheckRecordType> CCheckRecordInterfaceType) {
        this.CCheckRecordInterfaceType = CCheckRecordInterfaceType;
    }

    public class  CheckRecordType implements Serializable{
        public String DiseaseTypeId;
        public List<CheckRecordData> CCheckRecordInterfaceData;

        public String getDiseaseTypeId() {
            return DiseaseTypeId;
        }

        public void setDiseaseTypeId(String diseaseTypeId) {
            DiseaseTypeId = diseaseTypeId;
        }

        public List<CheckRecordData> getCCheckRecordInterfaceData() {
            return CCheckRecordInterfaceData;
        }

        public void setCCheckRecordInterfaceData(List<CheckRecordData> CCheckRecordInterfaceData) {
            this.CCheckRecordInterfaceData = CCheckRecordInterfaceData;
        }

        public class CheckRecordData implements Serializable{
            public String DiseaseId;
            public String Remark;
            public String StartMileageNum;
            public String EndMileageNum;
            public String Lengths;
            public String Widths;
            public String Deeps;
            public String Areas;
            public String Angles;
            public String CenterPoint;
            public String DiseaseFrom;
            public String EdgeDistance;
            public String CutCount;
            public String TheDeformation;
            public String ErrorDeformation;
            public String Percentage;
            public String Direction;
            public String DType;
            public String Height;
            public String TheRate;
            public String DiseaseRemark;
            public String SpaceId;
            private String LevelId;
            private boolean IsRepeat;
            private String IsRepeatId;
            private String ConservationMeasuresId;
            public List<CenterInfo> Center;
            public List<DistanceInfo> Distance;
            public List<DDocumentsEnityInfo> DDocumentsEnity;

            public String getConservationMeasuresId() {
                return ConservationMeasuresId;
            }

            public void setConservationMeasuresId(String conservationMeasuresId) {
                ConservationMeasuresId = conservationMeasuresId;
            }

            public String getDiseaseRemark() {
                return DiseaseRemark;
            }

            public void setDiseaseRemark(String diseaseRemark) {
                DiseaseRemark = diseaseRemark;
            }

            public boolean isRepeat() {
                return IsRepeat;
            }

            public void setIsRepeat(boolean isRepeat) {
                IsRepeat = isRepeat;
            }

            public String getIsRepeatId() {
                return IsRepeatId;
            }

            public void setIsRepeatId(String isRepeatId) {
                IsRepeatId = isRepeatId;
            }

            public String getLevelId() {
                return LevelId;
            }

            public void setLevelId(String levelId) {
                LevelId = levelId;
            }

            public String getSpaceId() {
                return SpaceId;
            }

            public void setSpaceId(String spaceId) {
                SpaceId = spaceId;
            }

            public String getDiseaseId() {
                return DiseaseId;
            }

            public void setDiseaseId(String diseaseId) {
                DiseaseId = diseaseId;
            }

            public String getRemark() {
                return Remark;
            }

            public void setRemark(String remark) {
                Remark = remark;
            }

            public String getStartMileageNum() {
                return StartMileageNum;
            }

            public void setStartMileageNum(String startMileageNum) {
                StartMileageNum = startMileageNum;
            }

            public String getEndMileageNum() {
                return EndMileageNum;
            }

            public void setEndMileageNum(String endMileageNum) {
                EndMileageNum = endMileageNum;
            }

            public String getLengths() {
                return Lengths;
            }

            public void setLengths(String lengths) {
                Lengths = lengths;
            }

            public String getWidths() {
                return Widths;
            }

            public void setWidths(String widths) {
                Widths = widths;
            }

            public String getDeeps() {
                return Deeps;
            }

            public void setDeeps(String deeps) {
                Deeps = deeps;
            }

            public String getAreas() {
                return Areas;
            }

            public void setAreas(String areas) {
                Areas = areas;
            }

            public String getAngles() {
                return Angles;
            }

            public void setAngles(String angles) {
                Angles = angles;
            }

            public String getCenterPoint() {
                return CenterPoint;
            }

            public void setCenterPoint(String centerPoint) {
                CenterPoint = centerPoint;
            }

            public String getDiseaseFrom() {
                return DiseaseFrom;
            }

            public void setDiseaseFrom(String diseaseFrom) {
                DiseaseFrom = diseaseFrom;
            }

            public String getEdgeDistance() {
                return EdgeDistance;
            }

            public void setEdgeDistance(String edgeDistance) {
                EdgeDistance = edgeDistance;
            }

            public String getCutCount() {
                return CutCount;
            }

            public void setCutCount(String cutCount) {
                CutCount = cutCount;
            }

            public String getTheDeformation() {
                return TheDeformation;
            }

            public void setTheDeformation(String theDeformation) {
                TheDeformation = theDeformation;
            }

            public String getErrorDeformation() {
                return ErrorDeformation;
            }

            public void setErrorDeformation(String errorDeformation) {
                ErrorDeformation = errorDeformation;
            }

            public String getPercentage() {
                return Percentage;
            }

            public void setPercentage(String percentage) {
                Percentage = percentage;
            }

            public String getDirection() {
                return Direction;
            }

            public void setDirection(String direction) {
                Direction = direction;
            }

            public String getDType() {
                return DType;
            }

            public void setDType(String DType) {
                this.DType = DType;
            }

            public String getHeight() {
                return Height;
            }

            public void setHeight(String height) {
                Height = height;
            }

            public String getTheRate() {
                return TheRate;
            }

            public void setTheRate(String theRate) {
                TheRate = theRate;
            }

            public List<CenterInfo> getCenter() {
                return Center;
            }

            public void setCenter(List<CenterInfo> center) {
                Center = center;
            }

            public List<DistanceInfo> getDistance() {
                return Distance;
            }

            public void setDistance(List<DistanceInfo> distance) {
                Distance = distance;
            }

            public List<DDocumentsEnityInfo> getDDocumentsEnity() {
                return DDocumentsEnity;
            }

            public void setDDocumentsEnity(List<DDocumentsEnityInfo> DDocumentsEnity) {
                this.DDocumentsEnity = DDocumentsEnity;
            }

            public  class CenterInfo implements Serializable{
                public String PointX;
                public String PointY;

                public String getPointX() {
                    return PointX;
                }

                public void setPointX(String pointX) {
                    PointX = pointX;
                }

                public String getPointY() {
                    return PointY;
                }

                public void setPointY(String pointY) {
                    PointY = pointY;
                }
            }
            public  class DistanceInfo implements Serializable{
                public String PointX;
                public String PointY;

                public String getPointX() {
                    return PointX;
                }

                public void setPointX(String pointX) {
                    PointX = pointX;
                }

                public String getPointY() {
                    return PointY;
                }

                public void setPointY(String pointY) {
                    PointY = pointY;
                }
            }
            public  class DDocumentsEnityInfo implements Serializable{
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