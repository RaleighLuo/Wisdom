package com.gkzxhn.wisdom.property.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Raleigh.Luo on 17/8/10.
 * 话题回复列表实体
 */

public class TopicReplayEntity {
    private String id;
    private String nickname;//昵称
    @SerializedName("user_image")
    private String portrait;//头像下载地址
    @SerializedName("user_id")
    private String userId;//创建者Userid
    private String content;//回复内容
    @SerializedName("created_at")
    private String date;//创建日期


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        if (id != null && !id.equals("null"))
            this.id = id;
    }

    public String getNickname() {
        return nickname == null ? "" : nickname;
    }

    public void setNickname(String nickname) {
        if (nickname != null && !nickname.equals("null"))
            this.nickname = nickname;
    }

    public String getPortrait() {
        return portrait == null ? "" : portrait;
    }

    public void setPortrait(String portrait) {
        if (portrait != null && !portrait.equals("null"))
            this.portrait = portrait;
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
}
