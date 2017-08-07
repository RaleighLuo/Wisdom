package com.gkzxhn.wisdom.presenter;

import android.content.Context;

import com.android.volley.VolleyError;
import com.gkzxhn.wisdom.async.VolleyUtils;
import com.gkzxhn.wisdom.entity.TopicDetailEntity;
import com.gkzxhn.wisdom.entity.TopicEntity;
import com.gkzxhn.wisdom.model.ITopicDetailModel;
import com.gkzxhn.wisdom.model.impl.TopicDetailModel;
import com.gkzxhn.wisdom.view.ITopicDetailView;
import com.google.gson.Gson;
import com.starlight.mobile.android.lib.util.ConvertUtil;
import com.starlight.mobile.android.lib.util.JSONUtil;

import org.json.JSONObject;

/**
 * Created by Raleigh.Luo on 17/8/7.
 */

public class TopicDetailPresenter extends BasePresenter<ITopicDetailModel,ITopicDetailView> {
    public TopicDetailPresenter(Context context, ITopicDetailView view) {
        super(context, new TopicDetailModel(), view);
    }
    public void request(String id){
        getView().startRefreshAnim();
        mModel.request(id, new VolleyUtils.OnFinishedListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                int code= ConvertUtil.strToInt( JSONUtil.getJSONObjectStringValue(response,"code"));
                if(code==200){
                    TopicDetailEntity detailEntity=new Gson().fromJson(JSONUtil.getJSONObjectStringValue(response,"topic"), TopicDetailEntity.class);
                    getView().update(detailEntity);
                    getView().stopRefreshAnim();
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
}
