package top.hcode.hoj.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description:
 */
@Data
public class UserAuthInfoVO {

    @ApiModelProperty(value = "角色列表")
    private List<String> roles;

    @ApiModelProperty(value = "权限列表")
    private List<String> permissions;
}
