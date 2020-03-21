package help.lixin.framework.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultTest {
	public static void main(String[] args) {
		User user = new User();
		List<User> users = new ArrayList<User>();
		users.add(user);
		
		Result<List<User>> results = new Result.Builder<List<User>>()
		.code(200)
		.message("认证成功")
		.other("a", "b")
		.data(users)
		.build();
		System.out.println(results);
	}
}

class User implements Serializable{
	private static final long serialVersionUID = 5885812666866931179L;
}
