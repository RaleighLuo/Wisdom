package com.gkzxhn.wisdom.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Raleigh.Luo on 17/8/8.
 * 用户id
 */

public class UserEntity {
    private String id;
    private String name;//名字
    private String phone;//手机号码
    @SerializedName("user_type")
    private String userType;//用户类型
    private String nickname;//昵称
    private String gender;//性别
    @SerializedName("user_image")
    private String portrait;//用户头像
    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        if (id != null && !id.equals("null"))
            this.id = id;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        if (name != null && !name.equals("null"))
            this.name = name;
    }

    public String getPhone() {
        return phone == null ? "" : phone;
    }

    public void setPhone(String phone) {
        if (phone != null && !phone.equals("null"))
            this.phone = phone;
    }

    public String getUserType() {
        return userType == null ? "" : userType;
    }

    public void setUserType(String userType) {
        if (userType != null && !userType.equals("null"))
            this.userType = userType;
    }

    public String getNickname() {
        return nickname == null ? "" : nickname;
    }

    public void setNickname(String nickname) {
        if (nickname != null && !nickname.equals("null"))
            this.nickname = nickname;
    }

    public String getGender() {
        return gender == null ? "" : gender;
    }

    public void setGender(String gender) {
        if (gender != null && !gender.equals("null"))
            this.gender = gender;
    }

    public String getPortrait() {
        return portrait == null ? "" : portrait;
    }

    public void setPortrait(String portrait) {
        if (portrait != null && !portrait.equals("null"))
            this.portrait = portrait;
    }
}
