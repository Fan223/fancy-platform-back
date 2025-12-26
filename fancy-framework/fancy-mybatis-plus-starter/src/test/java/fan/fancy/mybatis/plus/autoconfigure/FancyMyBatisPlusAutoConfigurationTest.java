package fan.fancy.mybatis.plus.autoconfigure;

import fan.fancy.mybatis.plus.TestApplication;
import fan.fancy.mybatis.plus.entity.MetaDO;
import fan.fancy.mybatis.plus.entity.SysUserDO;
import fan.fancy.mybatis.plus.mapper.SysUserMapper;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * {@link FancyMyBatisPlusAutoConfiguration} 测试类.
 *
 * @author Fan
 */
@SpringBootTest(classes = TestApplication.class)
class FancyMyBatisPlusAutoConfigurationTest {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Test
    void insertEntity_withoutIdAndTimes_autoFillIdAndTimes() {
        SysUserDO sysUserDO = new SysUserDO();
        sysUserDO.setUsername("test");
        sysUserMapper.insert(sysUserDO);

        Assertions.assertNotNull(sysUserDO.getId(), "id should be auto filled.");
        Assertions.assertNotNull(sysUserDO.getCreateTime(), "createTime should be auto filled.");
        Assertions.assertNotNull(sysUserDO.getUpdateTime(), "updateTime should be auto filled.");
    }

    @Test
    void updateEntity_withExistingUpdateTime_refreshUpdateTime() {
        SysUserDO sysUserDO = new SysUserDO();
        sysUserDO.setUsername("test");
        sysUserMapper.insert(sysUserDO);

        LocalDateTime oldUpdateTime = sysUserDO.getUpdateTime();
        Awaitility.await().atMost(2, TimeUnit.SECONDS);
        sysUserDO.setUsername("updated");
        sysUserMapper.updateById(sysUserDO);

        MetaDO updated = sysUserMapper.selectById(sysUserDO.getId());
        Assertions.assertTrue(updated.getUpdateTime().isAfter(oldUpdateTime), "updateTime should be refreshed on update.");
    }

    @Test
    void deleteEntity_byLogicDelete_setDeleteTime() {
        SysUserDO sysUserDO = new SysUserDO();
        sysUserDO.setUsername("test");
        sysUserMapper.insert(sysUserDO);

        Long id = sysUserDO.getId();
        Assertions.assertNotNull(id);

        int rows = sysUserMapper.deleteById(id);
        Assertions.assertEquals(1, rows);

        SysUserDO deletedUser = sysUserMapper.selectById(id);
        Assertions.assertNull(deletedUser);
    }
}
