package help.lixin.framework.power.meta.integration.plugin.impl;

import help.lixin.framework.power.meta.Menus;
import help.lixin.framework.power.meta.integration.context.PowerMetaContext;
import help.lixin.framework.power.meta.integration.plugin.Plugin;

public class MenusPlugin implements Plugin {

	@Override
	public void apply(PowerMetaContext context) {
		// 判断类上是否有注解:@Systems
		if (context.hasClassAnnotation(Menus.class)) {
			// 获得类上的注解(@Menus)信息
			Class<?> clazz = context.getClazz();
		}
	}
}
