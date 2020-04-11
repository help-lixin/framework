package help.lixin.framework.generic.annotations.persistence;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Table {
	// 数据库名称
	String schema() default "";

	// 表名称
	String table();

	// 列信息
	Columns columns() default @Columns;
}
