package fan.fancy.log.test;

import fan.fancy.log.annotation.FancyLog;
import org.springframework.stereotype.Service;

/**
 * 测试
 *
 * @author Fan
 */
@Service
public class TestService {

    @FancyLog
    public String test(String name) {
        return "Hello, " + name;
    }
}
