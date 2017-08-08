package com.gkzxhn.wisdom.presenter;

import android.content.Context;

import com.android.volley.VolleyError;
import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.async.VolleyUtils;
import com.gkzxhn.wisdom.common.Constants;
import com.gkzxhn.wisdom.entity.RoomEntity;
import com.gkzxhn.wisdom.entity.UserEntity;
import com.gkzxhn.wisdom.model.IPersonInforModel;
import com.gkzxhn.wisdom.model.impl.PersonInforModel;
import com.gkzxhn.wisdom.util.UploadHelper;
import com.gkzxhn.wisdom.util.Utils;
import com.gkzxhn.wisdom.view.IPersonInforView;
import com.google.gson.Gson;
import com.starlight.mobile.android.lib.util.ConvertUtil;
import com.starlight.mobile.android.lib.util.HttpStatus;
import com.starlight.mobile.android.lib.util.JSONUtil;

import org.json.JSONObject;

import java.text.SimpleDateFormat;

/**
 * Created by Raleigh.Luo on 17/8/2.
 */

public class PersonInforPresenter extends BasePresenter<IPersonInforModel,IPersonInforView>{
    private UploadHelper mUploadHelper=new UploadHelper();
    public PersonInforPresenter(Context context, IPersonInforView view) {
        super(context, new PersonInforModel(), view);
    }
    public void requestUserInfor(){
        mModel.requestUserInfor(new VolleyUtils.OnFinishedListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                int code=ConvertUtil.strToInt(JSONUtil.getJSONObjectStringValue(response,"code"));
                if(code==200){
                    UserEntity entity=new Gson().fromJson(JSONUtil.getJSONObjectStringValue(response,"user"),UserEntity.class);
                    getView().update(entity);
                }
            }

            @Override
            public void onFailed(VolleyError error) {

            }
        });
    }
    public void updateUserInfor(final String nickname, final String portraitUrl){
        getView().startRefreshAnim();
        mModel.updateUserInfor(nickname, portraitUrl, new VolleyUtils.OnFinishedListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                int code= ConvertUtil.strToInt(JSONUtil.getJSONObjectStringValue(response,"code"));
                if(code== HttpStatus.SC_OK) {
                    if (portraitUrl != null) {
                        //保存图片
                        getSharedPreferences().edit().putString(Constants.USER_PORTRAIT, portraitUrl).commit();
                        getView().showToast(R.string.upload_portrait_success);
                    } else if (nickname != null) {
                        getSharedPreferences().edit().putString(Constants.USER_NICKNAME, nickname).commit();
                        getView().update(null);//借用
                    }
                }else{
                    getView().showToast(portraitUrl != null?R.string.upload_portrait_faild:R.string.update_nickname_faild);
                }
                getView().stopRefreshAnim();
            }

            @Override
            public void onFailed(VolleyError error) {
                showErrors(error);
            }
        });
    }
    public void uploadPortrait(String filePath){
        getView().startRefreshAnim();
        mUploadHelper.setUploadFinishListener(new UploadHelper.UploadFinishListener() {
            @Override
            public void onSuccess(String message, String filePath) {
                JSONObject json=JSONUtil.getJSONObject(message);
                String url=JSONUtil.getJSONObjectStringValue(json,"url");
                updateUserInfor(null,url);
            }

            @Override
            public void onFailed(String error) {
                getView().stopRefreshAnim();
                getView().showToast(R.string.upload_portrait_faild);
            }
        });
        //上传图片命名规则， userId_timestamp.jpg
        String uploadName=getSharedPreferences().getString(Constants.USER_ID,"")
                +"_"+Utils.getDateFromTimeInMillis(System.currentTimeMillis(),new SimpleDateFormat("yyyyMMddHHmmss"));
        mUploadHelper.upload(filePath, Constants.UPLOAD_PROFILE_URL,uploadName);
    }
}
