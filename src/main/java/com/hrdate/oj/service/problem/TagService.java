package com.hrdate.oj.service.problem;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hrdate.oj.entity.problem.ProblemTag;
import com.hrdate.oj.entity.problem.Tag;
import com.hrdate.oj.mapper.TagMapper;
import com.hrdate.oj.pojo.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: huangrendi
 * @date: 2022-11-27
 **/

@Slf4j
@Service
public class TagService extends ServiceImpl<TagMapper, Tag> {

    @Autowired
    private ProblemTagService problemTagService;
    public CommonResult<List<Tag>> getAllProblemTagsList(String oj) {
        List<Tag> tagList;
        oj = oj.toUpperCase();
        if (oj.equals("ALL")) {
            tagList = this.list();
        } else {
            tagList = this.lambdaQuery().eq(Tag::getOj, oj).list();
        }
        return CommonResult.succeedResult(tagList);
    }

    public CommonResult<Collection<Tag>> getProblemTags(Long pid) {
        List<Tag> tagList = null;
        Map<String, Object> map = new HashMap<>();
        map.put("pid", pid);
        List<Long> tidList = problemTagService.listByMap(map)
                .stream()
                .map(ProblemTag::getTid)
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(tidList)) {
            tagList = this.listByIds(tidList);
        }
        return CommonResult.succeedResult(tagList);
    }
}
