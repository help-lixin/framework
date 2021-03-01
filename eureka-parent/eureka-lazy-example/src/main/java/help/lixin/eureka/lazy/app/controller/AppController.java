package help.lixin.eureka.lazy.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {
    private Logger logger = LoggerFactory.getLogger(AppController.class);

    @GetMapping("/consumer")
    public String index() {
        logger.info("******************************index*****************************");
        return "Success...";
    }
}
