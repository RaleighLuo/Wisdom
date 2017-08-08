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
}
