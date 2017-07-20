package com.gkzxhn.wisdom.presenter;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.android.volley.VolleyError;
import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.async.VolleyUtils;
import com.gkzxhn.wisdom.common.Constants;
import com.gkzxhn.wisdom.model.ILoginModel;
import com.gkzxhn.wisdom.model.impl.LoginModel;
import com.gkzxhn.wisdom.view.ILoginView;
import com.starlight.mobile.android.lib.util.ConvertUtil;
import com.starlight.mobile.android.lib.util.JSONUtil;

import org.json.JSONObject;

/**
 * Created by Raleigh.Luo on 17/7/17.
 */

public class LoginPresenter extends BasePresenter<ILoginModel ,ILoginView> {
    public LoginPresenter(Context context,  ILoginView view) {
        super(context, new LoginModel(), view);
    }
    public void requstCode(String phone){
        mModel.requestCode(phone, new VolleyUtils.OnFinishedListener<String>() {
            @Override
            public void onSuccess(String response) {
                JSONObject json= JSONUtil.getJSONObject(response);
                int code= ConvertUtil.strToInt(JSONUtil.getJSONObjectStringValue(json,"code"));
                if(code==200){
                    String verifyCode=JSONUtil.getJSONObjectStringValue(json,"sms_code");
                    showCode(verifyCode);
                }else{
                    getView().showToast(R.string.service_not_available);
                }


            }

            @Override
            public void onFailed(VolleyError error) {
                showErrors(error);
            }
        });

    }
    private void showCode(String code){
        final NotificationManager mNotificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        //FLAG_UPDATE_CURRENT：如果构建的PendingIntent已经存在，则替换它，常用
        final PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);
        final Notification notification = new NotificationCompat.Builder(getContext())
                .setContentTitle(getContext().getString(R.string.recive_verify_code))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(getContext().getString(R.string.verify_code_is)+code)
                .setDefaults(Notification.DEFAULT_ALL)//铃声、闪光、震动均系统默认。
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                .build();

        mNotificationManager.notify(1, notification);

    }
    public void login(String phone,String code){
       getView().startRefreshAnim();
        mModel.login(phone, code, new VolleyUtils.OnFinishedListener<String>() {
            @Override
            public void onSuccess(String response) {
                JSONObject json=JSONUtil.getJSONObject(response);
                int code= ConvertUtil.strToInt(JSONUtil.getJSONObjectStringValue(json,"code"));
                if(code==200){
                    String token=JSONUtil.getJSONObjectStringValue(json,"token");
                    getSharedPreferences().edit().putString(Constants.USER_TOKEN,token).commit();
                    getView().startRefreshAnim();
                    getView().onSuccess();
                }else{
                    getView().startRefreshAnim();
                    getView().showToast(R.string.service_not_available);
                }
            }

            @Override
            public void onFailed(VolleyError error) {
                showErrors(error);
            }
        });
    }
}
