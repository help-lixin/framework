package help.lixin.framework.power.meta;

/**
 * 菜单
 * 
 * @author lixin
 *
 */
public @interface Menu {

	/**
	 * 菜单唯一ID<br/>
	 * 如果指定了ID,则使用ID.<br/>
	 * 如果没有指定ID,则使用name+url
	 * 
	 * @return
	 */
	String id() default "";

	/**
	 * 菜单名称
	 * 
	 * @return
	 */
	String name();

	/**
	 * 菜单URL. 当与SpringMVC结合时,不需要指定URL,会读取Spring的注解信息.获得URL
	 * 
	 * @return
	 */
	String url() default "#";

	/**
	 * 菜单的父ID
	 * 
	 * @return
	 */
	String parentId() default "-1";

	/**
	 * 顺序
	 * 
	 * @return
	 */
	int order() default 1;
}