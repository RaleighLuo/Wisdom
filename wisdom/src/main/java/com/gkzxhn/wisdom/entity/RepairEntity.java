package com.gkzxhn.wisdom.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Raleigh.Luo on 17/8/22.
 */

public class RepairEntity {
    private String id;
    private String content;
    @SerializedName("user_id")
    private String userId;
    @SerializedName("created_at")
    private String createdDate;
    @SerializedName("updated_at")
    private String updatedDate;
    private String status;//当前状态 :undisposed, :processing, :finished
    @SerializedName("repair_type")
    private String repairType;//报修类型
    private int viewed;//浏览次数

    public int getViewed() {
        return viewed;
    }

    public void setViewed(int viewed) {
        this.viewed = viewed;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        if (id != null && !id.equals("null"))
            this.id = id;
    }

    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(String content) {
        if (content != null && !content.equals("null"))
            this.content = content;
    }

    public String getUserId() {
        return userId == null ? "" : userId;
    }

    public void setUserId(String userId) {
        if (userId != null && !userId.equals("null"))
            this.userId = userId;
    }

    public String getCreatedDate() {
        return createdDate == null ? "" : createdDate;
    }

    public void setCreatedDate(String createdDate) {
        if (createdDate != null && !createdDate.equals("null"))
            this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate == null ? "" : updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        if (updatedDate != null && !updatedDate.equals("null"))
            this.updatedDate = updatedDate;
    }

    public String getStatus() {
        return status == null ? "" : status;
    }

    public void setStatus(String status) {
        if (status != null && !status.equals("null"))
            this.status = status;
    }

    public String getRepairType() {
        return repairType == null ? "" : repairType;
    }

    public void setRepairType(String repairType) {
        if (repairType != null && !repairType.equals("null"))
            this.repairType = repairType;
    }
}
