package help.lixin.xxx.service;

import help.lixin.xxx.model.Order;
import java.util.List;

public interface IOrderService {
    int insert(Order order);

    List<Order> selectAll();

    int updateByPrimaryKey(Order order);

    int deleteByPrimaryKey(Long orderId);
}