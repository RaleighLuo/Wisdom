package com.gkzxhn.wisdom.property.view;


import com.gkzxhn.wisdom.property.entity.TopicCommentEntity;
import com.gkzxhn.wisdom.property.entity.TopicDetailEntity;
import com.gkzxhn.wisdom.property.entity.TopicReplayEntity;

import java.util.List;

/**
 * Created by Raleigh.Luo on 17/8/7.
 */

public interface ITopicDetailView extends IBaseView{
    void update(TopicDetailEntity entity);
    void updateComment(List<TopicCommentEntity> comments);
    void loadComment(List<TopicCommentEntity> comments);
    void deleteTopicSuccess();
    void deleteCommentSuccess(int position, int subPosition);
    void showProgress();
    void dismissProgress();
    void likeFinished(boolean isSuccess);
    void commentLikeFinished(boolean isSuccess, String commentId, int position);
    void publishCommentSuccess(TopicCommentEntity comment);
    void publishReplaySuccess(int position, TopicReplayEntity comment);
}
