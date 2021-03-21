package help.lixin.xxx.controller;

import help.lixin.xxx.model.Order2;
import help.lixin.xxx.service.IOrder2Service;
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

@RestController("/order2")
public class Order2Controller {
    @Resource
    private IOrder2Service order2Service;

    private Logger logger = LoggerFactory.getLogger(Order2Controller.class);

    @PostMapping
    public int save(@RequestBody Order2 order2) {
        return order2Service.insert(order2);
    }

    @GetMapping
    public List<Order2> queryAll() {
        return order2Service.selectAll();
    }

    @PutMapping
    public int updateByPrimaryKey(@RequestBody Order2 order2) {
        return order2Service.updateByPrimaryKey(order2);
    }

    @GetMapping("/{orderId}")
    public int deleteByPrimaryKey(@PathVariable("orderId") Long orderId) {
        return order2Service.deleteByPrimaryKey(orderId);
    }
}