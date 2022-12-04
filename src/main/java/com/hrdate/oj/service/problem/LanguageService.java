package com.hrdate.oj.service.problem;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hrdate.oj.entity.problem.Language;
import com.hrdate.oj.entity.problem.Problem;
import com.hrdate.oj.entity.problem.ProblemLanguage;
import com.hrdate.oj.mapper.LanguageMapper;
import com.hrdate.oj.pojo.CommonResult;
import com.hrdate.oj.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: huangrendi
 * @date: 2022-11-27
 **/

@Slf4j
@Service
public class LanguageService extends ServiceImpl<LanguageMapper, Language> {

    @Autowired
    private ProblemLanguageService problemLanguageService;

    public CommonResult<List<Language>> getLanguages(Long pid, Boolean all) {
        String oj = "ME";
        if (pid != null) {
            ProblemService problemService = SpringContextUtil.getBean(ProblemService.class);
            Problem problem = problemService.getById(pid);
            if (problem.getIsRemote()) {
                oj = problem.getProblemId().split("-")[0];
            }
        }
        // GYM用与CF一样的编程语言列表
        if (oj.equals("GYM")) {
            oj = "CF";
        }
        // 获取对应OJ支持的语言列表
        QueryWrapper<Language> queryWrapper = new QueryWrapper<Language>()
            .eq(BooleanUtils.isTrue(all), "oj", oj);
        List<Language> languageList = this.list(queryWrapper);
        return CommonResult.succeedResult(languageList);
    }

    public CommonResult<Collection<Language>> getProblemLanguages(Long pid) {
        QueryWrapper<ProblemLanguage> queryWrapper = new QueryWrapper<ProblemLanguage>()
            .eq("pid", pid).select("lid");
        List<Long> idList = problemLanguageService.list(queryWrapper)
                .stream().map(ProblemLanguage::getLid).collect(Collectors.toList());
        List<Language> languageList = this.listByIds(idList);
        return CommonResult.succeedResult(languageList);
    }
}
