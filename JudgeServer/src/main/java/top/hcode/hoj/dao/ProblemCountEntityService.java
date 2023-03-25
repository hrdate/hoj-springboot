package top.hcode.hoj.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import top.hcode.hoj.pojo.entity.problem.ProblemCount;

/**
 * <p>
 * 服务类
 * </p>
 *
 *
 * @Description:
 */
public interface ProblemCountEntityService extends IService<ProblemCount> {

    void updateCount(int status, Long pid);

}
