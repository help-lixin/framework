package help.lixin.framework.module.event;

import org.springframework.context.ApplicationEvent;

public class ModuleWatchEvent extends ApplicationEvent {
    public ModuleWatchEvent(Object source) {
        super(source);
    }
}
