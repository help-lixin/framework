package help.lixin.framework.generic.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiOperationModel implements Serializable {
	private static final long serialVersionUID = 4908138739810800742L;
	// 接口URL
	private String value;
	// 接口描述
	private String description;
	// 接口返回类型
	private Class<?> response;

	// 声明包装的容器(Set/Map/List)
	private String responseContainer;

	// 接口请求的方式("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS" and "PATCH".)
	private String httpMethod;

	// 接口操作者是谁
	private String nickname;

	// 是否隐藏
	private boolean hidden = false;

	// 扩展属性
	private Map<String, String> extProperties = new HashMap<>();

	// 属性信息
	private List<PropertyModel> propertys = new ArrayList<PropertyModel>();

	public List<PropertyModel> getPropertys() {
		return propertys;
	}

	public void addPropertys(PropertyModel property) {
		getPropertys().add(property);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Class<?> getResponse() {
		return response;
	}

	public void setResponse(Class<?> response) {
		this.response = response;
	}

	public String getResponseContainer() {
		return responseContainer;
	}

	public void setResponseContainer(String responseContainer) {
		this.responseContainer = responseContainer;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public Map<String, String> getExtProperties() {
		return extProperties;
	}

	public void addExtProperties(String name, String value) {
		getExtProperties().put(name, value);
	}
}
