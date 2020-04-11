package help.lixin.framework.power.meta.integration.plugin.impl;

import java.lang.reflect.Method;

import help.lixin.framework.power.meta.Menu;
import help.lixin.framework.power.meta.integration.context.PowerMetaContext;
import help.lixin.framework.power.meta.integration.plugin.Plugin;

public class MenuPlugin implements Plugin {

	@Override
	public void apply(PowerMetaContext context) {
		if (context.hasMethodAnnotation(Menu.class)) {
			// 获得方法上的注解(@Menu)信息
			Method method = context.getMethod();
			System.out.println(method);
		}
	}
}
