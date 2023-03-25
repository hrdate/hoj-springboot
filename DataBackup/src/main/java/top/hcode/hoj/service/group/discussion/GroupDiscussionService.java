package top.hcode.hoj.service.group.discussion;

import top.hcode.hoj.common.result.CommonResult;
import top.hcode.hoj.pojo.entity.discussion.Discussion;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @Description:
 */
public interface GroupDiscussionService {

    public CommonResult<IPage<Discussion>> getDiscussionList(Integer limit, Integer currentPage, Long gid, String pid);

    public CommonResult<IPage<Discussion>> getAdminDiscussionList(Integer limit, Integer currentPage, Long gid);

    public CommonResult<Void> addDiscussion(Discussion discussion);

    public CommonResult<Void> updateDiscussion(Discussion discussion);

    public CommonResult<Void> deleteDiscussion(Long did);

}
