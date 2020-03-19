package help.lixin.framework.swagger.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@Api(value = "HelloController", tags = { "测试接口" })
public class HelloController {

	private static Map<String, User> userMap = Collections.synchronizedMap(new HashMap<String, User>());

	@ApiOperation(value = "获取用户列表", notes = "获取用户列表")
	@GetMapping("/users")
	public List<User> users() {
		return new ArrayList<>(userMap.values());
	}

	@ApiOperation(value = "创建用户", notes = "创建用户时的注意事项")
	@ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, paramType = "body", dataType = "User")
	@PostMapping("/add")
	public void addUser(@RequestBody User user) {
		userMap.put(user.getId(), user);
	}

	/**
	 * 更新用户
	 * 
	 * @param userId
	 *            用户id
	 * @param user
	 *            修改的用户对象
	 */
	@ApiOperation(value = "修改用户", notes = "修改用户细信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userId", value = "用户名", required = true, paramType = "path", dataType = "String"),
			@ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, paramType = "body", dataType = "User") })
	@PutMapping("/update/{userId}")
	public void updateUser(@PathVariable String userId, User user) {
		userMap.remove(userId);
		userMap.put(user.getId(), user);
	}

	@ApiOperation(value = "根据姓名查询用户", notes = "根据姓名查询用户信息")
	// 下面两种写法都可以
	// @ApiParam(value = "name",name = "用户的名字",defaultValue = "zhangsan")
	@ApiImplicitParam(name = "name", value = "用户的名字", paramType = "query", dataType = "String")
	@GetMapping("/getUser")
	public User getUser(@RequestParam(value = "name", required = false) String name) {
		// 不要深究实现的内容，主要为了演示测试
		User user = new User();
		user.setId("1000");
		if (name != null) {
			user.setName(name);
		} else {
			user.setName("zhangsan");
		}
		user.setAge(26);
		return user;
	}

	/**
	 * 添加用户
	 * 
	 * @param user
	 *            用户对象
	 * @return 用户对象
	 */
	@ApiIgnore() // 添加这个注解将不会显示在UI界面上
	@ApiOperation(value = "添加用户", notes = "添加一个用户")
	@PostMapping("/addUser")
	public User addUser2(@RequestBody User user) {
		return user;
	}
}

@ApiModel(description = "用户的实体对象")
class User implements Serializable {
	private static final long serialVersionUID = 4736412703060770365L;

	@ApiModelProperty(value = "用户ID", name = "id", required = true, dataType = "String")
	private String id;

	@ApiModelProperty(value = "用户名", name = "name", required = true, dataType = "String")
	private String name;

	@ApiModelProperty(value = "年龄", name = "age", required = true, dataType = "String")
	private int age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", age=" + age + "]";
	}

}
