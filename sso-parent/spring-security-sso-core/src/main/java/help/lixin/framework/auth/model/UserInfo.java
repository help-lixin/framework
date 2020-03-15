package help.lixin.framework.auth.model;

import java.io.Serializable;
import java.util.Date;

public class UserInfo implements Serializable {
	private static final long serialVersionUID = -6781608632695894794L;
	// 用户基本信息表主键
	private Long userInfoId;
	// 昵称
	private String nickname;
	// 用户图标
	private String icon;
	// 注册时间
	private Date registerTime;
	// 租户ID
	private Long tenantId;

	public static Builder newBuilder() {
		return new Builder();
	}

	public static class Builder {
		private UserInfo userInfo = new UserInfo();

		public Builder userInfoId(Long userInfoId) {
			userInfo.userInfoId = userInfoId;
			return this;
		}

		public Builder tenantId(Long tenantId) {
			userInfo.tenantId = tenantId;
			return this;
		}

		public Builder nickname(String nickname) {
			userInfo.nickname = nickname;
			return this;
		}

		public Builder icon(String icon) {
			userInfo.icon = icon;
			return this;
		}

		public Builder registerTime(Date registerTime) {
			userInfo.registerTime = registerTime;
			return this;
		}

		public UserInfo builder() {
			return userInfo;
		}
	}

	public Long getUserInfoId() {
		return userInfoId;
	}

	public void setUserInfoId(Long userInfoId) {
		this.userInfoId = userInfoId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	@Override
	public String toString() {
		return "UserInfo [userInfoId=" + userInfoId + ", nickname=" + nickname + ", icon=" + icon + ", registerTime="
				+ registerTime + ", tenantId=" + tenantId + "]";
	}

}
