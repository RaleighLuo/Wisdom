package com.gkzxhn.wisdom.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.common.GKApplication;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.starlight.mobile.android.lib.view.CusPhotoFromDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    public static List<String> arrayToList(String[] array){
        List<String> list=new ArrayList<>();
        for(String str:array){
            list.add(str);
        }
        return list;
    }
    public static String getDateFromTimeInMillis(long timeInMillis,SimpleDateFormat df) {
        String result="";
        if(timeInMillis>0) {
            try {
                Date date = new Date(timeInMillis);
                //英文格式时间格式化
                result = df.format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    public static DisplayImageOptions getOptions(int defualtImgRes) {
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(defualtImgRes)//默认加载的图片
                .showImageForEmptyUri(defualtImgRes)//下载地址不存在

                .showImageOnFail(defualtImgRes).cacheInMemory(false).cacheOnDisk(true)//加载失败的图
                //	.displayer(new RoundedBitmapDisplayer(0))  设置圆角，设置后不能使用loadimage方法，项目并不需要圆角
                .bitmapConfig(Bitmap.Config.RGB_565)    //设置图片的质量
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)    //设置图片的缩放类型，该方法可以有效减少内存的占用
                .build();
    }

    /**
     * Convert String date to Date
     * @param date date for String format
     * @return Date object
     */
    public static String getFormateTime(String date,SimpleDateFormat formate) {
        String result = null;
        //2017-08-07T14:52:35.000+08:00
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz");
            //去掉时区部分的冒号
            date = date.replaceAll(":[^:]*$", "00");
            Date dateTime = df.parse(date);
            result = formate.format(dateTime);
        } catch (Exception e) {
            try {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                Date dateTime = df.parse(date);
                result = formate.format(dateTime);
            } catch (Exception e2) {
            }
        }
        return result;
    }

}
