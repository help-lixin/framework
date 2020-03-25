package help.lixin.framework.power.meta;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 系统
 * @author lixin
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface System {

	/**
	 * 系统ID<br/>
	 * 如果指定了id,则使用ID.<br/>
	 * 如果没有指定id,则使用:name+code进行md5产生ID<br/>
	 * 
	 * @return
	 */
	String id() default "";

	/**
	 * 系统名称
	 * 
	 * @return
	 */
	String name();

	/**
	 * 系统编码
	 * 
	 * @return
	 */
	String code();

	/**
	 * 所属系统的父ID
	 * 
	 * @return
	 */
	String parentId() default "-1";

	/**
	 * 系统描述
	 * 
	 * @return
	 */
	String desc() default "";

	/**
	 * 顺序
	 * 
	 * @return
	 */
	int order() default 1;
}
