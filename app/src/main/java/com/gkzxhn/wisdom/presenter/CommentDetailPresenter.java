package com.gkzxhn.wisdom.presenter;

import android.content.Context;

import com.gkzxhn.wisdom.model.ICommentDetailModel;
import com.gkzxhn.wisdom.model.impl.CommentDetailModel;
import com.gkzxhn.wisdom.view.ICommentDetailView;

/**
 * Created by Raleigh.Luo on 17/8/10.
 */

public class CommentDetailPresenter extends BasePresenter<ICommentDetailModel,ICommentDetailView> {

    public CommentDetailPresenter(Context context, ICommentDetailView view) {
        super(context, new CommentDetailModel(), view);
    }
    public void like(String id){

    }
}
