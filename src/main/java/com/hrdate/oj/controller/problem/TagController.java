package com.hrdate.oj.controller.problem;

import com.hrdate.oj.annotation.AnonApi;
import com.hrdate.oj.entity.problem.Tag;
import com.hrdate.oj.pojo.CommonResult;
import com.hrdate.oj.service.problem.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

/**
 * @description: 标签
 * @author: huangrendi
 * @date: 2022-11-27
 **/

@Slf4j
@RestController("/api")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/get-all-problem-tags")
    @AnonApi
    public CommonResult<List<Tag>> getAllProblemTagsList(@RequestParam(value = "oj", defaultValue = "ME") String oj) {
        return tagService.getAllProblemTagsList(oj);
    }

    @GetMapping("/get-problem-tags")
    @AnonApi
    public CommonResult<Collection<Tag>> getProblemTags(Long pid) {
        return tagService.getProblemTags(pid);
    }
}
