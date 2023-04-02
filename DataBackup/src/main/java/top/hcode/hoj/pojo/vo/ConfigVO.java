package top.hcode.hoj.pojo.vo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;


/**
 * @Description:
 */
@RefreshScope
@Data
@Component
public class ConfigVO {
    // 数据库配置
    @Value("${hoj.db.username:root}")
    private String mysqlUsername;

    @Value("${hoj.db.password:hot123456}")
    private String mysqlPassword;

    @Value("${hoj.db.name:hoj}")
    private String mysqlDBName;

    @Value("${hoj.db.host:192.168.42.131}")
    private String mysqlHost;

    @Value("${hoj.db.public-host:192.168.42.131}")
    private String mysqlPublicHost;

    @Value("${hoj.db.port:3306}")
    private Integer mysqlPort;

    @Value("${hoj.db.public-port:3306}")
    private Integer mysqlPublicPort;

    // 判题服务token
    @Value("${hoj.judge.token:318b07eda81f4ffdadc6507b99b169e2}")
    private String judgeToken;

    // 缓存配置
    @Value("${hoj.redis.host:192.168.42.131}")
    private String redisHost;

    @Value("${hoj.redis.port:6379}")
    private Integer redisPort;

    @Value("${hoj.redis.password:hoj123456}")
    private String redisPassword;

    // jwt配置
    @Value("${hoj.jwt.secret}")
    private String tokenSecret;

    @Value("${hoj.jwt.expire}")
    private String tokenExpire;

    @Value("${hoj.jwt.checkRefreshExpire}")
    private String checkRefreshExpire;
}
