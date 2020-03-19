package help.lixin.framework.swagger.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {
	// 标题
	private String title;
	// 描述
	private String description;
	// 服务地址
	private String srviceUrl;
	// 扫描路径
	private String basePackage;
	// 联系人
	private String contact;
	// 版本
	private String version;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSrviceUrl() {
		return srviceUrl;
	}

	public void setSrviceUrl(String srviceUrl) {
		this.srviceUrl = srviceUrl;
	}

	public String getBasePackage() {
		return basePackage;
	}

	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "SwaggerProperties [title=" + title + ", description=" + description + ", srviceUrl=" + srviceUrl
				+ ", basePackage=" + basePackage + ", contact=" + contact + ", version=" + version + "]";
	}
}
