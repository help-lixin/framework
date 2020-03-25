package help.lixin.framework.power.meta.integration.context;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

import org.springframework.core.annotation.AnnotatedElementUtils;

public class PowerMetaContext {
	private final Set<String> urls;
	private final Object instance;
	private final Class<?> clazz;
	private final Method method;

	public PowerMetaContext( //
			Set<String> urls, //
			Object instance, //
			Class<?> clazz, //
			Method method) {
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
