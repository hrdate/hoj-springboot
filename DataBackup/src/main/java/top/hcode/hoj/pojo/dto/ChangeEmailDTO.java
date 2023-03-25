package top.hcode.hoj.pojo.dto;

import lombok.Data;

/**
 * @Description:
 */
@Data
public class ChangeEmailDTO {

    private String password;

    private String newEmail;

    private String code;
}