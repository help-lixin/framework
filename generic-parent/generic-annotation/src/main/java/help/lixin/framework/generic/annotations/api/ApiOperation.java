package help.lixin.framework.generic.annotations.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import help.lixin.framework.generic.annotations.ExtensionProperty;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiOperation {
	// 操作的简要说明
	String value();

	// 对操作的详细描述
	String description() default "";

	// 响应类型(User.class/Integer.class)
	Class<?> response() default Void.class;

	// 声明包装的容器(Set/Map/List)
	String responseContainer() default "";

	// 接口请求的方式("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS" and "PATCH".)
	String httpMethod() default "";

	// 接口操作者是谁
	String nickname() default "";

	boolean hidden() default false;
	
	// 扩展信息
	ExtensionProperty[] extProperties() default @ExtensionProperty();
}
