package top.hcode.hoj.dao.problem.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.hcode.hoj.dao.problem.TagClassificationEntityService;
import top.hcode.hoj.mapper.TagClassificationMapper;
import top.hcode.hoj.pojo.entity.problem.TagClassification;

/**
 * @Description:
 */
@Service
public class TagClassificationEntityServiceImpl extends ServiceImpl<TagClassificationMapper, TagClassification> implements TagClassificationEntityService {
}
