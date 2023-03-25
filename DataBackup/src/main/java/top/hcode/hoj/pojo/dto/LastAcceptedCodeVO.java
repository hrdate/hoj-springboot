package top.hcode.hoj.pojo.dto;

import lombok.Data;

/**
 * @Description:
 */
@Data
public class LastAcceptedCodeVO {

    private Long submitId;

    private String code;

    private String language;
}
