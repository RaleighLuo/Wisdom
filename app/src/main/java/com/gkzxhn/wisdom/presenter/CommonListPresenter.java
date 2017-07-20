package com.gkzxhn.wisdom.presenter;

import android.content.Context;

import com.android.volley.VolleyError;
import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.async.VolleyUtils;
import com.gkzxhn.wisdom.model.ICommonListModel;
import com.gkzxhn.wisdom.model.impl.CommonListModel;
import com.gkzxhn.wisdom.view.ICommonListView;
import com.starlight.mobile.android.lib.util.ConvertUtil;
import com.starlight.mobile.android.lib.util.JSONUtil;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Raleigh.Luo on 17/7/20.
 */

public class CommonListPresenter<T> extends BasePresenter<ICommonListModel,ICommonListView<T>> {
    public CommonListPresenter(Context context, ICommonListView<T> view,int TAB) {
        super(context, new CommonListModel(TAB), view);
    }
    public void request(final boolean isRefresh){
        if(isRefresh){
            currentPage=FIRST_PAGE;
            getView().startRefreshAnim();
        }
        mModel.request(currentPage, PAGE_SIZE, new VolleyUtils.OnFinishedListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    int code= ConvertUtil.strToInt(JSONUtil.getJSONObjectStringValue(response,"code"));
                    if(code==200){
                        List<T> mData = null;
                        if (mData != null && mData.size() > 0) currentPage++;
                    } else {
                        getView().stopRefreshAnim();
                        getView().showToast(JSONUtil.getJSONObjectStringValue(response,"msg"));
                    }
                } catch (Exception e) {
                    getView().stopRefreshAnim();
                    getView().showToast(R.string.unexpected_errors);
                }
            }

            @Override
            public void onFailed(VolleyError error) {
                showErrors(error);
            }
        });
    }
}
