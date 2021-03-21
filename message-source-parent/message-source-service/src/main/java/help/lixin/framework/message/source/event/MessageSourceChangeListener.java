package help.lixin.framework.message.source.event;

import org.springframework.context.ApplicationListener;

public class MessageSourceChangeListener implements ApplicationListener<MessageSourceChangeEvent> {
    @Override
    public void onApplicationEvent(MessageSourceChangeEvent event) {
        // TODO
        // 刷新配置文件内容
    }
}
