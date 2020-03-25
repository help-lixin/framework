package help.lixin.framework.power.meta.integration.model;

import java.util.Set;

import help.lixin.framework.power.meta.model.MenuModel;
import help.lixin.framework.power.meta.model.OperationModel;
import help.lixin.framework.power.meta.model.PageElementModel;
import help.lixin.framework.power.meta.model.SystemModel;

public interface PowerStore {
	public void addSystem(SystemModel system);

	public void addMenu(MenuModel menu);

	public void addOperation(OperationModel operation);

	public void addPageElement(PageElementModel pageElement);

	public Set<SystemModel> getSystems();

	public Set<MenuModel> getMenus();

	public Set<OperationModel> getOperations();

	public Set<PageElementModel> getPageElements();
}
