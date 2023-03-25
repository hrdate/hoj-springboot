package top.hcode.hoj.dao.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.hcode.hoj.mapper.ProblemMapper;
import top.hcode.hoj.pojo.entity.problem.Problem;
import top.hcode.hoj.dao.ProblemEntityService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 *
 * @Description:
 */
@Service
public class ProblemEntityServiceImpl extends ServiceImpl<ProblemMapper, Problem> implements ProblemEntityService {

}
