package help.lixin.framework.auth.model;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = -8761276687766439958L;
	// 用户表主键
	private Long userId;
	// 用户基本信息主键
	private Long userInfoId;
	// 用户状态(0:禁用 1:启用)
	private short status;
	// 登录类型(手机号码/邮箱/用户名)或者第三方应用名称(微信/微博)
	private String type;
	// 标识(手机号码/邮箱/用户名)或者第三方的唯一标识
	private String identifier;
	// 密码凭证(站内保存的密码,站外不做保存或保存为token)
	private String credential;

	// 用户详细信息
	private UserInfo userInfo;

	public static Builder newBuilder() {
		return new Builder();
	}

	public static class Builder {
		private User user = new User();

		public Builder userId(Long userId) {
			user.userId = userId;
			return this;
		}

		public Builder userInfoId(Long userInfoId) {
			user.userInfoId = userInfoId;
			return this;
		}

		public Builder status(short status) {
			user.status = status;
			return this;
		}

		public Builder type(String type) {
			user.type = type;
			return this;
		}

		public Builder identifier(String identifier) {
			user.identifier = identifier;
			return this;
		}

		public Builder credential(String credential) {
			user.credential = credential;
			return this;
		}

		public Builder userInfo(UserInfo userInfo) {
			user.userInfo = userInfo;
			return this;
		}

		public User build() {
			return user;
		}
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserInfoId() {
		return userInfoId;
	}

	public void setUserInfoId(Long userInfoId) {
		this.userInfoId = userInfoId;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getCredential() {
		return credential;
	}

	public void setCredential(String credential) {
		this.credential = credential;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userInfoId=" + userInfoId + ", status=" + status + ", type=" + type
				+ ", identifier=" + identifier + ", credential=" + credential + ", userInfo=" + userInfo + "]";
	}
}
