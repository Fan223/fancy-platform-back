package fan.fancy.mybatis.plus;

import fan.fancy.mybatis.plus.generator.CodeProperties;
import fan.fancy.mybatis.plus.generator.FancyCodeGenerator;
import org.junit.jupiter.api.Test;

/**
 * 测试
 *
 * @author Fan
 */
class FancyCodeGeneratorTest {

    @Test
    void test() {
        CodeProperties properties = CodeProperties.builder()
                .url("jdbc:mysql://1.12.253.22:3306/fancy_blog?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&useSSL=false&autoReconnect=true&failOverReadOnly=false&connectTimeout=5000&socketTimeout=10000")
                .password("fan223")
                .outputDir("D://Fan//code")
                .build();
        FancyCodeGenerator.generate(properties, "article", "category");
    }
}
