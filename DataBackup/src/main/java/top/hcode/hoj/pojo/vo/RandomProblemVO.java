package top.hcode.hoj.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 */
@Data
public class RandomProblemVO {

    @ApiModelProperty(value = "题目id")
    private String problemId;
}