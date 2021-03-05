package help.lixin.framework.code.generator.core.util;

import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.PluginConfiguration;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Properties;

public abstract class ParseContext {

    // 通过反射获得当前插件的参数
    public static Properties parse(Context context, Class clazz) {
        Properties properties = new Properties();

        try {
            Field pluginConfigurations = Context.class.getDeclaredField("pluginConfigurations");
            pluginConfigurations.setAccessible(true);
            List<PluginConfiguration> values = (List<PluginConfiguration>) pluginConfigurations.get(context);
            for (PluginConfiguration pluginConfiguration : values) {
                if (clazz.getName().equalsIgnoreCase(pluginConfiguration.getConfigurationType())) {
                    properties.putAll(pluginConfiguration.getProperties());
                }
            }
        } catch (NoSuchFieldException e1) {
        } catch (IllegalAccessException e2) {
        }
        return properties;
    }
}
