package com.gkzxhn.wisdom.view;

import com.gkzxhn.wisdom.async.VolleyUtils;
import com.gkzxhn.wisdom.entity.PayRecordEntity;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Raleigh.Luo on 17/7/20.
 */

public interface ICommonListView<T> extends IBaseView {
    public void updateItems(List<T> datas, VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener);//更新
    public void loadItems(List<T> datas, VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener);//加载
}
