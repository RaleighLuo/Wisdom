package com.gkzxhn.wisdom.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.common.Constants;
import com.gkzxhn.wisdom.util.Utils;
import com.starlight.mobile.android.lib.album.AlbumActivity;
import com.starlight.mobile.android.lib.util.CommonHelper;
import com.starlight.mobile.android.lib.util.PermissionManager;
import com.starlight.mobile.android.lib.view.CusPhotoFromDialog;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Raleigh.Luo on 17/7/11.
 * 发布报修
 */

public class PublishRepairActivity extends SuperActivity{
    private EditText etContent;
    private Button btnSubmit;
    private File PhotoFile = null;
    private CusPhotoFromDialog optionsDialog;//选择相册或拍照对话框
    private ImageView ivFirstImage,ivSecondImage,ivThirdImage;
    private List<String> mImagePaths=new ArrayList<>();
    private int mCurrentOprateId =0;//当前操作的Id
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_repair_layout);
        initControls();
        init();
    }
    private void initControls(){
        etContent= (EditText) findViewById(R.id.publish_repair_layout_et_content);
        btnSubmit= (Button) findViewById(R.id.publish_repair_layout_btn_submit);
        ivFirstImage= (ImageView) findViewById(R.id.publish_repair_layout_iv_first);
        ivSecondImage= (ImageView) findViewById(R.id.publish_repair_layout_iv_second);
        ivThirdImage= (ImageView) findViewById(R.id.publish_repair_layout_iv_third);
    }
    private void init(){
        ivFirstImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //长按删除图片
                if(mImagePaths.size()>0)mImagePaths.remove(0);
                updateImages();
                return true;
            }
        });
        ivSecondImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //长按删除图片
                if(mImagePaths.size()>1)mImagePaths.remove(1);
                updateImages();
                return true;
            }
        });
        ivThirdImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //长按删除图片
                if(mImagePaths.size()>2)mImagePaths.remove(2);
                updateImages();
                return true;
            }
        });
    }
    private void updateImages(){
        switch (mImagePaths.size()){
            case 0:
                ivFirstImage.setImageResource(R.mipmap.repair_take_photo);
                if(!ivFirstImage.isShown())ivFirstImage.setVisibility(View.VISIBLE);
                ivSecondImage.setVisibility(View.INVISIBLE);
                ivThirdImage.setVisibility(View.INVISIBLE);
                break;
            case 1:
                ivFirstImage.setImageBitmap(BitmapFactory.decodeFile(mImagePaths.get(0)));
                ivSecondImage.setImageResource(R.mipmap.repair_take_photo);
                if(!ivFirstImage.isShown())ivFirstImage.setVisibility(View.VISIBLE);
                if(!ivSecondImage.isShown())ivSecondImage.setVisibility(View.VISIBLE);
                ivThirdImage.setVisibility(View.INVISIBLE);
                break;
            case 2:
                ivFirstImage.setImageBitmap(BitmapFactory.decodeFile(mImagePaths.get(0)));
                ivSecondImage.setImageBitmap(BitmapFactory.decodeFile(mImagePaths.get(1)));
                ivThirdImage.setImageResource(R.mipmap.repair_take_photo);
                if(!ivFirstImage.isShown())ivFirstImage.setVisibility(View.VISIBLE);
                if(!ivSecondImage.isShown())ivSecondImage.setVisibility(View.VISIBLE);
                if(!ivThirdImage.isShown())ivThirdImage.setVisibility(View.VISIBLE);
                break;
            case 3:
                ivFirstImage.setImageBitmap(BitmapFactory.decodeFile(mImagePaths.get(0)));
                ivSecondImage.setImageBitmap(BitmapFactory.decodeFile(mImagePaths.get(1)));
                ivThirdImage.setImageBitmap(BitmapFactory.decodeFile(mImagePaths.get(2)));
                if(!ivFirstImage.isShown())ivFirstImage.setVisibility(View.VISIBLE);
                if(!ivSecondImage.isShown())ivSecondImage.setVisibility(View.VISIBLE);
                if(!ivThirdImage.isShown())ivThirdImage.setVisibility(View.VISIBLE);
                break;
        }
        for(int i=0;i<mImagePaths.size();i++){
            String path=mImagePaths.get(i);
            switch (i){
                case 0:
                    ivFirstImage.setImageBitmap(BitmapFactory.decodeFile(path));
                    ivFirstImage.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    ivSecondImage.setImageBitmap(BitmapFactory.decodeFile(path));
                    ivSecondImage.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    ivThirdImage.setImageBitmap(BitmapFactory.decodeFile(path));
                    ivThirdImage.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }
    public void onClickListener(View view) {
        CommonHelper.clapseSoftInputMethod(this);
        switch (view.getId()) {
            case R.id.common_head_layout_iv_left:
                finish();
                break;
            case R.id.publish_repair_layout_iv_first:
                mCurrentOprateId =view.getId();
                showTakePhoto(mImagePaths.size()>0,0);
                break;
            case R.id.publish_repair_layout_iv_second:
                mCurrentOprateId =view.getId();
                showTakePhoto(mImagePaths.size()>1,1);
                break;
            case R.id.publish_repair_layout_iv_third:
                mCurrentOprateId =view.getId();
                showTakePhoto(mImagePaths.size()>2,2);
                break;
        }
    }
    private void showTakePhoto(boolean hasPhoto,int position){
        if(hasPhoto){
            Intent intent=new Intent(this,TakePhotoPreviewActivity.class);
            intent.putExtra(Constants.EXTRAS, (Serializable) mImagePaths);
            intent.putExtra(Constants.EXTRA_POSITION, position);
            startActivityForResult(intent,Constants.EXTRA_CODE);
        }else{
           //弹出选择相册 拍照对话框
            if (optionsDialog == null)
                optionsDialog = Utils.buildPhotoDialog(this, onOptionsMenuItemClickListener);
            if (!optionsDialog.isShowing()) optionsDialog.show();

        }

    }

    @Override
    public void onBackPressed() {
        CommonHelper.clapseSoftInputMethod(this);
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (resultCode == RESULT_OK) {
                switch (requestCode) {
                    case Constants.SELECT_PHOTO_CODE://选择图片
                        List<String> mList= (List<String>) data.getSerializableExtra(AlbumActivity.EXTRAS);
                        if(mList!=null&&mList.size()>0) {
                            mImagePaths.addAll(mList);
                            updateImages();
                        }
                        break;
                    case Constants.TAKE_PHOTO_CODE://拍照
                        switch (mCurrentOprateId){
                            case R.id.publish_repair_layout_iv_first:
                                mImagePaths.add(0,PhotoFile.getAbsolutePath());
                                ivFirstImage.setImageBitmap(BitmapFactory.decodeFile(PhotoFile.getAbsolutePath()));
                                ivSecondImage.setImageResource(R.mipmap.repair_take_photo);
                                if(!ivSecondImage.isShown())ivSecondImage.setVisibility(View.VISIBLE);
                                break;
                            case R.id.publish_repair_layout_iv_second:
                                mImagePaths.add(1,PhotoFile.getAbsolutePath());
                                ivSecondImage.setImageBitmap(BitmapFactory.decodeFile(PhotoFile.getAbsolutePath()));
                                ivThirdImage.setImageResource(R.mipmap.repair_take_photo);
                                if(!ivThirdImage.isShown())ivThirdImage.setVisibility(View.VISIBLE);
                                break;
                            case R.id.publish_repair_layout_iv_third:
                                mImagePaths.add(2,PhotoFile.getAbsolutePath());
                                ivThirdImage.setImageBitmap(BitmapFactory.decodeFile(PhotoFile.getAbsolutePath()));
                                break;
                        }
                        break;
                    case Constants.EXTRA_CODE:
                        List<String> list=(List<String>) data.getSerializableExtra(Constants.EXTRAS);
                        if(list==null){
                            this.mImagePaths.clear();
                            updateImages();
                        }else if(list.size()!=mImagePaths.size()) {
                            this.mImagePaths = list;
                            updateImages();
                        }
                        break;
                }
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
                    if (!PermissionManager.getInstance(PublishRepairActivity.this).execute(PublishRepairActivity.this, Constants.SELECT_PHOTO_CODE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE)){
                        Intent intent = new Intent(PublishRepairActivity.this, AlbumActivity.class);
                        intent.putExtra(AlbumActivity.EXTRA_IMAGE_SELECT_COUNT,3-mImagePaths.size());
                        startActivityForResult(intent, Constants.SELECT_PHOTO_CODE);
                    }
                } else if (v.getId() == R.id.cus_photo_from_dialog_layout_btn_take_photo) {//Take Photo
                    if (!PermissionManager.getInstance(PublishRepairActivity.this).execute(PublishRepairActivity.this, Constants.TAKE_PHOTO_CODE,
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

    /**申请权限回调
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0&&grantResults[0] == PackageManager.PERMISSION_GRANTED){
            switch (requestCode){
                case Constants.TAKE_PHOTO_CODE:
                    PhotoFile = new File(Constants.SD_PHOTO_PATH, UUID
                            .randomUUID().toString().replace("-", "")
                            + Constants.ATTACH_TYPE_IMAGE_POSTFIX_JPEG);
                    Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    // Set image file save path
                    CommonHelper.creatDirToSDCard(Constants.SD_PHOTO_PATH);
                    intentCamera.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(PhotoFile));
                    startActivityForResult(intentCamera, Constants.TAKE_PHOTO_CODE);
                    break;
                case Constants.SELECT_PHOTO_CODE:
                    Intent intent = new Intent(PublishRepairActivity.this, AlbumActivity.class);
                    intent.putExtra(AlbumActivity.EXTRA_IMAGE_SELECT_COUNT,3-mImagePaths.size());
                    startActivityForResult(intent, Constants.SELECT_PHOTO_CODE);
                    break;
            }
        }
    }
}
