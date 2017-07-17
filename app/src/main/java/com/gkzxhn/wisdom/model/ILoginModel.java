package com.gkzxhn.wisdom.model;

import com.gkzxhn.wisdom.async.VolleyUtils;

import org.json.JSONObject;

/**
 * Created by Raleigh.Luo on 17/7/17.
 */

public interface ILoginModel extends IBaseModel{
    public void requestCode(String phone, VolleyUtils.OnFinishedListener<String> onFinishedListener);
    public void login(String phone, String verifyCode, VolleyUtils.OnFinishedListener<String> onFinishedListener);
}
