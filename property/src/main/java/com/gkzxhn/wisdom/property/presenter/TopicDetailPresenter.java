package com.gkzxhn.wisdom.property.presenter;

import android.content.Context;

import com.android.volley.VolleyError;
import com.gkzxhn.wisdom.property.R;
import com.gkzxhn.wisdom.property.async.VolleyUtils;
import com.gkzxhn.wisdom.property.common.Constants;
import com.gkzxhn.wisdom.property.entity.TopicCommentEntity;
import com.gkzxhn.wisdom.property.entity.TopicDetailEntity;
import com.gkzxhn.wisdom.property.entity.TopicReplayEntity;
import com.gkzxhn.wisdom.property.model.ITopicDetailModel;
import com.gkzxhn.wisdom.property.model.impl.TopicDetailModel;
import com.gkzxhn.wisdom.property.view.ITopicDetailView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.starlight.mobile.android.lib.util.ConvertUtil;
import com.starlight.mobile.android.lib.util.JSONUtil;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Raleigh.Luo on 17/8/7.
 */

public class TopicDetailPresenter extends BasePresenter<ITopicDetailModel,ITopicDetailView> {
    public TopicDetailPresenter(Context context, ITopicDetailView view, String topicId) {
        super(context, new TopicDetailModel(topicId), view);
    }

