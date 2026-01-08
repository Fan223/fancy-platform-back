package fan.fancy.log.test;

import fan.fancy.log.annotation.FancyLog;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 日志测试.
 *
 * @author Fan
 */
@SpringBootTest(classes = TestApplication.class)
class LogTest {

    @FancyLog
    public String test(String name) {
        return "Hello, " + name;
    }

    @Test
    void testLog() {
        String result = test("World");
        System.out.println(result);
    }
}
