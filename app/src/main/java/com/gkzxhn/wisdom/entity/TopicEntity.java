package com.gkzxhn.wisdom.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.starlight.mobile.android.lib.util.JSONUtil;

import org.json.JSONObject;

import java.util.List;

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
    private String date;
    //    @Expose  //gson忽略字段
//    private String nickname;
//    @Expose  //gson忽略字段
//    private String portraitUrl;
//    @Expose  //gson忽略字段
//    private String user_type;
    @SerializedName("user_id")
    private String userId;
    private User user;
    private List<String> images;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public  class User {
        private String nickname;
        @SerializedName("user_image")
        private String userPortrait;
        @SerializedName("user_type")
        private String userType;

        public String getNickname() {
            return nickname == null ? "" : nickname;
        }

        public void setNickname(String nickname) {
            if (nickname != null && !nickname.equals("null"))
                this.nickname = nickname;
        }

        public String getUserPortrait() {
            return userPortrait == null ? "" : userPortrait;
        }

        public void setUserPortrait(String userPortrait) {
            if (userPortrait != null && !userPortrait.equals("null"))
                this.userPortrait = userPortrait;
        }

        public String getUserType() {
            return userType == null ? "" : userType;
        }

        public void setUserType(String userType) {
            if (userType != null && !userType.equals("null"))
                this.userType = userType;
        }
    }

}
