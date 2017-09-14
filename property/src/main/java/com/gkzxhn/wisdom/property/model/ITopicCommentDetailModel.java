package com.gkzxhn.wisdom.property.model;


import com.gkzxhn.wisdom.property.async.VolleyUtils;

import org.json.JSONObject;

/**
 * Created by Raleigh.Luo on 17/8/10.
 */

public interface ITopicCommentDetailModel extends IBaseModel {
    void publishReplay(String content, VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener);
    void like(VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener);
    void deleteReplay(String id, VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener);
    void request(VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener);
}
