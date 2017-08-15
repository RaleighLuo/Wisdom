package com.gkzxhn.wisdom.model;

import com.gkzxhn.wisdom.async.VolleyUtils;

import org.json.JSONObject;

/**
 * Created by Raleigh.Luo on 17/8/10.
 */

public interface ITopicCommentDetailModel extends IBaseModel {
    void requestReplayList(int currentPage, int pageSize,VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener);
    void publishReplay(String topicId,String content,VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener);
    void like(VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener);
    void delete(VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener);
}
