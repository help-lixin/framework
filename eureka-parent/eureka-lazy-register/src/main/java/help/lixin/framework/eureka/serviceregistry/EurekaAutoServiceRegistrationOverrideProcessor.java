package help.lixin.framework.eureka.serviceregistry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;

public class EurekaAutoServiceRegistrationOverrideProcessor implements BeanFactoryPostProcessor {
    private Logger logger = LoggerFactory.getLogger(EurekaAutoServiceRegistrationOverrideProcessor.class);

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        if (beanFactory instanceof DefaultListableBeanFactory) {
            DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) beanFactory;
            String beanName = "eurekaAutoServiceRegistration";
            boolean res = beanFactory.containsBeanDefinition(beanName);
            if (res) {
                defaultListableBeanFactory.removeBeanDefinition(beanName);
                BeanDefinition eurekaAutoServiceRegistrationOverrideDefinition = new RootBeanDefinition(
                        EurekaAutoServiceRegistrationOverride.class);
                ((DefaultListableBeanFactory) beanFactory).registerBeanDefinition(beanName, eurekaAutoServiceRegistrationOverrideDefinition);
                logger.info("Override EurekaAutoServiceRegistration To EurekaAutoServiceRegistrationOverride SUCCESS");
            } else {
                logger.warn("Override EurekaAutoServiceRegistration To EurekaAutoServiceRegistrationOverride FAIL,description = applicationContext NOT FOUND beanName:[{}]", beanName);
            }
            res = beanFactory.containsBeanDefinition(beanName);
            logger.info("Override EurekaAutoServiceRegistration To EurekaAutoServiceRegistrationOverride SUCCESS");
        } else {
            logger.warn("Override EurekaAutoServiceRegistration To EurekaAutoServiceRegistrationOverride FAIL,description = (ConfigurableListableBeanFactory !instanceof DefaultListableBeanFactory)");
        }
    }
}
