package com.gkzxhn.wisdom.model;

import com.gkzxhn.wisdom.async.VolleyUtils;

import org.json.JSONObject;

/**
 * Created by Raleigh.Luo on 17/8/7.
 */

public interface ISignModel extends IBaseModel {
    void sign(VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener);
}
