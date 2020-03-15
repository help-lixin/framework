package help.lixin.framework.auth.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import help.lixin.framework.auth.context.LoginModeContext;
import help.lixin.framework.auth.filter.Constants;
import help.lixin.framework.auth.service.IUserDetailService;

/**
 * 根据用户名加载用户详细信息
 * 
 * @author lixin
 */
public class UserDetailServiceImpl implements UserDetailsService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private IUserDetailService userDetailService;

	public UserDetailServiceImpl(IUserDetailService userDetailService) {
		this.userDetailService = userDetailService;
	}

	public void setUserDetailService(IUserDetailService userDetailService) {
		this.userDetailService = userDetailService;
	}

	public IUserDetailService getUserDetailService() {
		return userDetailService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Map<String, Object> params = new HashMap<>();
		params.put(Constants.LOGIN_MODE_KEY, LoginModeContext.getMode());
		params.put(Constants.IDENTIFIER, username);
		logger.info("load context loginMode:[{}] to params:[{}]", LoginModeContext.getMode(), params);
		return userDetailService.loadUser(params);
	}
}
