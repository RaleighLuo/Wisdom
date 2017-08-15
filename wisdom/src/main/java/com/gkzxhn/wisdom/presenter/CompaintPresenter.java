package com.gkzxhn.wisdom.presenter;

import android.content.Context;

import com.android.volley.VolleyError;
import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.async.VolleyUtils;
import com.gkzxhn.wisdom.model.IBaseModel;
import com.gkzxhn.wisdom.model.ICompaintModel;
import com.gkzxhn.wisdom.model.impl.ComplaintModel;
import com.gkzxhn.wisdom.view.IBaseView;
import com.gkzxhn.wisdom.view.IComplaintView;
import com.starlight.mobile.android.lib.util.ConvertUtil;
import com.starlight.mobile.android.lib.util.JSONUtil;

import org.json.JSONObject;

/**
 * Created by Raleigh.Luo on 17/7/20.
 */

public class CompaintPresenter extends BasePresenter<ICompaintModel,IComplaintView>{

    public CompaintPresenter(Context context, IComplaintView view) {
        super(context, new ComplaintModel(), view);
    }
    public void publish(String content){
//        getView().startRefreshAnim();
        mModel.publish(content, new VolleyUtils.OnFinishedListener<String>() {
            @Override
            public void onSuccess(String response) {
                JSONObject json= JSONUtil.getJSONObject(response);
                int code= ConvertUtil.strToInt(JSONUtil.getJSONObjectStringValue(json,"code"));
                getView().stopRefreshAnim();
                if(code==200){
                    getView().onSuccess();
                }else{
                    getView().showToast(JSONUtil.getJSONObjectStringValue(json,"message"));
                }
            }

            @Override
            public void onFailed(VolleyError error) {
                showErrors(error);
            }
        });

    }
}
