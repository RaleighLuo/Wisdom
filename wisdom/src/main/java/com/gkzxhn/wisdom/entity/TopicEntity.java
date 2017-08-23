package com.gkzxhn.wisdom.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.starlight.mobile.android.lib.util.JSONUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raleigh.Luo on 17/8/7.
 * 话题列表实体
 */

public class TopicEntity {
    private String id;
    private String content;//话题内容
    @SerializedName("comments_amount")
    private int commentCount;//评论数
    @SerializedName("created_at")
    private String date;//创建日期
    @SerializedName("user_id")
    private String userId;//创建者userId
    private int viewed;//浏览次数
    private String nickname;//昵称
    @SerializedName("user_image")
    private String portraitUrl;//头像下载地址
    @SerializedName("likes_amount")
    private int likesCount;//点赞数量
    @SerializedName("images_url")
    private List<String> images;//图片下载地址
    private boolean likeable;//是否可以点赞 true没有点赞 false 已经点赞

    public boolean isLikeable() {
        return likeable;
    }

    public void setLikeable(boolean likeable) {
        this.likeable = likeable;
    }

    public String getNickname() {
        return nickname == null ? "" : nickname;
    }

    public void setNickname(String nickname) {
        if (nickname != null && !nickname.equals("null"))
            this.nickname = nickname;
    }

    public String getPortraitUrl() {
        return portraitUrl == null ? "" : portraitUrl;
    }

    public void setPortraitUrl(String portraitUrl) {
        if (portraitUrl != null && !portraitUrl.equals("null"))
            this.portraitUrl = portraitUrl;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getViewed() {
        return viewed;
    }

    public void setViewed(int viewed) {
        this.viewed = viewed;
    }


    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        if (id != null && !id.equals("null"))
            this.id = id;
    }



    public String getDate() {
        return date == null ? "" : date;
    }

    public void setDate(String date) {
        if (date != null && !date.equals("null"))
            this.date = date;
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

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }


}
