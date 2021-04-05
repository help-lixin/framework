package help.lixin.framework.passport.service.impl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public class UserDetailsServiceImpl implements UserDetailsService {

    private PasswordEncoder passwordEncoder;

    public UserDetailsServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (!"admin".equalsIgnoreCase(username)) {
            throw new UsernameNotFoundException("username:[" + username + "] not found...");
        }

        // 数据库中的密码
        String password = "123";
        String encodePassword = passwordEncoder.encode(password);
        // 角色必须以:ROLE_开始.
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList("admin", "normal", "ROLE_admin","/main.html");

        UserDetails userDetails = User.withUsername(username).password(encodePassword).authorities(authorityList).build();
        return userDetails;
    }
}
