package com.gkzxhn.wisdom.presenter;

import android.content.Context;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.common.Constants;
import com.gkzxhn.wisdom.model.IPersonInforModel;
import com.gkzxhn.wisdom.model.impl.PersonInforModel;
import com.gkzxhn.wisdom.util.UploadHelper;
import com.gkzxhn.wisdom.util.Utils;
import com.gkzxhn.wisdom.view.IPersonInforView;
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
    public void uploadPortrait(String filePath){
        getView().startRefreshAnim();
        mUploadHelper.setUploadFinishListener(new UploadHelper.UploadFinishListener() {
            @Override
            public void onSuccess(String message, String filePath) {
                getView().stopRefreshAnim();
                JSONObject json=JSONUtil.getJSONObject(message);
                String url=JSONUtil.getJSONObjectStringValue(json,"url");
                //保存图片
                getSharedPreferences().edit().putString(Constants.USER_PORTRAIT,url).commit();
                getView().showToast(R.string.upload_portrait_success);
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
