package help.lixin.framework.event;

import org.springframework.context.ApplicationEvent;

/**
 * 开始向服务注册中心进行注册.
 */
public class RegisterServiceStartEvent extends ApplicationEvent {
    public RegisterServiceStartEvent(Object source) {
        super(source);
    }
}
