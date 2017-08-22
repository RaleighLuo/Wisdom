package com.gkzxhn.wisdom.view;

/**
 * Created by Raleigh.Luo on 17/8/4.
 */

public interface IPublishView extends IBaseView{
    void onSuccess();
    void uploadPhotoSuccess(String url);
}
