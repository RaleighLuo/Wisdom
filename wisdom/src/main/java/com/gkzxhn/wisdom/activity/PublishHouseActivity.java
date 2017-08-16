package com.gkzxhn.wisdom.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.adapter.OnItemClickListener;
import com.gkzxhn.wisdom.adapter.PublishHouseAdapter;
import com.gkzxhn.wisdom.common.Constants;
import com.gkzxhn.wisdom.customview.CusTextItem;
import com.gkzxhn.wisdom.customview.DecorateDialog;
import com.gkzxhn.wisdom.customview.FloorDialog;
import com.gkzxhn.wisdom.customview.HouseAreaDialog;
import com.gkzxhn.wisdom.customview.HouseTypeDialog;
import com.gkzxhn.wisdom.customview.MoneyDialog;
import com.gkzxhn.wisdom.util.Utils;
import com.starlight.mobile.android.lib.album.AlbumActivity;
import com.starlight.mobile.android.lib.util.CommonHelper;
import com.starlight.mobile.android.lib.util.PermissionManager;
import com.starlight.mobile.android.lib.view.CusPhotoFromDialog;
import com.starlight.mobile.android.lib.view.FullyGridLayoutManager;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Created by Raleigh.Luo on 17/7/28.
 */

public class PublishHouseActivity extends SuperActivity {
    private HouseTypeDialog mHouseTypeDialog;
    private DecorateDialog mDecorateDialog,mRentWayDialog;
    private FloorDialog mFloorDialog;
    private HouseAreaDialog mHouseAreaDialog;
    private MoneyDialog mMoneyDialog;
    private CusTextItem ctiHouseType,ctiDecorate,ctiRentWay,ctiFloor,ctiHouseArea,ctiMoney;
    private int TAB;
    private EditText etCommitteName,etContent,etContact,etPhone;
    private RecyclerView mRecyclerView;
    private PublishHouseAdapter adapter;
    private CusPhotoFromDialog optionsDialog;//选择相册或拍照对话框
    private File mPhotoFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_house_layout);
        initControls();
        init();
    }
    private void initControls(){
        mRecyclerView= (RecyclerView) findViewById(R.id.publish_house_layout_rv_images);
        etCommitteName= (EditText) findViewById(R.id.publish_house_layout_et_committe_name);
        etContent= (EditText) findViewById(R.id.publish_house_layout_et_house_description);
        etContact= (EditText) findViewById(R.id.publish_house_layout_et_contact);
        etPhone= (EditText) findViewById(R.id.publish_house_layout_et_contact_phone);
        ctiFloor= (CusTextItem) findViewById(R.id.publish_house_layout_cti_floor);
        ctiRentWay= (CusTextItem) findViewById(R.id.publish_house_layout_cti_method);
        ctiDecorate= (CusTextItem) findViewById(R.id.publish_house_layout_cti_decorate);
        ctiHouseType= (CusTextItem) findViewById(R.id.publish_house_layout_cti_house_type);
        ctiHouseArea= (CusTextItem) findViewById(R.id.publish_house_layout_cti_house_area);
        ctiMoney= (CusTextItem) findViewById(R.id.publish_house_layout_cti_rent_money);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mHouseTypeDialog.measureWindow();
        mDecorateDialog.measureWindow();
        mRentWayDialog.measureWindow();
        mFloorDialog.measureWindow();
        mHouseAreaDialog.measureWindow();
        mMoneyDialog.measureWindow();
    }

    private void init(){
        mRecyclerView.setHasFixedSize(true);
        FullyGridLayoutManager i=new FullyGridLayoutManager(this,4);
        i.setReverseLayout(true);
        mRecyclerView.setLayoutManager(new FullyGridLayoutManager(this,4));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter=new PublishHouseAdapter(this);
        adapter.setOnItemClickListener(onItemClickListener);
        mRecyclerView.setAdapter(adapter);
        TAB=getIntent().getIntExtra(Constants.EXTRA_TAB,Constants.HOUSE_LEASE_TAB);
        mHouseTypeDialog=new HouseTypeDialog(this);
        mHouseTypeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mHouseTypeDialog!=null&&mHouseTypeDialog.isShowing())mHouseTypeDialog.dismiss();
                ctiHouseType.getTvContent().setText(mHouseTypeDialog.getSelectedAll());
            }
        });
        mDecorateDialog=new DecorateDialog(this,R.array.decorate_type);
        mDecorateDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDecorateDialog!=null&&mDecorateDialog.isShowing())mDecorateDialog.dismiss();
                ctiDecorate.getTvContent().setText(mDecorateDialog.getSelectedAll());
            }
        });
        mFloorDialog =new FloorDialog(this);
        mFloorDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mFloorDialog.getCurrentFloor().length()==0){
                    showToast(R.string.please_input_belong_floor);
                }else if(mFloorDialog.getAllFloor().length()==0){
                    showToast(R.string.please_input_all_floor);
                }else{
                    if (mFloorDialog != null && mFloorDialog.isShowing())
                        mFloorDialog.dismiss();
                    ctiFloor.getTvContent().setText(mFloorDialog.getContent());
                }
            }
        });
        mHouseAreaDialog =new HouseAreaDialog(this);
        mHouseAreaDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mHouseAreaDialog.getContent().length()==0){
                    showToast(R.string.please_input_house_area);
                }else{
                    if (mHouseAreaDialog != null && mHouseAreaDialog.isShowing())
                        mHouseAreaDialog.dismiss();
                    ctiHouseArea.getTvContent().setText(mHouseAreaDialog.getContent());
                }
            }
        });
        mMoneyDialog=new MoneyDialog(this,TAB);
        mMoneyDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMoneyDialog.getContent().length()==0){
                    showToast(TAB==Constants.HOUSE_LEASE_TAB?R.string.please_input_house_lease_money:R.string.please_input_house_sale_money);
                }else{
                    if (mMoneyDialog != null && mMoneyDialog.isShowing())
                        mMoneyDialog.dismiss();
                    ctiMoney.getTvContent().setText(mMoneyDialog.getContent());
                }
            }
        });
        if(TAB==Constants.HOUSE_LEASE_TAB) {//房屋出租发布
            mRentWayDialog = new DecorateDialog(this, R.array.rent_ways);
            mRentWayDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mRentWayDialog != null && mRentWayDialog.isShowing())
                        mRentWayDialog.dismiss();
                    ctiRentWay.getTvContent().setText(mRentWayDialog.getSelectedAll());
                }
            });
        }else{
            ctiMoney.getTvTitle().setText(R.string.sale_money);
            ctiRentWay.getTvContent().setText(R.string.all_sale);
            ctiRentWay.setOnClickListener(null);
        }
    }
    public void onClickListener(View view){
        CommonHelper.clapseSoftInputMethod(this);
        switch (view.getId()){
            case R.id.common_head_layout_iv_left:
                finish();
                break;
            case R.id.publish_house_layout_cti_floor://楼层
                if(!mFloorDialog.isShowing()) mFloorDialog.show();
                break;
            case R.id.publish_house_layout_cti_house_area://面积
                if(!mHouseAreaDialog.isShowing()) mHouseAreaDialog.show();
                break;
            case R.id.publish_house_layout_cti_house_type://户型
                if(!mHouseTypeDialog.isShowing())mHouseTypeDialog.show();
                break;
            case R.id.publish_house_layout_cti_method://方式出租
                if(TAB==Constants.HOUSE_LEASE_TAB&&mRentWayDialog!=null&&!mRentWayDialog.isShowing())mRentWayDialog.show();
                break;
            case R.id.publish_house_layout_cti_rent_money://租金
                if(!mMoneyDialog.isShowing()) mMoneyDialog.show();
                break;
            case R.id.publish_house_layout_cti_decorate://装修
                if(!mDecorateDialog.isShowing())mDecorateDialog.show();
                break;
        }
    }
    private OnItemClickListener onItemClickListener=new OnItemClickListener() {
        @Override
        public void onClickListener(View convertView, int position) {
            switch (convertView.getId()){
                case R.id.publish_house_item_layout_iv_image://查看大图
                    if(adapter.checkTakePhoto(position)){
                        if(adapter.getPhotoCount()<adapter.getItemCount()) {
                            //弹出选择相册 拍照对话框
                            if (optionsDialog == null)
                                optionsDialog = Utils.buildPhotoDialog(PublishHouseActivity.this, onOptionsMenuItemClickListener);
                            if (!optionsDialog.isShowing()) optionsDialog.show();
                        }
                    }else {
                        Intent intent = new Intent(PublishHouseActivity.this, TakePhotoPreviewActivity.class);
                        intent.putExtra(Constants.EXTRAS, (Serializable) adapter.getLocalPaths());
                        intent.putExtra(Constants.EXTRA_POSITION, position);
                        startActivityForResult(intent, Constants.EXTRA_CODE);
                    }
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(mFloorDialog.isShowing()) mFloorDialog.dismiss();
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
                    if (!PermissionManager.getInstance(PublishHouseActivity.this).execute(PublishHouseActivity.this, Constants.SELECT_PHOTO_CODE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE)){
                        Intent intent = new Intent(PublishHouseActivity.this, AlbumActivity.class);
                        intent.putExtra(AlbumActivity.EXTRA_IMAGE_SELECT_COUNT,adapter.getMaxPhotoCount()-adapter.getPhotoCount());
                        startActivityForResult(intent, Constants.SELECT_PHOTO_CODE);
                    }
                } else if (v.getId() == R.id.cus_photo_from_dialog_layout_btn_take_photo) {//Take Photo
                    if (!PermissionManager.getInstance(PublishHouseActivity.this).execute(PublishHouseActivity.this, Constants.TAKE_PHOTO_CODE,
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
                    Intent intent = new Intent(PublishHouseActivity.this, AlbumActivity.class);
                    intent.putExtra(AlbumActivity.EXTRA_IMAGE_SELECT_COUNT,adapter.getMaxPhotoCount()-adapter.getPhotoCount());
                    startActivityForResult(intent, Constants.SELECT_PHOTO_CODE);
                    break;
            }
        }
    }
}
