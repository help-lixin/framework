package help.lixin.framework.message.source.event;

import com.sun.nio.file.SensitivityWatchEventModifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.Lifecycle;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 监听目录变化
 */
public class MessageSourceDirWatch implements ApplicationContextAware, Lifecycle {
    private Logger logger = LoggerFactory.getLogger(MessageSourceDirWatch.class);

    private ApplicationContext applicationContext;
    private String dir;
    private AtomicBoolean isRunning = new AtomicBoolean(false);

    public MessageSourceDirWatch(String watchDir) {
        this.dir = watchDir;
        //check dir
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void start() {
        if (!isRunning.get()) {
            try {
                final WatchService watcher = FileSystems.getDefault().newWatchService();
                Paths.get(dir).register(watcher,
                        new WatchEvent.Kind[]{StandardWatchEventKinds.ENTRY_CREATE,
                                StandardWatchEventKinds.ENTRY_MODIFY,
                                StandardWatchEventKinds.ENTRY_DELETE
                        },
                        SensitivityWatchEventModifier.HIGH);
                Thread watchThread = new Thread(() -> {
                    while (true) {
                        try {
                            WatchKey key = watcher.poll(60, TimeUnit.SECONDS);
                            if (key == null) {
                                continue;
                            }
                            for (WatchEvent<?> event : key.pollEvents()) {
                                WatchEvent.Kind kind = event.kind();
                                //异常事件跳过
                                if (kind == StandardWatchEventKinds.OVERFLOW) {
                                    continue;
                                }

                                if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                                    Path path = (Path) event.context();
                                    // 构造完整路径
                                    Path fullPath = Paths.get(dir, path.toString());
                                    // 发布事件
                                    logger.info("publish event [{}]", fullPath);
                                    applicationContext.publishEvent(new MessageSourceChangeEvent(fullPath));
                                }
                            }
                            key.reset();
                        } catch (Exception ignore) {
                            logger.warn("execute watch message source exception:[{}]", ignore);
                        }
                    }
                }, "watch-message-source dir:" + dir + " thread");
                watchThread.start();

                isRunning.set(true);
            } catch (IOException e) {
                logger.error("watch dir error:[{}]", e);
            }
        }
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return isRunning.get();
    }
}
