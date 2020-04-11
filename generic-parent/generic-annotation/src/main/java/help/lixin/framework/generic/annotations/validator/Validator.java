package help.lixin.framework.generic.annotations.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import help.lixin.framework.generic.annotations.ExtensionProperty;

@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface Validator {

	// 对应属性名称
	String name() default "";
	
	// 对验证进行分组
	Class<?>[] groups() default {};

	// 验证的详细描述
	String description() default "";

	// 约束名称(简写名称/或全路径名称)
	String rule() default "";

	// 提示的消息内容
	String message() default "";

	// 扩展信息
	ExtensionProperty[] extProperties() default @ExtensionProperty();
}
