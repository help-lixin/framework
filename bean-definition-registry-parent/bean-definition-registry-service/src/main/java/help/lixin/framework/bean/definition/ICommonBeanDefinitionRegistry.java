package help.lixin.framework.bean.definition;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 定义公共的BeanDefintition注册回调,留给业务处理
 * 
 * @author lixin
 *
 */
public interface ICommonBeanDefinitionRegistry {
	void registerBeanDefinitions(Environment environment, AnnotationMetadata importingClassMetadata,
			BeanDefinitionRegistry registry);
}
