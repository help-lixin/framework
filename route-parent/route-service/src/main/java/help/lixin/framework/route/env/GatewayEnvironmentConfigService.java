package help.lixin.framework.route.env;

import help.lixin.framework.environment.service.EnvironmentConfigService;

import java.util.Map;

/**
 * 在启动Spring之前,可以添加环境变量
 */
public class GatewayEnvironmentConfigService implements EnvironmentConfigService {
    // 排除gateway配置的:LoadBalancerClient
    private String excludeGatewayKey = "spring.autoconfigure.exclude";
    private String excludeGatewayValue = "org.springframework.cloud.gateway.config.GatewayLoadBalancerClientAutoConfiguration";

    @Override
    public void config(Map<String, Object> map) {
        map.put(excludeGatewayKey, excludeGatewayValue);
    }
}
