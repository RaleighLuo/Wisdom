package com.gkzxhn.wisdom.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.adapter.OnItemClickListener;
import com.gkzxhn.wisdom.adapter.PublishTopicAdapter;
import com.gkzxhn.wisdom.common.Constants;
import com.gkzxhn.wisdom.customview.CheckConfirmDialog;
import com.gkzxhn.wisdom.presenter.PublishTopicPresenter;
import com.gkzxhn.wisdom.util.Utils;
import com.gkzxhn.wisdom.view.IPublishTopicView;
import com.starlight.mobile.android.lib.album.AlbumActivity;
import com.starlight.mobile.android.lib.util.CommonHelper;
import com.starlight.mobile.android.lib.util.PermissionManager;
import com.starlight.mobile.android.lib.view.CusPhotoFromDialog;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Created by Raleigh.Luo on 17/8/2.
 */

public class PublishTopicActivity extends SuperActivity implements IPublishTopicView{
    private ImageView ivAdd;
    private RecyclerView mRecyclerView;
    private PublishTopicAdapter adapter;
    private PublishTopicPresenter mPresenter;
    private ProgressDialog mProgress;
    private CusPhotoFromDialog optionsDialog;//选择相册或拍照对话框
    private File mPhotoFile;
    private int currentPosition=0;
    private EditText etContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_topic_layout);
        initControls();
        init();
    }
    private void initControls(){
        mRecyclerView= (RecyclerView) findViewById(R.id.publish_topic_layout_recycleview);
        ivAdd= (ImageView) findViewById(R.id.publish_topic_layout_iv_add);
        etContent= (EditText) findViewById(R.id.publish_topic_layout_et_content);
    }
    private void init(){
        mProgress = ProgressDialog.show(this, null, getString(R.string.please_waiting));
        stopRefreshAnim();
        mPresenter=new PublishTopicPresenter(this,this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter=new PublishTopicAdapter(this);
        adapter.setOnItemClickListener(onItemClickListener);
        mRecyclerView.setAdapter(adapter);
    }
    public void onClickListener(View view){
        CommonHelper.clapseSoftInputMethod(this);
        switch (view.getId()){
            case R.id.common_head_layout_iv_left:
                finish();
                break;
            case R.id.common_head_layout_tv_right://发布
                if(etContent.getText().toString().trim().length()>0) {
                    if (currentPosition != 0) currentPosition = adapter.getUrls().size() - 1;
                    publish();
                }else{
                    showToast(R.string.topic_publish_hint);
                }
                break;
            case R.id.publish_topic_layout_iv_add://添加图片
                if(adapter.getPhotoCount()<adapter.getItemCount()) {
                    //弹出选择相册 拍照对话框
                    if (optionsDialog == null)
                        optionsDialog = Utils.buildPhotoDialog(this, onOptionsMenuItemClickListener);
                    if (!optionsDialog.isShowing()) optionsDialog.show();
                }
                break;
        }
    }
    private OnItemClickListener onItemClickListener=new OnItemClickListener() {
        @Override
        public void onClickListener(View convertView, int position) {
            switch (convertView.getId()){
                case R.id.publish_topic_item_layout_iv_image://查看大图
                    Intent intent=new Intent(PublishTopicActivity.this,TakePhotoPreviewActivity.class);
                    intent.putExtra(Constants.EXTRAS, (Serializable) adapter.getLocalPaths());
                    intent.putExtra(Constants.EXTRA_POSITION, position);
                    startActivityForResult(intent,Constants.EXTRA_CODE);
                    break;
                case R.id.publish_topic_item_layout_tv_del://删除
                    adapter.removeItem(position);
                    break;
            }
        }
    };

    @Override
    public void startRefreshAnim() {
        if(mProgress!=null&&!mProgress.isShowing())mProgress.show();
    }

    @Override
    public void stopRefreshAnim() {
        if(mProgress!=null&&mProgress.isShowing())mProgress.dismiss();
    }


    @Override
    public void onSuccess() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void uploadPhotoSuccess(String url) {
        adapter.addUrl(url,currentPosition);
        currentPosition++;
        publish();
    }

    /**
     * 发布
     */
    private void publish(){
        if(currentPosition<adapter.getPhotoCount())mPresenter.uploadPhoto(adapter.getLocalPath(currentPosition));
        else mPresenter.publish(etContent.getText().toString().trim(),adapter.getUrls());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            switch (requestCode) {
                case Constants.SELECT_PHOTO_CODE://选择图片
                    if (resultCode == RESULT_OK) {
                        List<String> mList= (List<String>) data.getSerializableExtra(AlbumActivity.EXTRAS);
                        adapter.addItems(mList);
                    }
                    break;
                case Constants.TAKE_PHOTO_CODE://拍照
                    if (resultCode == RESULT_OK) {
                        adapter.addItem(mPhotoFile.getAbsolutePath());
                    }
                    break;
                case Constants.EXTRA_CODE:
                    List<String> list=(List<String>) data.getSerializableExtra(Constants.EXTRAS);
                    if(list==null){
                        adapter.clear();
                    }else if(list.size()!=adapter.getPhotoCount()) {
                        adapter.updateItems(list);
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
                    if (!PermissionManager.getInstance(PublishTopicActivity.this).execute(PublishTopicActivity.this, Constants.SELECT_PHOTO_CODE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE)){
                        Intent intent = new Intent(PublishTopicActivity.this, AlbumActivity.class);
                        intent.putExtra(AlbumActivity.EXTRA_IMAGE_SELECT_COUNT,adapter.getItemCount()-adapter.getPhotoCount());
                        startActivityForResult(intent, Constants.SELECT_PHOTO_CODE);
                    }
                } else if (v.getId() == R.id.cus_photo_from_dialog_layout_btn_take_photo) {//Take Photo
                    if (!PermissionManager.getInstance(PublishTopicActivity.this).execute(PublishTopicActivity.this, Constants.TAKE_PHOTO_CODE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA)) {
                        mPhotoFile = new File(Constants.SD_PHOTO_PATH, UUID
                                .randomUUID().toString().replace("-", "")
                                + Constants.ATTACH_TYPE_IMAGE_POSTFIX_JPEG);
                        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        // Set image file save path
                        CommonHelper.creatDirToSDCard(Constants.SD_PHOTO_PATH);
                        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(mPhotoFile));
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
                    mPhotoFile = new File(Constants.SD_PHOTO_PATH, UUID
                            .randomUUID().toString().replace("-", "")
                            + Constants.ATTACH_TYPE_IMAGE_POSTFIX_JPEG);
                    Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    // Set image file save path
                    CommonHelper.creatDirToSDCard(Constants.SD_PHOTO_PATH);
                    intentCamera.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(mPhotoFile));
                    startActivityForResult(intentCamera, Constants.TAKE_PHOTO_CODE);
                    break;
                case Constants.SELECT_PHOTO_CODE:
                    Intent intent = new Intent(PublishTopicActivity.this, AlbumActivity.class);
                    intent.setAction(AlbumActivity.EXTRAS_SIGLE_MODE);
                    startActivityForResult(intent, Constants.SELECT_PHOTO_CODE);
                    break;

            }
        }
    }

    @Override
    public void onBackPressed() {
        if(!adapter.recoveryDelPosition()) {
            super.onBackPressed();
        }
    }
}
