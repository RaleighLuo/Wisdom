package com.gkzxhn.wisdom.view;

import com.gkzxhn.wisdom.entity.TopicCommentDetailEntity;
import com.gkzxhn.wisdom.entity.TopicCommentEntity;
import com.gkzxhn.wisdom.entity.TopicReplayEntity;

import java.util.List;

/**
 * Created by Raleigh.Luo on 17/8/10.
 */

public interface ITopicCommentDetailView extends IBaseView {
    void showProgress();
    void dismissProgress();
    void updateItems(List<TopicReplayEntity> mDatas);
    void loadItems(List<TopicReplayEntity> mDatas);
    void likeFinish(boolean isSuccess);
    void commentSuccess(TopicReplayEntity entity);
    void update(TopicCommentDetailEntity entity);
    void deleteSuccess(int position);
}
