package com.gkzxhn.wisdom.util;

import android.content.Context;

import com.gkzxhn.wisdom.R;
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
}
