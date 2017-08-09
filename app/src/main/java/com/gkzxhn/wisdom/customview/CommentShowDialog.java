package com.gkzxhn.wisdom.customview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.gkzxhn.wisdom.R;

/**
 * Created by Raleigh.Luo on 17/8/9.
 */

public class CommentShowDialog extends Dialog {
    private View root;
    public CommentShowDialog(Context context) {
        super(context, R.style.checkConfirmDialog);
        root = LayoutInflater.from(getContext()).inflate(R.layout.check_confirm_dialog_layout, null);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(root);
    }
}
