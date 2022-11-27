package com.hrdate.oj.pojo.dto;

import com.hrdate.oj.entity.problem.Problem;
import com.hrdate.oj.entity.problem.ProblemCase;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Himit_ZH
 * @Date: 2021/5/30 15:09
 * @Description:
 */
@Accessors(chain = true)
@Data
public class QDOJProblemDTO implements Serializable {
    private Problem problem;

    private List<String> languages;

    private List<ProblemCase> samples;

    private List<String> tags;

    private Boolean isSpj;

}