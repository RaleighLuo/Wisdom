package com.starlight.mobile.android.lib.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Raleigh.Luo on 17/4/7.
 *  android 6.0 Permission权限兼容的封装
 * 1.同时申请多个权限
 * PermissionManager.getInstance(getApplicationContext()).execute(this, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
 * 2.请求单个，显示对话框的方式
 * PermissionManager.getInstance(getApplicationContext()).executeDialog(this, Manifest.permission.RECORD_AUDIO,
 * PermissionManager.getInstance(getApplicationContext()).new Builder(this)
 * .setMessage("应用需要获取您的录音权限，是否授权？")
 * .setTitle(getString(R.string.app_name))
 * .setIcon(R.mipmap.ic_launcher)
 * .setOk("OK")
 * .setCancel("CANCEL"));
 *
 *   @Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
super.onRequestPermissionsResult(requestCode, permissions, grantResults);
PermissionManager.getInstance(getApplicationContext()).onRequestPermissionsResult(requestCode, permissions, grantResults);
}
 */

public class PermissionManager {

    private static class Holder {
        public static final PermissionManager MANAGER = new PermissionManager();
    }

    private static Context sContext;

    private PermissionManager() {
    }

    /**
     * 单例获取对象
     */
    public static PermissionManager getInstance(Context context) {
        sContext = context.getApplicationContext();
        return Holder.MANAGER;
    }

    /**
     * 执行请求多个权限
     */
    public boolean execute(@NonNull Activity activity, int requestCode,String... permissions) {
        boolean result=true;
        List<String> lists = new ArrayList<>();
        for (String permission : permissions) {
            if (!isGranted(permission) && !isRevoked(permission)) {
                lists.add(permission);
            }
        }
        if (lists.size() == 0){
            result=false;
        }else{
            String[] p = new String[lists.size()];
            requestPerissins(activity, requestCode,lists.toArray(p));
        }
        return  result;
    }

    /**
     * 执行请求一个权限
     */
    public boolean execute(@NonNull Activity activity, int requestCode,String permission) {
        boolean result=false;
        if (!isGranted(permission) && !isRevoked(permission)) {
            result=true;
            requestPerissins(activity,requestCode, permission);
        }
        return  result;
    }



    /**
     * 判断是不是授权
     */
    private boolean isGranted(@NonNull String permission) {
        return isM() && ContextCompat.checkSelfPermission(sContext, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 判断是不是在包中申明
     */
    @TargetApi(Build.VERSION_CODES.M)
    private boolean isRevoked(@NonNull String permission) {
        return isM() && sContext.getPackageManager().isPermissionRevokedByPolicy(permission, sContext.getPackageName());
    }

    /**
     * 判断是不是M及以上版本
     */
    private boolean isM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 请求的方法
     */
    private void requestPerissins(@NonNull Activity activity,int requestCode, String... permissions) {
        if (isM()) {
            ActivityCompat.requestPermissions(activity, permissions, requestCode);
        }
    }

//    /**
//     * 是不是需要显示权限请求的关系，可以设置对话框。采用内部类Builder的建造者模式，解耦合方式
//     * <p>
//     * 这个方法可能不适合同时申请多个权限
//     */
//    public boolean shouldShowRequestPermissionRationale(@NonNull Activity activity, String permission, Builder builder) {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
//            builder.showDialog(permission);
//            return true;
//        }
//        return false;
//    }


}
