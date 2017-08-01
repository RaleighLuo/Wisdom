package com.gkzxhn.wisdom.entity;

/**
 * Created by Raleigh.Luo on 17/8/1.
 *
 */
//  "buildingId": 1,
//          "buildingName": "1幢",
//          "regionId": 1,
//          "regionName": "紫荆苑",
//          "residentialAreasId": 1,
//          "residentialAreasName": "乾城江来",
//          "roomId": 1,
//          "roomName": "1501",
//          "unitsId": 1,
//          "unitsName": "一单元"
public class RoomEntity {
    private String buildingId;
    private String buildingName;
    private String regionId;
    private String regionName;
    private String residentialAreasId;
    private String residentialAreasName;
    private String roomId;
    private String roomName;
    private String unitsId;
    private String unitsName;

    public String getBuildingId() {
        return buildingId == null ? "" : buildingId;
    }

    public void setBuildingId(String buildingId) {
        if (buildingId != null && !buildingId.equals("null"))
            this.buildingId = buildingId;
    }

    public String getBuildingName() {
        return buildingName == null ? "" : buildingName;
    }

    public void setBuildingName(String buildingName) {
        if (buildingName != null && !buildingName.equals("null"))
            this.buildingName = buildingName;
    }

    public String getRegionId() {
        return regionId == null ? "" : regionId;
    }

    public void setRegionId(String regionId) {
        if (regionId != null && !regionId.equals("null"))
            this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName == null ? "" : regionName;
    }

    public void setRegionName(String regionName) {
        if (regionName != null && !regionName.equals("null"))
            this.regionName = regionName;
    }

    public String getResidentialAreasId() {
        return residentialAreasId == null ? "" : residentialAreasId;
    }

    public void setResidentialAreasId(String residentialAreasId) {
        if (residentialAreasId != null && !residentialAreasId.equals("null"))
            this.residentialAreasId = residentialAreasId;
    }

    public String getResidentialAreasName() {
        return residentialAreasName == null ? "" : residentialAreasName;
    }

    public void setResidentialAreasName(String residentialAreasName) {
        if (residentialAreasName != null && !residentialAreasName.equals("null"))
            this.residentialAreasName = residentialAreasName;
    }

    public String getRoomId() {
        return roomId == null ? "" : roomId;
    }

    public void setRoomId(String roomId) {
        if (roomId != null && !roomId.equals("null"))
            this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName == null ? "" : roomName;
    }

    public void setRoomName(String roomName) {
        if (roomName != null && !roomName.equals("null"))
            this.roomName = roomName;
    }

    public String getUnitsId() {
        return unitsId == null ? "" : unitsId;
    }

    public void setUnitsId(String unitsId) {
        if (unitsId != null && !unitsId.equals("null"))
            this.unitsId = unitsId;
    }

    public String getUnitsName() {
        return unitsName == null ? "" : unitsName;
    }

    public void setUnitsName(String unitsName) {
        if (unitsName != null && !unitsName.equals("null"))
            this.unitsName = unitsName;
    }
}
