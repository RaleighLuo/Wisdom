package com.gkzxhn.wisdom.property.model.impl;

import android.app.Activity;
import android.content.SharedPreferences;


import com.gkzxhn.wisdom.property.async.SingleRequestQueue;
import com.gkzxhn.wisdom.property.async.VolleyUtils;
import com.gkzxhn.wisdom.property.common.Constants;
import com.gkzxhn.wisdom.property.common.GKApplication;
import com.gkzxhn.wisdom.property.model.IBaseModel;

import java.util.UUID;

/**
 * Created by Administrator on 2016/6/12.
 */
public class BaseModel implements IBaseModel {
    protected final String REQUEST_TAG= UUID.randomUUID().toString().replace("-", "");
    protected VolleyUtils volleyUtils;
    protected SharedPreferences preferences;
    public BaseModel(){
        volleyUtils = new VolleyUtils();
        preferences= GKApplication.getInstance().
                getSharedPreferences(Constants.USER_TABLE, Activity.MODE_PRIVATE);
    }

    @Override
    public SharedPreferences getSharedPreferences() {
        return preferences;
    }

    public void stopAllReuqst() {
        SingleRequestQueue.getInstance().cancelAll(REQUEST_TAG);
    }
}
