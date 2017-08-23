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
    private final String topicId;

    public TopicCommentDetailModel(String commentId, String topicId) {
        this.commentId = commentId;
        this.topicId = topicId;
    }

    /**请求评论详情
     * @param onFinishedListener
     */
    @Override
    public void request(VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener) {
        try{
            String url=String.format("%s/%s/comments/%s", Constants.REQUEST_TOPIC_OPERATE_URL, topicId,commentId);
            volleyUtils.get(JSONObject.class,url,REQUEST_TAG,onFinishedListener);
        } catch (Exception authFailureError) {
            authFailureError.printStackTrace();
        }
    }

    /** 发布回复
     * @param content
     * @param onFinishedListener
     */
    @Override
    public void publishReplay(String content, VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener) {
        try{
            String url=String.format("%s/%s/comments/%s/subcomments", Constants.REQUEST_TOPIC_OPERATE_URL, topicId,commentId);
            JSONObject params=new JSONObject();
            params.put("content",content);
            volleyUtils.post(url,new JSONObject().put("comment",params),REQUEST_TAG,onFinishedListener);
        } catch (Exception authFailureError) {
            authFailureError.printStackTrace();
        }
    }

    /**评论点赞
     * @param onFinishedListener
     */
    @Override
    public void like(VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener) {
        try{
            String url=String.format("%s/%s/topics/%s/comments/%s/likes", Constants.REQUEST_BASE_URL,preferences.getString(Constants.USER_RESIDENTIALAREASID,""),
                    topicId,commentId);
            volleyUtils.post(url,new JSONObject(),REQUEST_TAG,onFinishedListener);
        } catch (Exception authFailureError) {
            authFailureError.printStackTrace();
        }
    }

    /**删除回复
     * @param id
     * @param onFinishedListener
     */
    @Override
    public void deleteReplay(String id,VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener) {
        try{
            String url=String.format("%s/%s/comments/%s", Constants.REQUEST_TOPIC_OPERATE_URL,topicId,id);
            volleyUtils.delete(url,new JSONObject(),REQUEST_TAG,onFinishedListener);
        } catch (Exception authFailureError) {
            authFailureError.printStackTrace();
        }
    }




}
