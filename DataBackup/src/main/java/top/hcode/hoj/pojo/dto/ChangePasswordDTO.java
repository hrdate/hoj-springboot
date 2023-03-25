package top.hcode.hoj.pojo.dto;

import lombok.Data;

/**
 * @Description:
 */
@Data
public class ChangePasswordDTO {

    private String oldPassword;

    private String newPassword;
}