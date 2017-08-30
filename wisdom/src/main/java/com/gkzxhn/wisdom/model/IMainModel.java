package com.gkzxhn.wisdom.model;

import com.gkzxhn.wisdom.async.VolleyUtils;

import org.json.JSONObject;

/**
 * Created by Raleigh.Luo on 17/8/30.
 */

public interface IMainModel extends IBaseModel {
    void requestVersion(VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener);

}
