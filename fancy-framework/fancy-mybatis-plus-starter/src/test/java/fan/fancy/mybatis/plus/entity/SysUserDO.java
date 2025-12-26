package fan.fancy.mybatis.plus.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 测试用户实体类.
 *
 * @author Fan
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
public class SysUserDO extends MetaDO {

    private String username;

    private Integer age;
}
