package top.hcode.hoj.dao.discussion.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.hcode.hoj.mapper.DiscussionLikeMapper;
import top.hcode.hoj.pojo.entity.discussion.DiscussionLike;
import top.hcode.hoj.dao.discussion.DiscussionLikeEntityService;

/**
 * @Description:
 */
@Service
public class DiscussionLikeEntityServiceImpl extends ServiceImpl<DiscussionLikeMapper, DiscussionLike> implements DiscussionLikeEntityService {
}