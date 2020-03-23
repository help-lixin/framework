package help.lixin.framework.power.meta.model;

import java.io.Serializable;

public class PageElementModel implements Serializable {
	private static final long serialVersionUID = 6205203832144808453L;
	/**
	 * 页面元素ID<br/>
	 * 如果指定了ID,则使用ID.<br/>
	 * 如果没有指定ID,则使用:name+code<br/>
	 */
	private String id;
	/**
	 * 页面元素名称
	 */
	private String name;
	/**
	 * 页面元素唯一标识
	 */
	private String code;
	/**
	 * 顺序
	 */
	private int order = 1;

	/**
	 * 属于哪个菜单ID
	 */
	private String menuId;

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuId() {
		return menuId;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static class Builder {
		private PageElementModel pageElementModel = new PageElementModel();

		/**
		 * 页面元素ID<br/>
		 * 如果指定了ID,则使用ID.<br/>
		 * 如果没有指定ID,则使用name+code
		 */
		public Builder id(String id) {
			pageElementModel.id = id;
			return this;
		}

		public Builder menuId(String menuId) {
			pageElementModel.menuId = menuId;
			return this;
		}

		/**
		 * 页面元素名称
		 */
		public Builder name(String name) {
			pageElementModel.name = name;
			return this;
		}

		public Builder code(String code) {
			pageElementModel.code = code;
			return this;
		}

		/**
		 * 顺序
		 */
		public Builder order(int order) {
			pageElementModel.order = order;
			return this;
		}

		public PageElementModel build() {
			return pageElementModel;
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

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "PageElementModel [id=" + id + ", name=" + name + ", code=" + code + ", order=" + order + "]";
	}
}
