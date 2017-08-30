package com.gkzxhn.wisdom.model;

import com.gkzxhn.wisdom.async.VolleyUtils;

import org.json.JSONObject;

/**
 * Created by Raleigh.Luo on 17/8/2.
 */

public interface IPersonInforModel extends IBaseModel{
    void requestVersion(VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener);
    void requestUserInfor(VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener);
    void updateUserInfor(String nickname,String portraitUrl,VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener);
}
