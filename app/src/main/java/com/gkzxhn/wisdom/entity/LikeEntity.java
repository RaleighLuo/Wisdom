package com.gkzxhn.wisdom.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Raleigh.Luo on 17/8/9.
 */

public class LikeEntity {
    @SerializedName("user_id")
    private String userId;

    public LikeEntity(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId == null ? "" : userId;
    }

    public void setUserId(String userId) {
        if (userId != null && !userId.equals("null"))
            this.userId = userId;
    }

    @Override
    public boolean equals(Object obj) {
        return getUserId().equals(((LikeEntity)obj).getUserId());
    }
}
