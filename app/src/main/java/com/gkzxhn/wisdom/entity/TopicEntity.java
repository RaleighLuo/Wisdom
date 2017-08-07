package com.gkzxhn.wisdom.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Raleigh.Luo on 17/8/7.
 */

public class TopicEntity {
    private String id;
    private String content;
    @SerializedName("likes")
    private int likeCount;//点赞数
    @SerializedName("comments_amount")
    private int commentCount;//评论数
    @SerializedName("created_at")
    private long date;
    private String nickname;
    @SerializedName("user_image")
    private String portraitUrl;
    @SerializedName("user_id")
    private String userId;

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

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }


    public String getPortraitUrl() {
        return portraitUrl == null ? "" : portraitUrl;
    }

    public void setPortraitUrl(String portraitUrl) {
        if (portraitUrl != null && !portraitUrl.equals("null"))
            this.portraitUrl = portraitUrl;
    }
}
