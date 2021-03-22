package help.lixin.framework.module;

import help.lixin.framework.module.service.Module;
import help.lixin.framework.module.service.ModuleConfig;
import help.lixin.framework.module.service.ModuleLoader;
import help.lixin.framework.module.service.ModuleManager;
import help.lixin.framework.module.service.impl.CommonModuleLoader;
import help.lixin.framework.module.load.PluginsLoadService;
import help.lixin.framework.module.model.PluginInfo;
import help.lixin.framework.module.model.PluginType;
import help.lixin.framework.module.util.TransformToModuleConfigUtil;
import org.springframework.context.SmartLifecycle;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * 模块初始化管理
 */
public class ModuleInitController implements SmartLifecycle {

    // 插件所在的目录
    private String pluginsDir;

    // 模块加载服务
    private ModuleLoader moduleLoader;

    // 模块元数据管理服务
    private ModuleManager moduleManager;

    public void setPluginsDir(String pluginsDir) {
        this.pluginsDir = pluginsDir;
    }

    public String getPluginsDir() {
        return pluginsDir;
    }

    public void setModuleLoader(ModuleLoader moduleLoader) {
        this.moduleLoader = moduleLoader;
    }

    public void setModuleManager(ModuleManager moduleManager) {
        this.moduleManager = moduleManager;
    }

    private AtomicBoolean isRunning = new AtomicBoolean(false);

    public void setIsRunning(boolean isRunning) {
        this.isRunning.set(isRunning);
    }

    public boolean getIsRunning() {
        return isRunning.get();
    }

    @Override
    public void start() {
        if (isRunning.compareAndSet(false, true)) {
            PluginsLoadService pluginsLoadService = new PluginsLoadService(pluginsDir);
            pluginsLoadService.loadPlugins();

            List<PluginInfo> plugins = pluginsLoadService.getPlugins();
            // 公共模块
            Optional<PluginInfo> commonModule = plugins.stream().filter(plugin -> plugin.getPluginType().equals(PluginType.COMMON)).filter(plugin->plugin.getEnabled()).findFirst();
            // 业务模块
            List<PluginInfo> businessMoundles = plugins.stream().filter(plugin -> plugin.getPluginType().equals(PluginType.PLUGIN)).filter(plugin->plugin.getEnabled()).collect(Collectors.toList());

            // 1. 加载公共模块
            loadCommonModule(commonModule);
            // 2. 加载业务模块
            loadBusinessModules(businessMoundles);
        }
    }

    // 加载公共模块
    public void loadCommonModule(Optional<PluginInfo> commonModule) {
        commonModule.ifPresent(pluginInfo -> {
            // 1.转换PluginInfo到ModuleConfig
            ModuleConfig moduleConfig = TransformToModuleConfigUtil.transformToModuleConfig(pluginInfo);
            // 2. 加载公共模块Jar包
            CommonModuleLoader.getInstance().initCommonModuleClassLoader(moduleConfig);
        });
    }

    // 加载业务模块
    public void loadBusinessModules(List<PluginInfo> businessMoundles) {
        businessMoundles
                .stream()
                .map(businessMoundle -> TransformToModuleConfigUtil.transformToModuleConfig(businessMoundle))
                .peek(moduleConfig -> {
                    // 1. 加载业务模块
                    Module module = moduleLoader.load(moduleConfig);
                    // 2. 注册模块信息
                    moduleManager.register(module);
                }).collect(Collectors.toList());
    }


    @Override
    public void stop() {
    }

    @Override
    public boolean isRunning() {
        return isRunning.get();
    }
}
