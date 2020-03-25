package help.lixin.framework.power.meta;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 页面元素
 * @author lixin
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface PageElement {

	/**
	 * 页面元素ID<br/>
	 * 如果指定了ID,则使用ID.<br/>
	 * 如果没有指定ID,则使用name+code
	 */
	String id() default "";

	/**
	 * 页面元素名称
	 */
	String name();

	/**
	 * 页面元素唯一编码
	 */
	String code();

	/**
	 * 所属于哪个菜单下面
	 * 
	 * @return
	 */
	String menuId();

	/**
	 * 顺序
	 * 
	 * @return
	 */
	int order() default 1;
}