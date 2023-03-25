package top.hcode.hoj.dao.problem.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.hcode.hoj.mapper.CodeTemplateMapper;
import top.hcode.hoj.pojo.entity.problem.CodeTemplate;
import top.hcode.hoj.dao.problem.CodeTemplateEntityService;

/**
 * @Description:
 */
@Service
public class CodeTemplateEntityServiceImpl extends ServiceImpl<CodeTemplateMapper, CodeTemplate> implements CodeTemplateEntityService {
}