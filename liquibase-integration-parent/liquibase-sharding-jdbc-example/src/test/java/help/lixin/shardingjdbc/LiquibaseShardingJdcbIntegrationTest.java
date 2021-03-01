package help.lixin.shardingjdbc;

import help.lixin.shardingjdbc.mapper.OrderMapper;
import help.lixin.shardingjdbc.mapper.UserMapper;
import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class LiquibaseShardingJdcbIntegrationTest {
    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void batchSave() throws Exception {
        // 测试ds2.t_order插入数据
        for (long i = 1; i < 20; i++) {
            orderMapper.insertOrder(1L, new BigDecimal(20.5 + i), "SUCCESS");
        }

        // 测试ds1.t_order插入数据
        for (long i = 1; i < 20; i++) {
            orderMapper.insertOrder(2L, new BigDecimal(20.5 + i), "SUCCESS");
        }
    }

    @SuppressWarnings({ "deprecation", "rawtypes" })
    @Test
    @Ignore
    public void query() {
        List<Long> ids = new ArrayList<Long>();
        ids.add(569598419530678272L);
        ids.add(569598420910604288L);
//		ids.add(566773221441929217L);
//		ids.add(566773221924274176L);
        List<Map> results = orderMapper.selectOrderbyIds(ids);
        Assert.assertNotNull(results);
    }


    @Resource
    private UserMapper userMapper;

    @Test
//    @Ignore
    public void testInsertUser() throws Exception {
        userMapper.insertUser(1L, "张三");
        userMapper.insertUser(2L, "李四");
        userMapper.insertUser(3L, "王五");
        userMapper.insertUser(4L, "赵六");
        userMapper.insertUser(5L, "田七");
    }

    @Test
    public void testSelectUserbyIds() {
        List<Long> userIds = new ArrayList<>();
        userIds.add(1L);
        userIds.add(2L);
        List<Map> users = userMapper.selectUserbyIds(userIds);
        System.out.println(users);
    }
}
