package help.lixin.framework.power.meta.integration.service;

import java.util.Map;
import java.util.Set;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

public class CollectionMetaHandler implements ApplicationListener<ContextRefreshedEvent> {

	private RequestMappingHandlerMapping requestMappingHandlerMapping;

	public void setRequestMappingHandlerMapping(RequestMappingHandlerMapping requestMappingHandlerMapping) {
		this.requestMappingHandlerMapping = requestMappingHandlerMapping;
	}

	public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
		return requestMappingHandlerMapping;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
		for (RequestMappingInfo rmi : handlerMethods.keySet()) {
			HandlerMethod handlerMethod = handlerMethods.get(rmi);
			Set<String> url = rmi.getPatternsCondition().getPatterns();
			// 获得类
			Class<?> clazz = handlerMethod.getBeanType();
			// handlerMethod.getMethodAnnotation(annotationType);
			// 解析类上的注解,进行注册.
		}
	}
}
