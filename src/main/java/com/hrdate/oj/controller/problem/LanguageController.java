package com.hrdate.oj.controller.problem;

import com.hrdate.oj.annotation.AnonApi;
import com.hrdate.oj.entity.problem.Language;
import com.hrdate.oj.pojo.CommonResult;
import com.hrdate.oj.service.problem.LanguageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

/**
 * @description:
 * @author: huangrendi
 * @date: 2022-11-27
 **/

@Slf4j
@RestController("/api")
public class LanguageController {

    @Autowired
    private LanguageService languageService;

    @GetMapping("/languages")
    @AnonApi
    public CommonResult<List<Language>> getLanguages(@RequestParam(value = "pid", required = false) Long pid,
                                                     @RequestParam(value = "all", required = false) Boolean all) {
        return languageService.getLanguages(pid, all);
    }

    @GetMapping("/get-problem-languages")
    @AnonApi
    public CommonResult<Collection<Language>> getProblemLanguages(@RequestParam("pid") Long pid) {
        return languageService.getProblemLanguages(pid);
    }
}
