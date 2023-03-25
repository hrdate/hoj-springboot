package top.hcode.hoj.dao.problem.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.hcode.hoj.mapper.ProblemTagMapper;
import top.hcode.hoj.pojo.entity.problem.ProblemTag;
import top.hcode.hoj.dao.problem.ProblemTagEntityService;

/**
 * @Description:
 */
@Service
public class ProblemTagEntityServiceImpl extends ServiceImpl<ProblemTagMapper, ProblemTag> implements ProblemTagEntityService {
}