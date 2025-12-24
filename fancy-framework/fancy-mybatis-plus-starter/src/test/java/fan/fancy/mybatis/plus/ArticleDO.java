package fan.fancy.mybatis.plus;

import com.baomidou.mybatisplus.annotation.TableName;
import fan.fancy.mybatis.plus.entity.MetaDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 实体类
 *
 * @author Fan
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("article")
public class ArticleDO extends MetaDO {

    /**
     * 封面
     */
    private String cover;

    /**
     * 标题
     */
    private String title;

    /**
     * 摘要
     */
    private String summary;

    /**
     * 内容
     */
    private String content;

    /**
     * 状态 0:未发布 1:已发布 2:推荐
     */
    private Byte status;
}
