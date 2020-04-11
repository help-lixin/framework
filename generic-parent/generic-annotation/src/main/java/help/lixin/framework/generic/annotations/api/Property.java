package help.lixin.framework.generic.annotations.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import help.lixin.framework.generic.annotations.ExtensionProperty;
import help.lixin.framework.generic.annotations.validator.Validators;

@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Property {
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

	// 与DB相关
//	Column column() default @Column(cloumn = "");

	// 验证规则
	Validators validators() default @Validators;

	// 扩展信息
	ExtensionProperty[] properties() default @ExtensionProperty();
}
