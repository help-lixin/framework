package help.lixin.framework.code.generator.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Propertys {
    public Property[] propertys();
}
