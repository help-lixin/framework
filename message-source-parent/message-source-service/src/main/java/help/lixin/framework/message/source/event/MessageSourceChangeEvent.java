package help.lixin.framework.message.source.event;

import org.springframework.context.ApplicationEvent;

public class MessageSourceChangeEvent extends ApplicationEvent {

    public MessageSourceChangeEvent(Object source) {
        super(source);
    }
}
