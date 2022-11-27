package com.hrdate.oj.pojo.dto;

import com.hrdate.oj.entity.problem.Language;
import com.hrdate.oj.entity.problem.Problem;
import com.hrdate.oj.entity.problem.ProblemCase;
import com.hrdate.oj.entity.problem.Tag;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;


/**
 * @Author: huangrendi
 * @Date: 2022/11/23 20:00
 * @Description:
 */
@Data
@Accessors(chain = true)
public class ProblemDTO {

    private Problem problem;

    private List<ProblemCase> samples;

    private Boolean isUploadTestCase;

    private String uploadTestcaseDir;

    private String judgeMode;

    private Boolean changeModeCode;

    private Boolean changeJudgeCaseMode;

    private List<Language> languages;

    private List<Tag> tags;


}