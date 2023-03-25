package top.hcode.hoj.pojo.dto;

import lombok.Data;

/**
 * @Description:
 */
@Data
public class ChangeGroupProblemProgressDTO {

    /**
     * 题目id
     */
    private Long pid;

    /**
     * 1为申请中，2为申请通过，3为申请拒绝
     */
    private Integer progress;
}
