package help.lixin.framework.message.source.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LanguateController {

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/test")
    public String test() {
        String message = messageSource.getMessage("", null, null);

        return "SUCCESS";
    }
}
