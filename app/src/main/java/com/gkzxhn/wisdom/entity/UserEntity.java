package com.gkzxhn.wisdom.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Raleigh.Luo on 17/8/8.
 */

public class UserEntity {
    private String id;
    private String name;
    private String phone;
    @SerializedName("user_type")
    private String userType;
    private String nickname;
    private String gender;
    @SerializedName("user_image")
    private String portrait;
    private List<RoomEntity> roomList;

    public List<RoomEntity> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<RoomEntity> roomList) {
        this.roomList = roomList;
    }

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
