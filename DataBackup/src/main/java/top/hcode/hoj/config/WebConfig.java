package top.hcode.hoj.config;

import lombok.Data;
import top.hcode.hoj.utils.IpUtils;

/**
 * @Description:  网站配置
 */
@Data
public class WebConfig {

    // 邮箱配置
    private String emailUsername;

    private String emailPassword;

    private String emailHost;

    private Integer emailPort;

    private Boolean emailSsl = true;

    private String emailBGImg = "https://gitee.com/diloveyu/image-cloud/blob/master/vj.jpg";

    // 网站前端显示配置
    private String baseUrl = "http://" + IpUtils.getServiceIp();

    private String name = "Hcode Online Judge";

    private String shortName = "HOJ";

    private String description;

    private Boolean register = true;

    private String recordName;

    private String recordUrl;

    private String projectName = "HOJ";

    private String projectUrl = "https://github.com/hrdate/My-OnlieJudge";
}
