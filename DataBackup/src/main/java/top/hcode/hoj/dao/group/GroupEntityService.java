package top.hcode.hoj.dao.group;

import top.hcode.hoj.pojo.entity.group.Group;
import top.hcode.hoj.pojo.vo.GroupVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description:
 */
public interface GroupEntityService extends IService<Group> {
    IPage<GroupVO> getGroupList(int limit,
                                int currentPage,
                                String keyword,
                                Integer auth,
                                String uid,
                                Boolean onlyMine,
                                Boolean isRoot);
}
