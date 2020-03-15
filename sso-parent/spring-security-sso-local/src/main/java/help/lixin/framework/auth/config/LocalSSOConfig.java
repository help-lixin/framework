package help.lixin.framework.auth.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import help.lixin.framework.auth.service.IResourceService;
import help.lixin.framework.auth.service.IUserDetailService;
import help.lixin.framework.auth.service.impl.LocalResourceService;
import help.lixin.framework.auth.service.impl.LocalUserDetailService;

@Configuration
@MapperScan("help.lixin.framework.auth.mapper")
public class LocalSSOConfig {

	@Bean
	public IUserDetailService userDetailService() {
		return new LocalUserDetailService();
	}

	@Bean
	public IResourceService resourceService() {
		return new LocalResourceService();
	}

}
