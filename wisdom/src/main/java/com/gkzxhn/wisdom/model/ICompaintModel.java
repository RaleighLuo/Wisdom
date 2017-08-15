package com.gkzxhn.wisdom.model;

import com.gkzxhn.wisdom.async.VolleyUtils;

/**
 * Created by Raleigh.Luo on 17/7/20.
 */

public interface ICompaintModel extends IBaseModel{
    public void publish(String content, VolleyUtils.OnFinishedListener<String> onFinishedListener);
}
