package top.hcode.hoj.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.hcode.hoj.pojo.entity.discussion.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.hcode.hoj.pojo.vo.CommentVO;

import java.util.List;


/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @Description: 
 */
@Mapper
@Repository
public interface CommentMapper extends BaseMapper<Comment> {

    IPage<CommentVO> getCommentList(Page<CommentVO> page,
                                    @Param("cid") Long cid,
                                    @Param("did") Integer did,
                                    @Param("onlyMineAndAdmin") Boolean onlyMineAndAdmin,
                                    @Param("myAndAdminUidList") List<String> myAndAdminUidList);
}
