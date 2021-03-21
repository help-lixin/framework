package help.lixin.framework.module.load;

import help.lixin.framework.module.PluginDesc;
import help.lixin.framework.module.model.PluginInfo;
import help.lixin.framework.module.model.PluginType;
import help.lixin.framework.module.exception.ParseException;
import help.lixin.framework.module.parse.ModuleXMLParse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class PluginsLoadService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PluginsLoadService.class);

    private String pluginsHome;

    private List<PluginInfo> plugins = new ArrayList<>(0);

    public PluginsLoadService(String pluginsHome) {
        this.pluginsHome = pluginsHome;
    }

    public String getPluginsHome() {
        return pluginsHome;
    }

    // 加载所有插件
    public void loadPlugins() {
        File plugins = new File(getPluginsHome());
        if (plugins.exists()) {
            File[] pluginsArray = plugins.listFiles();
            List<PluginInfo> pluginInfos = Arrays.stream(pluginsArray)
                    .map(plugin -> plugin.getPath())
                    .map(this::loadPluginInfo)
                    .filter(item -> null != item)
                    .collect(Collectors.toList());
            this.plugins.addAll(pluginInfos);
        }
    }

    // 加载插件信息
    public PluginInfo loadPluginInfo(String pluginItem) {
        File pluginDir = new File(pluginItem);
        if (pluginDir.exists()) {
            PluginInfo pluginInfo = new PluginInfo();
            pluginInfo.setPath(pluginItem);

            // 文件夹名称
            String name = pluginDir.getName();
            // 所有的jar
            Set<String> bundles = findBundles(pluginDir);
            if (bundles.isEmpty()) {
                return null;
            }

            // 查找plugin.xml文件
            Optional.of(pluginDir.listFiles((dir, filename) -> filename.equalsIgnoreCase("module.xml")))
                    .ifPresent(pluginXmlFiles -> {
                        if (pluginXmlFiles.length >= 1) {
                            File pluginXmlFile = pluginXmlFiles[0];
                            if (pluginXmlFile.exists()) {
                                // 解析XML
                                ModuleXMLParse pluginParse = new ModuleXMLParse(pluginXmlFile.getPath());
                                try {
                                    // 将XML模型信息与业务模型信息进行绑定
                                    PluginDesc pluginDesc = pluginParse.parse();

                                    // 设置插件类型
                                    pluginInfo.setPluginType(PluginType.PLUGIN);
                                    pluginInfo.setModule(pluginDesc.getModule());
                                    pluginInfo.setVersion(pluginDesc.getVersion());
                                    pluginInfo.setPropertiesFile(pluginDesc.getPropertiesFile());
                                    pluginInfo.setEnabled(pluginDesc.getEnabled());
                                    pluginInfo.setPluginClass(pluginDesc.getPluginClass());
                                } catch (ParseException e) {
                                    LOGGER.error("parse xml:[{}] error:[{}]", pluginXmlFile, e);
                                }
                            }
                        }
                    });

            // 如果XML没有解析到信息的情况下,则模块名称就是目录名称
            if (null == pluginInfo.getModule() || pluginInfo.getModule().isEmpty()) {
                pluginInfo.setModule(name);
            }

            // 添加所有的jar
            pluginInfo.getBundles().addAll(bundles);
            return pluginInfo;
        }
        return null;
    }


    public List<PluginInfo> getPlugins() {
        return plugins;
    }

    /**
     * 查找指定目录下所有的内容
     *
     * @param plugin
     * @return
     */
    private Set<String> findBundles(File plugin) {
        Set<String> bundles = new HashSet<>();
        File[] files = plugin.listFiles();
        if (files == null) {
            return bundles;
        }
        for (File f : files) {
            if (!f.isDirectory()) {// 如果不是文件夹
                bundles.add(f.getPath());
            } else {
                bundles.addAll(findBundles(f));// 如果是文件夹进行递归
            }
        }
        return bundles;
    }
}
