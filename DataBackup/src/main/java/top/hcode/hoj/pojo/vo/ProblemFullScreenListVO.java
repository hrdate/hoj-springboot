package top.hcode.hoj.pojo.vo;

import lombok.Data;

/**
 * @Description:
 */
@Data
public class ProblemFullScreenListVO {

    private Long pid;

    private String problemId;

    private String title;

    private Integer status;

    private Integer score;
}
