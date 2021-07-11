package help.lixin.framework.cache.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CacheableExt {

	/**
	 * 扩展属性的key
	 * 
	 * @return
	 */
	String key() default "";

	/**
	 * 扩展属性对应的value
	 * 
	 * @return
	 */
	String value() default "";
}