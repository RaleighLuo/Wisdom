package com.gkzxhn.wisdom.presenter;

import android.content.Context;

import com.android.volley.VolleyError;
import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.async.VolleyUtils;
import com.gkzxhn.wisdom.common.Constants;
import com.gkzxhn.wisdom.model.IPublishModel;
import com.gkzxhn.wisdom.model.impl.PublishModel;
import com.gkzxhn.wisdom.util.UploadHelper;
import com.gkzxhn.wisdom.util.Utils;
import com.gkzxhn.wisdom.view.IPublishView;
import com.starlight.mobile.android.lib.util.ConvertUtil;
import com.starlight.mobile.android.lib.util.JSONUtil;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Raleigh.Luo on 17/8/4.
 */

public class PublishTopicPresenter extends BasePresenter<IPublishModel,IPublishView> {
    private UploadHelper mUploadHelper=new UploadHelper();
    public PublishTopicPresenter(Context context, IPublishView view) {
        super(context, new PublishModel(), view);
    }
    public void publish(String content, List<String> imagUrls){
        getView().startRefreshAnim();
        mModel.publishTopic(content, imagUrls, new VolleyUtils.OnFinishedListener<JSONObject>() {
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
                getView().stopRefreshAnim();
            }
        });
    }
    public void uploadPhoto(String filePath,int position){
        getView().startRefreshAnim();
        mUploadHelper.setUploadFinishListener(new UploadHelper.UploadFinishListener() {
            @Override
            public void onSuccess(String message, String filePath) {
                JSONObject json= JSONUtil.getJSONObject(message);
                int code= ConvertUtil.strToInt(JSONUtil.getJSONObjectStringValue(json,"code"));
                if(code==200) {
                    String url = JSONUtil.getJSONObjectStringValue(json, "url");
                    getView().uploadPhotoSuccess(url);
                }else{
                    getView().stopRefreshAnim();
                    getView().showToast(R.string.upload_image_faild);
                }
            }

            @Override
            public void onFailed(String error) {
                getView().stopRefreshAnim();
                getView().showToast(R.string.upload_image_faild);
            }
        });
        //上传图片命名规则， userId_图片编号_timestamp.jpg
        String uploadName=getSharedPreferences().getString(Constants.USER_ID,"")
                +"_"+(position+1)+"_"+ Utils.getDateFromTimeInMillis(System.currentTimeMillis(),new SimpleDateFormat("yyyyMMddHHmmss"));
        mUploadHelper.upload(filePath, Constants.UPLOAD_TOPICS_URL,uploadName);
    }
    @Override
    public void onDestory() {
        super.onDestory();
        mUploadHelper.onStop();
    }
}
