package help.lixin.framework.generic.annotations.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import help.lixin.framework.generic.annotations.ExtensionProperty;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiParam {
	// 重写属性名称
	String name() default "";

	// 对字段的描述
	String value() default "";

	// 是否必传参数
	boolean required() default false;

	// 是否显示
	boolean hidden() default false;

	// 数据类型
	String dataType() default "";

	// 演示信息
	String example() default "";

	// 入参类型
	String paramType() default "";

	// 扩展信息
	ExtensionProperty[] properties() default @ExtensionProperty();
}
