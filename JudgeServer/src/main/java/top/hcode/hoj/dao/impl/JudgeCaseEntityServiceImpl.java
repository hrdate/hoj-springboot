package top.hcode.hoj.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.hcode.hoj.mapper.JudgeCaseMapper;
import top.hcode.hoj.pojo.entity.judge.JudgeCase;
import top.hcode.hoj.dao.JudgeCaseEntityService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @Description:
 */
@Service
public class JudgeCaseEntityServiceImpl extends ServiceImpl<JudgeCaseMapper, JudgeCase> implements JudgeCaseEntityService {

}
