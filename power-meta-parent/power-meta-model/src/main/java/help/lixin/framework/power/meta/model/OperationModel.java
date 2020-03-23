package help.lixin.framework.power.meta.model;

import java.io.Serializable;

public class OperationModel implements Serializable {
	private static final long serialVersionUID = 7179643628839518945L;

	/**
	 * 操作ID<br/>
	 * 如果指定了ID,则使用ID.<br/>
	 * 如果没有指定ID,则使用name+code
	 */
	private String id;

	/**
	 * 操作名称
	 */
	private String name;

	/**
	 * 操作code
	 */
	private String code;

	/**
	 * 操作的URL.当与SpringMVC结合时,不需要指定URL,会读取Spring的注解信息.获得URL<br/>
	 * 如果有指定URL,则使用指定的url,否则,读取注解上的URL
	 */
	private String url = "#";

	/**
	 * 菜单ID
	 */
	private String menuId = "-1";

	/**
	 * 顺序
	 */
	private int order = 1;

	public static Builder newBuilder() {
		return new Builder();
	}

	public static class Builder {
		private OperationModel operationModel = new OperationModel();

		public OperationModel builder() {
			return operationModel;
		}

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
}
