package com.hrdate.oj.config;

import com.hrdate.oj.utils.IpUtil;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: huangrendi
 * @date: 2022-11-27
 **/

@Data
@Component
public class WebConfig {

    // 邮箱配置
    private String emailUsername;

    private String emailPassword;

    private String emailHost;

    private Integer emailPort;

    private Boolean emailSsl = true;

    private String emailBGImg = "https://cdn.jsdelivr.net/gh/HimitZH/CDN/images/HCODE.png";

    // 网站前端显示配置
    private String baseUrl = "http://" + IpUtil.getServiceIp();

    private String name = "hrdate Online Judge";

    private String shortName = "HOJ";

    private String description;

    private Boolean register = true;

    private String recordName;

    private String recordUrl;

    private String projectName = "HOJ";

    private String projectUrl = "https://github.com/hrdate/My-OnlieJudge";
}
