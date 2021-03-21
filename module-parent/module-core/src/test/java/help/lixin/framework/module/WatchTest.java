package help.lixin.framework.module;

import com.sun.nio.file.SensitivityWatchEventModifier;

import java.nio.file.*;
import java.util.concurrent.TimeUnit;

public class WatchTest {
    public static void main(String[] args) throws Exception {
        String pluginDir = "/Users/lixin/FwRepository/framework/module-parent/module-example/plugins";
        WatchService watcher = FileSystems.getDefault().newWatchService();
        Paths.get(pluginDir).register(watcher,
                new WatchEvent.Kind[]{StandardWatchEventKinds.ENTRY_CREATE,
                        StandardWatchEventKinds.ENTRY_MODIFY,
                        StandardWatchEventKinds.ENTRY_DELETE},
                SensitivityWatchEventModifier.HIGH);

        while (true) {
            WatchKey watchKey = watcher.poll(60, TimeUnit.SECONDS);

            if (watchKey == null) {
                continue;
            }

            //获取监听事件
            for (WatchEvent<?> event : watchKey.pollEvents()) {
                //获取监听事件类型
                WatchEvent.Kind<?> kind = event.kind();
                // 子目录名称
                Object context = event.context();
                System.out.println(context);
                // 事件名称
                String name = kind.name();
                System.out.println(name);
            }
            //处理监听key后(即处理监听事件后)，监听key需要复位，便于下次监听
            watchKey.reset();
        }
    }
}
