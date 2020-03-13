package help.lixin.framework.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@GetMapping("/hello")
//	@PreAuthorize("hasAuthority('user')")
//	@PreAuthorize("hasRole('user')")
	public String hello() {
		return "hello jwt !";
	}

	@GetMapping("/admin")
//	@PreAuthorize("hasAuthority('admin')")
//	@PreAuthorize("hasRole('admin')")
	public String admin() {
		return "hello admin !";
	}
}
