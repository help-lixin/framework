package help.lixin.framework.cache.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import help.lixin.framework.cache.entity.User;
import help.lixin.framework.cache.service.IHelloService;

@RestController
public class HelloController {
	private Logger logger = LoggerFactory.getLogger(HelloController.class);

	@Autowired
	private IHelloService helloService;

	@GetMapping("/{tenantId}")
	public List<User> list(@PathVariable("tenantId") Long tenantId) {
		List<User> users = helloService.findByTenantId(tenantId);
		return users;
	}

	@PostMapping("/{tenantId}")
	public String addUser(@PathVariable("tenantId") Long tenantId, @RequestBody User user) {
		helloService.add(tenantId, user);
		return "SUCCESS";
	}
}