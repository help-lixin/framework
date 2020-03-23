package help.lixin.framework.power.meta;

/**
 * 操作
 * 
 * @author lixin
 */
public @interface Operation {

	/**
	 * 操作ID<br/>
	 * 如果指定了ID,则使用ID.<br/>
	 * 如果没有指定ID,则使用name+code
	 * 
	 * @return
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
	 * 
	 * @return
	 */
	String code();

	/**
	 * 操作的URL.当与SpringMVC结合时,不需要指定URL,会读取Spring的注解信息.获得URL<br/>
	 * 如果有指定URL,则使用指定的url,否则,读取注解上的URL
	 * 
	 * @return
	 */
	String url() default "#";

	/**
	 * 菜单ID
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