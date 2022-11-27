package com.hrdate.oj.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;

/**
 * @description: 题目类型
 * @author: huangrendi
 * @date: 2022-11-21
 **/

@Getter
public enum ProblemTypeEnum {

    ACM(0),
    OI(1)
    ;

    @JsonValue
    @EnumValue
    private final Integer problemType;

    ProblemTypeEnum(Integer problemType){
        this.problemType = problemType;
    }

}
