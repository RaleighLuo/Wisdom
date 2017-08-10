package com.gkzxhn.wisdom.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Raleigh.Luo on 17/8/10.
 */

public class TopicReplayEntity {
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
}
