package help.lixin.framework.generic.parse;

import java.io.Serializable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import help.lixin.framework.generic.annotations.ExtensionProperty;
import help.lixin.framework.generic.annotations.api.Api;
import help.lixin.framework.generic.annotations.api.ApiOperation;
import help.lixin.framework.generic.annotations.api.ApiParam;
import help.lixin.framework.generic.annotations.api.ApiParams;

@RestController
@RequestMapping("/user")
@Api(value = "用户操作", extProperties = { //
		@ExtensionProperty(name = "project", value = "erp"), //
		@ExtensionProperty(name = "module", value = "user") })
public class TestController {

	@PostMapping("/add")
	@ApiOperation(value = "添加用户")
	@ApiParams({ @ApiParam(name = "token", value = "用户Token", paramType = "header"), //
			@ApiParam(name = "timestamep", value = "时间戳", paramType = "query"), //
			@ApiParam(name = "sign", value = "签名", paramType = "query") })
	public User user(@RequestBody User user) {
		
		return null;
	}
}

class User implements Serializable {
	private static final long serialVersionUID = 4629258463352823970L;
	private String name;
	private int age;
}
