package help.lixin.framework.module;

import com.sun.nio.file.SensitivityWatchEventModifier;
import help.lixin.framework.module.event.EventSource;
import help.lixin.framework.module.event.ModuleWatchEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.SmartLifecycle;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 模块目录监听控制器
 */
public class ModulesWatchController implements SmartLifecycle, ApplicationContextAware, DisposableBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModulesWatchController.class);

    private ApplicationContext applicationContext;

    // 插件所在的目录
    private String pluginsDir;

    // 拉取间隔
    private long pollInterval = 60;

    private AtomicBoolean isRunning = new AtomicBoolean(false);

    private volatile boolean isWatch = false;

    public void setPollInterval(long pollInterval) {
        if (pollInterval < 0) {
            this.pollInterval = 60;
        }
        this.pollInterval = pollInterval;
    }

    public long getPollInterval() {
        return pollInterval;
    }

    public void setPluginsDir(String pluginsDir) {
        this.pluginsDir = pluginsDir;
    }

    public String getPluginsDir() {
        return pluginsDir;
    }


    public Runnable watchPlugins() {
        return () -> {
            try {
                WatchService watcher = FileSystems.getDefault().newWatchService();
                Paths.get(pluginsDir).register(watcher,
                        new WatchEvent.Kind[]{StandardWatchEventKinds.ENTRY_CREATE,
                                StandardWatchEventKinds.ENTRY_MODIFY,
                                StandardWatchEventKinds.ENTRY_DELETE},
                        SensitivityWatchEventModifier.HIGH);

                while (!isWatch) {
                    WatchKey watchKey = null;
                    try {
                        watchKey = watcher.poll(pollInterval, TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                        continue;
                    }

                    if (watchKey == null) {
                        continue;
                    }

                    //获取监听事件
                    for (WatchEvent<?> event : watchKey.pollEvents()) {
                        //获取监听事件类型
                        WatchEvent.Kind<?> kind = event.kind();
                        // 子目录名称
                        Object context = event.context();
                        // 事件名称
                        String eventName = kind.name();
                        // 创建事件源
                        EventSource eventSource = new EventSource(pluginsDir, eventName, context.toString(), System.currentTimeMillis());
                        // 创建事件
                        ModuleWatchEvent moduleWatchEvent = new ModuleWatchEvent(eventSource);
                        // 发布事件
                        applicationContext.publishEvent(moduleWatchEvent);
                    }
                    //处理监听key后(即处理监听事件后)，监听key需要复位，便于下次监听
                    watchKey.reset();
                }
            } catch (IOException e) {
                LOGGER.error("watch plugin:[{}] fail:[{}]", pluginsDir, e);
            }
        };
    }

    @Override
    public void start() {
        if (isRunning.compareAndSet(false, true)) {
            Runnable runnable = watchPlugins();
            Thread t = new Thread(runnable);
            t.setName("watch-plugin-dir-thread");
//            t.setDaemon(true);
            t.start();
        }
    }

    @Override
    public void stop() {
    }

    @Override
    public boolean isRunning() {
        return isRunning.get();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void destroy() throws Exception {
        isWatch = false;
    }
}
