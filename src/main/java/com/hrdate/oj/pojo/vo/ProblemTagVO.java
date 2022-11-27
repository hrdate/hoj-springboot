package com.hrdate.oj.pojo.vo;

import com.hrdate.oj.entity.problem.Tag;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: huangrendi
 * @Date: 2022/11/23 20:00
 */
@Data
public class ProblemTagVO implements Serializable {
    /**
     * 标签分类
     */
//    private TagClassification classification;

    /**
     * 标签列表
     */
    private List<Tag> tagList;

}
