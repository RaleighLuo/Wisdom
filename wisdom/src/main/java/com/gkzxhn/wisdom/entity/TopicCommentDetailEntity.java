package com.gkzxhn.wisdom.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raleigh.Luo on 17/8/15.
 */

public class TopicCommentDetailEntity implements Serializable{
    private String id;
    private String nickname;
    @SerializedName("user_image")
    private String portrait;
    @SerializedName("user_id")
    private String userId;
    private String content;
    @SerializedName("created_at")
    private String date;
    @SerializedName("likes_amount")
    private int likesCount;
    private int likeable;
    @SerializedName("comments_amount")
    private int commentCount;//评论数
    private int replayCount;//回复数
    public int getReplayCount() {
        return replayCount;
    }

    public void setReplayCount(int replayCount) {
        this.replayCount = replayCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public boolean isLikeable() {
        return likeable==1;
    }

    public void setLikeable(boolean likeable) {
        this.likeable = likeable?1:0;
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
