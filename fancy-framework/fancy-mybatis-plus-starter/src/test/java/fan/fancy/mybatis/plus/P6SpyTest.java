package fan.fancy.mybatis.plus;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * P6SpyEnabled 测试类.
 *
 * @author Fan
 */
@SpringBootTest(classes = P6SpyTest.TestApplication.class)
class P6SpyTest {

    @Autowired
    private ArticleDAO articleDAO;
    private ListAppender<ILoggingEvent> listAppender;

    @BeforeEach
    void setUp() {
        Logger logger = (Logger) LoggerFactory.getLogger("p6spy");
        listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
    }

    @AfterEach
    void tearDown() {
        listAppender.stop();
    }

    @Test
    void should_log_sql_with_p6spy_format() {
        articleDAO.selectById(1L);

        List<String> logs = listAppender.list.stream()
                .map(ILoggingEvent::getFormattedMessage)
                .toList();

        AssertionsForInterfaceTypes.assertThat(logs)
                .anyMatch(log -> log.contains("SELECT"))
                .anyMatch(log -> log.contains("ms"));
    }

    @SpringBootConfiguration
    @EnableAutoConfiguration
    @MapperScan("fan.fancy.mybatis.plus")
    static class TestApplication {
    }
}
