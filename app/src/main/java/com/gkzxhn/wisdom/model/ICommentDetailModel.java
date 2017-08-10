package com.gkzxhn.wisdom.model;

import com.gkzxhn.wisdom.async.VolleyUtils;

import org.json.JSONObject;

/**
 * Created by Raleigh.Luo on 17/8/10.
 */

public interface ICommentDetailModel extends IBaseModel {
    void comment(VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener);
    public void like(VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener);
}
