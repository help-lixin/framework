package help.lixin.framework.route.consumer.controller;

import help.lixin.framework.route.consumer.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {
    @Autowired
    private HelloService helloService;

    @GetMapping("/consumer")
    public String index() {
        return helloService.hello();
    }
}
