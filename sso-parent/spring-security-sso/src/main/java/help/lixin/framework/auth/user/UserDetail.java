package help.lixin.framework.auth.user;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetail implements UserDetails {
	private static final long serialVersionUID = -1972260612305175967L;
	// 用户基本信息ID
	private Long userInfoId;
	// 租户ID
	private Long tenantId;
	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> authorities = new ArrayList<>(0);
	private boolean isAccountNonLocked;
	// 登录模式
	private String mode;

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getMode() {
		return mode;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static class Builder {
		private UserDetail userDetail = new UserDetail();

		public Builder userInfoId(Long userInfoId) {
			userDetail.userInfoId = userInfoId;
			return this;
		}

		public Builder mode(String mode) {
			userDetail.mode = mode;
			return this;
		}

		public Builder tenantId(Long tenantId) {
			userDetail.tenantId = tenantId;
			return this;
		}

		public Builder isAccountNonLocked(boolean isAccountNonLocked) {
			userDetail.isAccountNonLocked = isAccountNonLocked;
			return this;
		}

		public Builder username(String username) {
			userDetail.username = username;
			return this;
		}

		public Builder password(String password) {
			userDetail.password = password;
			return this;
		}

		public Builder authorities(Collection<? extends GrantedAuthority> authorities) {
			if (null != authorities) {
				userDetail.authorities = authorities;
			}
			return this;
		}

		public UserDetail build() {
			return userDetail;
		}
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public Long getTenantId() {
		return tenantId;
	}

	public void setUserInfoId(Long userInfoId) {
		this.userInfoId = userInfoId;
	}

	public Long getUserInfoId() {
		return userInfoId;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	public void setAccountNonLocked(boolean isAccountNonLocked) {
		this.isAccountNonLocked = isAccountNonLocked;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
