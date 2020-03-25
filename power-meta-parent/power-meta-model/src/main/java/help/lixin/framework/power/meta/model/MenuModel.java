package help.lixin.framework.power.meta.model;

import java.io.Serializable;

public class MenuModel implements Serializable {
	private static final long serialVersionUID = -5947609531395706033L;

	/**
	 * 菜单唯一ID<br/>
	 * 如果指定了ID,则使用ID.<br/>
	 * 如果没有指定ID,则使用name+url <br/>
	 */
	private String id;

	/**
	 * 菜单名称
	 */
	private String name;

	/**
	 * 菜单URL. 当与SpringMVC结合时,不需要指定URL,会读取Spring的注解信息.获得URL<br/>
	 */
	private String url = "#";

	/**
	 * 菜单的父ID
	 */
	private String parentId = "-1";

	/**
	 * 顺序
	 */
	private int order = 1;

	public static Builder newBuilder() {
		return new Builder();
	}

	public static class Builder {
		private MenuModel menuModel = new MenuModel();

		/**
		 * 菜单唯一ID<br/>
		 * 如果指定了ID,则使用ID.<br/>
		 * 如果没有指定ID,则使用name+url <br/>
		 * 
		 * @return
		 */
		public Builder id(String id) {
			menuModel.id = id;
			return this;
		}

		/**
		 * 菜单名称
		 * 
		 * @return
		 */
		public Builder name(String name) {
			menuModel.name = name;
			return this;
		}

		/**
		 * 菜单URL. 当与SpringMVC结合时,不需要指定URL,会读取Spring的注解信息.获得URL<br/>
		 * 
		 * @return
		 */
		public Builder url(String url) {
			menuModel.url = url;
			return this;
		}

		/**
		 * 菜单的父ID
		 * 
		 * @return
		 */
		public Builder parentId(String parentId) {
			menuModel.parentId = parentId;
			return this;
		}

		/**
		 * 顺序
		 * 
		 * @return
		 */
		public Builder order(int order) {
			menuModel.order = order;
			return this;
		}

		public MenuModel build() {
			return menuModel;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MenuModel other = (MenuModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MenuModel [id=" + id + ", name=" + name + ", url=" + url + ", parentId=" + parentId + ", order=" + order
				+ "]";
	}
}
