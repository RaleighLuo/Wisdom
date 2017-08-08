package com.gkzxhn.wisdom.presenter;

import android.content.Context;

import com.android.volley.VolleyError;
import com.gkzxhn.wisdom.async.VolleyUtils;
import com.gkzxhn.wisdom.entity.TopicCommentEntity;
import com.gkzxhn.wisdom.entity.TopicDetailEntity;
import com.gkzxhn.wisdom.entity.TopicEntity;
import com.gkzxhn.wisdom.model.ITopicDetailModel;
import com.gkzxhn.wisdom.model.impl.TopicDetailModel;
import com.gkzxhn.wisdom.view.ITopicDetailView;
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
    public TopicDetailPresenter(Context context, ITopicDetailView view,String topicId) {
        super(context, new TopicDetailModel(topicId), view);
    }
    public void request(){
        getView().startRefreshAnim();
        mModel.request(new VolleyUtils.OnFinishedListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                int code= ConvertUtil.strToInt( JSONUtil.getJSONObjectStringValue(response,"code"));
                if(code==200){
                    TopicDetailEntity detailEntity=new Gson().fromJson(JSONUtil.getJSONObjectStringValue(response,"topic"), TopicDetailEntity.class);
                    getView().update(detailEntity);
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

    public void publishComments(String content){
        getView().startRefreshAnim();
        mModel.publishComments(content, new VolleyUtils.OnFinishedListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                int code= ConvertUtil.strToInt( JSONUtil.getJSONObjectStringValue(response,"code"));
                if(code==200){

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
    public void requestComments(final boolean isRefresh){
        if(isRefresh){
            currentPage=FIRST_PAGE;
            getView().startRefreshAnim();
        }
        mModel.requestComments(currentPage, PAGE_SIZE, new VolleyUtils.OnFinishedListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                int code= ConvertUtil.strToInt(JSONUtil.getJSONObjectStringValue(response,"code"));
                if(code==200){
                    List<TopicCommentEntity> list=new Gson().fromJson(JSONUtil.getJSONObjectStringValue(response,"comments"), new TypeToken<List<TopicCommentEntity>>() {}.getType());
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
    public void delete(){
        getView().startRefreshAnim();
        mModel.delete(new VolleyUtils.OnFinishedListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                int code= ConvertUtil.strToInt( JSONUtil.getJSONObjectStringValue(response,"code"));
                if(code==200){

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
    public void deleteComment(String commentId){
        getView().startRefreshAnim();
        mModel.deleteComment(commentId, new VolleyUtils.OnFinishedListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                int code= ConvertUtil.strToInt( JSONUtil.getJSONObjectStringValue(response,"code"));
                if(code==200){

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

}
