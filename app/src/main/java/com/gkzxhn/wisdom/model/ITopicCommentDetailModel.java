package com.gkzxhn.wisdom.model;

import com.gkzxhn.wisdom.async.VolleyUtils;

import org.json.JSONObject;

/**
 * Created by Raleigh.Luo on 17/8/10.
 */

public interface ITopicCommentDetailModel extends IBaseModel {
    void requestReplayList(String topicId,int currentPage, int pageSize,VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener);
    void publishReplay(String topicId,String content,VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener);
    void like(String topicId, VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener);
    void delete(String topicId, VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener);
    void request(VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener);
}
