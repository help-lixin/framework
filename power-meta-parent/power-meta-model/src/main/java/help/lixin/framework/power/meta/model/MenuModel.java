package help.lixin.framework.power.meta.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

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

	/**
	 * 所有的子菜单
	 */
	private Set<MenuModel> menus = new TreeSet<>();

	/**
	 * 所有的操作
	 */
	private Set<OperationModel> operations = new TreeSet<>();

	public void addOperation(OperationModel operationModel) {
		if (null != operations) {
			operations.add(operationModel);
		}
	}

	public void addOperations(Collection<OperationModel> operationModels) {
		if (null != operationModels) {
			operations.addAll(operationModels);
		}
	}

	public void setOperations(Set<OperationModel> operations) {
		this.operations = operations;
	}

	public Set<OperationModel> getOperations() {
		return operations;
	}

	public void addMenu(MenuModel menuModel) {
		if (null != menuModel) {
			menus.add(menuModel);
		}
	}

	public void addMenus(Collection<MenuModel> menuModels) {
		if (null != menuModels) {
			menus.addAll(menuModels);
		}
	}

	public void setMenus(Set<MenuModel> menus) {
		this.menus = menus;
	}

	public Set<MenuModel> getMenus() {
		return menus;
	}

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

		public Builder operation(OperationModel operationModel) {
			if (null != operationModel) {
				menuModel.operations.add(operationModel);
			}
			return this;
		}

		public Builder operations(Collection<OperationModel> operationModels) {
			if (null != operationModels) {
				menuModel.operations.addAll(operationModels);
			}
			return this;
		}

		public Builder menus(Collection<MenuModel> menus) {
			if (null != menus) {
				menuModel.menus.addAll(menus);
			}
			return this;
		}

		public Builder menu(MenuModel menu) {
			if (null != menu) {
				menuModel.menus.add(menu);
			}
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
	public String toString() {
		return "MenuModel [id=" + id + ", name=" + name + ", url=" + url + ", parentId=" + parentId + ", order=" + order
				+ "]";
	}
}
