package help.lixin.framework.power.meta.integration.bootstrapper;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.context.SmartLifecycle;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import help.lixin.framework.power.meta.integration.context.PowerMetaContext;
import help.lixin.framework.power.meta.integration.model.PowerStore;
import help.lixin.framework.power.meta.integration.plugin.Plugin;

/**
 * 收集元数据
 * 
 * @author lixin
 */
public class CollectionPowerMetaBootstrapper implements SmartLifecycle {
	// 是否运行过,如果已经运行过,则不会再次运行.
	private AtomicBoolean isRunning = new AtomicBoolean(Boolean.FALSE);
	// 获取Spring中所有的URL与对应的Controller
	private RequestMappingHandlerMapping requestMappingHandlerMapping;
	// 最终数据落地
	private PowerStore powerStore;
	// 插件链
	private Plugin pluginChain;

	public void setPluginChain(Plugin pluginChain) {
		this.pluginChain = pluginChain;
	}

	public Plugin getPluginChain() {
		return pluginChain;
	}

	public void setPowerStore(PowerStore powerStore) {
		this.powerStore = powerStore;
	}

	public PowerStore getPowerStore() {
		return powerStore;
	}

	public void setRequestMappingHandlerMapping(RequestMappingHandlerMapping requestMappingHandlerMapping) {
		this.requestMappingHandlerMapping = requestMappingHandlerMapping;
	}

	public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
		return requestMappingHandlerMapping;
	}

	@Override
	public void start() {
		// 标记为已经运行过
		if (!isRunning.getAndSet(Boolean.TRUE)) {
			Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
			for (RequestMappingInfo rmi : handlerMethods.keySet()) {
				HandlerMethod handlerMethod = handlerMethods.get(rmi);
				// 获得URLS
				Set<String> urls = rmi.getPatternsCondition().getPatterns();
				// 获得类
				Class<?> beanType = handlerMethod.getBeanType();
				// 获得实例
				Object bean = handlerMethod.getBean();
				// 获得方法信息
				Method method = handlerMethod.getMethod();
				// 创建上下文
				final PowerMetaContext context = new PowerMetaContext(powerStore, urls, bean, beanType, method);
				// 可以在之前和之后增加Hook
				pluginChain.apply(context);
			}
		}
	}

	@Override
	public void stop() {
	}

	@Override
	public boolean isRunning() {
		return isRunning.get();
	}

	@Override
	public int getPhase() {
		return 0;
	}

	@Override
	public boolean isAutoStartup() {
		return true;
	}

	@Override
	public void stop(Runnable callback) {
	}
}
