package com.gkzxhn.wisdom.presenter;

import android.content.Context;

import com.android.volley.VolleyError;
import com.gkzxhn.wisdom.async.VolleyUtils;
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

    public TopicCommentDetailPresenter(Context context, ITopicCommentDetailView view,String commentId) {
        super(context, new TopicCommentDetailModel(commentId), view);
    }
    public void request(){
        getView().startRefreshAnim();
        mModel.request(new VolleyUtils.OnFinishedListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                int code= ConvertUtil.strToInt( JSONUtil.getJSONObjectStringValue(response,"code"));
                if(code==200){
                    TopicCommentDetailEntity detailEntity=new Gson().fromJson(JSONUtil.getJSONObjectStringValue(response,"comment"), TopicCommentDetailEntity.class);
                    getView().update(detailEntity);
                    requestReplayList(true,detailEntity.getTopicId());
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
    public void publishReplay(String topicId, String content){

    }
    public void requestReplayList(final boolean isRefresh,String topicId){
        if(isRefresh){
            currentPage=FIRST_PAGE;
            getView().startRefreshAnim();
        }
        mModel.requestReplayList(topicId,currentPage, PAGE_SIZE, new VolleyUtils.OnFinishedListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                int code= ConvertUtil.strToInt(JSONUtil.getJSONObjectStringValue(response,"code"));
                if(code==200){
                    String comment=JSONUtil.getJSONObjectStringValue(response,"comments");
                    List<TopicReplayEntity> list=new Gson().fromJson(comment, new TypeToken<List<TopicReplayEntity>>() {}.getType());
                    if (list != null && list.size() > 0) currentPage++;
                    if(isRefresh)getView().updateItems(list);
                    else getView().loadItems(list);
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
    public void like(String topicId){
        mModel.like(topicId, new VolleyUtils.OnFinishedListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                int code= ConvertUtil.strToInt(JSONUtil.getJSONObjectStringValue(response,"code"));
                if(code==200){
                    getView().likeFinish(true);
                }else{
                    getView().likeFinish(false);
                    getView().showToast(JSONUtil.getJSONObjectStringValue(response,"message"));
                }
            }

            @Override
            public void onFailed(VolleyError error) {
                getView().likeFinish(false);
                showErrors(error);
            }
        });

    }
    public void delete(final int position, String topicId){
        getView().showProgress();
        mModel.delete(topicId, new VolleyUtils.OnFinishedListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                int code= ConvertUtil.strToInt(JSONUtil.getJSONObjectStringValue(response,"code"));
                if(code==200){
                    getView().deleteSuccess(position);
                }else{
                    getView().showToast(JSONUtil.getJSONObjectStringValue(response,"message"));
                }
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
