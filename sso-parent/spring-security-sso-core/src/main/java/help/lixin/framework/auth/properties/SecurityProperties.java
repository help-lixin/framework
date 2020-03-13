package help.lixin.framework.auth.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.auth")
public class SecurityProperties {
	// 登录页面
	private String loginPage;
	// 登录页面提交时处理的URL
	private String loginProcessingUrl;
	// 允许放行的URL信息
	private String[] permitUrls;

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

	@Override
	public String toString() {
		return "SecurityProperties [loginPage=" + loginPage + ", loginProcessingUrl=" + loginProcessingUrl
				+ ", permitUrls=" + permitUrls + "]";
	}

}
