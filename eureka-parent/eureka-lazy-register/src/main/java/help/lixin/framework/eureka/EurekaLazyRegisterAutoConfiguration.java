package help.lixin.framework.eureka;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import help.lixin.framework.eureka.config.EurekaLazyRegisterConfig;
import help.lixin.framework.eureka.properties.EurekaLazyRegisterProperties;

@Configuration
@EnableConfigurationProperties(EurekaLazyRegisterProperties.class)
@Import({EurekaLazyRegisterConfig.class})
@ConditionalOnProperty(prefix = "eureka.lazy", name = "enabled", havingValue = "true")
public class EurekaLazyRegisterAutoConfiguration {
	
}
