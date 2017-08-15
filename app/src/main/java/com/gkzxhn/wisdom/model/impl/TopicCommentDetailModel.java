package com.gkzxhn.wisdom.model.impl;

import com.gkzxhn.wisdom.async.VolleyUtils;
import com.gkzxhn.wisdom.common.Constants;
import com.gkzxhn.wisdom.model.ITopicCommentDetailModel;

import org.json.JSONObject;

/**
 * Created by Raleigh.Luo on 17/8/10.
 */

public class TopicCommentDetailModel extends BaseModel implements ITopicCommentDetailModel {

    private final String commentId;

    public TopicCommentDetailModel(String commentId) {
        this.commentId = commentId;
    }

    @Override
    public void requestReplayList( int currentPage, int pageSize, VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener) {
        try{
            String url=String.format("%s/%s/comments?page=%s&limit=%s", Constants.REQUEST_TOPIC_OPERATE_URL, commentId,currentPage,pageSize);
            volleyUtils.get(JSONObject.class,url,REQUEST_TAG,onFinishedListener);
        } catch (Exception authFailureError) {
            authFailureError.printStackTrace();
        }
    }

    @Override
    public void publishReplay(String topicId, String content, VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener) {
        try{
            String url=String.format("%s/%s/comments/%s/subcomments", Constants.REQUEST_TOPIC_OPERATE_URL, topicId,commentId);
            JSONObject params=new JSONObject();
            params.put("content",content);
            volleyUtils.post(url,new JSONObject().put("comment",params),REQUEST_TAG,onFinishedListener);
        } catch (Exception authFailureError) {
            authFailureError.printStackTrace();
        }
    }

    @Override
    public void like(VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener) {

    }

    @Override
    public void delete(VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener) {

    }


}
