package com.gkzxhn.wisdom.property.customview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.gkzxhn.wisdom.property.R;

/**
 * Created by Raleigh.Luo on 17/8/9.
 */

public class CommentShowDialog extends Dialog {
    private View root;
    private View.OnClickListener onClickListener;
    private int position=-1;
    private TextView tvCopy,tvDel;
    private View vLine;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public CommentShowDialog(Context context) {
        super(context, R.style.checkConfirmDialog);
        root = LayoutInflater.from(getContext()).inflate(R.layout.comment_show_dialog_layout, null);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(root);
        tvCopy= (TextView) findViewById(R.id.comment_show_dialog_layout_tv_copy);
        tvDel= (TextView) findViewById(R.id.comment_show_dialog_layout_tv_delete);
        vLine=findViewById(R.id.comment_show_dialog_layout_v_line);
        tvCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if(onClickListener!=null)onClickListener.onClick(v);
            }
        });
        tvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if(onClickListener!=null)onClickListener.onClick(v);
            }
        });
    }
    public void showDelete(boolean isShow){
        if(isShow){
            tvDel.setVisibility(View.VISIBLE);
            vLine.setVisibility(View.VISIBLE);
        }else{
            tvDel.setVisibility(View.GONE);
            vLine.setVisibility(View.GONE);
        }

    }
}
