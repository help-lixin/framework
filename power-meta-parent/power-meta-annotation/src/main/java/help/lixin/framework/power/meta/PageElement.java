package help.lixin.framework.power.meta;

/**
 * 页面元素
 * 
 * @author lixin
 *
 */
public @interface PageElement {

	/**
	 * 页面元素ID<br/>
	 * 如果指定了ID,则使用ID.<br/>
	 * 如果没有指定ID,则使用name+code
	 * 
	 * @return
	 */
	String id() default "";

	/**
	 * 页面元素名称
	 * 
	 * @return
	 */
	String name();

	/**
	 * 
	 * @return
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