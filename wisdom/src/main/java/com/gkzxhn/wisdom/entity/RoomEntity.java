package com.gkzxhn.wisdom.entity;

import java.io.Serializable;

/**
 * Created by Raleigh.Luo on 17/8/1.
 * 个人信息－房屋信息
 *
 */
public class RoomEntity implements Serializable{
    private String buildingId;
    private String buildingName;//建筑名称
    private String regionId;
    private String regionName;//区域名称
    private String residentialAreasId;
    private String residentialAreasName;//小区名称
    private String roomId;
    private String roomName;//房号
    private String unitsId;
    private String unitsName;//房屋单元
    private float usedArea;//使用面积
    private float floorArea;//房屋面积

    public float getUsedArea() {
        return usedArea;
    }

    public void setUsedArea(float usedArea) {
        this.usedArea = usedArea;
    }

    public float getFloorArea() {
        return floorArea;
    }

    public void setFloorArea(float floorArea) {
        this.floorArea = floorArea;
    }

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
