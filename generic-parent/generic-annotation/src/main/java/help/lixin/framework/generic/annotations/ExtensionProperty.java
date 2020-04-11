package help.lixin.framework.generic.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExtensionProperty {
	// 扩展的属性名称
	String name() default "";

	// 扩展的属性内容
	String value() default "";
}
