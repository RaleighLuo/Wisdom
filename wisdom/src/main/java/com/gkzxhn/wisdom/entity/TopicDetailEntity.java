package com.gkzxhn.wisdom.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raleigh.Luo on 17/8/7.
 * 话题详情
 */

public class TopicDetailEntity {
    private int viewed;//浏览次数
    private String id;
    @SerializedName("user_id")
    private String userId;//话题创建者用户ID
    private String content;//话题内容
    @SerializedName("residential_id")
    private String residentialId;//小区ID
    @SerializedName("created_at")
    private String createdDate;//创建日期
    @SerializedName("updated_at")
    private String updatedDate;//更新日期
    @SerializedName("comments_amount")
    private int commentCount;//评论数量
    @SerializedName("likes_amount")
    private int likesCount;//点赞数量
    @SerializedName("images_url")
    private List<String> images;//图片地址
    private int likeable;//是否可点赞 1没有点赞 0已经点赞
    private String nickname;//昵称
    @SerializedName("user_image")
    private String portrait;//头像下载地址

    public String getPortrait() {
        return portrait == null ? "" : portrait;
    }

    public void setPortrait(String portrait) {
        if (portrait != null && !portrait.equals("null"))
            this.portrait = portrait;
    }

    public String getNickname() {
        return nickname == null ? "" : nickname;
    }

    public void setNickname(String nickname) {
        if (nickname != null && !nickname.equals("null"))
            this.nickname = nickname;
    }

    public boolean isLikeable() {
        return likeable==1;
    }

    public void setLikeable(boolean likeable) {
        this.likeable = likeable?1:0;
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

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
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

    public String getResidentialId() {
        return residentialId == null ? "" : residentialId;
    }

    public void setResidentialId(String residentialId) {
        if (residentialId != null && !residentialId.equals("null"))
            this.residentialId = residentialId;
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


}
