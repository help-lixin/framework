package help.lixin.xxx.service;

import help.lixin.xxx.model.Order2;
import java.util.List;

public interface IOrder2Service {
    int insert(Order2 order2);

    List<Order2> selectAll();

    int updateByPrimaryKey(Order2 order2);

    int deleteByPrimaryKey(Long orderId);
}