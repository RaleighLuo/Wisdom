package com.gkzxhn.wisdom.view;

import com.gkzxhn.wisdom.entity.TopicCommentEntity;
import com.gkzxhn.wisdom.entity.TopicDetailEntity;

import java.util.List;

/**
 * Created by Raleigh.Luo on 17/8/7.
 */

public interface ITopicDetailView extends IBaseView{
    void update(TopicDetailEntity entity);
    void updateComment(List<TopicCommentEntity> comments);
    void loadComment(List<TopicCommentEntity> comments);
    void deleteTopicSuccess();
    void deleteCommentSuccess(int position,int subPosition);
    void showProgress();
    void dismissProgress();
    void likeFinished(boolean isSuccess);
    void commentLikeFinished(boolean isSuccess,String commentId,int position);
    void publishCommentSuccess(TopicCommentEntity comment);
}
