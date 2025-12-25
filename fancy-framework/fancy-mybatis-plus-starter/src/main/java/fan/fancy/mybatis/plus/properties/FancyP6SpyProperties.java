package fan.fancy.mybatis.plus.properties;

import lombok.Data;

/**
 * Properties
 *
 * @author Fan
 */
@Data
//@ConfigurationProperties("decorator.datasource.p6spy.log-format")
public class FancyP6SpyProperties {

    private String logFormat = "分类: %(category)\t耗时: %(executionTime)ms\n\t%(sql)";
}
