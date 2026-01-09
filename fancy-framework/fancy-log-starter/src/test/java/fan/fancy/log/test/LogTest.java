package fan.fancy.log.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 日志测试.
 *
 * @author Fan
 */
@SpringBootTest(classes = TestApplication.class)
class LogTest {

    @Autowired
    private TestService testService;

    @Test
    void testLog() {
        String result = testService.test("World");
        System.out.println(result);
    }
}
