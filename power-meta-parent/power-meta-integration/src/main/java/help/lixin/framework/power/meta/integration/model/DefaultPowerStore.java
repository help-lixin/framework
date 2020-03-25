package help.lixin.framework.power.meta.integration.model;

import java.util.Set;
import java.util.TreeSet;

import help.lixin.framework.power.meta.model.MenuModel;
import help.lixin.framework.power.meta.model.OperationModel;
import help.lixin.framework.power.meta.model.PageElementModel;
import help.lixin.framework.power.meta.model.SystemModel;

/**
 * 权限信息存储
 * 
 * @author lixin
 */
public class DefaultPowerStore implements PowerStore {

	private Set<SystemModel> systems = new TreeSet<SystemModel>();

	private Set<MenuModel> menus = new TreeSet<MenuModel>();

	private Set<OperationModel> operations = new TreeSet<>();

	private Set<PageElementModel> pageElements = new TreeSet<>();

	public void addSystem(SystemModel system) {
		systems.add(system);
	}

	public void addMenu(MenuModel menu) {
		menus.add(menu);
	}

	public void addOperation(OperationModel operation) {
		operations.add(operation);
	}

	public void addPageElement(PageElementModel pageElement) {
		pageElements.add(pageElement);
	}

	public Set<SystemModel> getSystems() {
		return systems;
	}

	public Set<MenuModel> getMenus() {
		return menus;
	}

	public Set<OperationModel> getOperations() {
		return operations;
	}

	public Set<PageElementModel> getPageElements() {
		return pageElements;
	}
}
