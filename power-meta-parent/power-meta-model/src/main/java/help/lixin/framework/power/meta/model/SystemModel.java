package help.lixin.framework.power.meta.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

public class SystemModel implements Serializable {
	private static final long serialVersionUID = 7200647607475859022L;
	/**
	 * 系统ID<br/>
	 * 如果指定了id,则使用ID.<br/>
	 * 如果没有指定id,则使用:name+code进行md5产生ID<br/>
	 */
	private String id;

	/**
	 * 系统名称
	 */
	private String name;

	/**
	 * 系统编码
	 */
	private String code;

	/**
	 * 所属系统的父ID
	 */
	private String parentId = "-1";

	/**
	 * 系统描述
	 */
	private String desc;

	/**
	 * 顺序
	 */
	private int order = 1;

	// 所有的子系统
	private Set<SystemModel> systems = new TreeSet<>();

	// 所有的菜单
	private Set<MenuModel> menus = new TreeSet<>();

	// 所有的页面元素
	private Set<PageElementModel> elements = new TreeSet<>();

	public void addPageElement(PageElementModel pageElementModel) {
		if (null != pageElementModel) {
			elements.add(pageElementModel);
		}
	}

	public void addPageElements(Set<PageElementModel> pageElementModels) {
		if (null != pageElementModels) {
			elements.addAll(pageElementModels);
		}
	}

	public void setElements(Set<PageElementModel> elements) {
		this.elements = elements;
	}

	public Set<PageElementModel> getElements() {
		return elements;
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

	public void addSystem(SystemModel systemModel) {
		if (null != systemModel) {
			systems.add(systemModel);
		}
	}

	public void addSystems(Collection<SystemModel> systemModels) {
		if (null != systemModels) {
			systems.addAll(systemModels);
		}
	}

	public void setMenus(Set<MenuModel> menus) {
		this.menus = menus;
	}

	public Set<MenuModel> getMenus() {
		return menus;
	}

	public Set<SystemModel> getSystems() {
		return systems;
	}

	public void setSystems(Set<SystemModel> systems) {
		this.systems = systems;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static class Builder {
		private SystemModel model = new SystemModel();

		/**
		 * 系统ID<br/>
		 * 如果指定了id,则使用ID.<br/>
		 * 如果没有指定id,则使用:name+code进行md5产生ID<br/>
		 * 
		 * @return
		 */
		public Builder id(String id) {
			model.id = id;
			return this;
		}

		/**
		 * 系统名称
		 * 
		 * @return
		 */
		public Builder name(String name) {
			model.name = name;
			return this;
		}

		/**
		 * 系统编码
		 * 
		 * @return
		 */
		public Builder code(String code) {
			model.code = code;
			return this;
		}

		/**
		 * 所属系统的父ID
		 * 
		 * @return
		 */
		public Builder parentId(String parentId) {
			model.parentId = parentId;
			return this;
		}

		/**
		 * 系统描述
		 * 
		 * @return
		 */
		public Builder desc(String desc) {
			model.desc = desc;
			return this;
		}

		/**
		 * 顺序
		 * 
		 * @return
		 */
		public Builder order(int order) {
			model.order = order;
			return this;
		}

		public SystemModel build() {
			return model;
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

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
}
