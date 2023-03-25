package top.hcode.hoj.dao.group;

import top.hcode.hoj.pojo.entity.contest.Contest;
import top.hcode.hoj.pojo.vo.ContestVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description:
 */
public interface GroupContestEntityService extends IService<Contest> {

    IPage<ContestVO> getContestList(int limit, int currentPage, Long gid);

    IPage<Contest> getAdminContestList(int limit, int currentPage, Long gid);

}
