package fan.fancy.redis.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Redis 配置.
 *
 * @author Fan
 */
@ConfigurationProperties(prefix = "spring.data.redis")
public class FancyRedisProperties {

    private String host;

    private Integer port;

    private String username;

    private String password;

    private int database;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }
}
