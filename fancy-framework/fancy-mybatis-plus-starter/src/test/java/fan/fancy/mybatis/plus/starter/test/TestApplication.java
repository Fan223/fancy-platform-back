package fan.fancy.mybatis.plus.starter.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

/**
 * 测试启动类.
 *
 * @author Fan
 */
@EnableAutoConfiguration
@MapperScan("fan.fancy.mybatis.plus.mapper")
public class TestApplication {
}
