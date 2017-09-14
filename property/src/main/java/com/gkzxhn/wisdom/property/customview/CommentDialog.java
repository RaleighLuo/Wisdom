package com.gkzxhn.wisdom.property.customview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.gkzxhn.wisdom.property.R;

/**
 * Created by Raleigh.Luo on 17/8/3.
 * 话题回复
 */

public class CommentDialog  extends Dialog {
    private Context context;
    private EditText etContent;
    private TextView tvSend;
    private Handler handler=new Handler();
    private View.OnClickListener onClickListener;
    private int position=-1;//position=-1 话题评论

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public CommentDialog(@NonNull Context context) {
        super(context, R.style.custom_translucent_dialog_style);
        this.context = context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LayoutInflater.from(context).inflate(R.layout.comment_dialog_layout, null));
        init();
        measureWindow();
    }
    private void init(){
        etContent= (EditText) findViewById(R.id.comment_dialog_layout_et_content);
        tvSend= (TextView) findViewById(R.id.comment_dialog_layout_tv_send);
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!tvSend.isEnabled()&&etContent.getText().length()>0){
                    tvSend.setEnabled(true);
                }else if(tvSend.isEnabled()&&etContent.getText().length()==0){
                    tvSend.setEnabled(false);
                }
            }
        });

        findViewById(R.id.comment_dialog_layout_tv_send).setOnClickListener(onClickListener);
    }
    public void measureWindow(){
        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams params = dialogWindow.getAttributes();
        WindowManager m = dialogWindow.getWindowManager();

        Display d = m.getDefaultDisplay();
        params.width = d.getWidth();
//        params.height=d.getHeight()/2;
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setAttributes(params);
    }
    public void setHint(int resId){
        etContent.setHint(resId);
    }
    public void setHint(String  hint){
        etContent.setHint(hint);
    }

    public String getContent() {
        return etContent!=null?etContent.getText().toString().trim():"";
    }

    @Override
    public void show() {
        super.show();
        etContent.setText("");
        etContent.setFocusable(true);
        etContent.setFocusableInTouchMode(true);
        //请求获得焦点
        etContent.requestFocus();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //调用系统输入法
                InputMethodManager inputManager = (InputMethodManager) etContent
                        .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(etContent, 0);
            }
        },100);
    }
}
