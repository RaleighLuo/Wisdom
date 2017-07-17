package com.gkzxhn.wisdom.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.common.GKApplication;
import com.starlight.mobile.android.lib.view.CusPhotoFromDialog;

/**
 * Created by Raleigh.Luo on 17/7/12.
 */

public class Utils {
    /**
     * 显示图片来源对话框，相册/拍照
     *
     * @param context
     * @param photoFromClickListener
     */
    public static CusPhotoFromDialog buildPhotoDialog(Context context,
                                                      CusPhotoFromDialog.PhotoFromClickListener photoFromClickListener) {
        CusPhotoFromDialog dialog = new CusPhotoFromDialog(context);
        dialog.setBtnClickListener(photoFromClickListener);
        dialog.setBtnTitle(context.getResources()
                .getString(R.string.take_photo), context.getResources()
                .getString(R.string.album), context.getResources()
                .getString(R.string.cancel));
        return dialog;
    }
    public static boolean isConnected() {
        try {
            //获得网络连接服务
            ConnectivityManager connManager = (ConnectivityManager) GKApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
            // State state = connManager.getActiveNetworkInfo().getState();
            // 获取WIFI网络连接状态
            NetworkInfo.State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
            // 判断是否正在使用WIFI网络
            if (NetworkInfo.State.CONNECTED == state) {
                return true;
            } else {
                state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
                // 判断是否正在使用GPRS网络
                return NetworkInfo.State.CONNECTED == state;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 验证手机号码的合法性
     * @param mobiles 手机号码
     * @return 返回是否为手机号码
     */
    public static boolean isPhoneNumber(String mobiles){
        String telRegex = "[1][3456789]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }
}
