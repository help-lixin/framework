package help.lixin.framework.auth.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import help.lixin.framework.auth.constant.Constants;
import help.lixin.framework.auth.mapper.UserMapper;
import help.lixin.framework.auth.model.User;
import help.lixin.framework.auth.service.IUserDetailService;
import help.lixin.framework.auth.user.UserDetail;

public class LocalUserDetailService implements IUserDetailService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserMapper userMapper;

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	public UserMapper getUserMapper() {
		return userMapper;
	}

	@Override
	public UserDetails loadUser(Map<String, Object> params) throws UsernameNotFoundException {
		if (!params.containsKey(Constants.LOGIN_MODE_KEY)) {
			throw new UsernameNotFoundException("登录错误,加载用户信息时,必须通过上下文配置登录模式");
		}
		if (!params.containsKey(Constants.IDENTIFIER)) {
			throw new UsernameNotFoundException("身份标识不能为空!");
		}
		String loginMode = (String) params.get(Constants.LOGIN_MODE_KEY);
		String userName = (String) params.get(Constants.IDENTIFIER);
		User user = userMapper.loadUser(loginMode, userName);
		if (null == user) {
			logger.warn("加载用户信息错误.传入的信息为:[{}]", params);
			throw new UsernameNotFoundException("用户名或密码错误!");
		}
		return UserDetail.newBuilder() //
				.mode(loginMode) //登录模式
				.userInfoId(user.getUserId()) // 用户ID
				.tenantId(user.getTenantId()) // 租户ID
				.username(user.getIdentifier()) //
				.password(user.getCredential()) //
				.isAccountNonLocked(user.getStatus() == 1 ? true : false) //
				.build();
	}
}
