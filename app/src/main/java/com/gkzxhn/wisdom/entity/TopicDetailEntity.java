package com.gkzxhn.wisdom.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raleigh.Luo on 17/8/7.
 */

public class TopicDetailEntity {
    private int viewed;
    private String id;
    @SerializedName("user_id")
    private String userId;
    private String content;
    @SerializedName("residential_id")
    private String residentialId;
    @SerializedName("created_at")
    private String createdDate;
    @SerializedName("updated_at")
    private String updatedDate;
    @SerializedName("comments_amount")
    private int commentCount;
    private List<LikeEntity> likes;
    private List<String> images;
    private TopicEntity.User user;

    public TopicEntity.User getUser() {
        return user;
    }

    public void setUser(TopicEntity.User user) {
        this.user = user;
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

    public List<LikeEntity> getLikes() {
        if(likes==null)likes=new ArrayList<>();
        return likes;
    }

    public void setLikes(List<LikeEntity> likes) {
        this.likes = likes;
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
