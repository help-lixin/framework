package help.lixin.framework.power.meta.integration.context;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

import org.springframework.core.annotation.AnnotatedElementUtils;

import help.lixin.framework.power.meta.integration.model.PowerStore;
import help.lixin.framework.power.meta.model.MenuModel;
import help.lixin.framework.power.meta.model.OperationModel;
import help.lixin.framework.power.meta.model.PageElementModel;
import help.lixin.framework.power.meta.model.SystemModel;

public class PowerMetaContext {
	private final PowerStore powerStore;
	private final Set<String> urls;
	private final Object instance;
	private final Class<?> clazz;
	private final Method method;

	public PowerMetaContext( //
			PowerStore powerStore, //
			Set<String> urls, //
			Object instance, //
			Class<?> clazz, //
			Method method) {
		this.powerStore = powerStore;
		this.urls = urls;
		this.instance = instance;
		this.clazz = clazz;
		this.method = method;
	}

	public Set<String> getUrls() {
		return urls;
	}

	public Object getInstance() {
		return instance;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public Method getMethod() {
		return method;
	}

	public PowerStore getPowerStore() {
		return powerStore;
	}

	public Set<SystemModel> getSystems() {
		return powerStore.getSystems();
	}

	public Set<MenuModel> getMenus() {
		return powerStore.getMenus();
	}

	public Set<OperationModel> getOperations() {
		return powerStore.getOperations();
	}

	public Set<PageElementModel> getPageElements() {
		return powerStore.getPageElements();
	}

	public <A extends Annotation> boolean hasClassAnnotation(Class<A> annotationType) {
		return AnnotatedElementUtils.hasAnnotation(this.clazz, annotationType);
	}

	public <A extends Annotation> A getClassAnnotation(Class<A> annotationType) {
		return AnnotatedElementUtils.findMergedAnnotation(this.clazz, annotationType);
	}

	public <A extends Annotation> boolean hasMethodAnnotation(Class<A> annotationType) {
		return AnnotatedElementUtils.hasAnnotation(this.method, annotationType);
	}

	public <A extends Annotation> A getMethodAnnotation(Class<A> annotationType) {
		return AnnotatedElementUtils.findMergedAnnotation(this.method, annotationType);
	}
}
