package com.gkzxhn.wisdom.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.common.Constants;
import com.gkzxhn.wisdom.util.Utils;
import com.starlight.mobile.android.lib.activity.CutPhotoActivity;
import com.starlight.mobile.android.lib.album.AlbumActivity;
import com.starlight.mobile.android.lib.util.CommonHelper;
import com.starlight.mobile.android.lib.util.PermissionManager;
import com.starlight.mobile.android.lib.view.CusPhotoFromDialog;

import java.io.File;
import java.util.UUID;

/**
 * Created by Raleigh.Luo on 17/7/12.
 */

public class PersonInforActivity extends SuperActivity {
    private CusPhotoFromDialog optionsDialog;//选择相册或拍照对话框
    private File PhotoFile = null;
    private ImageView ivPortrait;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_infor_layout);
        initControls();
        init();
    }
    private void initControls(){
        ivPortrait= (ImageView) findViewById(R.id.person_infor_layout_iv_portrait);
    }
    private void init(){

    }
    public void onClickListener(View view){
        switch (view.getId()){
            case R.id.common_head_layout_iv_left://返回
                finish();
                break;
            case R.id.person_infor_layout_iv_portrait://头像
                //弹出选择相册 拍照对话框
                if (optionsDialog == null)
                    optionsDialog = Utils.buildPhotoDialog(this, onOptionsMenuItemClickListener);
                if (!optionsDialog.isShowing()) optionsDialog.show();

                break;
            case R.id.person_infor_layout_tv_sign://每日签到
                break;
            case R.id.person_infor_layout_tv_pay_record://缴费记录
                break;
            case R.id.person_infor_layout_tv_repair_record://报修记录
                break;
            case R.id.person_infor_layout_tv_contact_property://联系物业
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            switch (requestCode) {
                case Constants.SELECT_PHOTO_CODE://选择图片
                    if (resultCode == RESULT_OK) {
                        PhotoFile = new File(data.getStringExtra(AlbumActivity.EXTRAS));
                        cutPhoto(false);
                    }
                    break;
                case Constants.TAKE_PHOTO_CODE://拍照
                    if (resultCode == RESULT_OK) {
                        cutPhoto(true);
                    }
                    break;

                case Constants.RESIZE_REQUEST_CODE://裁剪图片
                    String imagePath = data.getStringExtra(AlbumActivity.EXTRAS);
                    Bitmap mBitmap = BitmapFactory.decodeFile(imagePath);
                    if (mBitmap != null) {
                        ivPortrait.setImageBitmap(mBitmap);
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private CusPhotoFromDialog.PhotoFromClickListener onOptionsMenuItemClickListener = new CusPhotoFromDialog.PhotoFromClickListener() {
        @Override
        public void back(View v) {
            try {
                if (v.getId() == R.id.cus_photo_from_dialog_layout_btn_album) {//Album
                    if (!PermissionManager.getInstance(PersonInforActivity.this).execute(PersonInforActivity.this, Constants.SELECT_PHOTO_CODE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE)){
                        Intent intent = new Intent(PersonInforActivity.this, AlbumActivity.class);
                        intent.setAction(AlbumActivity.EXTRAS_SIGLE_MODE);
                        startActivityForResult(intent, Constants.SELECT_PHOTO_CODE);
                    }
                } else if (v.getId() == R.id.cus_photo_from_dialog_layout_btn_take_photo) {//Take Photo
                    if (!PermissionManager.getInstance(PersonInforActivity.this).execute(PersonInforActivity.this, Constants.TAKE_PHOTO_CODE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA)) {
                        PhotoFile = new File(Constants.SD_PHOTO_PATH, UUID
                                .randomUUID().toString().replace("-", "")
                                + Constants.ATTACH_TYPE_IMAGE_POSTFIX_JPEG);
                        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        // Set image file save path
                        CommonHelper.creatDirToSDCard(Constants.SD_PHOTO_PATH);
                        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(PhotoFile));
                        startActivityForResult(intentCamera, Constants.TAKE_PHOTO_CODE);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

    /**裁剪图片
     * @param isTakePhoto
     */
    private void cutPhoto(boolean isTakePhoto) {
        if (isTakePhoto) {
            if (PhotoFile.exists()) {
                Uri uri = Uri.fromFile(PhotoFile);
                Intent intent = new Intent(this, CutPhotoActivity.class);
                intent.putExtra(AlbumActivity.EXTRAS, Constants.SD_ROOT_PHOTO_PATH);
                intent.setData(uri);
                startActivityForResult(intent, Constants.RESIZE_REQUEST_CODE);
            } else {
//                showToast(R.string.photo_failure);
            }
        } else {
            Uri uri = Uri.fromFile(PhotoFile);
            Intent intent = new Intent(this, CutPhotoActivity.class);
            intent.putExtra(AlbumActivity.EXTRAS, Constants.SD_ROOT_PHOTO_PATH);
            intent.setData(uri);
            startActivityForResult(intent, Constants.RESIZE_REQUEST_CODE);
        }

    }

    /**申请权限回调
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0&&grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if (requestCode == Constants.TAKE_PHOTO_CODE) {//权限允许后拍照
                PhotoFile = new File(Constants.SD_PHOTO_PATH, UUID
                        .randomUUID().toString().replace("-", "")
                        + Constants.ATTACH_TYPE_IMAGE_POSTFIX_JPEG);
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Set image file save path
                CommonHelper.creatDirToSDCard(Constants.SD_PHOTO_PATH);
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(PhotoFile));
                startActivityForResult(intentCamera, Constants.TAKE_PHOTO_CODE);
            }else if(requestCode == Constants.SELECT_PHOTO_CODE){//权限允许后选择相册
                Intent intent = new Intent(PersonInforActivity.this, AlbumActivity.class);
                intent.setAction(AlbumActivity.EXTRAS_SIGLE_MODE);
                startActivityForResult(intent, Constants.SELECT_PHOTO_CODE);
            }
        }
    }
}
