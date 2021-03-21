package help.lixin.xxx.controller;

import help.lixin.xxx.model.Order;
import help.lixin.xxx.service.IOrderService;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/order")
public class OrderController {
    @Resource
    private IOrderService orderService;

    private Logger logger = LoggerFactory.getLogger(OrderController.class);

    @PostMapping
    public int save(@RequestBody Order order) {
        return orderService.insert(order);
    }

    @GetMapping
    public List<Order> queryAll() {
        return orderService.selectAll();
    }

    @PutMapping
    public int updateByPrimaryKey(@RequestBody Order order) {
        return orderService.updateByPrimaryKey(order);
    }

    @GetMapping("/{orderId}")
    public int deleteByPrimaryKey(@PathVariable("orderId") Long orderId) {
        return orderService.deleteByPrimaryKey(orderId);
    }
}