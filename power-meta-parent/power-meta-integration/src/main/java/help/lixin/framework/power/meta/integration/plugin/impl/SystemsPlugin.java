package help.lixin.framework.power.meta.integration.plugin.impl;

import help.lixin.framework.power.meta.Systems;
import help.lixin.framework.power.meta.integration.context.PowerMetaContext;
import help.lixin.framework.power.meta.integration.plugin.Plugin;


public class SystemsPlugin implements Plugin {
	@Override
	public void apply(PowerMetaContext context) {
		// 判断类上是否有注解:@Systems
		if (context.hasClassAnnotation(Systems.class)) {
			// 获得类上的注解(@Systems)信息
			Class<?> clazz = context.getClazz();
		}
	}
}
