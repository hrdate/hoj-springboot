package top.hcode.hoj.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Description:
 */
@Data
public class ContestPrintDTO {

    @NotBlank(message = "比赛id不能为空")
    private Long cid;

    @NotBlank(message = "打印内容不能为空")
    private String content;
}