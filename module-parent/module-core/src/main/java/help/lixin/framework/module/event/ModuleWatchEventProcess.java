package help.lixin.framework.module.event;

import help.lixin.framework.module.event.handler.EventHandler;
import help.lixin.framework.module.load.PluginsLoadService;
import help.lixin.framework.module.model.PluginInfo;
import help.lixin.framework.module.model.PluginType;
import help.lixin.framework.module.service.Module;
import help.lixin.framework.module.service.ModuleManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;

import java.io.File;
import java.nio.file.StandardWatchEventKinds;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ModuleWatchEventProcess implements ApplicationListener<ModuleWatchEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModuleWatchEventProcess.class);

    private List<EventHandler> handlers = new ArrayList<>(0);

    private ModuleManager moduleManager;

    public void addHandlers(List<EventHandler> handlers) {
        if (null != handlers) {
            this.handlers.addAll(handlers);
        }
    }

    public List<EventHandler> getHandlers() {
        return handlers;
    }

    public void setModuleManager(ModuleManager moduleManager) {
        this.moduleManager = moduleManager;
    }

    @Override
    public void onApplicationEvent(ModuleWatchEvent event) {
        EventSource eventSource = (EventSource) event.getSource();
        // 插件所在目录(/Users/lixin/FwRepository/framework/plugin-parent/plugin-example/plugins)
        String pluginDirPath = eventSource.getPath();
        // 事件名称(ENTRY_CREATE/ENTRY_MODIFY/ENTRY_DELETE)
        String eventName = eventSource.getName();
        // 模块目录名称(hello-service)
        String module = eventSource.getSource();

        // 模块详细路径
        String pluginItem = pluginDirPath + File.separator + module;
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("prepare load plugin:[{}]", pluginItem);
        }

        // 插件管理服务
        PluginsLoadService pluginsLoadService = new PluginsLoadService(pluginDirPath);
        PluginInfo pluginInfo = pluginsLoadService.loadPluginInfo(pluginItem);
        // 公共模块的处理
        if (null != pluginInfo && pluginInfo.getPluginType().equals(PluginType.COMMON)) {
            // 缺载所有的:业务插件和公共模块
            // 注意:filter为COMMON
            handlers.stream()
                    // 注意此处:这里是根据事件解析后的类型来过滤(COMMON/PLUGIN)
                    .filter(handler -> handler.support(pluginInfo.getPluginType().name()))
                    .findFirst()
                    .ifPresent(handler -> handler.handler(pluginInfo));
            return;
        }

        // 业务模块处理
        // 在删除的情况下,目录已经不存在了
        // 1. 删除事件(缷载)
        if (null == pluginInfo && StandardWatchEventKinds.ENTRY_DELETE.name().equalsIgnoreCase(eventName)) {
            // 通过路径比较
            Optional<Module> delModuleInfo = moduleManager.getModules()
                    .stream()
                    .filter(tmodule -> tmodule.getModuleConfig().getPath().equalsIgnoreCase(pluginItem)).findFirst();

            delModuleInfo.ifPresent(info -> {
                // 构建PluginInfo信息
                PluginInfo delPluginInfo = new PluginInfo();
                delPluginInfo.setModule(info.getName());
                delPluginInfo.setVersion(info.getVersion());

                handlers.stream()
                        // 注意此处:这里是根据所产生的事件来过滤(create/modify/delete)
                        .filter(handler -> handler.support(eventName))
                        .findFirst()
                        .ifPresent(handler -> handler.handler(delPluginInfo));
            });
            return;
        }

        if (null != pluginInfo) {
            // 业务模块的处理:
            // 2. 创建事件:加载
            // 3. 修改事件(先缷载,后加载)
            handlers.stream()
                    // 注意此处:这里是根据所产生的事件来过滤(create/modify/delete)
                    .filter(handler -> handler.support(eventName))
                    .findFirst()
                    .ifPresent(handler -> handler.handler(pluginInfo));

        }
    }
}