    /**
     * 请求话题详情
     */
    public void request(){
        getView().startRefreshAnim();
        mModel.request(new VolleyUtils.OnFinishedListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                int code= ConvertUtil.strToInt( JSONUtil.getJSONObjectStringValue(response,"code"));
                if(code==200){
                    List<TopicDetailEntity> result = new Gson().fromJson(JSONUtil.getJSONObjectStringValue(response,"topic"), new TypeToken<List<TopicDetailEntity>>() {}.getType());
                    getView().update(result!=null&&result.size()>0?result.get(0):null);
                    requestComments(true);
                }else{
                    getView().stopRefreshAnim();
                    getView().showToast(JSONUtil.getJSONObjectStringValue(response,"message"));
                }

            }

            @Override
            public void onFailed(VolleyError error) {
                showErrors(error);
            }
        });
    }

    /**发布评论
     * @param content
     */
    public void publishComments(final String content){
        getView().showProgress();
        mModel.publishComment(content, new VolleyUtils.OnFinishedListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                int code= ConvertUtil.strToInt( JSONUtil.getJSONObjectStringValue(response,"code"));
                if(code==200){
                    //TODO
                    TopicCommentEntity entity=new Gson().fromJson(JSONUtil.getJSONObjectStringValue(response,"comment"),TopicCommentEntity.class);
                    entity.setLikeable(true);
                    entity.setLikesCount(0);
                    entity.setPortrait(getSharedPreferences().getString(Constants.USER_PORTRAIT,""));
                    entity.setNickname(getSharedPreferences().getString(Constants.USER_NICKNAME,""));
                    getView().publishCommentSuccess(entity);
                }else{
                    getView().showToast(JSONUtil.getJSONObjectStringValue(response,"message"));
                }
                getView().dismissProgress();
            }

            @Override
            public void onFailed(VolleyError error) {
                showErrors(error);
            }
        });

    }
    public void publishReplay(final int position,String commentId,String content){
        getView().showProgress();
        mModel.publishReplay(commentId,content, new VolleyUtils.OnFinishedListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                int code= ConvertUtil.strToInt( JSONUtil.getJSONObjectStringValue(response,"code"));
                if(code==200){
                    TopicReplayEntity entity=new Gson().fromJson(JSONUtil.getJSONObjectStringValue(response,"sub_comments"),TopicReplayEntity.class);
                    entity.setPortrait(getSharedPreferences().getString(Constants.USER_PORTRAIT,""));
                    entity.setNickname(getSharedPreferences().getString(Constants.USER_NICKNAME,""));
                    getView().publishReplaySuccess(position,entity);
                }else{
                    getView().showToast(JSONUtil.getJSONObjectStringValue(response,"message"));
                }
                getView().dismissProgress();
            }

            @Override
            public void onFailed(VolleyError error) {
                showErrors(error);
            }
        });
    }

    /**请求评论列表
     * @param isRefresh
     */
    public void requestComments(final boolean isRefresh){
        if(isRefresh){
            currentPage=FIRST_PAGE;
            getView().startRefreshAnim();
        }
        mModel.requestComments(currentPage, PAGE_SIZE, new VolleyUtils.OnFinishedListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                int code= ConvertUtil.strToInt(JSONUtil.getJSONObjectStringValue(response,"code"));
                if(code==200){//TODO
                    String comment=JSONUtil.getJSONObjectStringValue(response,"comments");
                    List<TopicCommentEntity> list=new Gson().fromJson(comment, new TypeToken<List<TopicCommentEntity>>() {}.getType());
                    if (list != null && list.size() > 0) currentPage++;
                    if(isRefresh)getView().updateComment(list);
                    else getView().loadComment(list);
                }else{
                    getView().showToast(JSONUtil.getJSONObjectStringValue(response,"message"));
                }
                getView().stopRefreshAnim();
            }

            @Override
            public void onFailed(VolleyError error) {
                showErrors(error);
            }
        });
    }

    /**
     * 删除话题
     */
    public void delete(){
        getView().showProgress();
        mModel.delete(new VolleyUtils.OnFinishedListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                int code= ConvertUtil.strToInt( JSONUtil.getJSONObjectStringValue(response,"code"));
                if(code==200){
                    getView().deleteTopicSuccess();
                }else{
                    getView().showToast(JSONUtil.getJSONObjectStringValue(response,"message"));
                }
                getView().dismissProgress();
            }

            @Override
            public void onFailed(VolleyError error) {
                showErrors(error);
            }
        });
    }

    /**删除评论
     * @param commentId
     */
    public void deleteComment(String commentId, final int position, final int subPosition){
        getView().showProgress();
        mModel.deleteComment(commentId, new VolleyUtils.OnFinishedListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                int code= ConvertUtil.strToInt( JSONUtil.getJSONObjectStringValue(response,"code"));
                if(code==200){
                    getView().deleteCommentSuccess(position,subPosition);
                }else{
                    getView().showToast(JSONUtil.getJSONObjectStringValue(response,"message"));
                }
                getView().dismissProgress();
            }

            @Override
            public void onFailed(VolleyError error) {
                showErrors(error);
            }
        });
    }

    /**
     * 点赞／取消点赞 －话题
     */
    public void requestLike(){
        mModel.requestLike(new VolleyUtils.OnFinishedListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                int code = ConvertUtil.strToInt(JSONUtil.getJSONObjectStringValue(response, "code"));
                if (code == 200) {
//                    int likes=ConvertUtil.strToInt(JSONUtil.getJSONObjectStringValue(response, "likes"));
                    getView().likeFinished(true);
                } else {
                    getView().likeFinished(false);
                    getView().showToast(JSONUtil.getJSONObjectStringValue(response, "message"));
                }
            }
            @Override
            public void onFailed(VolleyError error) {
                getView().likeFinished(false);
                getView().showToast(R.string.unexpected_errors);
            }
        });
    }

    /**点赞／取消点赞－评论
     * @param commentId
     * @param position
     */
    public void requestCommentLike(final String commentId,final int position){
        mModel.requestCommentLike(commentId,new VolleyUtils.OnFinishedListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                int code = ConvertUtil.strToInt(JSONUtil.getJSONObjectStringValue(response, "code"));
                if (code == 200) {
//                    int isLike=ConvertUtil.strToInt(JSONUtil.getJSONObjectStringValue(response, "likes"));
                    getView().commentLikeFinished(true,commentId,position);
                } else {
                    getView().commentLikeFinished(false,commentId,position);
                    getView().showToast(JSONUtil.getJSONObjectStringValue(response, "message"));
                }
            }
            @Override
            public void onFailed(VolleyError error) {
                getView().commentLikeFinished(false,commentId,position);
                getView().showToast(R.string.unexpected_errors);
            }
        });
    }

    @Override
    protected void stopAnim() {
        super.stopAnim();
        getView().dismissProgress();
    }
}
