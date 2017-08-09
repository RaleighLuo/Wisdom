package com.gkzxhn.wisdom.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raleigh.Luo on 17/8/8.
 */

public class TopicCommentEntity {
    private String id;
    private String nickname;
    @SerializedName("user_image")
    private String portrait;
    //    @SerializedName("user_id")
    @Expose
    private String userId;
    private String content;
    //    @SerializedName("created_at")
    @Expose
    private String date;
    @Expose
    private List<LikeEntity> likes;
    @Expose
    private List<String> likeUsers;//点赞数-个人使用

    public List<String> getLikeUsers() {
        if(likeUsers==null)likeUsers=new ArrayList<>();
        if(likeUsers.size()==0&&likes!=null){
            for(LikeEntity entity:likes){
                likeUsers.add(entity.getUserId());
            }
        }
        return likeUsers;
    }

    public void setLikeUsers(List<String> likeUsers) {
        this.likeUsers = likeUsers;
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
