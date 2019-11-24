package help.lixin.framework.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/some-service-1")
public class HelloController {

	@GetMapping("/hello")
	public String hello() {
		return "some-service-1 Hello";
	}
}
