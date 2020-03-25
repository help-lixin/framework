package help.lixin.framework.power.meta;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 菜单
 * @author lixin
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface Menu {

	/**
	 * 菜单唯一ID<br/>
	 * 如果指定了ID,则使用ID.<br/>
	 * 如果没有指定ID,则使用name+url
	 */
	String id() default "";

	/**
	 * 菜单名称
	 */
	String name();

	/**
	 * 菜单URL. 当与SpringMVC结合时,不需要指定URL,会读取Spring的注解信息.获得URL
	 */
	String url() default "#";

	/**
	 * 菜单的父ID
	 */
	String parentId() default "-1";

	/**
	 * 顺序
	 */
	int order() default 1;
}