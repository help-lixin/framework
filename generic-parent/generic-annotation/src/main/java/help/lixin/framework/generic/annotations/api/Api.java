package help.lixin.framework.generic.annotations.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import help.lixin.framework.generic.annotations.ExtensionProperty;

// 注解只能应用于类上
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Api {
	// API描述信息
	String value();

	// API详细描述信息
	String description() default "";

	// 扩展信息
	ExtensionProperty[] extProperties() default @ExtensionProperty();
}
