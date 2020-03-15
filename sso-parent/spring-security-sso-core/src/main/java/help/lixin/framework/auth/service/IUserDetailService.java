package help.lixin.framework.auth.service;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface IUserDetailService {

	public UserDetails loadUser(Map<String, Object> params) throws UsernameNotFoundException;

}
