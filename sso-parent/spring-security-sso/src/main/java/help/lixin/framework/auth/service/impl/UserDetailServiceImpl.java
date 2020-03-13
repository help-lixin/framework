package help.lixin.framework.auth.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import help.lixin.framework.auth.user.User;

public class UserDetailServiceImpl implements UserDetailsService {
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = null;
		Set<GrantedAuthority> authorities = new HashSet<>();
		if (username.equals("admin")) {
//			authorities.add(new SimpleGrantedAuthority("ROLE_admin"));
			authorities.add(new SimpleGrantedAuthority("admin"));
			user = User.newBuilder().username(username).password("123").authorities(authorities).build();
		} else if (username.equals("sang")) {
//			authorities.add(new SimpleGrantedAuthority("ROLE_user"));
			authorities.add(new SimpleGrantedAuthority("user"));
			user = User.newBuilder().username(username).password("456").authorities(authorities).build();
		}
		if (null == user) {
			throw new UsernameNotFoundException("No User found with UserName :" + username);
		}
		return user;
	}

}
