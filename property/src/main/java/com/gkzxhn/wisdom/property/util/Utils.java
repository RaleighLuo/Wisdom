package com.gkzxhn.wisdom.property.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.gkzxhn.wisdom.property.R;
import com.gkzxhn.wisdom.property.common.GKApplication;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.starlight.mobile.android.lib.view.CusPhotoFromDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    /**网络是否连接
     * @return
     */
    public static boolean isConnected() {
        ConnectivityManager conn = (ConnectivityManager) GKApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        return (info != null && info.isConnected());
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

    /**数组字符串转list
     * @param array
     * @return
     */
    public static List<String> arrayToList(String[] array){
        List<String> list=new ArrayList<>();
        for(String str:array){
            list.add(str);
        }
        return list;
    }

    /**毫秒数转时间日期 年月日
     * @param timeInMillis
     * @return
     */
    public static String getDateFromTimeInMillis(long timeInMillis) {
        String result="";
        if(timeInMillis>0) {
            try {
                Context context=GKApplication.getInstance().getApplicationContext();
                SimpleDateFormat df=new SimpleDateFormat(String.format("yyyy%sMM%sdd%s",context.getString(R.string.year),
                        context.getString(R.string.month),context.getString(R.string.day)));
                Date date = new Date(timeInMillis);
                //英文格式时间格式化
                result = df.format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**毫秒数转时间日期 年月日时分
     * @param timeInMillis
     * @return
     */
    public static String getTimeFromTimeInMillis(long timeInMillis) {
        String result="";
        if(timeInMillis>0) {
            try {
                Context context=GKApplication.getInstance().getApplicationContext();
                SimpleDateFormat df=new SimpleDateFormat(String.format("yyyy%sMM%sdd%s HH:mm",context.getString(R.string.year),
                        context.getString(R.string.month),context.getString(R.string.day)));
                Date date = new Date(timeInMillis);
                //英文格式时间格式化
                result = df.format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**秒数转时间日期 自定义时间格式
     * @param timeInMillis
     * @param df
     * @return
     */
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

    /**图片下载 设置默认加载图片参数
     * @param defualtImgRes
     * @return
     */
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

    /**时区时间转字符串
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

    /**浏览次数单位转化
     * @param time
     * @return
     */
    public static String getViewedTime(int time){
        Context context=GKApplication.getInstance().getApplicationContext();
        String result="";
        int billion=time/100000000;
        if(billion>=1){//亿次
            result=String.format("%s%s",billion==1?String.valueOf(billion):String.format("%.1f",(float)time/(float)100000000), context.getString(R.string.billion));
        }else if(time/10000>=1){//万次
            result=String.format("%s%s",time/10000==1?String.valueOf(time/10000):String.format("%.1f",(float)time/(float)10000), context.getString(R.string.ten_thousand));
        }else{
            result=String.valueOf(time);
        }
        return result;
    }
}
