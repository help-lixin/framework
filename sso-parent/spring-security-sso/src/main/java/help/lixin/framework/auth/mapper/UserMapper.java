package help.lixin.framework.auth.mapper;

import help.lixin.framework.auth.model.User;

public interface UserMapper {
	User loadUser(String type, String userName);
}
