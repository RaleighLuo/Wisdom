package com.gkzxhn.wisdom.presenter;

import android.content.Context;

import com.android.volley.VolleyError;
import com.gkzxhn.wisdom.async.VolleyUtils;
import com.gkzxhn.wisdom.common.Constants;
import com.gkzxhn.wisdom.entity.TopicCommentDetailEntity;
import com.gkzxhn.wisdom.entity.TopicDetailEntity;
import com.gkzxhn.wisdom.entity.TopicReplayEntity;
import com.gkzxhn.wisdom.model.ITopicCommentDetailModel;
import com.gkzxhn.wisdom.model.impl.TopicCommentDetailModel;
import com.gkzxhn.wisdom.view.ITopicCommentDetailView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.starlight.mobile.android.lib.util.ConvertUtil;
import com.starlight.mobile.android.lib.util.JSONUtil;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Raleigh.Luo on 17/8/10.
 */

public class TopicCommentDetailPresenter extends BasePresenter<ITopicCommentDetailModel,ITopicCommentDetailView> {

    public TopicCommentDetailPresenter(Context context, ITopicCommentDetailView view,String commentId,String topicId) {
        super(context, new TopicCommentDetailModel(commentId,topicId), view);
    }
    public void request(){
        getView().startRefreshAnim();
        mModel.request(new VolleyUtils.OnFinishedListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                int code= ConvertUtil.strToInt(JSONUtil.getJSONObjectStringValue(response,"code"));
                if(code==200){
                    JSONObject result=JSONUtil.getJSONObject(response,"result");
                    TopicCommentDetailEntity entity=new Gson().fromJson(JSONUtil.getJSONObjectStringValue(result,"comment"),TopicCommentDetailEntity.class);
                    String comment=JSONUtil.getJSONObjectStringValue(result,"subcomments");
                    List<TopicReplayEntity> list=new Gson().fromJson(comment, new TypeToken<List<TopicReplayEntity>>() {}.getType());
                    getView().update(entity,list);
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
    public void publishReplay( String content){
        getView().showProgress();
        mModel.publishReplay( content, new VolleyUtils.OnFinishedListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                int code= ConvertUtil.strToInt( JSONUtil.getJSONObjectStringValue(response,"code"));
                if(code==200){
                    TopicReplayEntity entity=new Gson().fromJson(JSONUtil.getJSONObjectStringValue(response,"sub_comments"),TopicReplayEntity.class);
                    entity.setPortrait(getSharedPreferences().getString(Constants.USER_PORTRAIT,""));
                    entity.setNickname(getSharedPreferences().getString(Constants.USER_NICKNAME,""));
                    getView().commentSuccess(entity);
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
    public void like(){
        mModel.like( new VolleyUtils.OnFinishedListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                int code= ConvertUtil.strToInt(JSONUtil.getJSONObjectStringValue(response,"code"));
                if(code==200){
                    getView().likeFinish(true);
                }else{
                    getView().likeFinish(false);
                    getView().showToast(JSONUtil.getJSONObjectStringValue(response,"message"));
                }
                getView().dismissProgress();
            }

            @Override
            public void onFailed(VolleyError error) {
                getView().likeFinish(false);
                showErrors(error);
            }
        });

    }
    public void deleteReplay(final int position,String id){
        getView().showProgress();
        mModel.deleteReplay(id,new VolleyUtils.OnFinishedListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                int code= ConvertUtil.strToInt(JSONUtil.getJSONObjectStringValue(response,"code"));
                if(code==200){
                    getView().deleteSuccess(position);
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

    @Override
    protected void stopAnim() {
        super.stopAnim();
        getView().dismissProgress();
    }
}
