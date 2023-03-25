package top.hcode.hoj.pojo.vo;

import lombok.Data;

/**
 * @Description:
 */
@Data
public class ChangeAccountVO {

    private Integer code;

    private String msg;

    private UserInfoVO userInfo;
}