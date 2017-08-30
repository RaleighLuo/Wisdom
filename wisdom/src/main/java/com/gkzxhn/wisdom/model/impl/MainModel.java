package com.gkzxhn.wisdom.model.impl;

import com.android.volley.AuthFailureError;
import com.gkzxhn.wisdom.async.VolleyUtils;
import com.gkzxhn.wisdom.common.Constants;
import com.gkzxhn.wisdom.model.IMainModel;

import org.json.JSONObject;

/**
 * Created by Raleigh.Luo on 17/8/30.
 */

public class MainModel extends BaseModel implements IMainModel {
    @Override
    public void requestVersion(VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener) {
        try {
            volleyUtils.get(JSONObject.class, Constants.REQUEST_VERSION_URL,REQUEST_TAG,onFinishedListener);
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }
    }
}
