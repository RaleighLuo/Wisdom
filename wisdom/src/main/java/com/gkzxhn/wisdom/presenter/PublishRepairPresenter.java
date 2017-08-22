package com.gkzxhn.wisdom.presenter;

import android.content.Context;
import android.util.Log;

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
 * Created by Raleigh.Luo on 17/8/22.
 */

public class PublishRepairPresenter extends BasePresenter<IPublishModel,IPublishView>  {
    private UploadHelper mUploadHelper=new UploadHelper();
    public PublishRepairPresenter(Context context, IPublishView view) {
        super(context, new PublishModel(), view);
    }
    public void publish(String repairType,String content, List<String> imagUrls){
        mModel.publishRepair(repairType,content, imagUrls, new VolleyUtils.OnFinishedListener<JSONObject>() {
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
    public void uploadPhoto(String filePath){
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
        int random=(int)(Math.random()*100);//0-100的随机数
        //上传图片命名规则， 随机数_timestamp.jpg
        String uploadName=getSharedPreferences().getString(Constants.USER_ID,"")
                +"_"+ Utils.getDateFromTimeInMillis(System.currentTimeMillis(),new SimpleDateFormat("yyyyMMddHHmmss"))+random;
        mUploadHelper.upload(filePath, Constants.UPLOAD_REPAIRES_URL,uploadName);
    }

}
