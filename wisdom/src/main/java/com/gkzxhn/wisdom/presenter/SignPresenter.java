package com.gkzxhn.wisdom.presenter;

import android.content.Context;

import com.android.volley.VolleyError;
import com.gkzxhn.wisdom.async.VolleyUtils;
import com.gkzxhn.wisdom.model.ISignModel;
import com.gkzxhn.wisdom.model.impl.SignModel;
import com.gkzxhn.wisdom.view.ISignView;
import com.starlight.mobile.android.lib.util.ConvertUtil;
import com.starlight.mobile.android.lib.util.JSONUtil;

import org.json.JSONObject;

/**
 * Created by Raleigh.Luo on 17/8/7.
 */

public class SignPresenter extends BasePresenter<ISignModel,ISignView> {
    public SignPresenter(Context context, ISignView view) {
        super(context, new SignModel(), view);
    }
    public void sign(){
        getView().startRefreshAnim();
        mModel.sign(new VolleyUtils.OnFinishedListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                int code= ConvertUtil.strToInt(JSONUtil.getJSONObjectStringValue(response,"code"));
                if(code==200){
                    getView().stopRefreshAnim();
                    getView().onSuccess();
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
