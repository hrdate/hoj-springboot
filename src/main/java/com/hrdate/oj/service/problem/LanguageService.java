package com.hrdate.oj.service.problem;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hrdate.oj.entity.problem.Language;
import com.hrdate.oj.mapper.LanguageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: huangrendi
 * @date: 2022-11-27
 **/

@Slf4j
@Service
public class LanguageService extends ServiceImpl<LanguageMapper, Language> {
}
