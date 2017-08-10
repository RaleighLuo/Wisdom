package com.gkzxhn.wisdom.view;

import com.gkzxhn.wisdom.entity.TopicCommentEntity;

/**
 * Created by Raleigh.Luo on 17/8/10.
 */

public interface ICommentDetailView extends IBaseView {
    void likeFinish(boolean isSuccess);
    void commentSuccess(TopicCommentEntity entity);
    void replayLikeFinish(int position,boolean isSuccess);
}
