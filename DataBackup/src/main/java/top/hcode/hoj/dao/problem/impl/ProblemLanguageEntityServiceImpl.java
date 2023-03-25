package top.hcode.hoj.dao.problem.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.hcode.hoj.mapper.ProblemLanguageMapper;
import top.hcode.hoj.pojo.entity.problem.ProblemLanguage;
import top.hcode.hoj.dao.problem.ProblemLanguageEntityService;

/**
 * @Description:
 */
@Service
public class ProblemLanguageEntityServiceImpl extends ServiceImpl<ProblemLanguageMapper, ProblemLanguage> implements ProblemLanguageEntityService {
}