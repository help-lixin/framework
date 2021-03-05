package help.lixin.framework.code.generator.core.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Property {
    // 属性名称
    public String name();

    // 属性值
    public String value();

    // 描述
    public String desc() default "";
}
