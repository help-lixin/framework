package help.lixin.framework.auth.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.auth")
public class SecurityProperties {
	// auth实现:本地实现/远程实现.
	private String impl = "local";
	// 加载资源时的缓存时间(分钟为单位)
	private long resourceCacheExpireMinutes = 5;
	// 加载用户信息时的缓存时间(分钟为单位)
	private long userCacheExpireMinutes = 5;
	// 登录页面
	private String loginPage;
	// 登录页面提交时处理的URL
	private String loginProcessingUrl;
	// 允许放行的URL信息
	private String[] permitUrls;

	public void setImpl(String impl) {
		this.impl = impl;
	}

	public String getImpl() {
		return impl;
	}

	public String getLoginPage() {
		return loginPage;
	}

	public void setLoginPage(String loginPage) {
		this.loginPage = loginPage;
	}

	public String getLoginProcessingUrl() {
		return loginProcessingUrl;
	}

	public void setLoginProcessingUrl(String loginProcessingUrl) {
		this.loginProcessingUrl = loginProcessingUrl;
	}

	public String[] getPermitUrls() {
		return permitUrls;
	}

	public void setPermitUrls(String[] permitUrls) {
		this.permitUrls = permitUrls;
	}

	public long getResourceCacheExpireMinutes() {
		return resourceCacheExpireMinutes;
	}

	public void setResourceCacheExpireMinutes(long resourceCacheExpireMinutes) {
		this.resourceCacheExpireMinutes = resourceCacheExpireMinutes;
	}

	public long getUserCacheExpireMinutes() {
		return userCacheExpireMinutes;
	}

	public void setUserCacheExpireMinutes(long userCacheExpireMinutes) {
		this.userCacheExpireMinutes = userCacheExpireMinutes;
	}

	@Override
	public String toString() {
		return "SecurityProperties [loginPage=" + loginPage + ", loginProcessingUrl=" + loginProcessingUrl
				+ ", permitUrls=" + permitUrls + "]";
	}

}
