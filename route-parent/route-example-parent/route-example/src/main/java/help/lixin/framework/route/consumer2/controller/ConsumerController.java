package help.lixin.framework.route.consumer2.controller;

import help.lixin.framework.route.consumer2.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {
    @Autowired
    private HelloService helloService;

    @GetMapping("/consumer")
    public String index(@RequestHeader(value = "x-route",required = false) String xroute) {
        System.out.println("******************************x-route*****************************" + xroute);
        return helloService.hello();
    }
}
