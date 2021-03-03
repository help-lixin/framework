package help.lixin.framework.env.service.impl;

import help.lixin.framework.environment.service.EnvironmentConfigService;

import java.util.Map;

public class EnvironmentConfigServiceImpl implements EnvironmentConfigService {

    @Override
    public void config(Map<String, Object> properties) {
        properties.put("hello", "world");
    }
}
