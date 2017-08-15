package com.gkzxhn.wisdom.view;

import com.gkzxhn.wisdom.entity.TopicCommentEntity;

import java.util.List;

/**
 * Created by Raleigh.Luo on 17/8/10.
 */

public interface ITopicCommentDetailView extends IBaseView {
    void updateItems(List<TopicCommentEntity> mDatas);
    void loadItems(List<TopicCommentEntity> mDatas);
    void likeFinish(boolean isSuccess);
    void commentSuccess(TopicCommentEntity entity);
    void replayLikeFinish(int position,boolean isSuccess);
}
