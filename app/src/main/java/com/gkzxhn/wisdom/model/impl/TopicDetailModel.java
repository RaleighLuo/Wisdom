package com.gkzxhn.wisdom.model.impl;

import com.gkzxhn.wisdom.async.VolleyUtils;
import com.gkzxhn.wisdom.common.Constants;
import com.gkzxhn.wisdom.model.ITopicDetailModel;

import org.json.JSONObject;

/**
 * Created by Raleigh.Luo on 17/8/7.
 */

public class TopicDetailModel extends BaseModel implements ITopicDetailModel {
    private String topicId=null;
    public TopicDetailModel(String topicId) {
        this.topicId = topicId;
    }

    @Override
    public void request(VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener) {
        try{
            String url=String.format("%s/%s/topics/%s", Constants.REQUEST_TOPIC_URL,preferences.getString(Constants.USER_RESIDENTIALAREASID,""),
                    topicId);
            volleyUtils.get(JSONObject.class,url,REQUEST_TAG,onFinishedListener);
        } catch (Exception authFailureError) {
            authFailureError.printStackTrace();
        }
    }
    @Override
    public void publishComments(String content, VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener) {
        try{
            String url=String.format("%s/%s/comments", Constants.REQUEST_TOPIC_OPERATE_URL, topicId);
            JSONObject params=new JSONObject();
            params.put("content",content);
            volleyUtils.post(url,new JSONObject().put("comment",params),REQUEST_TAG,onFinishedListener);
        } catch (Exception authFailureError) {
            authFailureError.printStackTrace();
        }
    }

    @Override
    public void requestComments(int currentPage, int pageSize, VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener) {
        try{
            String url=String.format("%s/%s/comments", Constants.REQUEST_TOPIC_OPERATE_URL, topicId);
            volleyUtils.get(JSONObject.class,url,REQUEST_TAG,onFinishedListener);
        } catch (Exception authFailureError) {
            authFailureError.printStackTrace();
        }
    }

    @Override
    public void delete( VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener) {
        try{
            String url=String.format("%s/%s/topics/%s", Constants.REQUEST_TOPIC_URL,preferences.getString(Constants.USER_RESIDENTIALAREASID,""),
                    topicId);
            volleyUtils.delete(url,new JSONObject(),REQUEST_TAG,onFinishedListener);
        } catch (Exception authFailureError) {
            authFailureError.printStackTrace();
        }
    }

    @Override
    public void deleteComment(String commentId, VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener) {
        try{
            String url=String.format("%s/%s/comments/%s", Constants.REQUEST_TOPIC_OPERATE_URL,topicId, commentId);
            volleyUtils.delete(url,new JSONObject(),REQUEST_TAG,onFinishedListener);
        } catch (Exception authFailureError) {
            authFailureError.printStackTrace();
        }
    }


}
