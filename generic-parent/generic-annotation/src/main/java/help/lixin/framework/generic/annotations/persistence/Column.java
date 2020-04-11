package help.lixin.framework.generic.annotations.persistence;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import help.lixin.framework.generic.annotations.ExtensionProperty;

@Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
	// 对应属性名称
	String name() default "";

	// 对应DB的类名称
	String cloumn();

	// 列的详细描述
	String columnDefinition() default "";

	// 扩展信息
	ExtensionProperty[] extProperties() default @ExtensionProperty();
}
