package help.lixin.xxx.service.impl;

import help.lixin.xxx.mapper.OrderMapper;
import help.lixin.xxx.model.Order;
import help.lixin.xxx.service.IOrderService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
class OrderService implements IOrderService {
    @Resource
    private OrderMapper orderMapper;

    public int insert(Order order) {
        return orderMapper.insert(order);
    }

    public List<Order> selectAll() {
        return orderMapper.selectAll();
    }

    public int updateByPrimaryKey(Order order) {
        return orderMapper.updateByPrimaryKey(order);
    }

    public int deleteByPrimaryKey(Long orderId) {
        return orderMapper.deleteByPrimaryKey(orderId);
    }
}