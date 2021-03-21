package help.lixin.xxx.service.impl;

import help.lixin.xxx.mapper.Order2Mapper;
import help.lixin.xxx.model.Order2;
import help.lixin.xxx.service.IOrder2Service;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
class Order2Service implements IOrder2Service {
    @Resource
    private Order2Mapper order2Mapper;

    public int insert(Order2 order2) {
        return order2Mapper.insert(order2);
    }

    public List<Order2> selectAll() {
        return order2Mapper.selectAll();
    }

    public int updateByPrimaryKey(Order2 order2) {
        return order2Mapper.updateByPrimaryKey(order2);
    }

    public int deleteByPrimaryKey(Long orderId) {
        return order2Mapper.deleteByPrimaryKey(orderId);
    }
}