package com.gkzxhn.wisdom.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Raleigh.Luo on 17/8/8.
 */

public class TopicCommentEntity {
    private String id;
    private String nickname;
    @SerializedName("user_image")
    private String portrait;
    @SerializedName("user_id")
    private String userId;
    private String content;
    @SerializedName("created_at")
    private String date;

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        if (id != null && !id.equals("null"))
            this.id = id;
    }

    public String getUserId() {
        return userId == null ? "" : userId;
    }

    public void setUserId(String userId) {
        if (userId != null && !userId.equals("null"))
            this.userId = userId;
    }

    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(String content) {
        if (content != null && !content.equals("null"))
            this.content = content;
    }

    public String getDate() {
        return date == null ? "" : date;
    }

    public void setDate(String date) {
        if (date != null && !date.equals("null"))
            this.date = date;
    }
}
