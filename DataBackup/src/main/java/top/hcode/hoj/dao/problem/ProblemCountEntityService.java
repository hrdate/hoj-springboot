package top.hcode.hoj.dao.problem;

import top.hcode.hoj.pojo.entity.problem.ProblemCount;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @Description:
 */
public interface ProblemCountEntityService extends IService<ProblemCount> {
    ProblemCount getContestProblemCount(Long pid, Long cpid, Long cid);
}
