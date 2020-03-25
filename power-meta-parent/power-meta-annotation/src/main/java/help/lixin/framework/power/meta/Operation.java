package help.lixin.framework.power.meta;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 操作
 * @author lixin
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface Operation {

	/**
	 * 操作ID<br/>
	 * 如果指定了ID,则使用ID.<br/>
	 * 如果没有指定ID,则使用name+code
	 */
	String id() default "";

	/**
	 * 操作名称
	 * 
	 * @return
	 */
	String name();

	/**
	 * 操作code
	 */
	String code();

	/**
	 * 操作的URL.当与SpringMVC结合时,不需要指定URL,会读取Spring的注解信息.获得URL<br/>
	 * 如果有指定URL,则使用指定的url,否则,读取注解上的URL
	 */
	String url() default "#";

	/**
	 * 菜单ID
	 */
	String menuId();

	/**
	 * 顺序
	 */
	int order() default 1;
}