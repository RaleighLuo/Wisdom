package com.gkzxhn.wisdom.presenter;

import android.content.Context;

import com.gkzxhn.wisdom.model.ITopicCommentDetailModel;
import com.gkzxhn.wisdom.model.impl.TopicCommentDetailModel;
import com.gkzxhn.wisdom.view.ITopicCommentDetailView;

/**
 * Created by Raleigh.Luo on 17/8/10.
 */

public class TopicCommentDetailPresenter extends BasePresenter<ITopicCommentDetailModel,ITopicCommentDetailView> {

    public TopicCommentDetailPresenter(Context context, ITopicCommentDetailView view,String commentId) {
        super(context, new TopicCommentDetailModel(commentId), view);
    }
    public void publishReplay(String topicId, String content){

    }
    public void requestReplayList(boolean isRefresh,String commentId){

    }
    public void like(String id){

    }
    public void delete(String commentId){

    }
}
