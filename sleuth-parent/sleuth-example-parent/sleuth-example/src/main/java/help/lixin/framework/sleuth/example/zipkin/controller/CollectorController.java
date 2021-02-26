package help.lixin.framework.sleuth.example.zipkin.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CollectorController {

    @PostMapping("/api/v2/spans")
    public String spans(@RequestBody String line) {
        System.out.println("line: " + line);
        return "SUCCESS";
    }
}
