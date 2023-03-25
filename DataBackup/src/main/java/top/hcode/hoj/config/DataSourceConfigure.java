package top.hcode.hoj.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @Description:
 */
@Component
@RefreshScope
@ConfigurationProperties(prefix = "hoj.db")
@Data
public class DataSourceConfigure {

    private String username;

    private String password;

    private String host;

    private Integer port;

    private String name;
}
