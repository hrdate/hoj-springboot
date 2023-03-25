package top.hcode.hoj.dao.discussion;

import com.baomidou.mybatisplus.extension.service.IService;

import top.hcode.hoj.pojo.entity.discussion.Discussion;
import top.hcode.hoj.pojo.vo.DiscussionVO;

/**
 * @Description:
 */
public interface DiscussionEntityService extends IService<Discussion> {

    DiscussionVO getDiscussion(Integer did, String uid);

    void updatePostLikeMsg(String recipientId, String senderId, Integer discussionId, Long gid);
}
