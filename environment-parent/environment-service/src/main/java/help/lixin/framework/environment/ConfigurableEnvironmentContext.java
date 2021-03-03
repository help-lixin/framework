package help.lixin.framework.environment;

import org.springframework.core.env.ConfigurableEnvironment;

public class ConfigurableEnvironmentContext {
    private ConfigurableEnvironment configurableEnvironment;


    static class Holder {
        private static final ConfigurableEnvironmentContext instance = new ConfigurableEnvironmentContext();
    }

    public static ConfigurableEnvironmentContext getInstance() {
        return Holder.instance;
    }

    void setConfigurableEnvironment(ConfigurableEnvironment configurableEnvironment) {
        this.configurableEnvironment = configurableEnvironment;
    }

    public ConfigurableEnvironment getConfigurableEnvironment() {
        return configurableEnvironment;
    }
}
