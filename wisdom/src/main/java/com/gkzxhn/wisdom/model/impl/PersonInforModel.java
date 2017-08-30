package com.gkzxhn.wisdom.model.impl;

import com.android.volley.AuthFailureError;
import com.gkzxhn.wisdom.async.VolleyUtils;
import com.gkzxhn.wisdom.common.Constants;
import com.gkzxhn.wisdom.model.IPersonInforModel;

import org.json.JSONObject;

/**
 * Created by Raleigh.Luo on 17/8/2.
 */

public class PersonInforModel extends BaseModel implements IPersonInforModel {

    @Override
    public void requestVersion(VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener) {
        try {
            volleyUtils.get(JSONObject.class,Constants.REQUEST_VERSION_URL,REQUEST_TAG,onFinishedListener);
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }
    }

    /**请求用户信息
     * @param onFinishedListener
     */
    @Override
    public void requestUserInfor(VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener) {
        try {
            String url= String.format("%s/%s",Constants.REQUEST_USER_URL,preferences.getString(Constants.USER_ID,""));
            volleyUtils.get(JSONObject.class, url, REQUEST_TAG, onFinishedListener);
        } catch (Exception authFailureError) {
            authFailureError.printStackTrace();
        }
    }

    /**更新用户信息
     * @param nickname
     * @param portraitUrl
     * @param onFinishedListener
     */
    @Override
    public void updateUserInfor(String nickname, String portraitUrl,VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener) {
        try {
            String url= String.format("%s/%s",Constants.REQUEST_USER_URL,preferences.getString(Constants.USER_ID,""));
            JSONObject params=new JSONObject();
            if(nickname!=null&&nickname.length()>0)params.put("nickname",nickname);
            if(portraitUrl!=null&&portraitUrl.length()>0)params.put("user_image",portraitUrl);
            volleyUtils.patch(url,new JSONObject().put("user",params), REQUEST_TAG, onFinishedListener);
        } catch (Exception authFailureError) {
            authFailureError.printStackTrace();
        }
    }
}
