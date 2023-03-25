package top.hcode.hoj.dao.discussion;

import com.baomidou.mybatisplus.extension.service.IService;
import top.hcode.hoj.pojo.entity.discussion.Reply;
import top.hcode.hoj.pojo.vo.ReplyVO;

import java.util.List;

/**
 * @Description:
 */
public interface ReplyEntityService extends IService<Reply> {

    public List<ReplyVO> getAllReplyByCommentId(Long cid, String uid, Boolean isRoot, Integer commentId);

    public void updateReplyMsg(Integer sourceId, String sourceType, String content,
                               Integer quoteId, String quoteType, String recipientId,String senderId);
}