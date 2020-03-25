package help.lixin.framework.power.meta.integration.plugin.impl;

import help.lixin.framework.power.meta.Menu;
import help.lixin.framework.power.meta.integration.context.PowerMetaContext;
import help.lixin.framework.power.meta.integration.plugin.Plugin;

public class MenuPlugin implements Plugin {

	@Override
	public void apply(PowerMetaContext context) {
		// 判断类上是否有注解:@Menu
		if (context.hasClassAnnotation(Menu.class)) {
			// 获得类上的注解(@Menu)信息
			Class<?> clazz = context.getClazz();
		}
		
	}
}
