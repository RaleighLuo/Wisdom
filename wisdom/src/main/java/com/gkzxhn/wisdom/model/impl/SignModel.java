package com.gkzxhn.wisdom.model.impl;

import com.gkzxhn.wisdom.async.VolleyUtils;
import com.gkzxhn.wisdom.common.Constants;
import com.gkzxhn.wisdom.model.ISignModel;

import org.json.JSONObject;

/**
 * Created by Raleigh.Luo on 17/8/7.
 */

public class SignModel extends BaseModel implements ISignModel {
    /**每日签到
     * @param onFinishedListener
     */
    @Override
    public void sign(VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener) {
        try{
            String url=String.format("%s/%s/signin",Constants.REQUEST_USER_URL,preferences.getString(Constants.USER_ID,""));
            volleyUtils.patch(url,new JSONObject(),REQUEST_TAG,onFinishedListener);
        } catch (Exception authFailureError) {
            authFailureError.printStackTrace();
        }
    }
}
