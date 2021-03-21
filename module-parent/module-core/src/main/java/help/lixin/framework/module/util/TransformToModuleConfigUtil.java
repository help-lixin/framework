package help.lixin.framework.module.util;

import help.lixin.framework.module.model.PluginInfo;
import help.lixin.framework.module.service.ModuleConfig;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public abstract class TransformToModuleConfigUtil {

    // 把PluginInfo转换成:ModuleConfig
    public static ModuleConfig transformToModuleConfig(PluginInfo pluginInfo) {
        ModuleConfig moduleConfig = new ModuleConfig();

        List<URL> moduleUrl = pluginInfo.getBundles().stream().map(bundle -> {
            try {
                return new URL("file://" + bundle);
            } catch (MalformedURLException e) {
                // TODO lixin
            }
            return null;
        }).filter(url -> null != url)
                .collect(Collectors.toList());

        moduleConfig.setPath(pluginInfo.getPath());
        moduleConfig.setName(pluginInfo.getModule());
        moduleConfig.setVersion(pluginInfo.getVersion());
        moduleConfig.setEnabled(pluginInfo.getEnabled());
        moduleConfig.setMainClass(pluginInfo.getPluginClass());
        if (null != pluginInfo.getPropertiesFile()) {
            moduleConfig.setPropertiesFile(pluginInfo.getPath() + File.separator + pluginInfo.getPropertiesFile());
        }
        moduleConfig.setDesc(pluginInfo.getDesc());
        moduleConfig.setModuleUrl(moduleUrl);
        return moduleConfig;
    }
}
