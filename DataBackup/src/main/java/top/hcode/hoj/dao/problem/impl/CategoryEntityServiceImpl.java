package top.hcode.hoj.dao.problem.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.hcode.hoj.mapper.CategoryMapper;
import top.hcode.hoj.pojo.entity.problem.Category;
import top.hcode.hoj.dao.problem.CategoryEntityService;

/**
 * @Description:
 */
@Service
public class CategoryEntityServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryEntityService {
}